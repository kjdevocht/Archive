package server.command.moves;

import server.command.ICommand;
import server.command.IMoveCommand;
import server.handler.Credentials;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.MonumentRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class MonumentCommand implements IMoveCommand{
    private MonumentRequest request;
    private Credentials credentials;

    public MonumentCommand(MonumentRequest request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;

    }

    @Override
    public IGameModel execute() {
        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(this.getGameId());

        gameModel.monument(gameModel.getPlayerIndex(credentials.getPlayerId()));
        return gameModel;
    }
    
	@Override
	public int getGameId() {
		return credentials.getGameId();
	}
}
