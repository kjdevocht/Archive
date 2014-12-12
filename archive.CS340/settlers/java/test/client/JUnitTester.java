package client;

import client.communication.ServerPollerTest;
import client.model.*;
import client.model.map.*;
import client.model.message.*;

import client.roll.RollControllerTest;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.RunWith;
import org.junit.runner.notification.Failure;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import server.ServerTest;
import serverProxy.ClientCommunicatorTest;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        TurnTrackerTest.class,
        MessageLineTest.class,
        MessageListTest.class,
        BonusTrackerTest.class,
	    CatanMapTest.class,
        ServerPollerTest.class,
        ClientCommunicatorTest.class,
        RollControllerTest.class,
        ServerTest.class
})
public class JUnitTester {
    public static void main(String[] args) {
        System.out.println("[INFO]\tRunning JUnit Test Cases");

        System.out.println("[WARN]\tMAKE SURE THE TA TEST SERVER IS RUNNING TO RUN TEST CASES.");

        List<Class> tests = new ArrayList<Class>();
	   // Map Test Cases
	    tests.add(CatanMapTest.class);

        // Model Test Cases
        tests.add(ClientModelTest.class);
        tests.add(PlayerTest.class);
        tests.add(TurnTrackerTest.class);
        tests.add(BonusTrackerTest.class);
        tests.add(MessageLineTest.class);
        tests.add(MessageListTest.class);

        // Server Proxy Test Cases
	    tests.add(ClientCommunicatorTest.class);

        // Server Poller Test Cases
	    tests.add(ServerPollerTest.class);

        // Controller Implementation Test Cases
        tests.add(RollControllerTest.class);

        // Server Test Cases
        tests.add(ServerTest.class);

        System.out.println("[INFO]\tNumber of Classes being tested: " + tests.size());

        for(Class test : tests) {
            Result result = JUnitCore.runClasses(test);
            for (Failure failure : result.getFailures()) {
                System.out.println(failure.toString());
            }
        }
        System.out.println("[INFO]\tEnd Test Cases");
        System.exit(0);
    }
}
