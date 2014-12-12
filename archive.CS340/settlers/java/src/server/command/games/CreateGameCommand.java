package server.command.games;

import server.command.ICommand;
import server.model.IServerModel;
import server.model.ServerModel;
import server.model.game.IGameModel;
import server.result.ModelResult;
import serverProxy.request.CreateGamesRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class CreateGameCommand implements ICommand {

    private CreateGamesRequest request;
    private ModelResult result;
    public CreateGameCommand(CreateGamesRequest request) {
        this.request = request;
    }

    @Override
    public ModelResult execute() {
        IServerModel serverModel = ServerModel.getSingleton();
        IGameModel gameModel = serverModel.addGame(request.getName(), request.isRandomTiles(), request.isRandomNumbers(), request.isRandomPorts());
        result = new ModelResult(gameModel);
        return result;
    }
}
