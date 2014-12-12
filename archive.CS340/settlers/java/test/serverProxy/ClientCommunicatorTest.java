package serverProxy;

import client.communication.IServerPoller;
import client.communication.ServerPoller;
import client.model.ClientModel;
import client.model.IClientModel;
import com.google.gson.JsonObject;
import org.junit.*;
import static org.junit.Assert.*;

import serverProxy.ClientCommunicator;
import serverProxy.response.FailSuccessResponse;
import serverProxy.response.GameListResponse;
import serverProxy.response.GameResponse;
import utils.GSONFileImport;

import java.util.List;

public class ClientCommunicatorTest {

    private IServer Server;

    @BeforeClass
    public static void setUp() {
        System.out.println("[INFO]\tRunning ClientCommunicatorTest");
    }

    @Before
    public void createEnvironment() {
        this.Server = new ServerProxy("http://localhost", Integer.parseInt("8081"));

        JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);

        
    }

    @Test
    public void testRegisterAndLoginUser() {
        try {
        	ClientCommunicator communicator = new ClientCommunicator(this.Server);
        	
        	communicator.registerUser("tstusr", "123456");
        	
            boolean l1 = communicator.loginUser("tstusr", "123456");
            boolean l2 = communicator.loginUser("FAIL", "fail");

            assertTrue("Correct Login", l1);
            assertFalse("False Login", l2);

        } catch (Exception e) {
            System.out.println("Test Cases failed: " + e.getMessage());
        }
    }

    @Test
    public void testCreateGame() {
        try {
        	ClientCommunicator communicator = new ClientCommunicator(this.Server);
            GameResponse response = communicator.createGame("TestCreateGame", true, true, true);
            assertEquals(response.getTitle(), "TestCreateGame");
        } catch (Exception e) {
            System.out.println("Test Cases failed: " + e.getMessage());
        }
    }

    @Test
    public void testJoinGame() {
        try {
        	ClientCommunicator communicator = new ClientCommunicator(this.Server);
        	communicator.registerUser("gmusr", "123456");        	
            communicator.loginUser("gmusr", "123456");
            GameResponse game = communicator.createGame("Join Game", true, true, true);
            
            boolean response = communicator.joinGame(game.getId(), "red");
            
            assertEquals(game.getTitle(), "Join Game");
            assertTrue("Join Game", response);
        } catch (Exception e) {
            System.out.println("Test Cases failed: " + e.getMessage());
        }
    }
    
    @Test 
    public void testPlayGame() {
    	try{
    		ClientCommunicator p1Communicator = new ClientCommunicator(this.Server);
    		ClientCommunicator p2Communicator = new ClientCommunicator(this.Server);
    		ClientCommunicator p3Communicator = new ClientCommunicator(this.Server);
    		ClientCommunicator p4Communicator = new ClientCommunicator(this.Server);
    		
    		p1Communicator.registerUser("p1usr", "123456");        	
    		p1Communicator.loginUser("p1usr", "123456");
            
    		p2Communicator.registerUser("p2usr", "123456");        	
    		p2Communicator.loginUser("p2usr", "123456");
            
    		p3Communicator.registerUser("p3usr", "123456");        	
    		p3Communicator.loginUser("p3usr", "123456");
            
    		p4Communicator.registerUser("p4usr", "123456");        	
    		p4Communicator.loginUser("p4usr", "123456");
    		
    		GameResponse game = p1Communicator.createGame("Test Play Game", true, true, true);
    		
    		assertTrue("P1 Join Red",p1Communicator.joinGame(game.getId(), "red"));
    		assertTrue("P2 Join Yellow",p2Communicator.joinGame(game.getId(), "yellow"));
    		assertTrue("P3 Join Blue",p3Communicator.joinGame(game.getId(), "blue"));
    		assertTrue("P4 Join White",p4Communicator.joinGame(game.getId(), "white"));		
    	} catch (Exception e) {
            System.out.println("Test Cases failed: " + e.getMessage());
        }
    }
}
