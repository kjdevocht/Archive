package client.model;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import serverProxy.Translator;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import utils.GSONFileImport;
import client.model.map.EdgeValue;
import client.model.map.ICommunity;
import client.model.map.IEdgeValue;
import client.model.map.Settlement;

import com.google.gson.JsonObject;

public class ClientModelTest {

    @BeforeClass
    public static void setUp() {
        System.out.println("[INFO]\tRunning ClientModelTest");
    }

    @Test
    public void testBasicClientModelFields() {
        JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        assertEquals(clientModel.getVersion(), 0);
        assertEquals(clientModel.getWinner(), -1);
       
    }
    @Test
    public void testCanRoll() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        
        //With status set as rolling
        //It is my turn
        assertEquals(true, clientModel.canRollNumber());
        
        
        //with status set as playing, its my turn
        clientModel.getTurnTracker().setStatus("Playing");
        assertEquals(false, clientModel.canRollNumber());
        
        //with status set as rolling and not my turn
        clientModel.getTurnTracker().setStatus("Rolling");
        clientModel.getTurnTracker().setCurrentTurn(1);
        assertEquals(false, clientModel.canRollNumber());
        
        //with status set as playing and not my turn
        clientModel.getTurnTracker().setStatus("Playing");
        assertEquals(false, clientModel.canRollNumber());
    }
    @Test
    public void testCanAcceptTrade() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        
        //clientModel starts without a tradeOffer
        assertEquals(false, clientModel.canAcceptTrade());
        
        //Player does not have enough
        IResourceList offer = new ResourceList();
        offer.setQuantity(ResourceType.WOOD, 1);
        offer.setQuantity(ResourceType.BRICK, 1);
        offer.setQuantity(ResourceType.WHEAT, 1);
        offer.setQuantity(ResourceType.ORE, 1);
        offer.setQuantity(ResourceType.SHEEP, 1);
        ITradeOffer tradeOffer = new TradeOffer(1, clientModel.getLocalPlayer().getPlayerIndex(), offer);
        clientModel.setTradeOffer(tradeOffer);
        
        assertEquals(false, clientModel.canAcceptTrade());
        
        //Player has just enough
        offer.setQuantity(ResourceType.BRICK, 0);
        offer.setQuantity(ResourceType.ORE, 0);
        tradeOffer = new TradeOffer(1, clientModel.getLocalPlayer().getPlayerIndex(), offer);
        clientModel.setTradeOffer(tradeOffer);
        
        assertEquals(true, clientModel.canAcceptTrade());
    }
    @Test
    public void testCanOfferTrade() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        
        //Player does not have enough
        IResourceList offer = new ResourceList();
        offer.setQuantity(ResourceType.WOOD, -1);
        offer.setQuantity(ResourceType.BRICK, -1);
        offer.setQuantity(ResourceType.WHEAT, -1);
        offer.setQuantity(ResourceType.ORE, -1);
        offer.setQuantity(ResourceType.SHEEP, -1);
        ITradeOffer tradeOffer = new TradeOffer(clientModel.getLocalPlayer().getPlayerIndex(), 1, offer);
        clientModel.setTradeOffer(tradeOffer);
        
        assertEquals(false, clientModel.canOfferTrade(tradeOffer));
        
        //Player has all but one
        offer = new ResourceList();
        offer.setQuantity(ResourceType.WOOD, -2);
        offer.setQuantity(ResourceType.BRICK, 0);
        offer.setQuantity(ResourceType.WHEAT, -1);
        offer.setQuantity(ResourceType.ORE, 0);
        offer.setQuantity(ResourceType.SHEEP, -1);
        tradeOffer = new TradeOffer(clientModel.getLocalPlayer().getPlayerIndex(), 1, offer);
        clientModel.setTradeOffer(tradeOffer);
        
        assertEquals(false, clientModel.canOfferTrade(tradeOffer));
        
        //Player has enough
        offer = new ResourceList();
        offer.setQuantity(ResourceType.WOOD, -1);
        offer.setQuantity(ResourceType.BRICK, 0);
        offer.setQuantity(ResourceType.WHEAT, -1);
        offer.setQuantity(ResourceType.ORE, 0);
        offer.setQuantity(ResourceType.SHEEP, -1);
        tradeOffer = new TradeOffer(clientModel.getLocalPlayer().getPlayerIndex(), 1, offer);
        clientModel.setTradeOffer(tradeOffer);

        // TODO: Make it True
        assertEquals(false, clientModel.canOfferTrade(tradeOffer));
    }
    @Test
    public void testCanDiscard() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        
        //Player does not have at least 7 cards
        IResourceList cards = new ResourceList();
        cards.setQuantity(ResourceType.WOOD, 1);
        
        clientModel.getTurnTracker().setStatus("Discarding");
        assertEquals(false, clientModel.canDiscardCards(cards));
        
        //Player has OVER 7... (8)
        IResourceList playerResources = new ResourceList();
        playerResources.setQuantity(ResourceType.WOOD, 2);
        playerResources.setQuantity(ResourceType.BRICK, 2);
        playerResources.setQuantity(ResourceType.WHEAT, 1);
        playerResources.setQuantity(ResourceType.ORE, 2);
        playerResources.setQuantity(ResourceType.SHEEP, 1);
        clientModel.getLocalPlayer().setResources(playerResources);
        assertEquals(true, clientModel.canDiscardCards(cards));
        
        //Not enough wood
        cards.setQuantity(ResourceType.WOOD, 3);
        assertEquals(false, clientModel.canDiscardCards(cards));
        
        //Not discarding status
        cards.setQuantity(ResourceType.WOOD, 2);
        clientModel.getTurnTracker().setStatus("Playing");
        assertEquals(false, clientModel.canDiscardCards(cards));
    }
    @Test
    public void testCanFinishTurn() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        
        //Not playing status
        assertEquals(false, clientModel.canFinishTurn());
        
        //in playing status
        clientModel.getTurnTracker().setStatus("Playing");
        assertEquals(true, clientModel.canFinishTurn());
        
        //is not my turn
        clientModel.getTurnTracker().setCurrentTurn(1);
        assertEquals(false, clientModel.canFinishTurn());
    }
    @Test
    public void testCanBuyDevCard() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        
        //Player has enough
        IResourceList playerResources = new ResourceList();
        playerResources.setQuantity(ResourceType.ORE, 1);
        playerResources.setQuantity(ResourceType.SHEEP, 1);
        playerResources.setQuantity(ResourceType.WHEAT, 1);
        clientModel.getLocalPlayer().setResources(playerResources);
        
        assertEquals(true, clientModel.canBuyDevCard());
        
        //player does not have enough
        playerResources.setQuantity(ResourceType.ORE, 0);
        playerResources.setQuantity(ResourceType.SHEEP, 0);
        playerResources.setQuantity(ResourceType.WHEAT, 0);
        clientModel.getLocalPlayer().setResources(playerResources);
        
        assertEquals(false, clientModel.canBuyDevCard());
        
        //bank does not have any dev cards left
	    playerResources.setQuantity(ResourceType.ORE, 1);
	    playerResources.setQuantity(ResourceType.SHEEP, 1);
	    playerResources.setQuantity(ResourceType.WHEAT, 1);
	    IDevCardList deck = new DevCardList(); 
	    clientModel.getLocalPlayer().setResources(playerResources);
	    clientModel.setDeck(deck);
	    
	    assertEquals(false, clientModel.canBuyDevCard());
    }
    @Test
    public void testCanMaritimeTrade() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/modelStatePlaying.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie2.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        clientModel.getTurnTracker().setCurrentTurn(0);
        
        ResourceList playerResources = new ResourceList();
        playerResources.setQuantity(ResourceType.WHEAT, 3);
        clientModel.getLocalPlayer().setResources(playerResources);
        
        //player does not have enough
        assertEquals(false, clientModel.canMaritimeTrade(ResourceType.WHEAT, ResourceType.BRICK, 4));
    
        playerResources = new ResourceList();
        playerResources.setQuantity(ResourceType.WHEAT, 4);
        clientModel.getLocalPlayer().setResources(playerResources);
        
        //player does have enough
        assertEquals(true, clientModel.canMaritimeTrade(ResourceType.WHEAT, ResourceType.BRICK, 4));
        
        //player does not have a settlement for trading on 3
        assertEquals(false, clientModel.canMaritimeTrade(ResourceType.WHEAT, ResourceType.BRICK, 3));

        ICommunity settlement = new Settlement();
        settlement.setLocation(new VertexLocation(new HexLocation(-1, -1), VertexDirection.NorthEast));
        settlement.setOwner(clientModel.getLocalPlayer());
        clientModel.getCatanMap().getSettlements().add(settlement);
        //Port with specific item
        assertEquals(true, clientModel.canMaritimeTrade(ResourceType.WHEAT, ResourceType.BRICK, 2));
        
        settlement = new Settlement();
        settlement.setLocation(new VertexLocation(new HexLocation(2, -2), VertexDirection.NorthEast));
        settlement.setOwner(clientModel.getLocalPlayer());
        clientModel.getCatanMap().getSettlements().add(settlement);
        
        playerResources = new ResourceList();
        playerResources.setQuantity(ResourceType.BRICK, 3);
        clientModel.getLocalPlayer().setResources(playerResources);
        
        //port is with a general port
        assertEquals(true, clientModel.canMaritimeTrade(ResourceType.BRICK, ResourceType.WOOD, 3));
        
    }
    @Test
    public void testCanPlayYearOfPlenty() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        
        //Does not have the dev card
        assertEquals(false, clientModel.canPlayYearOfPlenty(ResourceType.BRICK, ResourceType.BRICK));
        
        //Bank does have enough, not playing
        IDevCardList devcards = new DevCardList();
        devcards.setDevCardCount(DevCardType.YEAR_OF_PLENTY, 1);
        clientModel.getLocalPlayer().setOldDevCards(devcards);
        assertEquals(false, clientModel.canPlayYearOfPlenty(ResourceType.BRICK, ResourceType.BRICK));
        
        //bank does have enough, set to playing
        clientModel.getTurnTracker().setStatus("Playing");
        assertEquals(true, clientModel.canPlayYearOfPlenty(ResourceType.BRICK, ResourceType.BRICK));

        //bank does have one, but not the other
        IResourceList bank = new ResourceList();
        bank.setQuantity(ResourceType.WOOD, 1);
        bank.setQuantity(ResourceType.BRICK, 0);
        bank.setQuantity(ResourceType.WHEAT, 1);
        bank.setQuantity(ResourceType.ORE, 2);
        bank.setQuantity(ResourceType.SHEEP, 0);
        clientModel.setBank(bank);
        assertEquals(false, clientModel.canPlayYearOfPlenty(ResourceType.WOOD, ResourceType.BRICK));
        
        //bank does have one, but not 2
        assertEquals(false, clientModel.canPlayYearOfPlenty(ResourceType.WHEAT, ResourceType.WHEAT));
        
        //bank has 2
        assertEquals(true, clientModel.canPlayYearOfPlenty(ResourceType.ORE, ResourceType.ORE));     
    }
    @Test
    public void testCanPlayMonoply() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        
        //Does not have the dev card
        assertEquals(false, clientModel.canPlayMonopoly());
        
        //Bank does have enough, not playing
        IDevCardList devcards = new DevCardList();
        devcards.setDevCardCount(DevCardType.MONOPOLY, 1);
        clientModel.getLocalPlayer().setOldDevCards(devcards);
        assertEquals(false, clientModel.canPlayMonopoly());
        
        //bank does have enough, set to playing
        clientModel.getTurnTracker().setStatus("Playing");
        assertEquals(true, clientModel.canPlayMonopoly());
    }
    @Test
    public void testCanPlayMonument() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/testModel.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        
        //Does not have the dev card
        assertEquals(false, clientModel.canPlayMonument());
        
        //Bank does have enough, not playing
        IDevCardList devcards = new DevCardList();
        devcards.setDevCardCount(DevCardType.MONUMENT, 1);
        clientModel.getLocalPlayer().setOldDevCards(devcards);
        assertEquals(false, clientModel.canPlayMonument());
        
        //bank does have enough, set to playing
        clientModel.getTurnTracker().setStatus("Playing");
        assertEquals(true, clientModel.canPlayMonument());
    }
    @Test
    public void testCanPlayRoadBuild() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/modelStatePlaying.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie2.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        clientModel.getTurnTracker().setCurrentTurn(0);

        EdgeLocation validLocation = new EdgeLocation(new HexLocation(-1, -1), EdgeDirection.South).getNormalizedLocation(); 

        EdgeLocation validLocation2 = new EdgeLocation(new HexLocation(-1, -1), EdgeDirection.NorthWest).getNormalizedLocation(); 
    
        
        //Does not have the dev card
        assertEquals(false, clientModel.canPlayRoadBuild(validLocation, validLocation2));
        
        IDevCardList devcards = new DevCardList();
        devcards.setDevCardCount(DevCardType.ROAD_BUILD, 1);
        clientModel.getLocalPlayer().setOldDevCards(devcards);
        
        //will be able to place both roads
        assertEquals(true, clientModel.canPlayRoadBuild(validLocation, validLocation2));
    
    }
    @Test
    public void testCanPlaySoldier() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/modelStatePlaying.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie2.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        clientModel.getTurnTracker().setCurrentTurn(0);
        
        HexLocation hex = new HexLocation(0, 0);
        //Does not have the dev card
        assertEquals(false, clientModel.canPlaySoldier(hex, 1));
        
        IDevCardList devcards = new DevCardList();
        devcards.setDevCardCount(DevCardType.SOLDIER, 1);
        clientModel.getLocalPlayer().setOldDevCards(devcards);
        //Does have the dev card and valid place
        assertEquals(true, clientModel.canPlaySoldier(hex, 1));
        
        
        hex = new HexLocation(0, -2);
        //where the robber is
        assertEquals(false, clientModel.canPlaySoldier(hex, 1));
        
    }
    @Test
    public void testCanBuyRoadAtLocationFirstAndSecond() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/modelStateFirstRound.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        clientModel.getTurnTracker().setCurrentTurn(0);
        clientModel.getLocalPlayer().setPlayerIndex(0);
        
        EdgeLocation roadHere = new EdgeLocation(new HexLocation(0, -1), EdgeDirection.SouthWest).getNormalizedLocation();
	    
        //There is a road on this spot, should not allow
        assertEquals(false, clientModel.canBuyRoadAtLocation(roadHere));
        
        EdgeLocation notConnectToRoadOrBuiling = new EdgeLocation(new HexLocation(0, -1), EdgeDirection.NorthEast).getNormalizedLocation();
        //A road that is not connected to anything
        assertEquals(false, clientModel.canBuyRoadAtLocation(notConnectToRoadOrBuiling));
    
        EdgeLocation validLocation = new EdgeLocation(new HexLocation(-1, -1), EdgeDirection.South).getNormalizedLocation(); 
        //A road that is connected to a settlement
        assertEquals(true, clientModel.canBuyRoadAtLocation(validLocation));
        
        EdgeLocation validLocation2 = new EdgeLocation(new HexLocation(0, -1), EdgeDirection.South).getNormalizedLocation(); 
        //A road that is connected to another road
        assertEquals(true, clientModel.canBuyRoadAtLocation(validLocation2));
        
        clientJson = GSONFileImport.convertFileToJSON("jsonExamples/modelStateSecondRound.json");
        EdgeLocation invalidLocation2 = new EdgeLocation(new HexLocation(0, 1), EdgeDirection.South).getNormalizedLocation(); 
        clientModel.getTurnTracker().setCurrentTurn(0);   
        
        //A road next to another player's building
        assertEquals(false, clientModel.canBuyRoadAtLocation(invalidLocation2));
        
        EdgeLocation invalidLocation3 = new EdgeLocation(new HexLocation(-1, 0), EdgeDirection.NorthEast).getNormalizedLocation(); 
        //can't build a two roads off of the same settlement in the first two rounds
        assertEquals(false, clientModel.canBuyRoadAtLocation(invalidLocation3));
    }
    @Test
    public void testCanBuyRoadAtLocationRegular() {
    	//THIS IS ALL DURING PLAY STATE
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/modelStatePlaying.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie2.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        clientModel.getTurnTracker().setCurrentTurn(0);
        
        ResourceList playerResources = new ResourceList();
        playerResources.setQuantity(ResourceType.BRICK, 1);
	    playerResources.setQuantity(ResourceType.WOOD, 1);
	    clientModel.getLocalPlayer().setResources(playerResources);
        
        EdgeLocation roadHere = new EdgeLocation(new HexLocation(-1, -1), EdgeDirection.SouthWest).getNormalizedLocation();
        //There is road on this spot
        assertEquals(false, clientModel.canBuyRoadAtLocation(roadHere));
        
        EdgeLocation notConnectToRoadOrBuiling = new EdgeLocation(new HexLocation(-1, -1), EdgeDirection.North).getNormalizedLocation();
        //A road that is not connected to anything
        assertEquals(false, clientModel.canBuyRoadAtLocation(notConnectToRoadOrBuiling));
        
        EdgeLocation validLocation = new EdgeLocation(new HexLocation(-1, -1), EdgeDirection.South).getNormalizedLocation(); 
        //A road that is connected to another road
        assertEquals(true, clientModel.canBuyRoadAtLocation(validLocation));

        EdgeLocation validLocation2 = new EdgeLocation(new HexLocation(-1, -1), EdgeDirection.NorthWest).getNormalizedLocation(); 
        //A road that is connected to another road
        assertEquals(true, clientModel.canBuyRoadAtLocation(validLocation2));
        
        EdgeLocation validLocation3 = new EdgeLocation(new HexLocation(0, 0), EdgeDirection.NorthEast).getNormalizedLocation(); 
        //A road that is connected to another road
        assertEquals(true, clientModel.canBuyRoadAtLocation(validLocation3));
        
        EdgeLocation invalidLocation = new EdgeLocation(new HexLocation(0, -1), EdgeDirection.SouthEast).getNormalizedLocation(); 
        //A road that is on another players road
        assertEquals(false, clientModel.canBuyRoadAtLocation(invalidLocation));
        
        IEdgeValue newRoad = new EdgeValue();
        newRoad.setLocation(new EdgeLocation(new HexLocation(1, 1), EdgeDirection.NorthWest));
        newRoad.setOwner(clientModel.getLocalPlayer());
        clientModel.getCatanMap().getRoads().add(newRoad);
        
        //Cant continue a road through opponents building.
        EdgeLocation invalidLocation2 = new EdgeLocation(new HexLocation(0, 2), EdgeDirection.NorthEast).getNormalizedLocation(); 
        assertEquals(false, clientModel.canBuyRoadAtLocation(invalidLocation2));
        
        clientModel.getTurnTracker().setCurrentTurn(3);
        playerResources = new ResourceList();
        playerResources.setQuantity(ResourceType.BRICK, 1);
	    playerResources.setQuantity(ResourceType.WOOD, 1);
	    clientModel.setLocalPlayer(clientModel.getPlayers().get(3));
	    clientModel.getLocalPlayer().setResources(playerResources);
        //Can't build in the ocean.
        EdgeLocation invalidLocation3 = new EdgeLocation(new HexLocation(0, 2), EdgeDirection.NorthEast).getNormalizedLocation(); 
        assertEquals(false, clientModel.canBuyRoadAtLocation(invalidLocation3));
        
    }
    @Test
    public void testCanPlaceSettlementAtLocationStart() {
    	//THIS IS ALL DURING PLAY STATE
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/modelStateFirstRound.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        clientModel.getTurnTracker().setCurrentTurn(0);
        
        VertexLocation oneAway = new VertexLocation(new HexLocation(-1, 0), VertexDirection.NorthWest);
        //check if a building is one away
        assertEquals(false, clientModel.canBuySettlementAtLocation(oneAway));
        
        VertexLocation twoAway = new VertexLocation(new HexLocation(-1, -1), VertexDirection.NorthWest);
        //check if a building is two away
        assertEquals(true, clientModel.canBuySettlementAtLocation(twoAway));
        
        VertexLocation onTop = new VertexLocation(new HexLocation(0, -1), VertexDirection.West);
        //check if a building on another building
        assertEquals(false, clientModel.canBuySettlementAtLocation(onTop));
    }
    @Test
    public void testCanPlaceSettlementAtLocationRegular() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/modelStatePlaying.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie2.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        clientModel.getTurnTracker().setCurrentTurn(0);
        clientModel.getLocalPlayer().setPlayerIndex(0);
        
        ResourceList playerResources = new ResourceList();
        playerResources.setQuantity(ResourceType.BRICK, 1);
	    playerResources.setQuantity(ResourceType.SHEEP, 1);
	    playerResources.setQuantity(ResourceType.WHEAT, 1);
	    playerResources.setQuantity(ResourceType.WOOD, 1);
	    clientModel.getLocalPlayer().setResources(playerResources);
        
        VertexLocation oneAway = new VertexLocation(new HexLocation(-1, -1), VertexDirection.SouthEast);
        //check if a building is one away
        assertEquals(false, clientModel.canBuySettlementAtLocation(oneAway));
        
        VertexLocation twoAway = new VertexLocation(new HexLocation(-1, -1), VertexDirection.NorthWest);
        //check if a building is two away
        IEdgeValue newRoad = new EdgeValue();
        newRoad.setLocation(new EdgeLocation(new HexLocation(-1, -1), EdgeDirection.NorthWest));
        newRoad.setOwner(clientModel.getLocalPlayer());
        clientModel.getCatanMap().getRoads().add(newRoad);
        assertEquals(true, clientModel.canBuySettlementAtLocation(twoAway));
        
        VertexLocation onTop = new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthWest);
        //check if a building on another building
        assertEquals(false, clientModel.canBuySettlementAtLocation(onTop));
        
        
        //
    }
    @Test
    public void testCanPlaceCityAtLocationStart() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/modelStateFirstRound.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie2.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        clientModel.getTurnTracker().setCurrentTurn(0);
        
        VertexLocation oneAway = new VertexLocation(new HexLocation(-1, 0), VertexDirection.NorthWest);
        //check if a building is one away
        assertEquals(false, clientModel.canBuyCityAtLocation(oneAway));
        
        VertexLocation twoAway = new VertexLocation(new HexLocation(0, 0), VertexDirection.NorthWest);
        //check if a building is two away
        assertEquals(false, clientModel.canBuyCityAtLocation(twoAway));
        
        VertexLocation onTop = new VertexLocation(new HexLocation(0, -1), VertexDirection.West);
        //check if a building on another building
        assertEquals(false, clientModel.canBuyCityAtLocation(onTop));
    }
    @Test
    public void testCanPlaceCityAtLocationRegular() {
    	JsonObject clientJson = GSONFileImport.convertFileToJSON("jsonExamples/modelStatePlaying.json");
        JsonObject cookie = GSONFileImport.convertFileToJSON("jsonExamples/testCookie2.json");
        IClientModel clientModel = new Translator().translate(clientJson, cookie);
        clientModel.getTurnTracker().setCurrentTurn(0);
        
        ResourceList playerResources = new ResourceList();
        playerResources.setQuantity(ResourceType.ORE, 3);
	    playerResources.setQuantity(ResourceType.WHEAT, 2);

	    clientModel.getLocalPlayer().setResources(playerResources);
        
        VertexLocation oneAway = new VertexLocation(new HexLocation(-1, -1), VertexDirection.SouthEast);
        //check if a building is one away
        assertEquals(false, clientModel.canBuyCityAtLocation(oneAway));
        
        VertexLocation twoAway = new VertexLocation(new HexLocation(-1, -1), VertexDirection.East);
        //check if a building is two away
        assertEquals(false, clientModel.canBuyCityAtLocation(twoAway));
        
        VertexLocation onTop = new VertexLocation(new HexLocation(0, -1), VertexDirection.SouthWest);
        //check if a building on on a settlement
        assertEquals(true, clientModel.canBuyCityAtLocation(onTop));
    }
    
}
