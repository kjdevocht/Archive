package client.model;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
/**
 * Represents a Player in a Catan Game
 * 
 * Domain: cities (0-4), settlements (0-5), roads (0-15), VictoryPoints (0-10),
 * hasDiscarded, hasPlayedDevCard, playerId, playerIndex (0-3), newDevCards,
 * oldDevCards, name, monuments (0-5), monopoly (0-2), resources, Color
 * 
 * Constructor:
 * @.pre name must be a non empty or null String, playerId must
 * be between 0-3 and be unique other players in the game. Color
 * must also be unique to other players in the game
 * @.post A player with the default setting will be created with
 * the given name, playerId and color.
 */
public interface IPlayer {
	
	/**
	 * @.pre none
	 * @.post True if the model is in a state where the player can play 
	 * the requested dev card type.
	 * @param devCard
	 */
	public boolean canPlayDevCard(DevCardType devCard);
	/**
	 * @.pre None 
	 * @.post True if the player has the required resources to offer
	 * a trade
	 */
	public boolean canOfferTradeOffer(ITradeOffer tradeOffer);	
	/**
	 * @.pre A valid trade offer from the server.
	 * @.post True if the model is in a state that player can accept
	 * a the given trade offer.
	 * @param tradeOffer
	 */
	public boolean canAcceptTradeOffer(ITradeOffer tradeOffer);
	/**
	 * @.pre None
	 * @.post True if the player has the required resources to buy
	 * a settlement.
	 */
	public boolean canBuySettlement();
	/**
	 * @.pre None
	 * @.post True if the player has the required resources to buy
	 * a city.
	 */
	public boolean canBuyCity();
	/**
	 * @.pre None
	 * @.post True if the player has the required resources to buy
	 * a road.
	 */
	public boolean canBuyRoad();
	/**
	 * @.pre None
	 * @.post True if the player has the required resources to buy
	 * a dev card.
	 */
	public boolean canBuyDevCard();
	/**
	 * @.obviousGetter
	 */
	public int getCities();
	/**
	 * @.obviousGetter
	 */
	public CatanColor getColor();
	/**
	 * @.obviousGetter
	 */
	public boolean isDiscarded();
	/**
	 * @.obviousSetter
	 */
	public void setDiscarded(boolean discarded);
	/**
	 * @.obviousGetter
	 */
	public int getMonuments();
	/**
	 * @.obviousGetter
	 */
	public String getName();
	/**
	 * @.obviousGetter
	 */
	public IDevCardList getNewDevCards();
	/**
	 * @.obviousGetter
	 */
	public IDevCardList getOldDevCards();
	/**
	 * @.obviousGetter
	 */
	public int getPlayerIndex();
	/**
	 * @.obviousGetter
	 */
	public boolean isPlayedDevCard();
	/**
	 * @.obviousGetter
	 */
	public int getPlayerId();
	/**
	 * @.obviousGetter
	 */
	public IResourceList getResources();
	/**
	 * @.obviousGetter
	 */
	public int getRoads();
	/**
	 * @.obviousGetter
	 */
	public int getSettlements();
	/**
	 * @.obviousGetter
	 */
	public int getSoldiers();
	/**
	 * @.obviousGetter
	 */
	public int getVictoryPoints();
	/**
	 * @.obviousSetter
	 */
	public void setCities(int cities);
	/**
	 * @.obviousSetter
	 */
	public void setColor(CatanColor color);
	/**
	 * @.obviousSetter
	 */
	public void setMonuments(int monuments);
	/**
	 * @.obviousSetter
	 */
	public void setName(String name);
	/**
	 * @.obviousSetter
	 */
	public void setNewDevCards(IDevCardList newDevCards);
	/**
	 * @.obviousSetter
	 */
	public void setOldDevCards(IDevCardList oldDevCards);
	/**
	 * @.obviousSetter
	 */
	public void setPlayerIndex(int playerIndex);
	/**
	 * @.obviousSetter
	 */
	public void setPlayedDevCard(boolean playedDevCard);
	/**
	 * @.obviousSetter
	 */
	public void setPlayerId(int playerId);
	/**
	 * @.obviousSetter
	 */
	public void setResources(IResourceList resources);
	/**
	 * @.obviousSetter
	 */
	public void setRoads(int roads);
	/**
	 * @.obviousSetter
	 */
	public void setSettlements(int settlements);
	/**
	 * @.obviousSetter
	 */
	public void setSoldiers(int soldiers);
	/**
	 * @.obviousSetter
	 */
	public void setVictoryPoints(int victoryPoints);
	
	
}
