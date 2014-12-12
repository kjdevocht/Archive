package server.logging;

/**
 * The Logger Class for the server that allows the user to
 * manually set the logging leave of the server
 */

public interface ILogger {

    /**
     * Set the Logging Level for the Server
     * @param level See LogLevel
     */
    public void setLoggingLevel(LogLevel level);

    /**
     * Set the Logging Level for the Server
     * @param level See LogLevel
     */
    public void setLoggingLevel(String level);

    /**
     * Print [ERROR] message
     * @param message
     */
    public void error(String message);

    /**
     * Print [WARN] message
     * @param message
     */
    public void warn(String message);

    /**
     * Print [INFO] message
     * @param message
     */
    public void info(String message);

    /**
     * Print [DEBUG] message
     * @param message
     */
    public void debug(String message);

}
