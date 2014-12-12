package server.command.moves;

import server.command.ICommand;
import server.command.IMoveCommand;
import server.handler.Credentials;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.SoldierRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class SoldierCommand implements IMoveCommand {
    private SoldierRequest request;
    private Credentials credentials;

    public SoldierCommand(SoldierRequest request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;
    }

    @Override
    public IGameModel execute() {
        int gameId = credentials.getGameId();

        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(getGameId());

        gameModel.soldier(gameModel.getPlayerIndex(credentials.getPlayerId()), request.getVictimIndex(), request.getLocation());

        return gameModel;
    }
    
	@Override
	public int getGameId() {
		return credentials.getGameId();
	}
}
