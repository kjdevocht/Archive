package server.handler;



import server.model.game.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import server.model.game.IDevCardList;
import server.model.game.IGameModel;
import server.model.game.IPlayer;
import server.model.game.IResourceList;
import server.model.game.ITradeOffer;
import server.model.map.*;
import server.model.message.IMessageLine;
import server.model.message.IMessageList;
import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.List;

/**
 * Created by curtis on 11/12/14.
 */
public class GameModelTranslator {
	
	
    public static JsonObject toJSON(IGameModel model) {
        JsonObject json = new JsonObject();

        JsonObject bank = resourceListToJson(model.getBank());
        JsonObject chat = messageListToJson(model.getChat());
        JsonObject log = messageListToJson(model.getLog());
        JsonArray players = playersToJson(model.getPlayers());
        JsonObject map = mapToJson(model.getMap());
        if(model.getTradeOffer() != null) {
        	json.add("tradeOffer", tradeOfferToJson(model.getTradeOffer()));
        }
        JsonObject turnTracker = turnTrackerToJson(model.getTurnTracker(), model.getBonusTracker());
        
        
        json.add("bank", bank);
        json.add("chat", chat);
        json.add("log", log);
        json.add("players", players);
        json.add("map", map);
        json.add("turnTracker", turnTracker);
        json.add("deck", devCardToJson(model.getDeck()));
        
        json.addProperty("version", model.getVersion());
        json.addProperty("winner", model.getWinner());

        return json;
    }
    private static JsonObject resourceListToJson(IResourceList bank) {
        JsonObject jsonObject = new JsonObject();
        for(ResourceType type:ResourceType.values()) {

            int qty = bank.getQuantity(type);
            jsonObject.addProperty(type.toString().toLowerCase(), qty);
        }
        return jsonObject;
    }
    private static JsonObject messageListToJson(IMessageList list) {
        JsonArray jsonArray = new JsonArray();
        for(IMessageLine line : list.getMessages()) {
            JsonObject lineJson = new JsonObject();
            lineJson.addProperty("message", line.getMessage());
            lineJson.addProperty("source", line.getSource());
            jsonArray.add(lineJson);
        }
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("lines", jsonArray);
        return jsonObject;
    }
    private static JsonArray playersToJson(List<IPlayer> players) {
        JsonArray jsonArray = new JsonArray();
        for(IPlayer player : players) {
            JsonObject playerJson = new JsonObject();
            playerJson.addProperty("cities", player.getCities());
            playerJson.addProperty("color", CatanColor.toString(player.getColor()));
            playerJson.addProperty("discarded", player.isDiscarded());
            playerJson.addProperty("monuments", player.getMonuments());
            playerJson.addProperty("name", player.getName());
            playerJson.addProperty("playerIndex", player.getPlayerIndex());
            playerJson.addProperty("playedDevCard", player.isPlayedDevCard());
            playerJson.addProperty("playerID", player.getPlayerId());
            playerJson.addProperty("roads", player.getRoads());
            playerJson.addProperty("settlements", player.getSettlements());
            playerJson.addProperty("soldiers", player.getSoldiers());
            playerJson.addProperty("victoryPoints", player.getVictoryPoints());

            JsonObject resources = resourceListToJson(player.getResources());
            playerJson.add("resources", resources);
            JsonObject oldDevCards = devCardToJson(player.getOldDevCards());
            playerJson.add("oldDevCards", oldDevCards);
            JsonObject newDevCards = devCardToJson(player.getNewDevCards());
            playerJson.add("newDevCards", newDevCards);
            jsonArray.add(playerJson);
        }
        while(jsonArray.size() < 4) {
        	jsonArray.add(null);
        }
        return jsonArray;
    }

    private static JsonObject devCardToJson(IDevCardList list) {
        JsonObject jsonObject = new JsonObject();
        for(DevCardType type: DevCardType.values()) {
            jsonObject.addProperty(DevCardType.toString(type), list.getDevCardCount(type));
        }
        return jsonObject;
    }

    private static JsonObject mapToJson(ICatanMap map) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.add("hexes", hexesToJson(map.getHexes()));
        jsonObject.add("ports", portsToJson(map.getPorts()));
        jsonObject.add("roads", roadsToJson(map.getRoads()));
        jsonObject.add("cities", communityToJson(map.getCities()));
        jsonObject.add("settlements", communityToJson(map.getSettlements()));
        jsonObject.addProperty("radius", 3);
        jsonObject.add("robber", robberToJson(map.getRobber()));        
        return jsonObject;
    }
    private static JsonArray hexesToJson(List<IHex> hexes) {
        JsonArray jsonArray = new JsonArray();
        for(IHex hex: hexes) {
            JsonObject jsonObject = new JsonObject();
            if(hex.getResource() != null) {
	            jsonObject.addProperty("resource", hex.getResource().toString().toLowerCase());
	            jsonObject.addProperty("number", hex.getNumber());
            }
            JsonObject location = new JsonObject();
            location.addProperty("x", hex.getLocation().getX());
            location.addProperty("y", hex.getLocation().getY());
            jsonObject.add("location", location);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    private static JsonArray portsToJson(List<IPort> ports) {
        JsonArray jsonArray = new JsonArray();
        for(IPort port: ports) {
            JsonObject jsonObject = new JsonObject();
            if(port.getResource() != null)
            	jsonObject.addProperty("resource", port.getResource().toString().toLowerCase());
            jsonObject.addProperty("ratio", port.getRatio());

            HexLocation hexLoc =  port.getLocation();
            EdgeDirection edgeDirection = port.getDirection();
            EdgeLocation edgeLocation = (new EdgeLocation(hexLoc, edgeDirection));
            jsonObject.addProperty("direction", EdgeLocation.getEdgeString(edgeLocation));

            JsonObject location = new JsonObject();
            location.addProperty("x", edgeLocation.getHexLoc().getX());
            location.addProperty("y", edgeLocation.getHexLoc().getY());
            jsonObject.add("location", location);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    private static JsonArray roadsToJson(List<IEdgeValue> roads) {
        JsonArray jsonArray = new JsonArray();
        for(IEdgeValue road: roads) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("owner", road.getOwner().getPlayerIndex());

            EdgeLocation edgeLocation =  road.getLocation().getRandomVariation();

            JsonObject location = new JsonObject();
            location.addProperty("x", edgeLocation.getHexLoc().getX());
            location.addProperty("y", edgeLocation.getHexLoc().getY());
            location.addProperty("direction", EdgeLocation.getEdgeString(edgeLocation));
            jsonObject.add("location", location);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    private static JsonArray communityToJson(List<ICommunity> communities) {
        JsonArray jsonArray = new JsonArray();
        for(ICommunity community: communities) {
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("owner", community.getOwner().getPlayerIndex());

            VertexLocation vertexLocation =  community.getLocation().getRandomVariation();

            JsonObject location = new JsonObject();
            location.addProperty("x", vertexLocation.getHexLoc().getX());
            location.addProperty("y", vertexLocation.getHexLoc().getY());
            location.addProperty("direction", VertexLocation.getVertexString(vertexLocation));
            jsonObject.add("location", location);
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }
    private static JsonObject tradeOfferToJson(ITradeOffer tradeOffer) {
    	int receiverId = tradeOffer.getReceiverId();
    	int senderId = tradeOffer.getSenderId();
    	
    	JsonObject jsonObject = new JsonObject();
    	jsonObject.addProperty("sender", senderId);
    	jsonObject.addProperty("receiver", receiverId);
    	
    	jsonObject.add("offer", resourceListToJson(tradeOffer.getResources()));
    	
    	return jsonObject;
    }
    private static JsonObject robberToJson(HexLocation robberLocation) {
    	JsonObject jsonObject = new JsonObject();
    	jsonObject.addProperty("x", robberLocation.getX());
    	jsonObject.addProperty("y", robberLocation.getY());
    	return jsonObject;
    }
    private static JsonObject turnTrackerToJson(ITurnTracker turnTracker, IBonusTracker bonusTracker) {
    	JsonObject jsonObject = new JsonObject();
    	jsonObject.addProperty("status", turnTracker.getStatus());
    	jsonObject.addProperty("currentTurn", turnTracker.getCurrentTurn());
    	jsonObject.addProperty("longestRoad", bonusTracker.getLongestRoad());
    	jsonObject.addProperty("largestArmy", bonusTracker.getLargestArmy());
    	return jsonObject;
    }
}
