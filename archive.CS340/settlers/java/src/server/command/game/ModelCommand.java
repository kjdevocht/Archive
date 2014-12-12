package server.command.game;

import server.command.ICommand;
import server.handler.Credentials;
import server.model.game.IGameModel;
import server.model.ServerModel;
import serverProxy.request.GameModelRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class ModelCommand implements ICommand {

    private GameModelRequest request;
    private Credentials credentials;
    
    public ModelCommand(GameModelRequest request, Credentials credentials) {
        this.request = request;   
        this.credentials = credentials;
    }

    @Override
    public IGameModel execute() {
        int gameId = credentials.getGameId();

        if(!ServerModel.getSingleton().checkGameCredentials(credentials))
            return null;

        IGameModel gameModel = ServerModel.getSingleton().getGames().get(gameId);

        return gameModel;
    }
}
