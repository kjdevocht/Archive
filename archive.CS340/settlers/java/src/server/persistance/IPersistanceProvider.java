package server.persistance;

/**
 * An interface that holds information about the current persistance system and can create DAOs
 * @author Jacob Glad
 *
 */
public interface IPersistanceProvider {
	/**
	 * A factory method that returns an DAO that matches this provider
	 * 
	 * @return An IGamesDAO that uses this persistance provider
	 */
	public IGamesDAO getGamesDAO();
	/**
	 * A factory method that returns an DAO that matches this provider
	 * 
	 * @return An IUserDAO that uses this persistance provider
	 */
	public IUsersDAO getUsersDAO();

}
