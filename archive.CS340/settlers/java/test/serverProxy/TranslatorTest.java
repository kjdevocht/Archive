package serverProxy;

import static org.junit.Assert.assertEquals;

import org.junit.*;

import utils.GSONFileImport;
import client.model.IClientModel;

import com.google.gson.JsonObject;

/**
 * Created by mitch10e on 9/29/14.
 */
public class TranslatorTest {

    @BeforeClass
    public void setUp() {
        System.out.println("[INFO]\tRunning TranslatorTest");
    }
    //This is very much used to test the client model, so the
    //majority of testing if this is correct will fall under
    //the test cased for the client model tests
    @Test
    public void testCreateModel() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        assertEquals(clientModel.getVersion(), 0);
        assertEquals(clientModel.getWinner(), -1);
    }
}
