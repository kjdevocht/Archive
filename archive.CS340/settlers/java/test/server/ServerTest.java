package server;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;
/**
 * Created by mitch10e on 11/18/14.
 */
public class ServerTest {

    private static IServer testServer;

    @BeforeClass
    public static void setUp() {
        System.out.println("[INFO]\tRunning ServerTest");
        testServer = new Server();
    }

    @Test
    public void testServerStart(){
        testServer.run(Server.TEST_PORT);



    }


}
