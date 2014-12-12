package serverProxy;

import java.io.BufferedReader;
import java.net.CookieStore;
import java.net.HttpCookie;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
import client.model.BonusTracker;
import client.model.ClientModel;
import client.model.DevCardList;
import client.model.IBonusTracker;
import client.model.IDevCardList;
import client.model.IPlayer;
import client.model.IResourceList;
import client.model.ITradeOffer;
import client.model.ITurnTracker;
import client.model.Player;
import client.model.ResourceList;
import client.model.TradeOffer;
import client.model.TurnTracker;
import client.model.map.CatanMap;
import client.model.map.City;
import client.model.map.EdgeValue;
import client.model.map.Hex;
import client.model.map.ICatanMap;
import client.model.map.ICommunity;
import client.model.map.IEdgeValue;
import client.model.map.IHex;
import client.model.map.IPort;
import client.model.map.Port;
import client.model.map.Settlement;
import client.model.message.IMessageList;
import client.model.message.MessageList;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

/**
 * Created by mitch10e on 9/29/14.
 */
public class Translator implements ITranslator {

	public List<IPlayer> players;
	public static Map<HexLocation, HexLocation> cordToHex;

	public static void main(String[] args) {
		// JsonObject json = GSONFileImport.convertFileToJSON("testModel.json");
		// Translator translator = new Translator();
		// ClientModel clientModel = translator.translate(json,null);
		// System.out.println();
	}

	/**
	 * Updates the given ClientModel based on the contents of the given
	 * BufferedReader. If the reader contains "true", then do nothing.
	 * Otherwise, assume the reader is JSON, and update the given ClientModel to
	 * match that JSON.
	 * 
	 * @.pre reader either contains "true" or a JSON representation of a
	 *       ClientModel.
	 * @.post If reader was "true", the model is unchanged. Otherwise, returns
	 *        an updated model based on the JSON in the reader.
	 * @param reader
	 * @param cookies
	 * @param model
	 */
	public ClientModel translate(BufferedReader reader, CookieStore cookies, ClientModel model) {
		StringBuilder sb = new StringBuilder();

		Scanner scan = new Scanner(reader);
		scan.useDelimiter("\n");
		while (scan.hasNext()) {
			sb.append(scan.next());
			if (scan.hasNext()) {
				sb.append("\n");
			}
		}
		scan.close();

		String input = sb.toString();

		if (input.equalsIgnoreCase("\"true\"")) {
			return model;
		}

		// TODO: Does this work? Test it.
		// Get the right cookie
		HttpCookie cookie = null;
		for (HttpCookie c : cookies.getCookies()) {
			if (c.getName().equalsIgnoreCase("catan.user")) {
				cookie = c;
			}
		}
		// Decode it
		String codedCookie = cookie.getValue().replace("catan.user=", "").replace(";Path=/;", "")
				.trim();
		@SuppressWarnings("deprecation")
		String decodedCookie = URLDecoder.decode(codedCookie);
		// Turn into JSON
		JsonObject cookieJson = new Gson().fromJson(decodedCookie, JsonObject.class);
		// Turn the reader text into JSON
		JsonObject json = new Gson().fromJson(input, JsonObject.class);
		// Translate
		model = translate(json, cookieJson);
		return model;
	}

	@Override
	public ClientModel translate(JsonObject input, JsonObject cookie) {
		cordToHex = new HashMap<>();

		int version = input.getAsJsonPrimitive("version").getAsInt();
		int winner = input.getAsJsonPrimitive("winner").getAsInt();
		ITurnTracker turnTracker = getTurnTracker(input);
		IMessageList chat = getMessageList(input.getAsJsonObject("chat"));
		IMessageList log = getMessageList(input.getAsJsonObject("log"));
		IResourceList bank = getResourceList(input.getAsJsonObject("bank"));
		IBonusTracker bonusTracker = getBonusTracker(input.getAsJsonObject("turnTracker"));
		ITradeOffer tradeOffer = null;
		if (input.has("tradeOffer"))
			tradeOffer = getTradeOffer(input.getAsJsonObject("tradeOffer"));
		IDevCardList deck = getDevCardList(input.getAsJsonObject("deck"));

		players = getPlayers(input.getAsJsonArray("players"));

		int playerId = cookie.get("playerID").getAsInt();
		IPlayer localPlayer = null;
		for (IPlayer player : players) {
			if (player.getPlayerId() == playerId)
				localPlayer = player;
		}
		if(localPlayer == null) {
			localPlayer = new Player(cookie.get("name").getAsString(), playerId, CatanColor.fromString(cookie.get("color").getAsString()));
		}

		ICatanMap catanMap = getCatanMap(input.getAsJsonObject("map"));

		ClientModel model = new ClientModel(turnTracker, winner, version, chat, log, catanMap,
				players, bank, bonusTracker, tradeOffer, localPlayer, deck);

		return model;
	}

	private CatanMap getCatanMap(JsonObject catanMapJson) {

		CatanMap catanMap = new CatanMap();

		List<IHex> hexes = new ArrayList<>();
		JsonArray hexesJson = catanMapJson.getAsJsonArray("hexes");
		for (int i = 0; i < hexesJson.size(); i++) {
			IHex hex = getHex(hexesJson.get(i).getAsJsonObject());
			hexes.add(hex);
		}
		catanMap.setHexes(hexes);

		List<IPort> ports = new ArrayList<>();
		JsonArray portsJson = catanMapJson.getAsJsonArray("ports");
		for (int i = 0; i < portsJson.size(); i++) {
			IPort port = getPort(portsJson.get(i).getAsJsonObject());
			ports.add(port);
		}
		catanMap.setPorts(ports);

		List<IEdgeValue> roads = new ArrayList<>();
		JsonArray roadsJson = catanMapJson.getAsJsonArray("roads");
		for (int i = 0; i < roadsJson.size(); i++) {
			IEdgeValue road = getEdgeValue(roadsJson.get(i).getAsJsonObject());
			roads.add(road);
		}
		catanMap.setRoads(roads);

		List<ICommunity> settlements = new ArrayList<>();
		JsonArray settlementsJson = catanMapJson.getAsJsonArray("settlements");
		for (int i = 0; i < settlementsJson.size(); i++) {
			ICommunity settlement = getSettlement(settlementsJson.get(i).getAsJsonObject());
			settlements.add(settlement);
		}
		catanMap.setSettlements(settlements);

		List<ICommunity> cities = new ArrayList<>();
		JsonArray citiesJson = catanMapJson.getAsJsonArray("cities");
		for (int i = 0; i < citiesJson.size(); i++) {
			ICommunity city = getCity(citiesJson.get(i).getAsJsonObject());
			cities.add(city);
		}
		catanMap.setCities(cities);

		HexLocation robber = getHexLocation(catanMapJson.getAsJsonObject("robber"));
		catanMap.setRobber(robber);

		int radius = catanMapJson.get("radius").getAsInt();
		catanMap.setRadius(radius);

		return catanMap;
	}

	private Port getPort(JsonObject portJson) {
		ResourceType resource = null;
		if (portJson.has("resource"))
			resource = getResourceType(portJson.get("resource").getAsString());
		HexLocation hex = getHexLocation(portJson.getAsJsonObject("location"));
		// TODO: docs say that the direction should include east, but is that
		// wrong???
		EdgeDirection edgeDirection = getEdgeDirection(portJson.get("direction").getAsString());
		int ratio = portJson.get("ratio").getAsInt();

		Port port = new Port();
		port.setRatio(ratio);
		port.setResource(resource);
		port.setLocation(hex);
		port.setDirection(edgeDirection);

		return port;

	}

	private VertexLocation getVertexLocation(JsonObject vertexLocationJson) {
		VertexDirection direction = getVertexDirection(vertexLocationJson.get("direction")
				.getAsString());
		HexLocation hexLocation = getHexLocation(vertexLocationJson);
		VertexLocation location = new VertexLocation(hexLocation, direction);
		return location.getNormalizedLocation();
	}

	private VertexDirection getVertexDirection(String vertexDirection) {
		switch (vertexDirection.toLowerCase()) {
		case "w":
			return VertexDirection.West;
		case "nw":
			return VertexDirection.NorthWest;
		case "ne":
			return VertexDirection.NorthEast;
		case "e":
			return VertexDirection.East;
		case "se":
			return VertexDirection.SouthEast;
		case "sw":
			return VertexDirection.SouthWest;
		}
		return null;
	}

	private IEdgeValue getEdgeValue(JsonObject edgeValueJson) {
		IEdgeValue edgeValue = new EdgeValue();

		EdgeLocation edgeLocation = getEdgeLocaton(edgeValueJson.getAsJsonObject("location"));
		int owner = edgeValueJson.get("owner").getAsInt();
		edgeValue.setLocation(edgeLocation.getNormalizedLocation());
		edgeValue.setOwner(players.get(owner));

		return edgeValue;
	}

	private ICommunity getSettlement(JsonObject settlementJson) {
		int owner = settlementJson.get("owner").getAsInt();
		VertexLocation location = getVertexLocation(settlementJson.getAsJsonObject("location"));
		ICommunity settlement = new Settlement(players.get(owner), location.getNormalizedLocation());

		return settlement;
	}

	private ICommunity getCity(JsonObject cityJson) {
		int owner = cityJson.get("owner").getAsInt();
		VertexLocation location = getVertexLocation(cityJson.getAsJsonObject("location"));
		ICommunity city = new City(players.get(owner), location);
		return city;
	}

	private EdgeLocation getEdgeLocaton(JsonObject edgeLocationJson) {
		int x = edgeLocationJson.get("x").getAsInt();
		int y = edgeLocationJson.get("y").getAsInt();
		String direction = edgeLocationJson.get("direction").getAsString();
		EdgeLocation edgeLocation = new EdgeLocation(new HexLocation(x, y),
				getEdgeDirection(direction));

		return edgeLocation;
	}

	private EdgeDirection getEdgeDirection(String edgeDirection) {
		switch (edgeDirection.toLowerCase()) {
		case "nw":
			return EdgeDirection.NorthWest;
		case "n":
			return EdgeDirection.North;
		case "ne":
			return EdgeDirection.NorthEast;
		case "sw":
			return EdgeDirection.SouthWest;
		case "s":
			return EdgeDirection.South;
		case "se":
			return EdgeDirection.SouthEast;
		}
		return null;
	}

	private Hex getHex(JsonObject hexJson) {

		HexLocation location = getHexLocation(hexJson.getAsJsonObject("location"));
		ResourceType resource = null;
		if (hexJson.has("resource")) {
			resource = getResourceType(hexJson.get("resource").getAsString());
		}
		int number = -1;
		if (hexJson.has("number")) {
			number = hexJson.get("number").getAsInt();
		}

		Hex hex = new Hex();
		hex.setLocation(location);
		hex.setResource(resource);
		hex.setNumber(number);

		return hex;
	}

	private ResourceType getResourceType(String resource) {
		switch (resource.toLowerCase()) {
		case "wood":
			return ResourceType.WOOD;
		case "brick":
			return ResourceType.BRICK;
		case "sheep":
			return ResourceType.SHEEP;
		case "wheat":
			return ResourceType.WHEAT;
		case "ore":
			return ResourceType.ORE;
		}
		return null;
	}

	private HexLocation getHexLocation(JsonObject hexLocationJson) {
		int x = hexLocationJson.get("x").getAsInt();
		int y = hexLocationJson.get("y").getAsInt();

		// Yes, this does seem strange. But it makes sure the hex location is
		// shared.
		HexLocation hexLocation = new HexLocation(x, y);

		if (cordToHex.containsKey(hexLocation))
			hexLocation = cordToHex.get(hexLocation);
		else
			cordToHex.put(hexLocation, hexLocation);

		return hexLocation;
	}

	private TradeOffer getTradeOffer(JsonObject tradeOfferJson) {

		int sender = tradeOfferJson.get("sender").getAsInt();
		int receiver = tradeOfferJson.get("receiver").getAsInt();
		ResourceList offer = getResourceList(tradeOfferJson.getAsJsonObject("offer"));

        //THIS IS A HACK BECAUSE WHEN I MADE THIS TE SWAGGER PAGE HAD +/- FLIPPED...
        ResourceList flipped = new ResourceList();
        flipped.setQuantity(ResourceType.BRICK, -1*offer.getQuantity(ResourceType.BRICK));
        flipped.setQuantity(ResourceType.WOOD, -1*offer.getQuantity(ResourceType.WOOD));
        flipped.setQuantity(ResourceType.WHEAT, -1*offer.getQuantity(ResourceType.WHEAT));
        flipped.setQuantity(ResourceType.ORE, -1*offer.getQuantity(ResourceType.ORE));
        flipped.setQuantity(ResourceType.SHEEP, -1*offer.getQuantity(ResourceType.SHEEP));


		TradeOffer tradeOffer = new TradeOffer(sender, receiver, flipped);

		return tradeOffer;
	}

	private TurnTracker getTurnTracker(JsonObject input) {
		TurnTracker turnTracker = new TurnTracker();

		JsonObject turnTrackerJson = input.getAsJsonObject("turnTracker");
		int ttCurrentTurn = turnTrackerJson.getAsJsonPrimitive("currentTurn").getAsInt();
		String ttStatus = turnTrackerJson.getAsJsonPrimitive("status").getAsString();

		// TODO: Check if these exist, then use setters
		// int ttLongestRoad =
		// turnTrackerJson.getAsJsonPrimitive("currentTurn").getAsInt();
		// int ttLargestArmy =
		// turnTrackerJson.getAsJsonPrimitive("currentTurn").getAsInt();

		turnTracker.setCurrentTurn(ttCurrentTurn);
		turnTracker.setStatus(ttStatus);

		return turnTracker;
	}

	private BonusTracker getBonusTracker(JsonObject turnTrackerJson) {
		int longestRoad = turnTrackerJson.getAsJsonPrimitive("longestRoad").getAsInt();
		int largestArmy = turnTrackerJson.getAsJsonPrimitive("largestArmy").getAsInt();
		BonusTracker bonusTracker = new BonusTracker();
		bonusTracker.setLargestArmy(largestArmy);
		bonusTracker.setLongestRoad(longestRoad);
		return bonusTracker;
	}

	private MessageList getMessageList(JsonObject messageListJson) {
		MessageList messages = new MessageList();

		JsonArray lines = messageListJson.getAsJsonArray("lines");
		for (int i = 0; i < lines.size(); i++) {
			JsonObject line = lines.get(i).getAsJsonObject();
			String message = line.getAsJsonPrimitive("message").getAsString();
			String source = line.getAsJsonPrimitive("source").getAsString();
			messages.addMessage(source, message);
		}
		return messages;
	}

	private List<IPlayer> getPlayers(JsonArray playersJsonArray) {
		List<IPlayer> players = new ArrayList<IPlayer>();

		for (int i = 0; i < playersJsonArray.size(); i++) {
			if (playersJsonArray.get(i).isJsonNull())
				continue;
			JsonObject player = playersJsonArray.get(i).getAsJsonObject();
			int cities = player.getAsJsonPrimitive("cities").getAsInt();
			CatanColor color = getColor(player.getAsJsonPrimitive("color").getAsString());
			boolean discarded = player.getAsJsonPrimitive("discarded").getAsBoolean();
			int monuments = player.getAsJsonPrimitive("monuments").getAsInt();
			String name = player.getAsJsonPrimitive("name").getAsString();
			IDevCardList newDevCards = getDevCardList(player.getAsJsonObject("newDevCards"));
			IDevCardList oldDevCards = getDevCardList(player.getAsJsonObject("oldDevCards"));
			int playerIndex = player.getAsJsonPrimitive("playerIndex").getAsInt();
			boolean playerDevCard = player.getAsJsonPrimitive("playedDevCard").getAsBoolean();
			int playerId = player.getAsJsonPrimitive("playerID").getAsInt();
			IResourceList resources = getResourceList(player.getAsJsonObject("resources"));
			int roads = player.getAsJsonPrimitive("roads").getAsInt();
			int settlements = player.getAsJsonPrimitive("settlements").getAsInt();
			int soldiers = player.getAsJsonPrimitive("soldiers").getAsInt();
			int victoryPoints = player.getAsJsonPrimitive("victoryPoints").getAsInt();
			Player aPlayer = new Player(cities, color, discarded, monuments, name, newDevCards,
					oldDevCards, playerIndex, playerDevCard, playerId, resources, roads,
					settlements, soldiers, victoryPoints);
			players.add(aPlayer);
		}

		return players;
	}

	private DevCardList getDevCardList(JsonObject devCardListJson) {
		DevCardList devCardList = new DevCardList();

		int monopoly = devCardListJson.getAsJsonPrimitive("monopoly").getAsInt();
		int monument = devCardListJson.getAsJsonPrimitive("monument").getAsInt();
		int roadBuilding = devCardListJson.getAsJsonPrimitive("roadBuilding").getAsInt();
		int soldier = devCardListJson.getAsJsonPrimitive("soldier").getAsInt();
		int yearOfPlenty = devCardListJson.getAsJsonPrimitive("yearOfPlenty").getAsInt();

		devCardList.setDevCardCount(DevCardType.MONOPOLY, monopoly);
		devCardList.setDevCardCount(DevCardType.MONUMENT, monument);
		devCardList.setDevCardCount(DevCardType.ROAD_BUILD, roadBuilding);
		devCardList.setDevCardCount(DevCardType.SOLDIER, soldier);
		devCardList.setDevCardCount(DevCardType.YEAR_OF_PLENTY, yearOfPlenty);

		return devCardList;
	}

	private ResourceList getResourceList(JsonObject resourceListJson) {
		ResourceList resourceList = new ResourceList();

		int brick = resourceListJson.get("brick").getAsInt();
		int ore = resourceListJson.get("ore").getAsInt();
		int sheep = resourceListJson.get("sheep").getAsInt();
		int wheat = resourceListJson.get("wheat").getAsInt();
		int wood = resourceListJson.get("wood").getAsInt();

		resourceList.setQuantity(ResourceType.BRICK, brick);
		resourceList.setQuantity(ResourceType.ORE, ore);
		resourceList.setQuantity(ResourceType.SHEEP, sheep);
		resourceList.setQuantity(ResourceType.WHEAT, wheat);
		resourceList.setQuantity(ResourceType.WOOD, wood);

		return resourceList;
	}

	private CatanColor getColor(String color) {
		CatanColor result = null;
		switch (color.toLowerCase()) {
		case "red":
			result = CatanColor.RED;
			break;
		case "orange":
			result = CatanColor.ORANGE;
			break;
		case "yellow":
			result = CatanColor.YELLOW;
			break;
		case "blue":
			result = CatanColor.BLUE;
			break;
		case "green":
			result = CatanColor.GREEN;
			break;
		case "purple":
			result = CatanColor.PURPLE;
			break;
		case "puce":
			result = CatanColor.PUCE;
			break;
		case "white":
			result = CatanColor.WHITE;
			break;
		case "brown":
			result = CatanColor.BROWN;
			break;
		default:
			break;
		}
		return result;
	}

}
