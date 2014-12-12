package serverProxy.response;

import serverProxy.IResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.CookieStore;

/**
 * A basic response type expected from server endpoints that return a boolean value
 * @author Jacob Glad
 *
 */
public class FailSuccessResponse implements IResponse {

	private boolean success;
	private static String successValue = "Success";
	/**
	 * @return the success
	 */
	public boolean isSuccess() {
		return success;
	}

	/**
	 * @param success the success to set
	 */
	private void setSuccess(boolean success) {
		this.success = success;
	}

	public FailSuccessResponse() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public IResponse fromResponse(BufferedReader response, CookieStore cookies) throws IOException {
		String inputLine;
		StringBuffer responseText = new StringBuffer();
		 
		while ((inputLine = response.readLine()) != null) {
			responseText.append(inputLine);
		}
		
		this.setSuccess(responseText.toString().equals(successValue));
		
		return this;
	}

}
