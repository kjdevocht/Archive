package client.map;

import client.base.Controller;
import client.data.RobPlayerInfo;
import client.model.ClientModel;
import client.model.IPlayer;
import client.model.map.ICommunity;
import client.model.map.IEdgeValue;
import client.model.map.IHex;
import client.model.map.IPort;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;
import shared.definitions.CatanColor;
import shared.definitions.HexType;
import shared.definitions.PieceType;
import shared.definitions.PortType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.awt.*;


/**
 * Implementation for the map controller
 */
public class MapController extends Controller implements IMapController {
	
	private IRobView robView;
    private boolean first;
    private IMapView view;
    private boolean gameOver;

	public MapController(IMapView view, IRobView robView) {
		
		super(view);
		first = true;
        this.view = view;
		setRobView(robView);
        gameOver = false;

		EventObservable.getSingleton().subscribeToEvent(Event.UpdateMapAddHex, new IObserver<IHex>() {
			@Override
			public void update(final IHex metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        addHex(metadata);
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateMapAddRoad, new IObserver<IEdgeValue>() {
            @Override
            public void update(final IEdgeValue metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    	stateMayHaveChanged();
                        addRoad(metadata);
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateMapAddPort, new IObserver<IPort>() {
            @Override
            public void update(final IPort metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        addPort(metadata);
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateMapAddSettlement, new IObserver<ICommunity>() {
            @Override
            public void update(final ICommunity metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    	stateMayHaveChanged();
                        addSettlement(metadata);
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateMapAddCity, new IObserver<ICommunity>() {
            @Override
            public void update(final ICommunity metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        addCity(metadata);
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateMapRobber, new IObserver<Object>() {
            @Override
            public void update(Object metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        addRobber(ClientModel.getUpdatableModel().getCatanMap().getRobber());
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateStatus, new IObserver<Object>() {
            @Override
            public void update(Object metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        stateMayHaveChanged();
                        if(ClientModel.getUpdatableModel().isLocalPlayersTurn()) {
                            if(ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase().equals("robbing")) {
                                MapState.getState().startMove(PieceType.ROBBER, true, true, getView(), getRobView());
                            }
                        }
                    }
                });
            }
        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdateCurrentTurn, new IObserver<Object>() {
            @Override
            public void update(Object metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        stateMayHaveChanged();
                    }
                });
            }
        });
//        EventObservable.getSingleton().subscribeToEvent(Event.UpdateWinner, new IObserver<Object>() {
//            @Override
//            public void update(Object metadata) {
//                EventQueue.invokeLater(new Runnable() {
//                    @Override
//                    public void run() {
//                        gameOver = true;
//                        MapState.getState().updateState("GameOver");
//                    }
//                });
//            }
//        });
        EventObservable.getSingleton().subscribeToEvent(Event.UpdatePlayerColor, new IObserver<Integer>() {
            @Override
            public void update(Integer metadata) {
                EventQueue.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                    	for(IEdgeValue road: ClientModel.getUpdatableModel().getCatanMap().getRoads()) {
                    		addRoad(road);
                    	}
                        for(ICommunity settlement: ClientModel.getUpdatableModel().getCatanMap().getSettlements()) {
                        	addSettlement(settlement);;
                        }
                        for(ICommunity city: ClientModel.getUpdatableModel().getCatanMap().getCities()) {
                        	addCity(city);;
                        }
                        for(IPort port: ClientModel.getUpdatableModel().getCatanMap().getPorts()) {
                        	addPort(port);
                        }
                    }
                });
            }
        });


	}
    private void stateMayHaveChanged() {
//        if(gameOver) {
//            return;
//        }
        IPlayer localplayer = ClientModel.getUpdatableModel().getLocalPlayer();
        String state = ClientModel.getUpdatableModel().getTurnTracker().getStatus();
//        System.out.println("STATE MAY HAVE CHANGED: " + state);

        if(ClientModel.getUpdatableModel().isLocalPlayersTurn()) {
//            System.out.println("LOCAL PLAYER TURN");
            if(state.equals("FirstRound")) {
                if(localplayer.getSettlements() == 5) {
//                    getView().updateGameState("Place Settlement", false);
                    MapState.getState().updateState("FirstRound");
//                    this.startMove(PieceType.SETTLEMENT, true, true, );
                }
                else if(localplayer.getRoads() == 15) {
//                    getView().updateGameState("Place Road", false);
                    MapState.getState().updateState("FirstRound");
                }
                else {
//                    try {
//                        ClientCommunicator.getClientCommunicator().endTurn(localplayer.getPlayerIndex());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }
            else if(state.equals("SecondRound")) {
                if(localplayer.getSettlements() == 4) {
//                    getView().updateGameState("Place Settlement", false);
                    MapState.getState().updateState("SecondRound");
                }
                else if(localplayer.getRoads() == 14) {
//                    getView().updateGameState("Place Road", false);
                    MapState.getState().updateState("SecondRound");
                }
                else {
//                    try {
//                        ClientCommunicator.getClientCommunicator().endTurn(localplayer.getPlayerIndex());
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                }
            }
            else if(state.equals("Rolling")) {
                MapState.getState().updateState("Rolling");
            }
            else if(state.equals("Playing")) {
                MapState.getState().updateState("Playing");
            }
            else if(state.equals("Discarding")) {
                MapState.getState().updateState("Discarding");
            }
            else if(state.equals("Robbing")) {

                MapState.getState().updateState("Robbing");
            }
        }
    }
    private void addHex(IHex hex) {
        if(first) {
            addWater();
            first = false;
        }

        HexType type = HexType.resourceTypeToHexType(hex.getResource());
        getView().addHex(hex.getLocation(), HexType.resourceTypeToHexType(hex.getResource()));
        if(type != HexType.DESERT)
            getView().addNumber(hex.getLocation(), hex.getNumber());
    }

    private void addRoad(IEdgeValue road) {
        
        getView().placeRoad(road.getLocation(), road.getOwner().getColor());
    }

    private void addPort(IPort port) {
        EdgeLocation edgeLocation = new EdgeLocation(port.getLocation(), port.getDirection());
        PortType type = PortType.portToPortType(port);
        getView().addPort(edgeLocation, type);
    }

    private void addSettlement(ICommunity settlement) {
        
        getView().placeSettlement(settlement.getLocation(), settlement.getOwner().getColor());
    }

    private void addCity(ICommunity city) {
        getView().placeCity(city.getLocation(), city.getOwner().getColor());
    }
    private void addRobber(HexLocation hex) {
        getView().placeRobber(hex);
    }
    private void addWater() {
        getView().addHex(new HexLocation(0, -3), HexType.WATER);
        getView().addHex(new HexLocation(1, -3), HexType.WATER);
        getView().addHex(new HexLocation(2, -3), HexType.WATER);
        getView().addHex(new HexLocation(3, -3), HexType.WATER);

        getView().addHex(new HexLocation(3, -2), HexType.WATER);
        getView().addHex(new HexLocation(3, -1), HexType.WATER);
        getView().addHex(new HexLocation(3, 0), HexType.WATER);

        getView().addHex(new HexLocation(2, 1), HexType.WATER);
        getView().addHex(new HexLocation(1, 2), HexType.WATER);
        getView().addHex(new HexLocation(0, 3), HexType.WATER);

        getView().addHex(new HexLocation(-1, 3), HexType.WATER);
        getView().addHex(new HexLocation(-2, 3), HexType.WATER);
        getView().addHex(new HexLocation(-3, 3), HexType.WATER);

        getView().addHex(new HexLocation(-3, 2), HexType.WATER);
        getView().addHex(new HexLocation(-3, 1), HexType.WATER);
        getView().addHex(new HexLocation(-3, 0), HexType.WATER);

        getView().addHex(new HexLocation(-1, -2), HexType.WATER);
        getView().addHex(new HexLocation(-2, -1), HexType.WATER);
    }

	public IMapView getView() {
		return (IMapView)super.getView();
	}

	private IRobView getRobView() {
		return robView;
	}
	private void setRobView(IRobView robView) {
		this.robView = robView;
	}

	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return MapState.getState().canPlaceRoad(edgeLoc);
	}

	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return MapState.getState().canPlaceSettlement(vertLoc);
	}

	public boolean canPlaceCity(VertexLocation vertLoc) {
		return MapState.getState().canPlaceCity(vertLoc);
	}

	public boolean canPlaceRobber(HexLocation hexLoc) {
		return MapState.getState().canPlaceRobber(hexLoc);
	}

	public void placeRoad(EdgeLocation edgeLoc) {
		MapState.getState().placeRoad(edgeLoc);
	}

	public void placeSettlement(VertexLocation vertLoc) {
		MapState.getState().placeSettlement(vertLoc);
	}

	public void placeCity(VertexLocation vertLoc) {
		MapState.getState().placeCity(vertLoc);
	}

	public void placeRobber(HexLocation hexLoc) {
		MapState.getState().placeRobber(hexLoc, view, robView);
	}
	
	public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected) {
        MapState.getState().startMove(pieceType, isFree, allowDisconnected, getView(), getRobView());
	}
	
	public void cancelMove() {
		MapState.getState().cancelMove();
	}
	
	public void playSoldierCard() {
        MapState.getState().playSoldierCard(getView());
	}
	
	public void playRoadBuildingCard() {
//        System.out.println("Playing Road Building Card");
//        CatanColor playerColor = ClientModel.getUpdatableModel().getLocalPlayer().getColor();
//        getView().startDrop(PieceType.ROAD, playerColor, true);
//		System.out.println(ClientModel.getUpdatableModel().getTurnTracker().getStatus());
        MapState.getState().playRoadBuildingCard(getView());
	}
	
	public void robPlayer(RobPlayerInfo victim) {
		MapState.getState().robPlayer(victim);
	}
	
}

