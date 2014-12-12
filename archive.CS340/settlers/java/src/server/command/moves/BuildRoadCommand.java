package server.command.moves;

import server.command.ICommand;
import server.command.IMoveCommand;
import server.handler.Credentials;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.BuildRoadRequest;
import shared.locations.EdgeLocation;

/**
 * Created by mitch10e on 11/5/14.
 */
public class BuildRoadCommand implements IMoveCommand {
    private BuildRoadRequest request;
    private Credentials credentials;

    public BuildRoadCommand(BuildRoadRequest request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;
    }

    @Override
    public IGameModel execute() {
        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(this.getGameId());

        EdgeLocation location = request.getRoadLocation();

        gameModel.placeRoad(location, gameModel.getPlayerIndex(credentials.getPlayerId()), request.isFree());

        gameModel.addLog(gameModel.getPlayerIndex(credentials.getPlayerId()), credentials.getName()+" built a road");

        return gameModel;
    }
    
	@Override
	public int getGameId() {
		return credentials.getGameId();
	}
}
