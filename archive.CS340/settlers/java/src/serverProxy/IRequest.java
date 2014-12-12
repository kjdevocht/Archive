package serverProxy;

public interface IRequest {
	/**
	 * @return The endpoint that this IRequest is to be sent to
	 */
	public String getEndpoint();
	
	/**
	 * @return The HTTP Request Method (GET, POST, DELETE, PUT)
	 */
	public String getRequestMethod();
	
	/**
	 * @return The HTTP body information for this type of request
	 */
	public String getBody();
	
	/**
	 * @return A sample response that this request would expect
	 */
	public IResponse getDefaultResponse();
}
