package serverProxy;

public class RequestException extends Exception {

	public RequestException(int errorCode) {
		super("The error code was: " + errorCode);
	}
}
