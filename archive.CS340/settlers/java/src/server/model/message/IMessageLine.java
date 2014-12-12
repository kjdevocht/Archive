package server.model.message;
/**
 * @author mitch10e
 * Represents a single entry in a <code>MessageList</code>
 */
/**
 * MessageLine(String source, String message)
 * Basic Constructor for <code>MessageLine</code>
 * @.pre None
 * @.post A new <code>MessageLine</code>
 * @param source - the source of the message
 * @param message - the message the player wants to send
 */

public interface IMessageLine {
    /**
     * @.obviousGetter
     */
    public String getSource();

    /**
     * @.obviousSetter
     */
    public void setSource(String source);

    /**
     * @.obviousGetter
     */
    public String getMessage();

    /**
     * @.obviousSetter
     */
    public void setMessage(String line);
}
