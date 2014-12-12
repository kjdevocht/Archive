package server.command.moves;

import server.command.ICommand;
import server.command.IMoveCommand;
import server.handler.Credentials;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.YearOfPlentyRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class YearOfPlentyCommand implements IMoveCommand {
    private YearOfPlentyRequest request;
    private Credentials credentials;

    public YearOfPlentyCommand(YearOfPlentyRequest request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;
    }

    @Override
    public IGameModel execute() {

        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(this.getGameId());

        gameModel.yearOfPlenty(gameModel.getPlayerIndex(credentials.getPlayerId()), request.getResource1(), request.getResource2());

        return gameModel;
    }

	@Override
	public int getGameId() {
		return credentials.getGameId();
	}
}
