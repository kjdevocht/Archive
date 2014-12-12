package serverProxy.request;

import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /game/reset endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre The player has a valid catan.user and catan.game id 
 */
public class ResetGameRequest implements IRequest {

	public ResetGameRequest(){
	}
	
	@Override
	public String getEndpoint() {
		return "/game/reset";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
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
