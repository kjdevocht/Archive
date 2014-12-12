package server.handler;


import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import server.logging.Logger;
import server.model.game.IGameModel;
import server.model.game.IPlayer;
import server.model.game.IUser;
import server.model.game.ResourceList;
import server.model.game.ai.AIList;
import server.result.IResult;
import server.result.ListGamesResult;
import serverProxy.request.*;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.*;

import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mitch10e on 11/4/14.
 */
public class ServerTranslator implements IServerTranslator {

    private InputStream requestBody;
    private Headers headers;
    private Map<String, String> queries;
    public ServerTranslator(InputStream requestBody) {
        this.requestBody = requestBody;
    }
    public ServerTranslator(InputStream requestBody, Headers requestHeaders) {
        this.requestBody = requestBody;
        this.headers = requestHeaders;
    }
    public ServerTranslator(HttpExchange exchange) {
        this.requestBody = exchange.getRequestBody();
        this.headers = exchange.getRequestHeaders();
        String uri = exchange.getRequestURI().getQuery();
        this.queries = queryToMap(uri);
    }
    public Map<String, String> queryToMap(String query){
    	
    	
        Map<String, String> result = new HashMap<String, String>();
        
        if(query == null)
        	return result;
        
        for (String param : query.split("&")) {
            String pair[] = param.split("=");
            if (pair.length>1) {
                result.put(pair[0], pair[1]);
            }else{
                result.put(pair[0], "");
            }
        }
        return result;
    }

    public JsonObject getRequestBody() throws IOException {
        InputStreamReader isr =  new InputStreamReader(this.requestBody,"utf-8");
        BufferedReader br = new BufferedReader(isr);
        int b;
        StringBuilder buf = new StringBuilder();
        while ((b = br.read()) != -1) {
            buf.append((char) b);
        }
        br.close();
        isr.close();
        Logger.getSingleton().debug("Request Body: " + buf.toString());
        Gson gson = new Gson();
        JsonObject json = gson.fromJson(buf.toString(), JsonObject.class);
        return json;
    }

    @Override
    public LoginUserRequest getLoginUserRequest() throws JsonParseException, IOException {
    	try{
	        JsonObject json = getRequestBody();
	        String username = json.get("username").getAsString();
	        String password = json.get("password").getAsString();
	        return new LoginUserRequest(username, password);
    	}
    	catch(NullPointerException e) {
    		throw new JsonParseException("Malformed json request");
    	}
    }

    @Override
    public RegisterUserRequest getRegisterUserRequest() throws IOException {
    	try{
	        JsonObject json = getRequestBody();
	        String username = json.get("username").getAsString();
	        String password = json.get("password").getAsString();
	        return new RegisterUserRequest(username, password);
    	}
    	catch(NullPointerException e) {
    		throw new JsonParseException("Malformed json request");
    	}
    }

    @Override
    public CreateGamesRequest getCreateGameRequest() throws IOException {
        JsonObject json = getRequestBody();
        String name = json.get("name").getAsString();
        boolean randomTiles = json.get("randomTiles").getAsBoolean();
        boolean randomNumbers = json.get("randomNumbers").getAsBoolean();
        boolean randomPorts = json.get("randomPorts").getAsBoolean();
        return new CreateGamesRequest(name, randomTiles, randomNumbers, randomPorts);
    }

    @Override
    public JoinGameRequest getJoinGameRequest() throws IOException {
        JsonObject json = getRequestBody();
        int id = json.get("id").getAsInt();
        String color = json.get("color").getAsString();
        return new JoinGameRequest(id, color);
    }

    @Override
    public ListGamesRequest getListGamesRequest() throws IOException {
        JsonObject json = getRequestBody();
        return new ListGamesRequest();
    }

    @Override
    public JsonArray makeListGamesResponse(IResult result) {
        ListGamesResult gamesResult = (ListGamesResult) result;
        JsonArray json = new JsonArray();
        if(gamesResult.getResult() == null)
            return json;
        for(IGameModel model : gamesResult.getResult()) {
            json.add(makeSimpleCatanGameObject(model));
        }
        return json;
    }

    @Override
    public LoadGameRequest getLoadGameRequest() throws IOException {
        JsonObject json = getRequestBody();

        String name = json.get("name").getAsString();

        return new LoadGameRequest(name);
    }

    @Override
    public SaveGameRequest getSaveGameRequest() throws IOException {
        JsonObject json = getRequestBody();

        int id = json.get("id").getAsInt();
        String name = json.get("name").getAsString();

        return new SaveGameRequest(id, name);
    }




    @Override
    public AddAIRequest getAddAIRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("AIType").getAsString();
        return new AddAIRequest(type);
    }

    @Override
    public GetGameCommandsRequest getCommandsRequest() throws IOException {
        //TODO: I'm not sure if this GetGameCommandsRequest is correct (according to Swagger)
        JsonObject json = getRequestBody();
//        int userId = json.get("userId").getAsInt();
//        int gameId = json.get("gameId").getAsInt();
        return new GetGameCommandsRequest();
    }

    @Override
    public PostGameCommandsRequest getPostCommandsRequest() throws IOException {
        JsonObject json = getRequestBody();
        String username = json.get("username").getAsString();
        String password = json.get("password").getAsString();
        return new PostGameCommandsRequest(username, password);
    }

    @Override
    public ListAIRequest getListAIRequest() throws IOException {
        JsonObject json = getRequestBody();
        return new ListAIRequest();
    }

    @Override
    public GameModelRequest getGameModelRequest() throws IOException {
        Integer version = -1;
        if(queries.containsKey("version")) {
        	try{
        	version = Integer.parseInt(queries.get("version"));
        	}catch(Exception e){};
        }
        return new GameModelRequest(version);
    }

    @Override
    public ResetGameRequest getResetGameRequest() throws IOException {
        JsonObject json = getRequestBody();
        return new ResetGameRequest();
    }

    @Override
    public String makeGameModelResponse(IGameModel model, int versionNumber) throws IOException{

        if(model.getVersion() != versionNumber) {
        	return GameModelTranslator.toJSON(model).toString();
        }
        else {
        	return "true";
        }
    }
    @Override
    public String makeGameModelResponse(IGameModel model) throws IOException{
    	return makeGameModelResponse(model, -1);
    }
    

    @Override
    public JsonObject makeAIListResponse(IResult result) throws IOException{
        JsonObject json = new JsonObject();

        json.addProperty("Status", "Unimplemented");

        return json;
    }

    @Override
    public JsonObject makeCommandList(IResult result) throws IOException{
        JsonObject json = new JsonObject();

        json.addProperty("Status", "Unimplemented");

        return json;
    }

    @Override
    public AcceptTradeRequest getAcceptTradeRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        boolean willAccept = json.get("willAccept").getAsBoolean();
        return new AcceptTradeRequest(playerIndex, willAccept);
    }

    @Override
    public BuildCityRequest getBuildCityRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        int x = json.get("vertexLocation").getAsJsonObject().get("x").getAsInt();
        int y = json.get("vertexLocation").getAsJsonObject().get("y").getAsInt();
        String direction = json.get("vertexLocation").getAsJsonObject().get("direction").getAsString();

        HexLocation hex = new HexLocation(x, y);
        VertexDirection dir = getVertexDirection(direction);
        VertexLocation location = new VertexLocation(hex, dir);

        return new BuildCityRequest(playerIndex, location);
    }

    @Override
    public BuildRoadRequest getBuildRoadRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        int x = json.get("roadLocation").getAsJsonObject().get("x").getAsInt();
        int y = json.get("roadLocation").getAsJsonObject().get("y").getAsInt();
        String direction = json.get("roadLocation").getAsJsonObject().get("direction").getAsString();
        boolean free = json.get("free").getAsBoolean();

        HexLocation hex = new HexLocation(x, y);
        EdgeDirection dir = getEdgeDirection(direction);
        EdgeLocation edge = new EdgeLocation(hex, dir);

        return new BuildRoadRequest(playerIndex, edge, free);
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
    @Override
    public BuildSettlementRequest getBuildSettlementRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        int x = json.get("vertexLocation").getAsJsonObject().get("x").getAsInt();
        int y = json.get("vertexLocation").getAsJsonObject().get("y").getAsInt();
        String direction = json.get("vertexLocation").getAsJsonObject().get("direction").getAsString();
        boolean free = json.get("free").getAsBoolean();

        HexLocation hex = new HexLocation(x, y);
        VertexDirection dir = getVertexDirection(direction);
        VertexLocation location = new VertexLocation(hex, dir);

        return new BuildSettlementRequest(playerIndex, location, free);
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

    @Override
    public BuyDevCardRequest getBuyDevCardRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        return new BuyDevCardRequest(playerIndex);
    }

    @Override
    public DiscardCardsRequestServer getDiscardCardsRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();

        int brick = json.get("discardedCards").getAsJsonObject().get("brick").getAsInt();
        int ore = json.get("discardedCards").getAsJsonObject().get("ore").getAsInt();
        int sheep = json.get("discardedCards").getAsJsonObject().get("sheep").getAsInt();
        int wheat = json.get("discardedCards").getAsJsonObject().get("wheat").getAsInt();
        int wood = json.get("discardedCards").getAsJsonObject().get("wood").getAsInt();

        ResourceList list = new ResourceList();
        list.setQuantity(ResourceType.BRICK, brick);
        list.setQuantity(ResourceType.ORE, ore);
        list.setQuantity(ResourceType.SHEEP, sheep);
        list.setQuantity(ResourceType.WHEAT, wheat);
        list.setQuantity(ResourceType.WOOD, wood);

        return new DiscardCardsRequestServer(list, playerIndex);
    }

    @Override
    public FinishTurnRequest getFinishTurnRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        return new FinishTurnRequest(playerIndex);
    }

    @Override
    public MaritimeTradeRequest getMaritimeTradeRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        int ratio = json.get("ratio").getAsInt();
        String inputResource = json.get("inputResource").getAsString();
        String outputResource = json.get("outputResource").getAsString();
        ResourceType input = ResourceType.valueOf(inputResource.toUpperCase());
        ResourceType output = ResourceType.valueOf(outputResource.toUpperCase());
        return new MaritimeTradeRequest(playerIndex, ratio, input, output);
    }

    @Override
    public MonopolyRequest getMonopolyRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        String resourceType = json.get("resource").getAsString();
        ResourceType resource = ResourceType.valueOf(resourceType.toUpperCase());
        return new MonopolyRequest(playerIndex, resource);
    }

    @Override
    public MonumentRequest getMonumentRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        return new MonumentRequest(playerIndex);
    }

    @Override
    public OfferTradeRequestServer getOfferTradeRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        int receiver = json.get("receiver").getAsInt();

        int brick = json.get("offer").getAsJsonObject().get("brick").getAsInt();
        int ore = json.get("offer").getAsJsonObject().get("ore").getAsInt();
        int sheep = json.get("offer").getAsJsonObject().get("sheep").getAsInt();
        int wheat = json.get("offer").getAsJsonObject().get("wheat").getAsInt();
        int wood = json.get("offer").getAsJsonObject().get("wood").getAsInt();

        ResourceList list = new ResourceList();
        list.setQuantity(ResourceType.BRICK, brick);
        list.setQuantity(ResourceType.ORE, ore);
        list.setQuantity(ResourceType.SHEEP, sheep);
        list.setQuantity(ResourceType.WHEAT, wheat);
        list.setQuantity(ResourceType.WOOD, wood);

        return new OfferTradeRequestServer(playerIndex, list, receiver);
    }

    @Override
    public RoadBuildingRequest getRoadBuildingRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();

        int x1 = json.get("spot1").getAsJsonObject().get("x").getAsInt();
        int y1 = json.get("spot1").getAsJsonObject().get("y").getAsInt();
        String dir1 = json.get("spot1").getAsJsonObject().get("direction").getAsString();

        int x2 = json.get("spot2").getAsJsonObject().get("x").getAsInt();
        int y2 = json.get("spot2").getAsJsonObject().get("y").getAsInt();
        String dir2 = json.get("spot2").getAsJsonObject().get("direction").getAsString();

        HexLocation hex1 = new HexLocation(x1, y1);
        EdgeDirection direction1 = getEdgeDirection(dir1);
        EdgeLocation spot1 = new EdgeLocation(hex1, direction1);

        HexLocation hex2 = new HexLocation(x2, y2);
        EdgeDirection direction2 = getEdgeDirection(dir2);
        EdgeLocation spot2 = new EdgeLocation(hex2, direction2);

        return new RoadBuildingRequest(playerIndex, spot1, spot2);
    }

    @Override
    public RobPlayerRequest getRobPlayerRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        int victimIndex = json.get("victimIndex").getAsInt();

        int x = json.get("location").getAsJsonObject().get("x").getAsInt();
        int y = json.get("location").getAsJsonObject().get("y").getAsInt();

        HexLocation hex = new HexLocation(x, y);
        return new RobPlayerRequest(playerIndex, victimIndex, hex);
    }

    @Override
    public RollNumberRequest getRollNumberRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        int number = json.get("number").getAsInt();
        return new RollNumberRequest(playerIndex, number);
    }

    @Override
    public SendChatRequest getSendChatRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        String message = json.get("content").getAsString();
        return new SendChatRequest(message, playerIndex);
    }

    @Override
    public SoldierRequest getSoldierRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        int victimIndex = json.get("victimIndex").getAsInt();

        int x = json.get("location").getAsJsonObject().get("x").getAsInt();
        int y = json.get("location").getAsJsonObject().get("y").getAsInt();

        HexLocation hex = new HexLocation(x, y);
        return new SoldierRequest(playerIndex, victimIndex, hex);
    }

    @Override
    public YearOfPlentyRequest getYearOfPlentyRequest() throws IOException {
        JsonObject json = getRequestBody();
        String type = json.get("type").getAsString();
        int playerIndex = json.get("playerIndex").getAsInt();
        String r1 = json.get("resource1").getAsString();
        String r2 = json.get("resource2").getAsString();
        ResourceType resource1 = ResourceType.valueOf(r1.toUpperCase());
        ResourceType resource2 = ResourceType.valueOf(r2.toUpperCase());
        return new YearOfPlentyRequest(playerIndex, resource1, resource2);
    }

	@Override
	public JsonObject makeLoginCookieResponse(IUser user) {
		JsonObject json = new JsonObject();
		json.addProperty("name", user.getName());
		json.addProperty("password", user.getPassword());
		json.addProperty("playerID", user.getId());
		return json;
	}
	
	@Override
	public JsonObject makeSimpleCatanGameObject(IGameModel gameModel) {
		JsonObject json = new JsonObject();
		json.addProperty("title", gameModel.getTitle());
		json.addProperty("id", gameModel.getId());
		JsonArray jsonArray = new JsonArray();
		for (int i=0; i<4; i++) {
			JsonObject playerJson = new JsonObject();
			if(gameModel.getPlayers().size() > i) {
				IPlayer player = gameModel.getPlayers().get(i);
				playerJson.addProperty("color", CatanColor.toString(player.getColor()));
				playerJson.addProperty("id", player.getPlayerId());
				playerJson.addProperty("name", player.getName());
			}
			jsonArray.add(playerJson);
		}
		json.add("players", jsonArray);
		return json;
	}
	public String makeJoinGameCookie(int gameId, Credentials credentials) {
		credentials.setGameId(gameId);
		JsonObject json = new JsonObject();
		json.addProperty("name", credentials.getName());
		json.addProperty("password", credentials.getPassword());
		json.addProperty("playerID", credentials.getPlayerId());
		String result = "catan.game="+gameId+"; catan.user="+URLEncoder.encode(json.toString())+"; Path=/;";
		return result;
	}
    public Credentials getCookieCredentials() {
        if(headers == null) {
            return new Credentials(-1, -1, null, null);
        }
        Credentials credentials = null;
        String name = null;
        String password = null;
        int id = -1;
        int gameId = -1;

        for(String key : headers.keySet()) {
            if(key.toLowerCase().equals("cookie")) {
                Gson gson = new Gson();
                List<String> headerList = headers.get(key);
                for(String firstRawHeader : headerList) {
                	for(String headerRaw : firstRawHeader.split(";")) {
	                    String[] splitHeader = headerRaw.split("=");
	                    if(splitHeader.length != 2)
	                        continue;
	                    if(splitHeader[0].trim().equals("catan.user")) {
	                        JsonObject json = gson.fromJson(URLDecoder.decode(splitHeader[1]), JsonObject.class);
	                        id = json.get("playerID").getAsInt();
	                        name = json.get("name").getAsString();
	                        password = json.get("password").getAsString();
	                    }
	                    if(splitHeader[0].trim().equals("catan.game")) {
	                        gameId = Integer.parseInt(URLDecoder.decode(splitHeader[1]));
	
	                    }
                	}
                }
            }
        }
        credentials = new Credentials(id, gameId, name, password);
        return credentials;
    }

}
