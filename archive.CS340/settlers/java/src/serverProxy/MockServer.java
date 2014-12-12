package serverProxy;

/**
 * A server that only returns the default response of a given request
 * @author Jacob Glad
 *
 * 
 */
public class MockServer implements IServer {

	public MockServer(){}
	/*@Override
	public IResponse sendCommand(IRequest request) {
		return request.getDefaultResponse();
	}*/

	@Override
	public <T extends IResponse> T sendCommand(IRequest request, T emptyResponse) {
		
		return (T)request.getDefaultResponse();
	}

}
