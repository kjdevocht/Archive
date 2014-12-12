package server.command.moves;

import server.command.ICommand;
import server.command.IMoveCommand;
import server.handler.Credentials;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.OfferTradeRequest;
import serverProxy.request.OfferTradeRequestServer;

/**
 * Created by mitch10e on 11/5/14.
 */
public class OfferTradeCommand implements IMoveCommand{
    private OfferTradeRequestServer request;
    private Credentials credentials;

    public OfferTradeCommand(OfferTradeRequestServer request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;
    }

    @Override
    public IGameModel execute() {
        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(this.getGameId());

        gameModel.offerTrade(gameModel.getPlayerIndex(credentials.getPlayerId()), request.getReceiver(), request.getOffer());

        return gameModel;
    }
    
	@Override
	public int getGameId() {
		return credentials.getGameId();
	}
}
