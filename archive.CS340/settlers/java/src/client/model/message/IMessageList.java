package client.model.message;
/**
 * @author mitch10e
 * Represents a list of messages that can be seen by all players of a game
 */

import java.util.List;

/**
 * public MessageList()
 * Basic Constructor for <code>MessageList</code>
 * @.pre None
 * @.post A <code>MessageList</code> ready to use
 */
public interface IMessageList {
    /**
     * @param source  - the index of the player creating the message
     * @param message - the message the player wants to send
     * @.pre player must be a valid index, message must have at least 1 character
     * @.post creates a new <code>MessageLine</code> at the end of the <code>MessageList</code>
     */
    public void addMessage(String source, String message);

    /**
     * @.obviousGetter
     */
    public List<MessageLine> getMessages();

    /**
     * @param index - the message number in the list
     * @.pre at least one message must be in the list, the index cannot be outside the range
     * @.post gets the <code>MessageLine</code> at the specified index
     */
    public MessageLine getMessage(int index);
}

