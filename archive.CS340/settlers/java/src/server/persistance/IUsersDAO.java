package server.persistance;

import server.model.game.IUser;

import java.util.List;

/**
 * An interface that saves and retrieves the Users on the Server
 */
public interface IUsersDAO {

    /**
     * Retrieve the Users from the Database specified at startup
     * @return a List of IUser
     */
    public List<IUser> getUsers();

    /**
     * Save all the Users to the Database specified at startup
     */
    public void saveUser(IUser user);

}
