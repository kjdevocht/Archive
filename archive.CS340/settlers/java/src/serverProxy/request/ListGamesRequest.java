package serverProxy.request;

import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /games/list endpoint
 * @author Jacob Glad
 *
 * 
 */
public class ListGamesRequest implements IRequest {

	public ListGamesRequest(){
		
	}
	
	@Override
	public String getEndpoint() {
		return "/games/list";
	}

	@Override
	public String getRequestMethod() {
		return "GET";
	}

	@Override
	public String getBody() {
		return "";
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}
}

