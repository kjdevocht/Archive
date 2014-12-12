package server.command.game;

import server.command.ICommand;
import serverProxy.request.ResetGameRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class ResetCommand implements ICommand {

    private ResetGameRequest request;

    public ResetCommand(ResetGameRequest request) {
        this.request = request;
    }

    @Override
    public String execute() {
        return "Unimplemented";
    }
}
