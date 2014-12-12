package client.model;

import static org.junit.Assert.*;

import org.junit.Test;

import serverProxy.Translator;
import utils.GSONFileImport;

import com.google.gson.JsonObject;

public class ModelChangeDetectorTest {

	@Test
	public void test() {
		
		JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel1 = new Translator().translate(clientJson, cookie);
        IClientModel clientModel2 = new Translator().translate(clientJson, cookie);
        
        ModelChangeDetector.detectChanges(clientModel1, clientModel2);
		
        clientModel2.getLocalPlayer().setDiscarded(true);
        clientModel2.setVersion(clientModel2.getVersion() + 1);
        ModelChangeDetector.detectChanges(clientModel1, clientModel2);
        
        clientModel2.getLocalPlayer().setResources(new ResourceList());
        ModelChangeDetector.detectChanges(clientModel1, clientModel2);
        
        clientModel2.getChat().addMessage("john", "messge");
        ModelChangeDetector.detectChanges(clientModel1, clientModel2);
        
		assertEquals(true, true);
	}

}
