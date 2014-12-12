package serverProxy.request;

import com.google.gson.JsonObject;
import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /user/register endpoint
 * @author Jacob Glad
 *
 * 
 * @.post The user is created if it did not exist
 */
public class RegisterUserRequest implements IRequest {

	private String username;
	private String password;
	
	public RegisterUserRequest(String username, String password){
		this.setPassword(password);
		this.setUsername(username);
	}
	@Override
	public String getEndpoint() {
		return "/user/register";
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
