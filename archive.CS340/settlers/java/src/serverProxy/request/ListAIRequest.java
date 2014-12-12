package serverProxy.request;

import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /game/listAI endpoint
 * @author Jacob Glad
 *
 * 
 */
public class ListAIRequest implements IRequest {

	public ListAIRequest(){}
	
	@Override
	public String getEndpoint() {
		return "/game/listAI";
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
