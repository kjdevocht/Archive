package client.discard;

import client.model.IPlayer;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;
import client.resources.ResourceBarElement;
import serverProxy.ClientCommunicator;
import shared.definitions.*;
import client.base.*;
import client.misc.*;
import client.model.ClientModel;
import client.model.IResourceList;
import client.model.ResourceList;

import java.awt.*;


/**
 * Discard controller implementation
 */
public class DiscardController extends Controller implements IDiscardController {

    private IWaitView waitView;
    private ResourceList discardList;
    private int total= 0;
    private int required = 0;
    /**
     * DiscardController constructor
     *
     * @param view View displayed to let the user select cards to discard
     * @param waitView View displayed to notify the user that they are waiting for other players to discard
     */
    public DiscardController(IDiscardView view, IWaitView waitView) {

        super(view);
        discardList = new ResourceList();

        this.waitView = waitView;

        EventObservable.getSingleton().subscribeToEvent(Event.UpdateStatus, new IObserver<Object>() {
            @Override
            public void update(Object metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        IPlayer localplayer = ClientModel.getUpdatableModel().getLocalPlayer();
                        String status = ClientModel.getUpdatableModel().getTurnTracker().getStatus();
                        if(status.toLowerCase().equals("discarding")) {
//                            System.out.println(status);
                            if(!localplayer.isDiscarded() && localplayer.getResources().getTotalResourceCount() > 7) {

                                startDiscard();



                                return;
                            }
                        }
                        if(getDiscardView().isModalShowing()) {
                            getDiscardView().closeModal();
                        }
                    }
                });
            }
        });
    }
    private void startDiscard() {
        IPlayer localplayer = ClientModel.getUpdatableModel().getLocalPlayer();
        discardList = new ResourceList();
        required = localplayer.getResources().getTotalResourceCount()/2;
        total = 0;
        IResourceList playerResources = localplayer.getResources();

        for(ResourceType type: ResourceType.values()) {
            getDiscardView().setResourceDiscardAmount(type, 0);
        }

        setEnabled();

        getDiscardView().showModal();
    }
    public IDiscardView getDiscardView() {
        return (IDiscardView)super.getView();
    }

    public IWaitView getWaitView() {
        return waitView;
    }

    private void setEnabled() {
        IPlayer localplayer = ClientModel.getUpdatableModel().getLocalPlayer();
        IResourceList playerResources = localplayer.getResources();
        for(ResourceType type : ResourceType.values()) {
            getDiscardView().setResourceMaxAmount(type, playerResources.getQuantity(type));

            boolean canDecrease = false;
            boolean canIncrease = false;

            if(playerResources.getQuantity(type) == 0) {

            }
            else if(discardList.getQuantity(type) == playerResources.getQuantity(type)) {
                canDecrease = true;

            }
            else if(discardList.getQuantity(type) ==0){
                canIncrease = true;
            }
            else {
                canIncrease = true;
                canDecrease = true;
            }

            if(required == total) {
                canIncrease = false;
                getDiscardView().setStateMessage("Discard");
                getDiscardView().setDiscardButtonEnabled(true);
            }
            else
            {
                getDiscardView().setStateMessage(""+total+"/"+required);
                getDiscardView().setDiscardButtonEnabled(false);
            }

            getDiscardView().setResourceAmountChangeEnabled(type, canIncrease, canDecrease);




        }


    }

    @Override
    public void increaseAmount(ResourceType resource) {
        int amt = discardList.getQuantity(resource);
        amt++;
        total ++;
        discardList.setQuantity(resource, amt);
        getDiscardView().setResourceDiscardAmount(resource, amt);
        setEnabled();
    }

    @Override
    public void decreaseAmount(ResourceType resource) {
        int amt = discardList.getQuantity(resource);
        amt--;
        total --;
        discardList.setQuantity(resource, amt);
        getDiscardView().setResourceDiscardAmount(resource, amt);
        setEnabled();
    }

    @Override
    public void discard() {
//        System.out.println("Discarding!!!!");
        if(ClientModel.getUpdatableModel().canDiscardCards(discardList)) {
            int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
            ClientModel model = null;
            try {
                model = ClientCommunicator.getClientCommunicator().discardCards(discardList, playerIndex);
            } catch (Exception e) {
                e.printStackTrace();
            }
            if(model != null) {
                getDiscardView().closeModal();
            }
        }

    }

}

