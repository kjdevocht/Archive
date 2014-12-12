package client.roll;

import client.base.*;
import client.model.ClientModel;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;
import serverProxy.ClientCommunicator;

import java.awt.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


/**
 * Implementation for the roll controller
 */
public class RollController extends Controller implements IRollController {

    private IRollResultView resultView;

    /**
     * RollController constructor
     *
     * @param view Roll view
     * @param resultView Roll result view
     */
    public RollController(final IRollView view, IRollResultView resultView) {

        super(view);

        setResultView(resultView);
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateStatus, new IObserver<Object>() {
            @Override
            public void update(Object metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        String state = ClientModel.getUpdatableModel().getTurnTracker().getStatus();
                        if (state.toLowerCase().equals("rolling") && ClientModel.getUpdatableModel().isLocalPlayersTurn()) {
                            getRollView().showModal();
                            final Timer time = new Timer();
                            
                            TimerTask task = new TimerTask() {
                                int counter = 5;
                                @Override
                                public void run() {
                                    String state = ClientModel.getUpdatableModel().getTurnTracker().getStatus();
                                    if(counter > 0){
                                        view.setMessage("Rolling automatically in " + "... "+ counter +" seconds");
                                        counter--;
                                    }
                                    else{
                                        if (state.toLowerCase().equals("rolling") && ClientModel.getUpdatableModel().isLocalPlayersTurn()) {
                                            EventQueue.invokeLater(new Runnable() {
                                               @Override
                                               public void run() {
                                                   rollDice();
                                               }
                                           });

                                        } else {
                                            time.cancel();
                                            time.purge();
                                        }
                                    }
                                }
                            };
                            time.schedule(task, 0, 1000);
                        }
                        else {
                            if(getRollView().isModalShowing()) {
                                getRollView().closeModal();
                            }
                        }
                    }
                });
            }
        });

    }

    public IRollResultView getResultView() {
        return resultView;
    }
    public void setResultView(IRollResultView resultView) {
        this.resultView = resultView;
    }

    public IRollView getRollView() {
        return (IRollView)getView();
    }

    @Override
    public void rollDice() {
    	if(!ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase().equals("rolling"))
    		return;
        ClientModel model = null;
        int diceRoll = -1;
        try {

            diceRoll = getRollView().getRollInput();

            System.out.println("Manual Dice: "+diceRoll);

            if(diceRoll == -1)
                diceRoll = getDiceRoll();

            

            int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
            model = ClientCommunicator.getClientCommunicator().rollDice(playerIndex, diceRoll);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (model != null) {
//            ClientModel.setUpdatableModel(model);
            getResultView().setRollValue(diceRoll);
            getResultView().showModal();
        }
    }

    public int getDiceRoll() throws Exception {
        int min = 1;
        int max = 6;
        int dice1 = -1;
        int dice2 = -1;
        Random dice = new Random();
        dice1 = dice.nextInt((max - min) + 1) + min;
        dice2 = dice.nextInt((max - min) + 1) + min;
        if(diceRollValid(dice1, dice2)) {
            return dice1 + dice2;
        } else {
            throw new Exception("Bad Dice Roll" + (dice1 + dice2));
        }
    }

    private boolean diceRollValid(int d1, int d2) {
        if(d1 > 0 && d1 < 7 && d2 > 0 && d2 < 7) {
            if(d1 + d2 > 1 && d1 + d2 < 13)
                return true;
        }
        return false;
    }

}

