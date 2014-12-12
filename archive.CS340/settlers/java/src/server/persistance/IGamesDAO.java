package server.persistance;

import server.command.ICommand;
/**
 * An interface that saves and retrieves the Games on the Server
 */
public interface IGamesDAO {

    /**
     * Retrieve the Games from the Database specified at startup
     * @return a List of GameModel
     */
    public void loadGames();



    /**
     * Save the most recent command to the Database specified at startup
     * if the number of stored commands has been reached
     * store the entire Game Model instead
     */
    public void storeCommand(int gameID, ICommand command);


    /**
     *
     * @return The number of commands until a new system state should be saved
     */
    public int getNumCommands();
    /**
     *
     * @param numCommands The number of commands until a new system state should be saved
     */
    public void setNumCommands(int numCommands);

}

