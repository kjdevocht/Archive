package client.model;

import java.io.BufferedReader;
import java.net.CookieStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import serverProxy.IResponse;
import serverProxy.Translator;
import shared.definitions.DevCardType;
import shared.definitions.PortType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.model.map.EdgeValue;
import client.model.map.ICatanMap;
import client.model.map.ICommunity;
import client.model.map.IEdgeValue;
import client.model.map.IPort;
import client.model.map.LocationComparer;
import client.model.map.Port;
import client.model.message.IMessageList;
import client.model.message.MessageList;

public class ClientModel implements IClientModel, IResponse {

	private static ClientModel updatableModel = null;

	private ITurnTracker turnTracker;
	private int winner;
	private int version;
	private IMessageList chat;
	private IMessageList log;
	private ICatanMap catanMap;
	private List<IPlayer> players;
	private IResourceList bank;
	private IBonusTracker bonusTracker;
	private ITradeOffer tradeOffer;
	private IPlayer localPlayer;
	private IDevCardList deck;

	public ClientModel() {
		winner = -1;
		version = -1;
		turnTracker = new TurnTracker();
		chat = new MessageList();
		log = new MessageList();
	}

	public ClientModel(ITurnTracker turnTracker, int winner, int version, IMessageList chat,
			IMessageList log, ICatanMap catanMap, List<IPlayer> players, IResourceList bank,
			IBonusTracker bonusTracker, ITradeOffer tradeOffer, IPlayer localPlayer,
			IDevCardList deck) {
		super();
		this.turnTracker = turnTracker;
		this.winner = winner;
		this.version = version;
		this.chat = chat;
		this.log = log;
		this.catanMap = catanMap;
		this.players = players;
		this.bank = bank;
		this.bonusTracker = bonusTracker;
		this.tradeOffer = tradeOffer;
		this.localPlayer = localPlayer;
		this.deck = deck;
	}

	public static void setUpdatableModel(ClientModel newModel) {
		updatableModel = newModel;
	}

	public static ClientModel getUpdatableModel() {
		return updatableModel;
	}

	/**
	 * @.obviousGetter
	 */
	public ITurnTracker getTurnTracker() {
		return turnTracker;
	}

	/**
	 * @.obviousGetter
	 */
	public ICatanMap getCatanMap() {
		return catanMap;
	}

	/**
	 * @.obviousGetter
	 */
	public int getWinner() {
		return winner;
	}

	/**
	 * @.obviousGetter
	 */
	public int getVersion() {
		return version;
	}

	/**
	 * @.obviousGetter
	 */
	public IMessageList getChat() {
		return chat;
	}

	/**
	 * @.obviousGetter
	 */
	public IMessageList getLog() {
		return log;
	}

	/**
	 * @.obviousGetter
	 */
	public List<IPlayer> getPlayers() {
		return players;
	}

	/**
	 * @.obviousSetter
	 */
	public void setTurnTracker(ITurnTracker turnTracker) {
		this.turnTracker = turnTracker;
	}

	/**
	 * @.obviousSetter
	 */
	public void setWinner(int winner) {
		this.winner = winner;
	}

	/**
	 * @.obviousSetter
	 */
	public void setVersion(int version) {
		this.version = version;
	}

	/**
	 * @.obviousSetter
	 */
	public void setChat(IMessageList chat) {
		this.chat = chat;
	}

	/**
	 * @.obviousSetter
	 */
	public void setLog(IMessageList log) {
		this.log = log;
	}

	/**
	 * @.obviousSetter
	 */
	public void setCatanMap(ICatanMap catanMap) {
		this.catanMap = catanMap;
	}

	/**
	 * @.obviousSetter
	 */
	public void setPlayers(List<IPlayer> players) {
		this.players = players;
	}

	/**
	 * @.obviousGetter
	 */
	public IResourceList getBank() {
		return bank;
	}

	/**
	 * @.obviousSetter
	 */
	public void setBank(IResourceList bank) {
		this.bank = bank;
	}

	/**
	 * @.obviousGetter
	 */
	public IBonusTracker getBonusTracker() {
		return bonusTracker;
	}

	/**
	 * @.obviousSetter
	 */
	public void setBonusTracker(IBonusTracker bonusTracker) {
		this.bonusTracker = bonusTracker;
	}

	/**
	 * @.obviousGetter
	 */
	public ITradeOffer getTradeOffer() {
		return tradeOffer;
	}

	/**
	 * @.obviousSetter
	 */
	public void setTradeOffer(ITradeOffer tradeOffer) {
		this.tradeOffer = tradeOffer;
	}

	/**
	 * @.obviousGetter
	 */
	public IPlayer getLocalPlayer() {
		return localPlayer;
	}

	/**
	 * @.obviousSetter
	 */
	public void setLocalPlayer(IPlayer localPlayer) {
		this.localPlayer = localPlayer;
	}

	public IDevCardList getDeck() {
		return deck;
	}

	public void setDeck(IDevCardList deck) {
		this.deck = deck;
	}

	public boolean canRollNumber() {
		// Not my turn
		if (turnTracker.getCurrentTurn() != localPlayer.getPlayerIndex())
			return false;
		// has rolling as the status
		if (!turnTracker.getStatus().toLowerCase().equals("rolling"))
			return false;
		return true;
	}

	public boolean canAcceptTrade() {
		if (tradeOffer == null)
			return false;
		if (tradeOffer.getReceiverId() != localPlayer.getPlayerIndex())
			return false;

		if (!localPlayer.canAcceptTradeOffer(tradeOffer))
			return false;

		return true;
	}

	public boolean canOfferTrade(ITradeOffer tradeOffer) {
		if (tradeOffer == null)
			return false;
		if (tradeOffer.getSenderId() != localPlayer.getPlayerIndex())
			return false;
        if(!hasPositiveAndNegative(tradeOffer.getResources()))
            return false;
		if (!localPlayer.canOfferTradeOffer(tradeOffer))
			return false;
		return true;
	}
    private boolean hasPositiveAndNegative(IResourceList list) {
        boolean hasPositive = false;
        if(list.getQuantity(ResourceType.BRICK) > 0)
            hasPositive = true;
        if(list.getQuantity(ResourceType.WOOD) > 0)
            hasPositive = true;
        if(list.getQuantity(ResourceType.WHEAT) > 0)
            hasPositive = true;
        if(list.getQuantity(ResourceType.ORE) > 0)
            hasPositive = true;
        if(list.getQuantity(ResourceType.SHEEP) > 0)
            hasPositive = true;

        boolean hasNegative = false;
        if(list.getQuantity(ResourceType.BRICK) < 0)
            hasNegative = true;
        if(list.getQuantity(ResourceType.WOOD) < 0)
            hasNegative = true;
        if(list.getQuantity(ResourceType.WHEAT) < 0)
            hasNegative = true;
        if(list.getQuantity(ResourceType.ORE) < 0)
            hasNegative = true;
        if(list.getQuantity(ResourceType.SHEEP) < 0)
            hasNegative = true;

        return hasNegative && hasPositive;
    }
	public boolean canDiscardCards(IResourceList cards) {
		if (cards == null)
			return false;
		if (localPlayer.isDiscarded())
			return false;

		if (!turnTracker.getStatus().toLowerCase().equals("discarding"))
			return false;

		IResourceList playerResources = localPlayer.getResources();
		int totalCards = playerResources.getTotalResourceCount();

		if (totalCards <= 7)
			return false;

		if (!playerResources.isGreaterOrEqualThanInput(cards))
			return false;

		return true;
	}

	public boolean canFinishTurn() {
		// TODO: is this only true if the current player is playing???
		// Not my turn
		if (turnTracker.getCurrentTurn() != localPlayer.getPlayerIndex())
			return false;

		if (!turnTracker.getStatus().toLowerCase().equals("playing"))
			return false;

		return true;
	}

	public boolean canBuyDevCard() {
		// TODO: can you buy during any part of the game??? should I check
		// status
		if (turnTracker.getCurrentTurn() != localPlayer.getPlayerIndex())
			return false;

		if (!localPlayer.canBuyDevCard())
			return false;

		if (deck.getTotalCardCount() < 1)
			return false;

		return true;
	}
    public Map<ResourceType, Integer> getTradeRatios() {
        Map<ResourceType, Integer> ratios = new HashMap<>();
        ratios.put(ResourceType.BRICK, 4);
        ratios.put(ResourceType.ORE, 4);
        ratios.put(ResourceType.SHEEP, 4);
        ratios.put(ResourceType.WOOD, 4);
        ratios.put(ResourceType.WHEAT, 4);

        List<ICommunity> communities = new ArrayList<ICommunity>();
        communities.addAll(catanMap.getSettlements());
        communities.addAll(catanMap.getCities());
        for(IPort port : catanMap.getPorts()) {
            for(ICommunity community : communities){
            	if(community.getOwner().getPlayerIndex() == localPlayer.getPlayerIndex()) {
	                EdgeLocation portEdge = new EdgeLocation(port.getLocation(), port.getDirection());
	                if(LocationComparer.vertexIsOnEdge(portEdge, community.getLocation())){
	                    int portRatio = port.getRatio();
	                    ResourceType type = port.getResource();
	                    if(type == null) {
	                        for(ResourceType restype: ResourceType.values()) {
	                            if(ratios.get(restype) > portRatio)
	                                ratios.put(restype, 3);
	                        }
	                    }
	                    else if(ratios.get(type) > portRatio)
	                        ratios.put(type, portRatio);
	                }
            	}
            }
        }

        return ratios;
    }
	public boolean canMaritimeTrade(ResourceType inputResource, ResourceType outputResource,
			int ratio) {
		if (ratio < 2 || ratio > 4)
			return false;
		boolean canMakeOffer = false;
		

		Map<ResourceType, Integer> ratios = new HashMap<>();
		ratios = getTradeRatios();
		if(ratios.get(inputResource)!=ratio)
			return false;
		
		ResourceList inputResources = new ResourceList();
		inputResources.setQuantity(inputResource, ratio);

		ResourceList outputResources = new ResourceList();
		outputResources.setQuantity(outputResource, 1);

		if (turnTracker.getCurrentTurn() != localPlayer.getPlayerIndex())
			return false;
		if (!localPlayer.getResources().isGreaterOrEqualThanInput(inputResources))
			return false;
		if (!bank.isGreaterOrEqualThanInput(outputResources))
			return false;

		return true;
	}

	private boolean canPlayDevCard(DevCardType devCard) {
		if (!turnTracker.getStatus().toLowerCase().equals("playing"))
			return false;
		if (turnTracker.getCurrentTurn() != localPlayer.getPlayerIndex())
			return false;
		if (!localPlayer.canPlayDevCard(devCard))
			return false;
		return true;
	}

	public boolean canPlayYearOfPlenty(ResourceType resource1, ResourceType resource2) {
		if (!canPlayDevCard(DevCardType.YEAR_OF_PLENTY))
			return false;
		if (resource1.equals(resource2)) {
			if (bank.getQuantity(resource1) < 2)
				return false;
		} else {
			if (bank.getQuantity(resource2) < 1)
				return false;
			if (bank.getQuantity(resource1) < 1)
				return false;
		}
		return true;
	}

	public boolean canPlayMonument() {
		if (!canPlayDevCard(DevCardType.MONUMENT))
			return false;
		return true;
	}

	public boolean canPlayMonopoly() {
		if (!canPlayDevCard(DevCardType.MONOPOLY))
			return false;
		return true;
	}

	public boolean canPlaySoldier(HexLocation hexLocation, int victimIndex) {
		if (!canPlayDevCard(DevCardType.SOLDIER))
			return false;
		if (victimIndex == localPlayer.getPlayerIndex())
			return false;

		if (getCatanMap().getRobber().equals(hexLocation))
			return false;

		if (victimIndex > 0
				&& getPlayers().get(victimIndex).getResources().getTotalResourceCount() == 0)
			return false;

		return true;
	}

	public boolean canPlayRoadBuild(EdgeLocation location1, EdgeLocation location2) {
		if (!canPlayDevCard(DevCardType.ROAD_BUILD))
			return false;

		if(getCatanMap().canPlaceRoadAtLocation(location1, localPlayer.getPlayerIndex(), "Playing")) {
			IEdgeValue road = new EdgeValue();
			road.setLocation(location1);
			road.setOwner(getLocalPlayer());
			getCatanMap().getRoads().add(road);
			
			if(getCatanMap().canPlaceRoadAtLocation(location2, localPlayer.getPlayerIndex(), "Playing")) {
				getCatanMap().getRoads().remove(road);
				return true;
			}
			getCatanMap().getRoads().remove(road);		
		}

		if(!getCatanMap().canPlaceRoadAtLocation(location2, localPlayer.getPlayerIndex(), "Playing"))
			return false;
		
		IEdgeValue road = new EdgeValue();
		road.setLocation(location2);
		road.setOwner(getLocalPlayer());
		getCatanMap().getRoads().add(road);	
		
		if(getCatanMap().canPlaceRoadAtLocation(location1, localPlayer.getPlayerIndex(), "Playing")) {
			getCatanMap().getRoads().remove(road);
			return true;
		}
		getCatanMap().getRoads().remove(road);
		return false;

	}

	@Override
	public IResponse fromResponse(BufferedReader response, CookieStore cookies) {
		ClientModel oldModel = ClientModel.getUpdatableModel();
		ClientModel newModel = new Translator().translate(response, cookies, oldModel);

//		if(oldModel != null && oldModel.getVersion() == newModel.getVersion())
//			return oldModel;

//        if(oldModel != null)
//            System.out.println(""+oldModel.getVersion() + " " + newModel.getVersion());

		setUpdatableModel(newModel);

		ModelChangeDetector.detectChanges(oldModel, newModel);		
		
		return newModel;
	}

	@Override
	public boolean canBuyRoadAtLocation(EdgeLocation edgeLocation) {

		String status = getTurnTracker().getStatus().toLowerCase();
		int playerIndex = getLocalPlayer().getPlayerIndex();

		if (!(status.equals("firstround") || status.equals("secondround") || status
				.equals("playing")))
			return false;
		if (!(status.equals("firstround") || status.equals("secondround")))
			if (!getLocalPlayer().canBuyRoad())
				return false;

		if (!getCatanMap().canPlaceRoadAtLocation(edgeLocation.getNormalizedLocation(),
				playerIndex, status))
			return false;

		return true;
	}

	@Override
	public boolean canBuySettlementAtLocation(VertexLocation vertexLocation) {
		String status = getTurnTracker().getStatus().toLowerCase();
		int playerIndex = getLocalPlayer().getPlayerIndex();

		if (!(status.equals("firstround") || status.equals("secondround") || status
				.equals("playing")))
			return false;
		if (!(status.equals("firstround") || status.equals("secondround")))
			if (!getLocalPlayer().canBuySettlement())
				return false;

		if (!getCatanMap().canPlaceSettlementAtLocation(vertexLocation.getNormalizedLocation(),
				playerIndex, status))
			return false;

		return true;
	}

	@Override
	public boolean canBuyCityAtLocation(VertexLocation vertexLocation) {
		String status = getTurnTracker().getStatus().toLowerCase();
		int playerIndex = getLocalPlayer().getPlayerIndex();

		if (!(status.equals("firstround") || status.equals("secondround") || status
				.equals("playing")))
			return false;
		if (!(status.equals("firstround") || status.equals("secondround")))
			if (!getLocalPlayer().canBuyCity())
				return false;

		if (!getCatanMap().canPlaceCityAtLocation(vertexLocation.getNormalizedLocation(),
				playerIndex, status))
			return false;

		return true;
	}
	
	@Override
	public boolean isLocalPlayersTurn() {
		return turnTracker.getCurrentTurn() == localPlayer.getPlayerIndex();
	}

}
