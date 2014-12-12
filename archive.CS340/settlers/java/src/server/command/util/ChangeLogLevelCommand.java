package server.command.util;

import server.command.ICommand;
import server.logging.Logger;

/**
 * Created by mitch10e on 11/5/14.
 */
public class ChangeLogLevelCommand implements ICommand {

    private String level;

    public ChangeLogLevelCommand(String level) {
        this.level = level;
    }

    @Override
    public String execute() {
        try {
            Logger.getSingleton().setLoggingLevel(level);
        } catch (IllegalArgumentException e) {
            return e.getMessage();
        }
        return "Success";
    }
}
