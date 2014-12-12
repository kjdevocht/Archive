package client.model;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import serverProxy.Translator;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import utils.GSONFileImport;

import com.google.gson.JsonObject;

public class PlayerTest {
	@BeforeClass
    public static void setUp() {
        System.out.println("[INFO]\tRunning PlayerTest");
    }
	@Test
	public void testBasicPlayerFields() {
		JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        IPlayer player = clientModel.getLocalPlayer();
        
        assertEquals("Sam", player.getName());
        assertEquals(CatanColor.RED, player.getColor());
        assertEquals(13, player.getRoads());
        assertEquals(4, player.getCities());
        assertEquals(3, player.getSettlements());
        assertEquals(0, player.getSoldiers());
        assertEquals(2, player.getVictoryPoints());
        assertEquals(0, player.getMonuments());
        assertEquals(false, player.isPlayedDevCard());
        assertEquals(false, player.isDiscarded());
        assertEquals(0, player.getPlayerId());
        assertEquals(0, player.getPlayerIndex());
	}
	@Test
	public void testCanBuySettlement() {
		JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        IPlayer player = clientModel.getLocalPlayer();
        
        //Does not have the resources
        assertEquals(false, player.canBuySettlement());
        
        //Does have the resources
        IResourceList resources = new ResourceList();
        resources.setQuantity(ResourceType.WOOD, 1);
        resources.setQuantity(ResourceType.BRICK, 1);
        resources.setQuantity(ResourceType.WHEAT, 1);
        resources.setQuantity(ResourceType.ORE, 0);
        resources.setQuantity(ResourceType.SHEEP, 1);
        player.setResources(resources);
        
        assertEquals(true, player.canBuySettlement());
	}
	@Test
	public void testCanBuyCity() {
		JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        IPlayer player = clientModel.getLocalPlayer();
        
        //Does not have the resources
        assertEquals(false, player.canBuyCity());
        
        //Does have the resources
        IResourceList resources = new ResourceList();
        resources.setQuantity(ResourceType.WOOD, 0);
        resources.setQuantity(ResourceType.BRICK, 0);
        resources.setQuantity(ResourceType.WHEAT, 2);
        resources.setQuantity(ResourceType.ORE, 3);
        resources.setQuantity(ResourceType.SHEEP, 0);
        player.setResources(resources);
        
        assertEquals(true, player.canBuyCity());
	}
	@Test
	public void testCanBuyRoad() {
		JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        IPlayer player = clientModel.getLocalPlayer();
        
        //Does not have the resources
        assertEquals(false, player.canBuyRoad());
        
        //Does have the resources
        IResourceList resources = new ResourceList();
        resources.setQuantity(ResourceType.BRICK, 1);
        resources.setQuantity(ResourceType.WOOD, 1);

        player.setResources(resources);
        
        assertEquals(true, player.canBuyRoad());
	}
	@Test
	public void testCanDevCard() {
		JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        IPlayer player = clientModel.getLocalPlayer();
        
        //Does not have the resources
        assertEquals(false, player.canBuyDevCard());
        
        //Does have the resources
        IResourceList resources = new ResourceList();
        resources.setQuantity(ResourceType.ORE, 1);
        resources.setQuantity(ResourceType.SHEEP, 1);
        resources.setQuantity(ResourceType.WHEAT, 1);

        player.setResources(resources);
        
        assertEquals(true, player.canBuyDevCard());
	}
	@Test
	public void canPlayDevCard() {
		JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        IPlayer player = clientModel.getLocalPlayer();
        
        //Does not have devcards
        assertEquals(false, player.canPlayDevCard(DevCardType.YEAR_OF_PLENTY));
        assertEquals(false, player.canPlayDevCard(DevCardType.SOLDIER));
        assertEquals(false, player.canPlayDevCard(DevCardType.MONOPOLY));
        assertEquals(false, player.canPlayDevCard(DevCardType.MONUMENT));
        assertEquals(false, player.canPlayDevCard(DevCardType.ROAD_BUILD));
        
        //Does have devcards
        IDevCardList devcards = new DevCardList();
        devcards.setDevCardCount(DevCardType.YEAR_OF_PLENTY, 1);
        devcards.setDevCardCount(DevCardType.SOLDIER, 1);
        devcards.setDevCardCount(DevCardType.MONOPOLY, 1);
        devcards.setDevCardCount(DevCardType.MONUMENT, 1);
        devcards.setDevCardCount(DevCardType.ROAD_BUILD, 1);
        player.setOldDevCards(devcards);
        
        assertEquals(true, player.canPlayDevCard(DevCardType.YEAR_OF_PLENTY));
        assertEquals(true, player.canPlayDevCard(DevCardType.SOLDIER));
        assertEquals(true, player.canPlayDevCard(DevCardType.MONOPOLY));
        assertEquals(true, player.canPlayDevCard(DevCardType.MONUMENT));
        assertEquals(true, player.canPlayDevCard(DevCardType.ROAD_BUILD));
        
        //has already discarded
        player.setPlayedDevCard(true);
        assertEquals(false, player.canPlayDevCard(DevCardType.YEAR_OF_PLENTY));
        assertEquals(false, player.canPlayDevCard(DevCardType.SOLDIER));
        assertEquals(false, player.canPlayDevCard(DevCardType.MONOPOLY));
        assertEquals(false, player.canPlayDevCard(DevCardType.MONUMENT));
        assertEquals(false, player.canPlayDevCard(DevCardType.ROAD_BUILD));
	}
	@Test
	public void canOfferTrade() {
		JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        IPlayer player = clientModel.getLocalPlayer();
        
        //has the resources
        IResourceList resources = new ResourceList();
        resources.setQuantity(ResourceType.BRICK, 2);
        resources.setQuantity(ResourceType.WOOD, -1);
        resources.setQuantity(ResourceType.WHEAT, -1);
        resources.setQuantity(ResourceType.SHEEP, -1);
        resources.setQuantity(ResourceType.ORE, 2);
        ITradeOffer tradeOffer = new TradeOffer(0, 1, resources);
        
        assertEquals(true, player.canOfferTradeOffer(tradeOffer));
        
        //does not have the resources 
        resources.setQuantity(ResourceType.BRICK, 2);
        resources.setQuantity(ResourceType.WOOD, -2);
        resources.setQuantity(ResourceType.WHEAT, -2);
        resources.setQuantity(ResourceType.SHEEP, -2);
        resources.setQuantity(ResourceType.ORE, 2);
        tradeOffer = new TradeOffer(0, 1, resources);
        assertEquals(false, player.canOfferTradeOffer(tradeOffer));
        
	}
	@Test
	public void canAcceptTrade() {
		JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        IPlayer player = clientModel.getLocalPlayer();

        //has the resources
        IResourceList resources = new ResourceList();
        resources.setQuantity(ResourceType.BRICK, -2);
        resources.setQuantity(ResourceType.WOOD, 1);
        resources.setQuantity(ResourceType.WHEAT, 1);
        resources.setQuantity(ResourceType.SHEEP, 1);
        resources.setQuantity(ResourceType.ORE, -2);
        ITradeOffer tradeOffer = new TradeOffer(1, 0, resources);

        //does not have the resources
        resources = new ResourceList();
        resources.setQuantity(ResourceType.BRICK, -2);
        resources.setQuantity(ResourceType.WOOD, 2);
        resources.setQuantity(ResourceType.WHEAT, 2);
        resources.setQuantity(ResourceType.SHEEP, 2);
        resources.setQuantity(ResourceType.ORE, -2);
        tradeOffer = new TradeOffer(1, 0, resources);
	}
	
}
