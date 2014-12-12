package server.command.game;

import server.command.ICommand;
import serverProxy.request.AddAIRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class AddAICommand implements ICommand {

    private AddAIRequest request;

    public AddAICommand(AddAIRequest request) {
        this.request = request;
    }

    @Override
    public String execute() {
        return "Unimplemented";
    }
}
