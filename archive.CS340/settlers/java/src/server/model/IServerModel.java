package server.model;

import server.handler.Credentials;
import server.model.game.IGameModel;
import server.model.game.IUser;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author Jacob Glad
 *
 * Describes the model of the server
 */
public interface IServerModel {

    /**
    * Retrieve the list of existing games
    * @return the list of all games currently on the server
    */
	public List<IGameModel> getGames();

    /**
    * Adds a game to the list of games currently on the server
    */
	IGameModel addGame(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts);

    /**
    * Removes a game from the server
    * @param game The game to remove from the server
    * @return Success Status of the removal
    */
	public boolean removeGame(IGameModel game);

	public void loadGames(List<IGameModel> load);

    /**
    * Retrieve the list of existing users
    * @return the list of all users currently on the server
    */
	public Map<String, IUser> getUsers();

    /**
    * Adds a user to the list of users currently on the server
    * @param username obvious
    * @param username obvious
    */
	public IUser addUser(String username, String password);
	
	/**
	 * Checks if the username and password are valid
	 * @ return True if it's valid, False if it is not valid.
	 */
	public boolean canAddUser(String username, String password);
	public IUser getUser(String username);
    public boolean checkCredentials(Credentials credentials);
	

	

	
}
