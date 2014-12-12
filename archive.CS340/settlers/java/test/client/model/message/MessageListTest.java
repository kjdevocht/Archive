package client.model.message;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by mitch10e on 9/27/14.
 */
public class MessageListTest {

    @BeforeClass
    public static void setUp() {
        System.out.println("[INFO]\tRunning MessageListTest");
    }

    @Test
    public void testGettersAndSetters() {
        MessageList messages = new MessageList();

        messages.addMessage("1", "player 1");
        messages.addMessage("2", "player 2");
        messages.addMessage("3", "player 3");
        messages.addMessage("4", "player 4");

        assertEquals("player 1", messages.getMessage(0).getMessage());
        assertEquals("player 2", messages.getMessage(1).getMessage());
        assertEquals("player 3", messages.getMessage(2).getMessage());
        assertEquals("player 4", messages.getMessage(3).getMessage());

    }

    @Test
    public void testAddMessages() {
        MessageList messages = new MessageList();
        messages.addMessage("1", "player 1");
        messages.addMessage("2", "player 2");
        messages.addMessage("3", "player 3");
        messages.addMessage("4", "player 4");

        assertEquals(4, messages.getMessages().size());
        assertEquals("player 1", messages.getMessage(0).getMessage());
        assertEquals("player 2", messages.getMessage(1).getMessage());
        assertEquals("player 3", messages.getMessage(2).getMessage());
        assertEquals("player 4", messages.getMessage(3).getMessage());
        assertEquals("1", messages.getMessage(0).getSource());
        assertEquals("2", messages.getMessage(1).getSource());
        assertEquals("3", messages.getMessage(2).getSource());
        assertEquals("4", messages.getMessage(3).getSource());



    }

}
