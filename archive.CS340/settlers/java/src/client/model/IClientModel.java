package client.model;

import java.util.List;

import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.model.map.ICatanMap;
import client.model.message.IMessageList;
/**
 * The datastructure that represent the Client in a Catan Game
 * 
 * Domain: TurnTracker, Winner (Player), version (>=0), chat, log, CatanMap, Players (0-4)
 * bank, BonusTracker, TradeOffer, LocalPlayer, Deck. 
 * @.pre The JSONObject must generated from the json from the server.
 * 
 * Constructor:
 * Can not be null and must contains all the fields
 * as specified in the client model json spec.
 * @.post The ClientModel will be created according to the json
 * returned by the server. The TurnTracker, Chat, Log, and Players
 * will be build based on json.
 * @param json
 */
public interface IClientModel {

	/**
	 * @.obviousGetter
	 */
	public ITurnTracker getTurnTracker();
	/**
	 * @.obviousGetter
	 */
	public ICatanMap getCatanMap();
	/**
	 * @.obviousGetter
	 */
	public int getWinner();
	/**
	 * @.obviousGetter
	 */
	public int getVersion();
	/**
	 * @.obviousGetter
	 */
	public IMessageList getChat();
	/**
	 * @.obviousGetter
	 */
	public IMessageList getLog();
	/**
	 * @.obviousGetter
	 */
	public List<IPlayer> getPlayers();
	/**
	 * @.obviousSetter
	 */
	public void setTurnTracker(ITurnTracker turnTracker);
	/**
	 * @.obviousSetter
	 */
	public void setWinner(int winner);
	/**
	 * @.obviousSetter
	 */
	public void setVersion(int version);
	/**
	 * @.obviousSetter
	 */
	public void setChat(IMessageList chat);
	/**
	 * @.obviousSetter
	 */
	public void setLog(IMessageList log);
	/**
	 * @.obviousSetter
	 */
	public void setCatanMap(ICatanMap catanMap);
	/**
	 * @.obviousSetter
	 */
	public void setPlayers(List<IPlayer> players);	
	/**
	 * @.obviousGetter
	 */
    public IResourceList getBank();
    /**
	 * @.obviousSetter
	 */
	public void setBank(IResourceList bank);
	/**
	 * @.obviousGetter
	 */
	public IBonusTracker getBonusTracker();
	/**
	 * @.obviousSetter
	 */
	public void setBonusTracker(IBonusTracker bonusTracker);
	/**
	 * @.obviousGetter
	 */
	public ITradeOffer getTradeOffer();
	/**
	 * @.obviousSetter
	 */
	public void setTradeOffer(ITradeOffer tradeOffer);
	/**
	 * @.obviousGetter
	 */
	public IPlayer getLocalPlayer();
	/**
	 * @.obviousSetter
	 */
	public void setLocalPlayer(IPlayer localPlayer);
	/**
	 * @.obviousGetter
	 */
	public IDevCardList getDeck();
	/**
	 * @.obviousSetter
	 */
	public void setDeck(IDevCardList deck);
	/**
	 * @.pre None
	 * @.post True if it is the players turn and the state is "Rolling"
	 */
	public boolean canRollNumber();
	/**
	 * @.pre None
	 * @.post True if the local player matches the recieverId and has the resources
	 */
	public boolean canAcceptTrade();
	/**
	 * @.pre None
	 * @.post True if the local player matches the senderId and has the resources
	 */
	public boolean canOfferTrade(ITradeOffer tradeOffer);
	/**
	 * @.pre None
	 * @.post True if is the players turn and the status is discarding. They must have more than 7 cards. They have to have the resources they request to discard.
	 */
	public boolean canDiscardCards(IResourceList cards);
	/**
	 * @.pre None
	 * @.post True if the status is playing and it's the players turn
	 */
	public boolean canFinishTurn();
	/**
	 * @.pre None
	 * @.post True if it's the players turn and they have the resources. The deck must have dev cards left
	 */
	public boolean canBuyDevCard();
	/**
	 * @.pre None
	 * @.post True it its the players turn, they have the resources, the bank has the resources and they have access to the given ratio
	 */
	public boolean canMaritimeTrade(ResourceType inputResource, ResourceType outputResource, int ratio);
	/**
	 * @.pre None
	 * @.post True if the status is "playing", it's the players turn, the player can play it (See Player.canPlayDevCard()). The bank has the resources.
	 */
	public boolean canPlayYearOfPlenty(ResourceType resource1, ResourceType resource2);
	/**
	 * @.pre None
	 * @.post True if the status is "playing", it's the players turn, the player can play it (See Player.canPlayDevCard()).
	 */
	public boolean canPlayMonument();
	/**
	 * @.pre None
	 * @.post True if the status is "playing", it's the players turn, the player can play it (See Player.canPlayDevCard()).
	 */
	public boolean canPlayMonopoly();
	/**
	 * @.pre None
	 * @.post True if the status is "playing", it's the players turn, the player can play it (See Player.canPlayDevCard()). The victim is not the same player. The hex does not already have the robber. 
	 */
	public boolean canPlaySoldier(HexLocation hexLocation, int victimIndex);
	/**
	 * @.pre None
	 * @.post True if the status is "playing", it's the players turn, the player can play it (See Player.canPlayDevCard()). The player has somewhere to build a road.
	 */
	public boolean canPlayRoadBuild(EdgeLocation location1, EdgeLocation location2);
	public boolean canBuyRoadAtLocation(EdgeLocation edgeLocation);
	public boolean canBuySettlementAtLocation(VertexLocation vertexLocation);
	public boolean canBuyCityAtLocation(VertexLocation vertexLocation);
	public boolean isLocalPlayersTurn();

}
