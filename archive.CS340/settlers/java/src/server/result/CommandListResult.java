package server.result;

import server.command.ICommandList;
import server.model.game.ai.AIList;

/**
 * The result for running a list of commands through the games/commands end point
 */
public class CommandListResult implements IResult {

    private ICommandList commandList;

    public CommandListResult(ICommandList commandList) {
        this.commandList = commandList;
    }

    @Override
    public ICommandList getResult() {
        return commandList;
    }

    @Override
    public void setResult(Object commandList) {
        this.commandList = (ICommandList)commandList;
    }

}
