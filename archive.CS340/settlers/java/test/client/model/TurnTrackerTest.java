package client.model;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by mitch10e on 9/27/14.
 */
public class TurnTrackerTest {

    @BeforeClass
    public static void setUp() {
        System.out.println("[INFO]\tRunning TurnTrackerTest");
    }

    @Test
    public void testGettersAndSetters() {

        TurnTracker tracker = new TurnTracker();

        // Check for init settings
        assertEquals("", tracker.getStatus());
        assertEquals(0, tracker.getCurrentTurn());

        tracker.setCurrentTurn(3);
        tracker.setStatus("status set");

        // Check that status is set
        assertEquals("status set", tracker.getStatus());

        // Check that player 4 is current player
        assertEquals(3, tracker.getCurrentTurn());
    }

    @Test
    public void testEndTurn() {
        TurnTracker tracker = new TurnTracker();

        // Check for Player 1 Turn
        assertEquals(0, tracker.getCurrentTurn());
        tracker.endTurn();

        // Check for Player 2 Turn
        assertEquals(1, tracker.getCurrentTurn());
        tracker.endTurn();

        // Check for Player 3 Turn
        assertEquals(2, tracker.getCurrentTurn());
        tracker.endTurn();

        //Check for PLayer 4 Turn
        assertEquals(3, tracker.getCurrentTurn());
        tracker.endTurn();

        // Check for Player 1 Turn
        assertEquals(0, tracker.getCurrentTurn());
        tracker.endTurn();

        // Check for Player 2 Turn
        assertEquals(1, tracker.getCurrentTurn());
        tracker.endTurn();

        // Check for Player 3 Turn
        assertEquals(2, tracker.getCurrentTurn());
        tracker.endTurn();

        //Check for PLayer 4 Turn
        assertEquals(3, tracker.getCurrentTurn());
    }
}
