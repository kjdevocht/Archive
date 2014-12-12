package server.model.game;

import server.model.map.ICatanMap;
import server.model.message.IMessageList;
import shared.definitions.CatanColor;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.List;



/**
 * 
 * @author Jacob Glad
 * Describes the model of a game on the server
 */
public interface IGameModel {
	public IMessageList getChat();
	public void setChat(IMessageList chat);
	
	public IMessageList getLog();
	public void setLog(IMessageList log);
	
	public ICatanMap getMap();
	public void setMap(ICatanMap map);

	public int getCommandsExecuted();
	public void setCommandsExecuted(int commandsExecuted);

    /**
    * Retrieves a list of all players in the current game
    * @return The list of all players currently in the game
    */
	public List<IPlayer> getPlayers();

    /**
    * Adds a player to the list of players in the current game
    * @param player The player to be added to the game
    */
	public void addPlayer(IPlayer player);

    boolean canAddPlayer(int playerId, CatanColor color);
    public boolean playerInGame(int playerId);
    /**
    * Removes a player from the current game
    * @param player The player to be removed from the current game
    * @return Success Status of the removal of the player
    */
	public boolean removePlayer(IPlayer player);

    void placeSettlement(VertexLocation location, int playerIndex, boolean isFree);

    void placeCity(VertexLocation location, int playerIndex);

    int getPlayerIndex(int playerId);

    void finishTurn(int playerIndex);

    void rollNumber(int playerIndex, int roll);

    void discardCards(int playerIndex, ResourceList resourceList);

    void robPlayer(int playerIndex, int victimIndex, HexLocation hexLocation);

    void offerTrade(int playerIndex, int recieverIndex, IResourceList trade);

    void acceptTrade(int playerIndex, boolean accept);

    void maritimeTrade(int playerIndex, int ratio, ResourceType input, ResourceType output);

    void buyDevCard(int playerIndex);

    void yearOfPlenty(int playerIndex, ResourceType card1, ResourceType card2);

    void roadBuild(int playerIndex, EdgeLocation edgeLocation1, EdgeLocation edgeLocation2);

    void soldier(int playerIndex, int victimIndex, HexLocation hexLocation);

    void monopoly(int playerIndex, ResourceType type);

    void monument(int playerIndex);

    void sendChat(int playerIndex, String message);

    void addLog(int playerIndex, String message);

    public ITurnTracker getTurnTracker();
	public void setTurnTracker(ITurnTracker turnTracker);
	
	public int getVersion();
	public void setVersion(int version);
	
	public int getWinner();
	public void setWinner(int winner);
	
	public IResourceList getBank();
	public void setBank(IResourceList bank);
	
	public IDevCardList getDeck();
	public void setDeck(IDevCardList deck);
	
	public String getTitle();
	public void setName(String title);
	
	public int getId();
	public void setId(int id);
	boolean canBuyCityAtLocation(VertexLocation vertexLocation, int playerId);
	boolean canBuySettlementAtLocation(VertexLocation vertexLocation, int playerId);
	boolean canBuyRoadAtLocation(EdgeLocation edgeLocation, int playerId);
	
	public ITradeOffer getTradeOffer();
	public void setTradeOffer(ITradeOffer tradeOffer);
	public IBonusTracker getBonusTracker();
	public void setBonusTracker(IBonusTracker bonusTracker);
	public void reset();
    public void placeRoad(EdgeLocation edgeLocation, int playerIndex, boolean isFree);

}
