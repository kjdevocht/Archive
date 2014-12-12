package server.command.moves;

import server.command.ICommand;
import server.command.IMoveCommand;
import server.handler.Credentials;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.RollNumberRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class RollNumberCommand implements IMoveCommand {
    private RollNumberRequest request;
    private Credentials credentials;

    public RollNumberCommand(RollNumberRequest request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;
    }

    @Override
    public IGameModel execute() {
        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(this.getGameId());

        gameModel.rollNumber(gameModel.getPlayerIndex(credentials.getPlayerId()), request.getNumber());

        return gameModel;
    }
    
	@Override
	public int getGameId() {
		return credentials.getGameId();
	}
}
