package server.command.moves;

import server.command.ICommand;
import server.command.IMoveCommand;
import server.handler.Credentials;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.SendChatRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class SendChatCommand implements IMoveCommand {
    private SendChatRequest request;
    private Credentials credentials;

    public SendChatCommand(SendChatRequest request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;
    }

    @Override
    public IGameModel execute() {
        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(this.getGameId());

        gameModel.sendChat(gameModel.getPlayerIndex(credentials.getPlayerId()), request.getMessage());

        return gameModel;
    }
    
	@Override
	public int getGameId() {
		return credentials.getGameId();
	}
}
