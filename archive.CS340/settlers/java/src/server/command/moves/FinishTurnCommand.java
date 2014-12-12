package server.command.moves;

import server.command.ICommand;
import server.command.IMoveCommand;
import server.handler.Credentials;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.FinishTurnRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class FinishTurnCommand implements IMoveCommand{
    private FinishTurnRequest request;
    private Credentials credentials;

    public FinishTurnCommand(FinishTurnRequest request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;

    }

    @Override
    public IGameModel execute() {
        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(this.getGameId());

        gameModel.finishTurn(gameModel.getPlayerIndex(credentials.getPlayerId()));

        gameModel.addLog(gameModel.getPlayerIndex(credentials.getPlayerId()), credentials.getName()+"'s turn just ended");

        return gameModel;
    }
    
	@Override
	public int getGameId() {
		return credentials.getGameId();
	}
}
