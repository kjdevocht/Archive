package client.resources;

import java.awt.*;
import java.util.*;
import java.util.List;

import client.base.*;
import client.communication.LogEntry;
import client.map.MapState;
import client.model.ClientModel;
import client.model.IPlayer;
import client.model.IResourceList;
import client.model.ResourceList;
import client.model.map.IEdgeValue;
import client.model.message.MessageLine;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;
import serverProxy.ClientCommunicator;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;


/**
 * Implementation for the resource bar controller
 */
public class ResourceBarController extends Controller implements IResourceBarController {

	private Map<ResourceBarElement, IAction> elementActions;
    private boolean firstSettlement;
    private boolean firstRoad;
    private boolean secondSettlement;
    private boolean secondRoad;
	public ResourceBarController(IResourceBarView view) {

		super(view);

        firstSettlement = false;
        firstRoad = false;
        secondSettlement = false;
        secondRoad = false;

		elementActions = new HashMap<ResourceBarElement, IAction>();

        EventObservable.getSingleton().subscribeToEvent(Event.UpdateLocalPlayerResourceList, new IObserver<Object>() {
            @Override
            public void update(Object metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {

                        IResourceList list = ClientModel.getUpdatableModel().getLocalPlayer().getResources();
                        getView().setElementAmount(ResourceBarElement.BRICK, list.getQuantity(ResourceType.BRICK));
                        getView().setElementAmount(ResourceBarElement.SHEEP, list.getQuantity(ResourceType.SHEEP));
                        getView().setElementAmount(ResourceBarElement.ORE, list.getQuantity(ResourceType.ORE));
                        getView().setElementAmount(ResourceBarElement.WHEAT, list.getQuantity(ResourceType.WHEAT));
                        getView().setElementAmount(ResourceBarElement.WOOD, list.getQuantity(ResourceType.WOOD));
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdatePlayerRoads, new IObserver<Integer>() {
            @Override
            public void update(Integer metadata) {
                final int index = metadata;
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
                        if(playerIndex == index) {
                            getView().setElementAmount(ResourceBarElement.ROAD, ClientModel.getUpdatableModel().getLocalPlayer().getRoads());
                        }
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdatePlayerCities, new IObserver<Integer>() {
            @Override
            public void update(Integer metadata) {
                final int index = metadata;
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
                        if(playerIndex == index) {
                            getView().setElementAmount(ResourceBarElement.CITY, ClientModel.getUpdatableModel().getLocalPlayer().getCities());
                        }
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdatePlayerSettlements, new IObserver<Integer>() {
            @Override
            public void update(Integer metadata) {
                final int index = metadata;
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
                        if(playerIndex == index) {
                            getView().setElementAmount(ResourceBarElement.SETTLEMENT, ClientModel.getUpdatableModel().getLocalPlayer().getSettlements());
                        }
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdatePlayerSoldiers, new IObserver<Integer>() {
            @Override
            public void update(Integer metadata) {
                final int index = metadata;
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
                        if(playerIndex == index) {
                            getView().setElementAmount(ResourceBarElement.SOLDIERS, ClientModel.getUpdatableModel().getLocalPlayer().getSoldiers());
                        }
                    }
                });
            }
        });

        IObserver observer = new IObserver() {
            @Override
            public void update(Object metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        stateMayHaveChanged();
                    }
                });
            }
        };
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateCurrentTurn, observer);
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateStatus, observer);
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateMapAddRoad, observer);
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateMapAddSettlement, observer);
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateAddBlankPlayer, observer);

	}
    private void stateMayHaveChanged() {
        IPlayer localplayer = ClientModel.getUpdatableModel().getLocalPlayer();
        String state = ClientModel.getUpdatableModel().getTurnTracker().getStatus();

        if(ClientModel.getUpdatableModel().isLocalPlayersTurn() && ClientModel.getUpdatableModel().getPlayers().size() == 4) {
            if(state.equals("FirstRound")) {
                if(localplayer.getSettlements() == 5) {
//                    System.out.println("building first settlement");
                    if(!firstSettlement)
                        buildSettlement();
                    firstSettlement = true;
                }
                else if(localplayer.getRoads() == 15) {
//                    System.out.println("building first road");
                    if(!firstRoad)
                        buildRoad();
                    firstRoad = true;
                }
            }
            else if(state.equals("SecondRound")) {
                if(localplayer.getSettlements() == 4) {
//                    System.out.println("building second settlemnt");
                    if(!secondSettlement)
                        buildSettlement();
                    secondSettlement = true;
                }
                else if(localplayer.getRoads() == 14) {
//                    System.out.println("building second road");
                    if(!secondRoad)
                        buildRoad();
                    secondRoad = true;
                }
            }
        }
    }
	@Override
	public IResourceBarView getView() {
		return (IResourceBarView)super.getView();
	}

	/**
	 * Sets the action to be executed when the specified resource bar element is clicked by the user
	 * 
	 * @param element The resource bar element with which the action is associated
	 * @param action The action to be executed
	 */
	public void setElementAction(ResourceBarElement element, IAction action) {

		elementActions.put(element, action);
	}

	@Override
	public void buildRoad() {
		executeElementAction(ResourceBarElement.ROAD);
	}

	@Override
	public void buildSettlement() {
		executeElementAction(ResourceBarElement.SETTLEMENT);
	}

	@Override
	public void buildCity() {
		executeElementAction(ResourceBarElement.CITY);
	}

	@Override
	public void buyCard() {
		executeElementAction(ResourceBarElement.BUY_CARD);
	}

	@Override
	public void playCard() {
		executeElementAction(ResourceBarElement.PLAY_CARD);
	}
	
	private void executeElementAction(ResourceBarElement element) {
		
		if (elementActions.containsKey(element)) {
			
			IAction action = elementActions.get(element);
			action.execute();
		}
	}

}

