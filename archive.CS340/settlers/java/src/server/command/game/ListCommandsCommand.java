package server.command.game;

import server.command.ICommand;
import server.command.ICommandList;
import serverProxy.request.GetGameCommandsRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class ListCommandsCommand implements ICommand {

    private GetGameCommandsRequest request;

    public ListCommandsCommand(GetGameCommandsRequest request) {
        this.request = request;
    }

    @Override
    public ICommandList execute() {
        return null;
    }
}
