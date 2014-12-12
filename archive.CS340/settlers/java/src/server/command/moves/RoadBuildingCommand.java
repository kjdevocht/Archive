package server.command.moves;

import server.command.ICommand;
import server.command.IMoveCommand;
import server.handler.Credentials;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.RoadBuildingRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class RoadBuildingCommand implements IMoveCommand{
    private RoadBuildingRequest request;
    private Credentials credentials;

    public RoadBuildingCommand(RoadBuildingRequest request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;
    }

    @Override
    public IGameModel execute() {
        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(this.getGameId());

        gameModel.roadBuild(gameModel.getPlayerIndex(credentials.getPlayerId()), request.getSpot1(), request.getSpot2());

        return gameModel;
    }
    
	@Override
	public int getGameId() {
		return credentials.getGameId();
	}
}
