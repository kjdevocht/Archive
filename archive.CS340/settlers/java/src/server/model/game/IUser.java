package server.model.game;

/**
 * 
 * @author Jacob Glad
 * 
 * Describes a registered user on the server
 */
public interface IUser {
	/**
	 * @.obviousGetter
	 */
	public int getId();
	/**
	 * @.obviousSetter
	 */
	public void setId(int id);
	
	/**
	 * @.obviousGetter
	 */
	public String getName();
	/**
	 * @.obviousSetter
	 */
	public void setName(String name);
	
	/**
	 * @.obviousGetter
	 */
	public String getPassword();
	/**
	 * @.obviousSetter
	 */
	public void setPassword(String password);
}
