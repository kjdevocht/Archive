package serverProxy;

public interface IServer {
	/**
	 * @.pre All of the preconditions for the IRequest are met
	 * @.post An IResponse of the appropriate type for the given IRequest is returned
	 * 
	 * @param request Some request to be sent to the server
	 * @param emptyResponse A response object to which the servers response should be written
	 * @return The response from the server
	 * @throws Exception
	 */
	public <T extends IResponse> T sendCommand(IRequest request, T emptyResponse) throws Exception;
}
