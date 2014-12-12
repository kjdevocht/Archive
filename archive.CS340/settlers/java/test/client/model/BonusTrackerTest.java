package client.model;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by mitch10e on 9/29/14.
 */
public class BonusTrackerTest {

    @BeforeClass
    public static void setUp() {
        System.out.println("[INFO]\tRunning BonusTrackerTest");
    }

    @Test
    public void testGettersAndSetters() {
        BonusTracker tracker = new BonusTracker();
        // Check to see that Initialized to NO player having the longest road
        assertEquals(-1, tracker.getLongestRoad());
        // Check to see that Initialized to NO player having the largest army
        assertEquals(-1, tracker.getLargestArmy());

        tracker.setLargestArmy(3);
        tracker.setLongestRoad(2);

        // Check to see if player at index 3 has the largest army
        assertEquals(3, tracker.getLargestArmy());
        // Check to see if player at index 2 has the longest road
        assertEquals(2, tracker.getLongestRoad());


    }

}
