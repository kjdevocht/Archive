package client.map;

import client.data.RobPlayerInfo;
import client.model.ClientModel;
import client.model.IPlayer;
import serverProxy.ClientCommunicator;
import shared.definitions.CatanColor;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.List;

public class RobbingState implements IState {
	private HexLocation newRobberLocation;

    @Override
    public boolean canPlaceRoad(EdgeLocation edgeLoc) {
        return false;
    }

    @Override
    public boolean canPlaceSettlement(VertexLocation vertLoc) {
        return false;
    }

    @Override
    public boolean canPlaceCity(VertexLocation vertLoc) {
        return false;
    }

    @Override
    public boolean canPlaceRobber(HexLocation hexLoc) {
        return ClientModel.getUpdatableModel().getCatanMap().canPlaceRobberAtLocation(hexLoc);
    }

    @Override
    public void placeRoad(EdgeLocation edgeLoc) {

    }

    @Override
    public void placeSettlement(VertexLocation vertLoc) {

    }

    @Override
    public void placeCity(VertexLocation vertLoc) {

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
        CatanColor playerColor = ClientModel.getUpdatableModel().getLocalPlayer().getColor();
        view.startDrop(pieceType,playerColor,false);
    }

    @Override
    public void cancelMove() {

    }

    @Override
    public void robPlayer(RobPlayerInfo victim) {
		ClientModel success = null;
		int playerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
		try {
			success = ClientCommunicator.getClientCommunicator().robPlayer(playerIndex, victim.getPlayerIndex(), this.newRobberLocation);
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
		// TODO Auto-generated method stub
		
	}

	@Override
	public void playRoadBuildingCard(IMapView view) {
		// TODO Auto-generated method stub
		
	}
}
