package client.model.message;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by mitch10e on 9/27/14.
 */
public class MessageLineTest {

    @BeforeClass
    public static void setUp() {
        System.out.println("[INFO]\tRunning MessageLineTest");
    }

    @Test
    public void testGettersAndSetters() {
        MessageLine messageLine1 = new MessageLine("1", "This is player 1");
        MessageLine messageLine2 = new MessageLine("2", "This is player 2");
        MessageLine messageLine3 = new MessageLine("3", "This is player 3");
        MessageLine messageLine4 = new MessageLine("4", "This is player 4");
        MessageLine messageLine5 = new MessageLine("1", "Do you have any grain?");
        MessageLine messageLine6 = new MessageLine("2", "no");
        MessageLine messageLine7 = new MessageLine("3", "I don't have any grain.");
        MessageLine messageLine8 = new MessageLine("4", "Yes, but I won't trade. :P");

        assertEquals("1", messageLine1.getSource());
        assertEquals("This is player 1", messageLine1.getMessage());

        assertEquals("2", messageLine2.getSource());
        assertEquals("This is player 2", messageLine2.getMessage());

        assertEquals("3", messageLine3.getSource());
        assertEquals("This is player 3", messageLine3.getMessage());

        assertEquals("4", messageLine4.getSource());
        assertEquals("This is player 4", messageLine4.getMessage());

        assertEquals("1", messageLine5.getSource());
        assertEquals("Do you have any grain?", messageLine5.getMessage());

        assertEquals("2", messageLine6.getSource());
        assertEquals("no", messageLine6.getMessage());

        assertEquals("3", messageLine7.getSource());
        assertEquals("I don't have any grain.", messageLine7.getMessage());

        assertEquals("4", messageLine8.getSource());
        assertEquals("Yes, but I won't trade. :P", messageLine8.getMessage());
    }

}
