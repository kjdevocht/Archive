package server.model.message;

/**
 * @author mitch10e
 * Represents a single entry in a <code>MessageList</code>
 */
public class MessageLine implements IMessageLine {
    private String source;
    private String message;

    /**
     * Basic Constructor for <code>MessageLine</code>
     * @.pre None
     * @.post A new <code>MessageLine</code>
     * @param source - the source of the message
     * @param message - the message the player wants to send
     */
    MessageLine(String source, String message) {
        if (source == null) {
            throw new IllegalArgumentException("source cannot be null");
        }
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        this.source = source;
        this.message = message;
    }

    /**
     * @.obviousGetter
     */
    public String getSource() {
        return source;
    }

    /**
     * @.obviousSetter
     */
    public void setSource(String source) {
        if (source == null) {
            throw new IllegalArgumentException("source cannot be null");
        }
        this.source = source;
    }

    /**
     * @.obviousGetter
     */
    public String getMessage() {
        return message;
    }

    /**
     * @.obviousSetter
     */
    public void setMessage(String line) {
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        this.message = line;
    }
}
