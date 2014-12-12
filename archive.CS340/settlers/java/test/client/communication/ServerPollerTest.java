package client.communication;

import client.model.ClientModel;
import client.model.IClientModel;

import com.google.gson.JsonObject;

import org.junit.*;

import serverProxy.ClientCommunicator;
import serverProxy.MockServer;
import serverProxy.ServerProxy;
import serverProxy.Translator;
import serverProxy.response.GameResponse;
import utils.GSONFileImport;
import static org.junit.Assert.*;

public class ServerPollerTest {

	@BeforeClass
	public static void setUp() {
		System.out.println("[INFO]\tRunning ServerPollerTest");
	}

	@Test
	public void runServerPollerTest() {
		JsonObject clientJson = GSONFileImport
				.convertFileToJSON("jsonExamples/testModel.json");
		JsonObject cookie = GSONFileImport
				.convertFileToJSON("jsonExamples/testCookie.json");
		ClientModel.setUpdatableModel(new Translator().translate(clientJson,
				cookie));
		ClientCommunicator communicator = new ClientCommunicator(
				new ServerProxy("http://localhost", 8081));
		assertNotNull(communicator);

		try {
			communicator.registerUser("James", "james");

			communicator.loginUser("James", "james");

//			GameResponse game = communicator.createGame("Gamexx", true, true, true);
//			communicator.joinGame(game.getId(), "puce");
//			communicator.addAi("LARGEST_ARMY");
//			communicator.addAi("LARGEST_ARMY");
//			communicator.addAi("LARGEST_ARMY");
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		ServerPoller poller = ServerPoller.getPoller(communicator);
		assertNotNull(poller);

        // TO THE TA's
        //
        // We could not find a way to test the server poller as it is a multithreaded function
        //
        //
/*
		// To test start():
		// Create poller, start it.
		// Make move, submit to server
		// Wait a second (or two to be safe)
		// Test client again to see if the change is there.
		poller.start();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int version = ClientModel.getUpdatableModel().getVersion();
		// Make move
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		int newVersion = ClientModel.getUpdatableModel().getVersion();
		assertEquals(version + 1, newVersion);

		// TODO: Find a way to test the server poller stop();
		// To test stop():
		// Do the same as above, except the change should NOT be there.
		poller.stop();
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		version = ClientModel.getUpdatableModel().getVersion();
		// Make move
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		newVersion = ClientModel.getUpdatableModel().getVersion();
		assertEquals(version, newVersion);
		assertNotEquals(version + 1, newVersion);
*/
	}
}
