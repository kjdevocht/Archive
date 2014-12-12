package server.command.game;

import server.command.ICommand;
import server.model.game.ai.AIList;
import serverProxy.request.ListAIRequest;

/**
 * Created by mitch10e on 11/5/14.
 */
public class ListAICommand implements ICommand {

    private ListAIRequest request;

    public ListAICommand(ListAIRequest request) {
        this.request = request;
    }

    @Override
    public AIList execute() {
        return null;
    }
}
