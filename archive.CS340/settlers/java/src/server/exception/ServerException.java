/**
 * An exception 
 */
package server.exception;

/**
 * @author mitch10e
 *
 */

public class ServerException extends Exception {
    public ServerException() {}
    public ServerException(String message) { super(message); }
    public ServerException(Throwable throwable) { super(throwable); }
    public ServerException(String message, Throwable throwable) { super(message, throwable); }
}