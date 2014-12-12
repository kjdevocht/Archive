package client.roll;

import static org.junit.Assert.*;

import client.base.Controller;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created by mitch10e on 10/17/14.
 */
public class RollControllerTest {

    @BeforeClass
    public static void setUp() {
        System.out.println("[INFO]\tRunning RollControllerTest");
    }

    @Test
    public void testDiceRolls() {
        RollController roller = new RollController(null, null);

        // Roll Dice 100 times, check to make sure all are valid rolls
        for(int i = 0; i < 100; i++) {
            try {
                int roll = roller.getDiceRoll();
                // Check that dice roll is 2+
                assertTrue(roll >= 2);
                // Check that dice roll is 12-
                assertTrue(roll <=12);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}
