package server.command.moves;

import server.command.ICommand;
import server.command.IMoveCommand;
import server.handler.Credentials;
import server.model.ServerModel;
import server.model.game.IGameModel;
import serverProxy.request.AcceptTradeRequest;
import serverProxy.request.BuildSettlementRequest;
import shared.locations.EdgeLocation;

/**
 * Created by mitch10e on 11/5/14.
 */
public class BuildSettlementCommand implements IMoveCommand{
    private BuildSettlementRequest request;
    private Credentials credentials;

    public BuildSettlementCommand(BuildSettlementRequest request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;
    }

    @Override
    public IGameModel execute() {
        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(this.getGameId());

        gameModel.placeSettlement(request.getVertexLocation(), gameModel.getPlayerIndex(credentials.getPlayerId()), request.isFree());

        gameModel.addLog(gameModel.getPlayerIndex(credentials.getPlayerId()), credentials.getName()+" built a settlement");

        return gameModel;
    }
    
	@Override
	public int getGameId() {
		return credentials.getGameId();
	}
}
