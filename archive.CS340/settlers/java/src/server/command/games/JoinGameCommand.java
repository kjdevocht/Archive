package server.command.games;

import server.command.ICommand;
import server.handler.Credentials;
import server.model.ServerModel;
import serverProxy.request.JoinGameRequest;
import shared.definitions.CatanColor;

/**
 * Created by mitch10e on 11/5/14.
 */
public class JoinGameCommand implements ICommand {

    private JoinGameRequest request;
    private Credentials credentials;
    private Boolean response = false;

    public JoinGameCommand(JoinGameRequest request, Credentials credentials) {
        this.request = request;
        this.credentials = credentials;

    }

    @Override
    public Boolean execute() {

        response = false;
        if(!ServerModel.getSingleton().checkCredentials(credentials)){
            return null;
        }
        int gameId = request.getGameId();
        String color = request.getColor();

        CatanColor catanColor = CatanColor.fromString(color);

        if(catanColor == null){
            return null;
        }

        if(!ServerModel.getSingleton().canJoinGame(gameId, credentials.getPlayerId(), catanColor)) {
            return null;
        }

        ServerModel.getSingleton().joinGame(gameId, credentials.getPlayerId(), catanColor);
        response = true;

        return response;
    }
}
