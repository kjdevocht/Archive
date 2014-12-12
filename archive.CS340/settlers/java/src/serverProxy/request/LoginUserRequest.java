package serverProxy.request;

import com.google.gson.JsonObject;
import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /user/login  endpoint
 * @author Jacob Glad
 *
 * 
 * @.post The user is logged in if the credentials are valid
 */
public class LoginUserRequest implements IRequest {

	private String username;
	private String password;
	
	public LoginUserRequest(String username, String password){
		this.setPassword(password);
		this.setUsername(username);
	}
	@Override
	public String getEndpoint() {
		return "/user/login";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("username", this.getUsername());
		obj.addProperty("password", this.getPassword());
		
		return obj.toString();
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}
	public String getUsername() {
		return username;
	}
	private void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	private void setPassword(String password) {
		this.password = password;
	}
	
}
