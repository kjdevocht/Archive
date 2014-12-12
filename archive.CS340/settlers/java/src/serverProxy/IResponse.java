package serverProxy;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.CookieStore;

/**
 * Represents values that can be returned from the server. If an instance of a class can be returned 
 * from a server request, it must implement this interface.
 * @author Jacob Glad
 *
 * 
 */
public interface IResponse {
	
	/**
	 * @param response Some input stream that may be encoded in any way
	 * @param cookies A cookie store containing the cookie values
	 * @return A java class that represents the data sent from the server
	 * @throws IOException
	 */
	public IResponse fromResponse(BufferedReader response, CookieStore cookies) throws IOException;
}
