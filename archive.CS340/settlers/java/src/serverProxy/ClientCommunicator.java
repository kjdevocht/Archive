package serverProxy;

import client.communication.IServerPoller;
import client.communication.ServerPoller;
import client.model.ClientModel;
import client.model.ResourceList;

import java.util.List;

import serverProxy.request.*;
import serverProxy.response.*;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
/**
 * A facade for all of the possible IRequests and IResponses that can be submitted through an IServer
 * 
 * @author Jacob Glad
 *
 */
public class ClientCommunicator {

	private IServer server;
	private IServerPoller poller;
	private static ClientCommunicator singleton;
	public static ClientCommunicator getClientCommunicator() {
		if(singleton == null) {
			singleton = new ClientCommunicator(new ServerProxy("http://localhost", 8081));
		}
		return singleton;
	}
	/**
	 * Creates a client communicator that will communicate with the given IServer
	 * 
	 * @param server
	 */
	public ClientCommunicator(IServer server) {
		this.setServer(server);
		this.setPoller(ServerPoller.getPoller(this));
	}

	/**
	 * Calls the /users/register endpoint on the server
	 * 
	 * @.post The user is created if it did not exist
	 * @param username
	 * @param password
	 * @return True if the user is created, false otherwise
	 * @throws Exception
	 */
	public boolean registerUser(String username, String password) throws Exception {
		RegisterUserRequest request = new RegisterUserRequest(username, password);
		FailSuccessResponse response = new FailSuccessResponse();

		try {
			return server.sendCommand(request, response).isSuccess();
		} catch (RequestException r) {
			return false;
		}
	}

	/**
	 * Calls the users/login endpoint on the server
	 * 
	 * @.post The user is logged in if the credentials are valid
	 * 
	 * @param username
	 * @param password
	 * @return true if the user is logged in, false otherwise
	 * @throws Exception
	 */
	public boolean loginUser(String username, String password) throws Exception {
		LoginUserRequest request = new LoginUserRequest(username, password);
		FailSuccessResponse response = new FailSuccessResponse();

		try {
			return server.sendCommand(request, response).isSuccess();
		} catch (RequestException r) {
			return false;
		}
	}

	/**
	 * Calls the games/list endpoint on the server
	 * 
	 * @return A list of games on the server
	 * @throws Exception
	 */
	public List<GameResponse> listGames() throws Exception {
		ListGamesRequest request = new ListGamesRequest();
		GameListResponse response = new GameListResponse();
		
	
		return server.sendCommand(request, response).getGameList();
	}

	/**
	 * Calls the /games/create endpoint on the server
	 * 
	 * @param name
	 * @return A new game created on the server
	 * @throws Exception
	 */
	public GameResponse createGame(String name, boolean randomTiles, boolean randomNumbers,
			boolean randomPorts) throws Exception {
		CreateGamesRequest request = new CreateGamesRequest(name, randomTiles, randomNumbers,
				randomPorts);
		GameResponse response = new GameResponse();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the games/join endpoint on the server
	 * 
	 * @.pre The player has a valid catan.user cookie set. 
	 * @.pre The player may join the game because there is space, or they are already in the game 
	 * 
	 * @param id
	 * @param color
	 * @return True if the game was joined, false otherwise
	 * @throws Exception
	 */
	public boolean joinGame(int id, String color) throws Exception {
		JoinGameRequest request = new JoinGameRequest(id, color);
		FailSuccessResponse response = new FailSuccessResponse();

		try {
			return server.sendCommand(request, response).isSuccess();
		} catch (RequestException r) {
			return false;
		}
	}

	/**
	 * Calls the game/model endpoint on the server
	 * 
	 * @.pre The player has a valid catan.user and catan.game id
	 * @.post The model is updated if necessary 
	 * 
	 * @param modelNumber
	 * @return A ClientModel representing the game for the give model number, null if the game model number is up to date
	 * @throws Exception
	 */
	public ClientModel getGame(Integer modelNumber) throws Exception {
		GameModelRequest request = new GameModelRequest(modelNumber);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the game/reset endpoint on the server
	 * 
	 * @.pre The player has a valid catan.user and catan.game id 
	 * @return The ClientModel of the resetted game
	 * @throws Exception
	 */
	public ClientModel resetGame() throws Exception {
		ResetGameRequest request = new ResetGameRequest();
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the game/addAi endpoint on the server
	 * 
	 * @.pre The player has a valid catan.user and catan.game id
	 * @.pre There is space in the game for an AI player  
	 * @.post The AI player is added to the next open spot in the game in the poster’s catan.game cookie
	 * 
	 * @param aiType
	 * @return True if the AI was added, false otherwise
	 * @throws Exception
	 */
	public boolean addAi(String aiType) throws Exception {
		AddAIRequest request = new AddAIRequest(aiType);
		FailSuccessResponse response = new FailSuccessResponse();

		try {
			return server.sendCommand(request, response).isSuccess();
		} catch (RequestException r) {
			return false;
		}
	}

	/**
	 * Calls the game/listAi endpoint on the server
	 * 
	 * @return A list of AI types
	 * @throws Exception
	 */
	public List<String> listAi() throws Exception {
		ListAIRequest request = new ListAIRequest();
		ListAIResponse response = new ListAIResponse();
		return server.sendCommand(request, response).getAiTypes();
	}

	/**
	 * Calls the games/sendChat endpoint on the server
	 * 
	 * @.post The chat contains your message at the end
	 * 
	 * @param message
	 * @param playerIndex
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel sendChat(String message, int playerIndex) throws Exception {
		SendChatRequest request = new SendChatRequest(message, playerIndex);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}
	
	/**
	 * Calls the moves/rollNumber endpoint on the server
	 * 
	 * @.pre The client model’s status is ‘rolling’ 
	 * @.pre It’s your turn 
	 * @.post The client model’s status is now in ‘discarding’ or ‘robbing’ or ‘playing’ 
	 * 
	 * @param number
	 * @param playerIndex
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel rollDice(int number, int playerIndex) throws Exception {
		RollNumberRequest request = new RollNumberRequest(number, playerIndex);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/robPlayer endpoint on the server
	 * 
	 * @.pre It's your turn 
	 * @.pre The client model status is 'Playing' 
	 * @.pre The robber isn't being kept in the same place 
	 * @.pre The player to rob has cards (­1 if you can’t rob anyone)
	 * @.post The robber is in the new location
	 * @.post The player to rob gives one random resource card to the robbing player
	 * 
	 * @param playerIndex
	 * @param victimIndex
	 * @param location
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel robPlayer(int playerIndex, int victimIndex, HexLocation location)
			throws Exception {
		RobPlayerRequest request = new RobPlayerRequest(playerIndex, victimIndex, location);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/finishTurn endpoint on the server
	 * 
	 * @.pre It's your turn
	 * @.pre The client model status is 'Playing'
	 * @.post It’s the next players turn 
	 * 
	 * @param playerIndex
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel endTurn(int playerIndex) throws Exception {
		FinishTurnRequest request = new FinishTurnRequest(playerIndex);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/buyDevCard endpoint on the server
	 * 
	 * @.pre It's your turn
	 * @.pre The client model status is 'Playing'
	 * @.pre You have the resources (1 ore, 1 wheat, 1 sheep)
	 * @.pre There are dev cards left in the deck 
	 * @.post You have the new card
	 * @.post If it is a monument card, it goes into the old devcard hand
	 * @.post If it’s any other card, it goes into the new devcard hand (unplayable this turn)
	 * @param playerIndex
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel buyDevCard(int playerIndex) throws Exception {
		BuyDevCardRequest request = new BuyDevCardRequest(playerIndex);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/year_of_plenty endpoint on the server
	 * 
	 * @.pre You have the specific card you want to play in your 'old dev card hand' 
	 * @.pre You haven't played a dev card this turn yet 
	 * @.pre It's your turn 
	 * @.pre The client model status is 'Playing' 
	 * @.pre The two resources you specify are in the bank 
	 * @.post You gain the two resources specified 
	 * 
	 * @param playerIndex
	 * @param resource1
	 * @param resource2
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel yearOfPlenty(int playerIndex, ResourceType resource1, ResourceType resource2)
			throws Exception {
		YearOfPlentyRequest request = new YearOfPlentyRequest(playerIndex, resource1, resource2);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/road_building endpoint on the server
	 * 
	 * @.pre You have the specific card you want to play in your 'old dev card hand' 
	 * @.pre You haven't played a dev card this turn yet 
	 * @.pre It's your turn 
	 * @.pre The client model status is 'Playing' 
	 * @.pre The first road location is connected to one of your roads 
	 * @.pre The second road location is connected to one of your roads or the Previous location 
	 * @.pre Neither location is on water 
	 * @.pre You have two roads 
	 * @.post You use two roads 
	 * @.post The map lists the roads correctly 
	 * 
	 * @param playerIndex
	 * @param edge1
	 * @param edge2
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel roadBuilding(int playerIndex, EdgeLocation edge1, EdgeLocation edge2)
			throws Exception {
		RoadBuildingRequest request = new RoadBuildingRequest(playerIndex, edge1, edge2);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/soldier endpoint on the server
	 * 
	 * @.pre You have the specific card you want to play in your 'old dev card hand' 
	 * @.pre You haven't played a dev card this turn yet 
	 * @.pre It's your turn 
	 * @.pre The client model status is 'Playing' 
	 * @.pre The robber isn't being kept in the same place 
	 * @.pre The player to rob has cards (­1 if you can’t rob anyone)
	 * @.post The robber is in the new location
	 * @.post The player to rob gives one random resource card to the player playing the soldier 
	 * 
	 * @param playerIndex
	 * @param victimIndex
	 * @param location
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel soldier(int playerIndex, int victimIndex, HexLocation location)
			throws Exception {
		SoldierRequest request = new SoldierRequest(playerIndex, victimIndex, location);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/monopoly endpoint on the server
	 *
	 * @.pre You have the specific card you want to play in your 'old dev card hand' 
	 * @.pre You haven't played a dev card this turn yet 
	 * @.pre It's your turn 
	 * @.pre The client model status is 'Playing' 
	 * @.pre The two resources you specify are in the bank 
	 * @.post All other players lose the resource card type chosen 
	 * @.post The player of the card gets an equal numbe
	 * @param playerIndex
	 * @param resource
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel monopoly(int playerIndex, ResourceType resource) throws Exception {
		MonopolyRequest request = new MonopolyRequest(playerIndex, resource);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/monument endpoint on the server
	 * 
	 * @.pre You have the specific card you want to play in your 'old dev card hand' 
	 * @.pre You haven't played a dev card this turn yet 
	 * @.pre It's your turn 
	 * @.pre The client model status is 'Playing' 
	 * @.post You gain a victory point 
	 * 
	 * @param playerIndex
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel monument(int playerIndex) throws Exception {
		MonumentRequest request = new MonumentRequest(playerIndex);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}
	
	/**
	 * Calls the moves/buildRoad endpoint on the server
	 * 
	 * @.pre It's your turn
	 * @.pre The client model status is 'Playing'
	 * @.pre The road location is open 
	 * @.pre The road location is connected to another road you own 
	 * @.pre The road location is not on water 
	 * @.pre You have the resources (1 wood, 1 brick; 1 road) 
	 * @.post You expend the resources to play the road (1 wood, 1 brick; 1 road)
	 * @.post The map lists the road correctly
	 * 
	 * @param playerIndex
	 * @param roadLocation
	 * @param free
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel buildRoad(int playerIndex, EdgeLocation roadLocation, boolean free)
			throws Exception {
		BuildRoadRequest request = new BuildRoadRequest(playerIndex, roadLocation, free);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/buildSettlement endpoint on the server
	 * 
	 * @.pre It's your turn
	 * @.pre The client model status is 'Playing'
	 * @.pre The settlement location is open 
	 * @.pre The settlement location is not on water 
	 * @.pre The settlement location is connected to one of your roads 
	 * @.pre You have the resources (1 wood, 1 brick, 1 wheat, 1 sheep; 1 settlement) 
	 * @.post You expend the resources to play the settlement (1 wood, 1 brick, 1 wheat, 1 sheep; 1 settlement)
	 * @.post The map lists the settlement correctly 
	 * 
	 * @param playerIndex
	 * @param vertexLocation
	 * @param free
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel buildSettlement(int playerIndex, VertexLocation vertexLocation, boolean free)
			throws Exception {
		BuildSettlementRequest request = new BuildSettlementRequest(playerIndex, vertexLocation,
				free);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}
	
	/**
	 * Calls the moves/buildCity endpoint on the server
	 * 
	 * @.pre It's your turn
	 * @.pre The client model status is 'Playing'
	 * @.pre The city location is where you currently have a settlement 
	 * @.pre You have the resources (2 wheat, 3 ore; 1 city) 
	 * @.post You expend the resources to play the settlement (2 wheat, 3 ore; 1 city)
	 * @.post You get a settlement back 
	 * @.post The map lists the city correctly
	 * 
	 * @param playerIndex
	 * @param vertexLocation
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel buildCity(int playerIndex, VertexLocation vertexLocation)
			throws Exception {
		BuildCityRequest request = new BuildCityRequest(playerIndex, vertexLocation);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}
		
	/**
	 * Calls the moves/offerTrade endpoint on the server
	 * 
	 * @.pre It's your turn
	 * @.pre The client model status is 'Playing'
	 * @.pre You have the resource 
	 * @.post The trade is offered to the other player (stored in the model)  
	 * 
	 * @param playerIndex
	 * @param offer
	 * @param receiver
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel offerTrade(int playerIndex, ResourceList offer, int receiver)
			throws Exception {
		OfferTradeRequest request = new OfferTradeRequest(playerIndex, offer, receiver);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/acceptTrade endpoint on the server
	 * 
	 * @.pre You have been offered a domestic trade
	 * @.pre You have the  resources 
	 * @.post If you accepted, you and the player who offered swap the specified resouces
	 * @.post If you declined no resources are changed
	 * @.post The trade offer is removed
	 * 
	 * @param playerIndex
	 * @param willAccept
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel acceptTrade(int playerIndex, boolean willAccept) throws Exception {
		AcceptTradeRequest request = new AcceptTradeRequest(playerIndex, willAccept);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/maritimeTrade endpoint on the server
	 * 
	 * @.pre It's your turn
	 * @.pre The client model status is 'Playing'
	 * @.pre You have the resources
	 * @.pre You have access to the specified ratio for the specified resource
	 * @.post You ratio of the input resource
	 * @.post You gain 1 of the output resource
	 * 
	 * @param playerIndex
	 * @param ratio
	 * @param inputResource
	 * @param outputResource
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel maritimeTrade(int playerIndex, int ratio, ResourceType inputResource,
			ResourceType outputResource) throws Exception {
		MaritimeTradeRequest request = new MaritimeTradeRequest(playerIndex, ratio, inputResource,
				outputResource);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * Calls the moves/discardCards endpoint on the server
	 * 
	 * @.pre The state of the client model is 'Discarding'
	 * @.pre You have over 7 cards
	 * @.pre You have the cards you're choosing to discard 
	 * @.post If you're the last one to discard, the client model status changes to 'Robbing' 
	 * @.post You give up the specified resources
	 * 
	 * @param discardedCards
	 * @param playerIndex
	 * @return A ClientModel containing the current state of the game
	 * @throws Exception
	 */
	public ClientModel discardCards(ResourceList discardedCards, int playerIndex) throws Exception {
		DiscardCardsRequest request = new DiscardCardsRequest(discardedCards, playerIndex);
		ClientModel response = new ClientModel();

		return server.sendCommand(request, response);
	}

	/**
	 * @return the server
	 */
	public IServer getServer() {
		return server;
	}

	/**
	 * @param server the server to set
	 */
	public void setServer(IServer server) {
		this.server = server;
	}

	/**
	 * 
	 * @return the server poller
	 */
	public IServerPoller getPoller() {
		return poller;
	}

	/**
	 * 
	 * @param poller the poller to set
	 */
	public void setPoller(IServerPoller poller) {
		this.poller = poller;
	}

}
