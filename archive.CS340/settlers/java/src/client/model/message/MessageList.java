package client.model.message;

import java.util.ArrayList;
import java.util.List;

/**
 * @author mitch10e
 * Represents a list of messages that can be seen by all players of a game
 */
public class MessageList implements IMessageList {
    private List<MessageLine> messages;

    /**
     * Basic Constructor for <code>MessageList</code>
     * @.pre None
     * @.post A <code>MessageList</code> ready to use
     */
    public MessageList() {
        this.messages = new ArrayList<MessageLine>();
    }

    /**
     * @.pre player must be a valid index, message must have at least 1 character
     * @.post creates a new <code>MessageLine</code> at the end of the <code>MessageList</code>
     * @param source - the index of the player creating the message
     * @param message - the message the player wants to send
     */
    public void addMessage(String source, String message) {
        if (source == null) {
            throw new IllegalArgumentException("source cannot be null");
        }
        if (message == null) {
            throw new IllegalArgumentException("message cannot be null");
        }
        MessageLine msg = new MessageLine(source, message);
        messages.add(msg);
    }

    /**
     * @.obviousGetter
     */
    public List<MessageLine> getMessages() {
        return messages;
    }

    /**
     * @.pre at least one message must be in the list, the index cannot be outside the range
     * @.post gets the <code>MessageLine</code> at the specified index
     * @param index - the message number in the list
     */
    public MessageLine getMessage(int index) {
        if(index >= 0 && index < messages.size()) {
            return messages.get(index);
        }
        throw new IndexOutOfBoundsException("index out of bounds");
    }


}
