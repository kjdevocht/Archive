package serverProxy.request;

import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /game/commands[POST] endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre The player has a valid catan.user and catan.game id 
 */
public class PostGameCommandsRequest implements IRequest {

	private String username;
	private String password;
	
	public PostGameCommandsRequest(String username, String password){
		this.setPassword(password);
		this.setUsername(username);
	}
	
	@Override
	public String getEndpoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username the username to set
	 */
	private void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	private void setPassword(String password) {
		this.password = password;
	}

	
}
