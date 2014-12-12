package client.map;

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

public class SetupState implements IState {

    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc) {
        ClientModel model = ClientModel.getUpdatableModel();
        String status = model.getTurnTracker().getStatus().toLowerCase();
        int playerIndex = model.getLocalPlayer().getPlayerIndex();
        String state = ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase();
        if(state.equals("firstround") && model.getLocalPlayer().getRoads() == 15) {
            return ClientModel.getUpdatableModel().getCatanMap().canPlaceRoadAtLocation(edgeLoc, playerIndex, status);
        } else if(state.equals("secondround") && model.getLocalPlayer().getRoads() == 14) {
            return ClientModel.getUpdatableModel().getCatanMap().canPlaceRoadAtLocation(edgeLoc, playerIndex, status);
        } else {
            return false;
        }
    }


    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        ClientModel model = ClientModel.getUpdatableModel();
        String status = model.getTurnTracker().getStatus().toLowerCase();
        int playerIndex = model.getLocalPlayer().getPlayerIndex();
        String state = ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase();
        if(state.equals("firstround") && model.getLocalPlayer().getSettlements() == 5) {
            return ClientModel.getUpdatableModel().getCatanMap().canPlaceSettlementAtLocation(vertLoc,playerIndex,status);
        } else if(state.equals("secondround") && model.getLocalPlayer().getSettlements() == 4) {
            return ClientModel.getUpdatableModel().getCatanMap().canPlaceSettlementAtLocation(vertLoc,playerIndex,status);
        } else {
            return false;
        }

    }

    @Override
    public boolean canPlaceCity(VertexLocation vertLoc) {
        return false;
    }

    @Override
    public boolean canPlaceRobber(HexLocation hexLoc) {
        return false;
    }

    @Override
    public void placeRoad(EdgeLocation edgeLoc) {
        if(canPlaceRoad(edgeLoc)){
            ClientModel success;
            int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
            try {
                success = ClientCommunicator.getClientCommunicator().buildRoad(playerIndex, edgeLoc, true);
                if(success == null){
                    throw new Exception();
                }
//                System.out.println("ENDING TURN SETUP PHASE");
//                success = ClientCommunicator.getClientCommunicator().endTurn(playerIndex);
//                if(success == null){
//                    throw new Exception();
//                }
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
                success = ClientCommunicator.getClientCommunicator().buildSettlement(playerIndex, vertLoc, true);
                if(success == null){
                    throw new Exception();
                }
//                System.out.println("PLACE SETTLEMENT: " + success);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void placeCity(VertexLocation vertLoc) {

    }

    @Override
    public void placeRobber(HexLocation hexLoc, IMapView view, IRobView robView) {

    }

    @Override
    public void startMove(PieceType pieceType, boolean isFree, boolean allowDisconnected, IMapView view, IRobView robView) {
        CatanColor playerColor = ClientModel.getUpdatableModel().getLocalPlayer().getColor();
        String state = ClientModel.getUpdatableModel().getTurnTracker().getStatus().toLowerCase();
        int turn = ClientModel.getUpdatableModel().getTurnTracker().getCurrentTurn();
        IPlayer localplayer = ClientModel.getUpdatableModel().getLocalPlayer();

        if (turn == ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex()) {
//            System.out.println("\u001B[30m[START MOVE]\t\t" + state + "\u001B[0m");
            if (state.equals("firstround")) {
                if (localplayer.getRoads() != 15) {
                    return;
                }
            } else if (state.equals("secondround")) {
                if (localplayer.getRoads() != 14) {
                    return;
                }
            }
            view.startDrop(pieceType, playerColor, allowDisconnected);

        }
    }

    @Override
    public void cancelMove() {

    }

    @Override
    public void robPlayer(RobPlayerInfo victim) {

    }


	@Override
	public void playSoldierCard(IMapView view) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void playRoadBuildingCard(IMapView view) {
		// TODO Auto-generated method stub
		
	}
}
