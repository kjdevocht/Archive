package client.model;

import shared.definitions.CatanColor;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;

public class Player implements IPlayer{
	private int cities;
	private CatanColor color;
	private boolean discarded;
	private int monuments;
	private String name;
	private IDevCardList newDevCards;
	private IDevCardList oldDevCards;
	private int playerIndex;
	private boolean playedDevCard;
	private int playerId;
	private IResourceList resources;
	private int roads;
	private int settlements;
	private int soldiers;
	private int victoryPoints;
	/**
	 * @.pre name must be a non empty or null String, playerId must
	 * be between 0-3 and be unique other players in the game. Color
	 * must also be unique to other players in the game
	 * @.post A player with the default setting will be created with
	 * the given name, playerId and color.
	 * @param name - Name of player
	 * @param playerId - id for the game to reference for game order
	 * @param color - Color to represent the player on the board.
	 */
	public Player(String name, int playerId, CatanColor color) {
		this.name = name;
        this.playerId = playerId;
        this.color = color;
	}
	
	public Player(int cities, CatanColor color, boolean discarded,
			int monuments, String name, IDevCardList newDevCards,
			IDevCardList oldDevCards, int playerIndex, boolean playedDevCard,
			int playerId, IResourceList resources, int roads, int settlements,
			int soldiers, int victoryPoints) {
		super();
		this.cities = cities;
		this.color = color;
		this.discarded = discarded;
		this.monuments = monuments;
		this.name = name;
		this.newDevCards = newDevCards;
		this.oldDevCards = oldDevCards;
		this.playerIndex = playerIndex;
		this.playedDevCard = playedDevCard;
		this.playerId = playerId;
		this.resources = resources;
		this.roads = roads;
		this.settlements = settlements;
		this.soldiers = soldiers;
		this.victoryPoints = victoryPoints;
	}

	/**
	 * @.pre none
	 * @.post True if the model is in a state where the player can play 
	 * the requested dev card type.
	 * @param devCard
	 */
	public boolean canPlayDevCard(DevCardType devCard) {
		if(isPlayedDevCard())
			return false;
		if(devCard == DevCardType.ROAD_BUILD) {
			if(getRoads() < 2)
				return false;
		}
		if(DevCardType.MONUMENT == devCard) {
            if(getNewDevCards().getDevCardCount(devCard) >= 1)
                return true;
        }
		if(getOldDevCards().getDevCardCount(devCard) < 1)
			return false;
		return true;
	}
	/**
	 * @.pre A valid trade offer from the server.
	 * @.post True if the model is in a state the player can accept
	 * a the given trade offer.
	 * @param tradeOffer
	 */
	public boolean canOfferTradeOffer(ITradeOffer tradeOffer) {
		IResourceList offer = tradeOffer.getResources();
		if(!hasEnoughForOfferTrade(offer, resources, ResourceType.BRICK))
			return false;
		if(!hasEnoughForOfferTrade(offer, resources, ResourceType.ORE))
			return false;
		if(!hasEnoughForOfferTrade(offer, resources, ResourceType.WHEAT))
			return false;
		if(!hasEnoughForOfferTrade(offer, resources, ResourceType.WOOD))
			return false;
		if(!hasEnoughForOfferTrade(offer, resources, ResourceType.SHEEP))
			return false;
		return true;
	}
	private boolean hasEnoughForOfferTrade(IResourceList offers, IResourceList playerResources, ResourceType type) {
		if(offers.getQuantity(type) < 0) {
			if(Math.abs(offers.getQuantity(type)) > playerResources.getQuantity(type))
				return false;
		}
		return true;
	}
	public boolean canAcceptTradeOffer(ITradeOffer tradeOffer) {
		IResourceList offer = tradeOffer.getResources();		
		if(!resources.isGreaterOrEqualThanInput(offer))
			return false;
		return true;
	}
	/**
	 * @.pre None
	 * @.post True if the player has the required resources to buy
	 * a settlement.
	 */
	public boolean canBuySettlement() {
		
		if(settlements <= 0)
			return false;
		
		IResourceList devCardCost = new ResourceList();
		devCardCost.setQuantity(ResourceType.BRICK, 1);
		devCardCost.setQuantity(ResourceType.WHEAT, 1);
		devCardCost.setQuantity(ResourceType.SHEEP, 1);
		devCardCost.setQuantity(ResourceType.WOOD, 1);
		
		if(!resources.isGreaterOrEqualThanInput(devCardCost))
			return false;
		
		return true;
	}
	/**
	 * @.pre None
	 * @.post True if the player has the required resources to buy
	 * a city.
	 */
	public boolean canBuyCity() {
		if(cities <= 0)
			return false;
		
		ResourceList devCardCost = new ResourceList();
		devCardCost.setQuantity(ResourceType.WHEAT, 2);
		devCardCost.setQuantity(ResourceType.ORE, 3);
		
		if(!resources.isGreaterOrEqualThanInput(devCardCost))
			return false;
		
		return true;
	}
	/**
	 * @.pre None
	 * @.post True if the player has the required resources to buy
	 * a road.
	 */
	public boolean canBuyRoad() {
		if(roads <= 0)
			return false;
		
		ResourceList devCardCost = new ResourceList();
		devCardCost.setQuantity(ResourceType.BRICK, 1);
		devCardCost.setQuantity(ResourceType.WOOD, 1);
		
		if(!resources.isGreaterOrEqualThanInput(devCardCost))
			return false;
		
		return true;
	}
	/**
	 * @.pre None
	 * @.post True if the player has the required resources to buy
	 * a dev card.
	 */
	public boolean canBuyDevCard() {
		
		ResourceList devCardCost = new ResourceList();
		devCardCost.setQuantity(ResourceType.WHEAT, 1);
		devCardCost.setQuantity(ResourceType.ORE, 1);
		devCardCost.setQuantity(ResourceType.SHEEP, 1);
		
		if(!resources.isGreaterOrEqualThanInput(devCardCost))
			return false;
		
		return true;
	}
	/**
	 * @.obviousGetter
	 */
	public int getCities() {
		return cities;
	}
	/**
	 * @.obviousGetter
	 */
	public CatanColor getColor() {
		return color;
	}
	/**
	 * @.obviousGetter
	 */
	public boolean isDiscarded() {
		return discarded;
	}
	/**
	 * @.obviousSetter
	 */
	public void setDiscarded(boolean discarded) {
		this.discarded = discarded;
	}
	/**
	 * @.obviousGetter
	 */
	public int getMonuments() {
		return monuments;
	}
	/**
	 * @.obviousGetter
	 */
	public String getName() {
		return name;
	}
	/**
	 * @.obviousGetter
	 */
	public IDevCardList getNewDevCards() {
		return newDevCards;
	}
	/**
	 * @.obviousGetter
	 */
	public IDevCardList getOldDevCards() {
		return oldDevCards;
	}
	/**
	 * @.obviousGetter
	 */
	public int getPlayerIndex() {
		return playerIndex;
	}
	/**
	 * @.obviousGetter
	 */
	public boolean isPlayedDevCard() {
		return playedDevCard;
	}
	/**
	 * @.obviousGetter
	 */
	public int getPlayerId() {
		return playerId;
	}
	/**
	 * @.obviousGetter
	 */
	public IResourceList getResources() {
		return resources;
	}
	/**
	 * @.obviousGetter
	 */
	public int getRoads() {
		return roads;
	}
	/**
	 * @.obviousGetter
	 */
	public int getSettlements() {
		return settlements;
	}
	/**
	 * @.obviousGetter
	 */
	public int getSoldiers() {
		return soldiers;
	}
	/**
	 * @.obviousGetter
	 */
	public int getVictoryPoints() {
		return victoryPoints;
	}
	/**
	 * @.obviousSetter
	 */
	public void setCities(int cities) {
		this.cities = cities;
	}
	/**
	 * @.obviousSetter
	 */
	public void setColor(CatanColor color) {
		this.color = color;
	}
	/**
	 * @.obviousSetter
	 */
	public void setMonuments(int monuments) {
		this.monuments = monuments;
	}
	/**
	 * @.obviousSetter
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @.obviousSetter
	 */
	public void setNewDevCards(IDevCardList newDevCards) {
		this.newDevCards = newDevCards;
	}
	/**
	 * @.obviousSetter
	 */
	public void setOldDevCards(IDevCardList oldDevCards) {
		this.oldDevCards = oldDevCards;
	}
	/**
	 * @.obviousSetter
	 */
	public void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}
	/**
	 * @.obviousSetter
	 */
	public void setPlayedDevCard(boolean playedDevCard) {
		this.playedDevCard = playedDevCard;
	}
	/**
	 * @.obviousSetter
	 */
	public void setPlayerId(int playerId) {
		this.playerId = playerId;
	}
	/**
	 * @.obviousSetter
	 */
	public void setResources(IResourceList resources) {
		this.resources = resources;
	}
	/**
	 * @.obviousSetter
	 */
	public void setRoads(int roads) {
		this.roads = roads;
	}
	/**
	 * @.obviousSetter
	 */
	public void setSettlements(int settlements) {
		this.settlements = settlements;
	}
	/**
	 * @.obviousSetter
	 */
	public void setSoldiers(int soldiers) {
		this.soldiers = soldiers;
	}
	/**
	 * @.obviousSetter
	 */
	public void setVictoryPoints(int victoryPoints) {
		this.victoryPoints = victoryPoints;
	}
	
	
}
