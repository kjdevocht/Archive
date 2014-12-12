package client.map;

import client.model.observable.Event;

import client.model.IPlayer;

import client.model.map.IHex;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.IObserver;
import serverProxy.ClientCommunicator;
import shared.definitions.PieceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.base.IView;
import client.data.RobPlayerInfo;
import client.model.ClientModel;

import java.awt.*;

public class MapState implements IState{
    IState state;
	private static MapState instance = null;


	private MapState(){
		String status = ClientModel.getUpdatableModel().getTurnTracker().getStatus();
		updateState(status);
	}

	public static MapState getState(){
		if(instance == null){
			instance = new MapState();
		}
//        instance = new MapState();

		return instance;
	}


	public void updateState(String status){
//        System.out.println("\u001B[32mMapState.updateState(status) Status: " + status + "\u001B[0m");
        switch(status){
			case("Rolling"):
				state = new RollingState();
//                System.out.println("\u001B[31m[STATE CHANGE]\tROLLING STATE\u001B[0m");
                break;
			case("Robbing"):
				state = new RobbingState();
//                System.out.println("\u001B[31m[STATE CHANGE]\tROBBING STATE\u001B[0m");
				break;
			case("Playing"):
				state = new PlayingState();
//                System.out.println("\u001B[31m[STATE CHANGE]\tPLAYING STATE\u001B[0m");
				break;
            case("Discarding"):
                state = new DiscardingState();
//                System.out.println("\u001B[31m[STATE CHANGE]\tDISCARDING STATE\u001B[0m");
                break;
            case("FirstRound"):
                state = new SetupState();
//                System.out.println("\u001B[31m[STATE CHANGE]\tFIRST ROUND SETUP STATE\u001B[0m");
                break;
            case("SecondRound"):
                state = new SetupState();
//                System.out.println("\u001B[31m[STATE CHANGE]\tSECOND ROUND SETUP STATE\u001B[0m");
                break;
            case("GameOver"):
                state = new GameOverState();
//                System.out.println("GAME OVER");
                break;
            default:
				//TODO I need to figure out what to do with the default case
				//Maybe make this not your turn and just not let the player do anything
                state = new NotTurnState();
//                System.out.println("\u001B[31m[STATE CHANGE]\tNOT YOUR TURN STATE\u001B[0m");
				break;
		}
	}

	@Override
	public boolean canPlaceRoad(EdgeLocation edgeLoc) {
		return state.canPlaceRoad(edgeLoc);
	}

	@Override
	public boolean canPlaceSettlement(VertexLocation vertLoc) {
		return state.canPlaceSettlement(vertLoc);
	}

	@Override
	public boolean canPlaceCity(VertexLocation vertLoc) {
		return state.canPlaceCity(vertLoc);
	}

	@Override
	public boolean canPlaceRobber(HexLocation hexLoc) {
		return state.canPlaceRobber(hexLoc);
	}

	@Override
	public void placeRoad(EdgeLocation edgeLoc) {
		state.placeRoad(edgeLoc);
	}

	@Override
	public void placeSettlement(VertexLocation vertLoc) {
		state.placeSettlement(vertLoc);
	}

	@Override
	public void placeCity(VertexLocation vertLoc) {
		state.placeCity(vertLoc);
	}

    @Override
    public void placeRobber(HexLocation hexLoc, IMapView view, IRobView robView) {
        state.placeRobber(hexLoc, view, robView);
    }

	@Override
	public void startMove(PieceType pieceType, boolean isFree,boolean allowDisconnected, IMapView view, IRobView robView) {
		state.startMove(pieceType, isFree, allowDisconnected, view, robView);
	}

	@Override
	public void cancelMove() {
		state.cancelMove();
	}

	@Override
	public void robPlayer(RobPlayerInfo victim) {
		state.robPlayer(victim);
	}

	@Override
	public void playSoldierCard(IMapView view) {
		state.playSoldierCard(view);
		
	}

	@Override
	public void playRoadBuildingCard(IMapView view) {
		state.playRoadBuildingCard(view);
	}

}
