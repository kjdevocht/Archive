package server.exception;

/**
 * Created by mitch10e on 11/5/14.
 */
public class NotImplementedException extends ServerException {
    public NotImplementedException() {}
    public NotImplementedException(String message) { super(message); }
    public NotImplementedException(Throwable throwable) { super(throwable); }
    public NotImplementedException(String message, Throwable throwable) { super(message, throwable); }
}