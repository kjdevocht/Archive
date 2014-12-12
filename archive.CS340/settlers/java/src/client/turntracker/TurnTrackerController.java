package client.turntracker;

import client.map.MapController;
import client.map.MapState;
import client.model.ClientModel;
import client.model.IBonusTracker;
import client.model.IPlayer;
import client.model.ITurnTracker;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;
import client.resources.ResourceBarElement;
//import com.sun.deploy.util.SessionState;
import serverProxy.ClientCommunicator;
import shared.definitions.CatanColor;
import client.base.*;
import shared.definitions.PieceType;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;


/**
 * Implementation for the turn tracker controller
 */
public class TurnTrackerController extends Controller implements ITurnTrackerController {
    private Set<Integer> initedPlayers;
    private String subState;

	public TurnTrackerController(ITurnTrackerView view) {

		super(view);
        subState = "settlement";
        initedPlayers =  new HashSet<>();




        IObserver observer = new IObserver() {
            @Override
            public void update(Object metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        updateTurnTracker();
                    }
                });
            }
        };
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateCurrentTurn, observer);
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateStatus, observer);
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateAddBlankPlayer, observer);
        EventObservable.getSingleton().subscribeToEvent(Event.UpdatePlayerColor, observer);
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateMapAddSettlement, observer);
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateMapAddRoad, observer);

		initFromModel();
	}
	private void updateTurnTracker() {

        String state = ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase();

        int turn = ClientModel.getUpdatableModel().getTurnTracker().getCurrentTurn();
        IBonusTracker bonusTracker = ClientModel.getUpdatableModel().getBonusTracker();

        IPlayer localplayer = ClientModel.getUpdatableModel().getLocalPlayer();

        for(IPlayer player : ClientModel.getUpdatableModel().getPlayers()) {

            int points = player.getVictoryPoints();
            boolean highlight = turn == player.getPlayerIndex();
            boolean largestArmy = player.getPlayerIndex() ==  bonusTracker.getLargestArmy();
            boolean longestRoad = player.getPlayerIndex() == bonusTracker.getLongestRoad();
            if(!initedPlayers.contains(player.getPlayerIndex())) {
                getView().initializePlayer(player.getPlayerIndex(), player.getName(), player.getColor());

                initedPlayers.add(player.getPlayerIndex());
                if(player.getPlayerIndex() == localplayer.getPlayerIndex()) {
                    getView().setLocalPlayerColor(player.getColor());
                }
            }

            getView().updatePlayer(player.getPlayerIndex(), points, highlight, largestArmy, longestRoad);
        }
        if(turn == ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex()) {
//            System.out.println("\u001B[33m[STATE TT]\t\t" + state + "\u001B[0m");
            if(state.equals("firstround")) {
                if(localplayer.getSettlements() == 5) {
                    getView().updateGameState("Place Settlement", false);
                }
                else if(localplayer.getRoads() == 15) {
                    getView().updateGameState("Place Road", false);
                }
                else {
                	getView().updateGameState("Finish Turn", true);
                	try {
						ClientCommunicator.getClientCommunicator().endTurn(ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex());
					} catch (Exception e) {
					}
                    
                }
            }
            else if(state.equals("secondround")) {
                if(localplayer.getSettlements() == 4) {
                    getView().updateGameState("Place Settlement", false);
                }
                else if(localplayer.getRoads() == 14) {
                    getView().updateGameState("Place Road", false);
                }
                else {
                	getView().updateGameState("Finish Turn", true);
                	try {
						ClientCommunicator.getClientCommunicator().endTurn(ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex());
					} catch (Exception e) {
					}
                    
                }
            }
            else if(state.equals("rolling")) {
                getView().updateGameState("You Are Rolling", false);
            }
            else if(state.equals("playing")) {
                getView().updateGameState("Finish Turn", true);
            }
            else if(state.equals("discarding")) {
                getView().updateGameState("Players Are Discarding", false);
            }
            else if(state.equals("robbing")) {
                getView().updateGameState("Robbing!!!", false);
            }
        }
        else {
            getView().updateGameState("Waiting On " + ClientModel.getUpdatableModel().getPlayers().get(turn).getName(), false);
        }
    }
	@Override
	public ITurnTrackerView getView() {
		
		return (ITurnTrackerView)super.getView();
	}

	@Override
	public void endTurn() {
        int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
        ClientModel model = null;
        try {
            model = ClientCommunicator.getClientCommunicator().endTurn(playerIndex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(model != null) {
            ClientModel.setUpdatableModel(model);
        }
	}
	
	private void initFromModel() {
		//<temp>
//		getView().setLocalPlayerColor(CatanColor.RED);

		//</temp>
	}

}

