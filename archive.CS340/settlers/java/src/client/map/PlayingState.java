package client.map;

import java.util.List;

import client.base.IView;
import client.data.RobPlayerInfo;
import client.model.ClientModel;
import client.model.IPlayer;
import serverProxy.ClientCommunicator;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

public class PlayingState implements IState {
	private HexLocation newRobberLocation;
	private String subState;
	private IMapView view;
	private EdgeLocation road1;
	public PlayingState() {
		super();
		subState = "Playing";
	}
    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc) {
    	if(subState.equals("road2")) {
    		return ClientModel.getUpdatableModel().canPlayRoadBuild(road1, edgeLoc);
    	}
        int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
        String status = ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase();
        return ClientModel.getUpdatableModel().getCatanMap().canPlaceRoadAtLocation(edgeLoc, playerIndex, status);
    }

    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
        String status = ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase();
        return ClientModel.getUpdatableModel().getCatanMap().canPlaceSettlementAtLocation(vertLoc, playerIndex, status);
    }

    @Override
    public boolean canPlaceCity(VertexLocation vertLoc) {
        int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
        String status = ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase();
        return ClientModel.getUpdatableModel().getCatanMap().canPlaceCityAtLocation(vertLoc, playerIndex, status);
    }

    @Override
    public boolean canPlaceRobber(HexLocation hexLoc) {
        return ClientModel.getUpdatableModel().getCatanMap().canPlaceRobberAtLocation(hexLoc);
    }

    @Override
    public void placeRoad(EdgeLocation edgeLoc) {
    	if(subState.equals("road1")) {
    		subState = "road2";
    		road1 = edgeLoc;
    		view.startDrop(PieceType.ROAD, ClientModel.getUpdatableModel().getLocalPlayer().getColor(), true);
    		view.placeTempRoad(edgeLoc, ClientModel.getUpdatableModel().getLocalPlayer().getColor());
    		return;
    	}
    	if(subState.equals("road2")) {
    		int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
    		ClientModel success;
    		try {
                success = ClientCommunicator.getClientCommunicator().roadBuilding(playerIndex, road1, edgeLoc);
                if(success == null){
                    throw new Exception();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
    		
    		subState = "Playing";
    		road1 = null;
    		view.removeTempRoad();
    		
    	}
    	
        if(canPlaceRoad(edgeLoc)){
            ClientModel success;
            int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
            try {
                success = ClientCommunicator.getClientCommunicator().buildRoad(playerIndex, edgeLoc, false);
                if(success == null){
                    throw new Exception();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void placeSettlement(VertexLocation vertLoc) {
        if(canPlaceSettlement(vertLoc)){
            ClientModel success;
            int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
            try {
                success = ClientCommunicator.getClientCommunicator().buildSettlement(playerIndex, vertLoc, false);
                if(success == null){
                    throw new Exception();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void placeCity(VertexLocation vertLoc) {
        if(canPlaceCity(vertLoc)){
            ClientModel success = null;
            int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
            try {
                success = ClientCommunicator.getClientCommunicator().buildCity(playerIndex, vertLoc);
                if(success == null){
                    throw new Exception();
                }
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void placeRobber(HexLocation hexLoc, IMapView view, IRobView robView) {
        List<Integer> victims = ClientModel.getUpdatableModel().getCatanMap().getVictims(hexLoc);
        List<IPlayer> players = ClientModel.getUpdatableModel().getPlayers();
        RobPlayerInfo[] candidates = new RobPlayerInfo[victims.size()];
		this.newRobberLocation = hexLoc;
//        System.out.println("Victims length: " + victims.size());
        for (int i = 0; i < victims.size(); i++) {
            IPlayer player = players.get(victims.get(i));
            RobPlayerInfo candidate = new RobPlayerInfo();
            candidate.setNumCards(player.getResources().getTotalResourceCount());
            candidate.setColor(player.getColor());
            candidate.setId(player.getPlayerId());
            candidate.setName(player.getName());
            candidate.setPlayerIndex(player.getPlayerIndex());
            candidates[i] = candidate;
        }

        robView.setPlayers(candidates);
        robView.showModal();
    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected, IMapView view, IRobView robView) {
//        System.out.println("Playing State");
        if(pieceType == PieceType.ROAD){
            if(!ClientModel.getUpdatableModel().getLocalPlayer().canBuyRoad())
                return;
        }
        if(pieceType == PieceType.CITY){
            if(!ClientModel.getUpdatableModel().getLocalPlayer().canBuyCity())
                return;
        }
        if(pieceType == PieceType.SETTLEMENT){
            if(!ClientModel.getUpdatableModel().getLocalPlayer().canBuySettlement())
                return;
        }

        CatanColor playerColor = ClientModel.getUpdatableModel().getLocalPlayer().getColor();
        //false here is to allow cancel
        view.startDrop(pieceType,playerColor,true);
    }

    @Override
    public void cancelMove() {
    	subState = "Playing";
		road1 = null;
		if(view != null)
			view.removeTempRoad();
    }

    @Override
    public void robPlayer(RobPlayerInfo victim) {
    	ClientModel success = null;
		int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
		try {
//			success = ClientCommunicator.getClientCommunicator().robPlayer(playerIndex, victim.getPlayerIndex(), this.newRobberLocation);
			success = ClientCommunicator.getClientCommunicator().soldier(playerIndex, victim.getPlayerIndex(), this.newRobberLocation);
			if(success == null){
				throw new Exception();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
    }
	@Override
	public void playSoldierCard(IMapView view) {
		view.startDrop(PieceType.ROBBER, CatanColor.BLUE, true);
		
	}
	@Override
	public void playRoadBuildingCard(IMapView view) {
		subState = "road1";
		this.view = view;
		view.startDrop(PieceType.ROAD, ClientModel.getUpdatableModel().getLocalPlayer().getColor(), true);
	}
}
