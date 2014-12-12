package server.command.moves;

import server.command.ICommand;
import server.command.IMoveCommand;
import server.handler.Credentials;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.MaritimeTradeRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class MaritimeTradeCommand implements IMoveCommand{
    private MaritimeTradeRequest request;
    private Credentials credentials;

    public MaritimeTradeCommand(MaritimeTradeRequest request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;
    }

    @Override
    public IGameModel execute() {  	
        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(this.getGameId());

        gameModel.maritimeTrade(gameModel.getPlayerIndex(credentials.getPlayerId()), request.getRatio(), request.getInputResource(), request.getOutputResource());

        return gameModel;
    }
    
	@Override
	public int getGameId() {
		return credentials.getGameId();
	}
}
