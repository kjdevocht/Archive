package server.command;

/**
 * ICommand is the interface for all commands that the
 * server can receive and process
 */
public interface ICommand {

    /**
     * Execute the Command (Command Design Pattern)
     * @return Object, type depends on Command being made
     */
    public Object execute();
}
