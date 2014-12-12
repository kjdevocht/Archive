package server.command.game;

import server.command.ICommand;
import server.model.game.IGameModel;
import serverProxy.request.PostGameCommandsRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class RunCommandsCommand implements ICommand {

    private PostGameCommandsRequest request;

    public RunCommandsCommand(PostGameCommandsRequest request) {
        this.request = request;
    }

    @Override
    public IGameModel execute() {
        return null;
    }
}
