package client.model;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import serverProxy.ClientCommunicator;
import shared.definitions.DevCardType;
import shared.definitions.ResourceType;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;
import client.model.map.ICatanMap;
import client.model.map.ICommunity;
import client.model.map.IEdgeValue;
import client.model.map.IHex;
import client.model.map.IPort;
import client.model.message.IMessageList;
import client.model.observable.Event;
import client.model.observable.EventObservable;
import client.model.observable.EventObservable.EventNotRegisteredException;

public class ModelChangeDetector {
	
	public static void detectChanges(IClientModel oldModel, IClientModel newModel) {
		if(oldModel == null && newModel == null)
			return;
		
		if(oldModel == null) {
			updatedWholeModel(newModel);
			return;
		}
		
//		if(oldModel.getVersion() == newModel.getVersion())
//			return;
		
		if(newModel.getWinner() != -1)
		{
			updateWinner();
			ClientCommunicator.getClientCommunicator().getPoller().stop();
			EventObservable.resetSingleton();
			
			return;
		}
		
		if(oldModel.getWinner() != newModel.getWinner())
			updateWinner();
		
		
		detectChangesTurnTracker(oldModel.getTurnTracker(), newModel.getTurnTracker());
		detectChangesBonus(oldModel.getBonusTracker(), newModel.getBonusTracker());
		detectChangeChat(oldModel.getChat(), newModel.getChat());
		detectChangeLog(oldModel.getLog(), newModel.getLog());
		detectChangesLocalPlayer(oldModel.getLocalPlayer(), newModel.getLocalPlayer());
		detectChangePlayerList(oldModel.getPlayers(), newModel.getPlayers());
		detectTradeOfferChange(oldModel.getTradeOffer(), newModel.getTradeOffer());
		detectChangesMap(oldModel.getCatanMap(), newModel.getCatanMap());
		
	}
	private static void detectChangesTurnTracker(ITurnTracker oldTurnTracker, ITurnTracker newTurnTracker) {
		if(oldTurnTracker == null && newTurnTracker == null)
			return;
		if(oldTurnTracker == null) {
			updateWholeTurnTracker();
			return;
		}
		if(oldTurnTracker.getCurrentTurn() != newTurnTracker.getCurrentTurn()) {
			updateCurrentTurn();
		}
		if(!oldTurnTracker.getStatus().equals(newTurnTracker.getStatus())){
			updateStatus();
		}
	}
	private static void detectChangesBonus(IBonusTracker oldBonusTracker, IBonusTracker newBonusTracker) {
		if(oldBonusTracker == null && newBonusTracker == null) {
			return;
		}
		if(oldBonusTracker == null) {
			updateWholeBonusTracker();
			return;
		}
		if(oldBonusTracker.getLargestArmy() != newBonusTracker.getLargestArmy()) {
			updateLargestArmy();
		}
		if(oldBonusTracker.getLongestRoad() != newBonusTracker.getLongestRoad()) {
			updateLongestRoad();
		}			
	}
	private static void detectChangeChat(IMessageList oldChat, IMessageList newChat) {
		if(oldChat == null && newChat == null)
			return;
		if(oldChat == null){
			updateChat(0);
			return;
		}
		if(oldChat.getMessages().size() != newChat.getMessages().size()) {
			updateChat(oldChat.getMessages().size());
		}			
	}
	private static void detectChangeLog(IMessageList oldLog, IMessageList newLog) {
		if(oldLog == null && newLog == null)
			return;
		if(oldLog == null){
			updateLog(0);
			return;
		}
//		if(oldLog.getMessages().size() != newLog.getMessages().size()) {
			updateLog(oldLog.getMessages().size());
//		}
	}
	private static void detectChangePlayerList(List<IPlayer> oldPlayers, List<IPlayer> newPlayers) {
		if(oldPlayers == null && newPlayers == null)
			return;
		if(oldPlayers == null) {
			updateWholePlayerList(newPlayers.size());
			return;
		}
		int mostPlayers = Math.max(oldPlayers.size(), newPlayers.size());
		for(int i=0; i<mostPlayers; i++) {
			if(i >= newPlayers.size()) {
//				System.out.println("ERROR should not be able to leave game...");
			}
			if(i >= oldPlayers.size()) {
				updateAddPlayerOnAndAfter(i, newPlayers.size());
				return;
			}
			detectChangesPlayer(oldPlayers.get(i), newPlayers.get(i), i);
		}
	}
	private static void detectChangesPlayer(IPlayer oldPlayer, IPlayer newPlayer, int playerIndex) {
		if(oldPlayer == null && newPlayer == null)
			return;
		if(oldPlayer == null) {
			updateWholePlayer(playerIndex);
		}
		if(oldPlayer.getCities() != newPlayer.getCities()) {
			updatePlayerCities(playerIndex);
		}
		if(oldPlayer.getSettlements() != newPlayer.getSettlements()) {
			updatePlayerSettlements(playerIndex);
		}
		if(!oldPlayer.getColor().equals(newPlayer.getColor())) {
			updatePlayerColor(playerIndex);
		}
		if(!oldPlayer.getName().equals(newPlayer.getName())){
			updatePlayerName(playerIndex);
		}
		if(oldPlayer.getRoads() != newPlayer.getRoads()) {
			updatePlayerRoads(playerIndex);
		}
		if(oldPlayer.getVictoryPoints() != newPlayer.getVictoryPoints()) {
			updatePlayerVictoryPoints(playerIndex);
		}
		if(oldPlayer.isDiscarded() != newPlayer.isDiscarded()) {
			updatePlayerDiscarded(playerIndex);
		}
		if(oldPlayer.isPlayedDevCard() != newPlayer.isPlayedDevCard()) {
			updatePlayerPlayedDevCard(playerIndex);
		}
		if(oldPlayer.getMonuments() != newPlayer.getMonuments()) {
			updatePlayerMonuments(playerIndex);
		}
		if(oldPlayer.getSoldiers() != newPlayer.getSoldiers()) {
			updatePlayerSoldiers(playerIndex);
		}
	}
	private static void detectChangesMap(ICatanMap oldMap, ICatanMap newMap) {
		if(oldMap == null && newMap == null)
			return;
		if(oldMap ==  null){
			updateWholeMap(newMap);
			return;
		}
		
		if(!oldMap.getRobber().equals(newMap.getRobber())) {
			updateMapRobber();
		}
		detectChangesHexes(oldMap.getHexes(), newMap.getHexes());
		detectChangesRoads(oldMap.getRoads(), newMap.getRoads());
		detectChangesSettlement(oldMap.getSettlements(), newMap.getSettlements());
		detectChangesCity(oldMap.getCities(), newMap.getCities());
		detectChangesPort(oldMap.getPorts(), newMap.getPorts());

	}
	private static void detectChangesRoads(List<IEdgeValue> oldRoads, List<IEdgeValue> newRoads) {
		Map<EdgeLocation, IEdgeValue> oldRoadSet = new HashMap<>();
		for(IEdgeValue road: oldRoads) {
			oldRoadSet.put(road.getLocation().getNormalizedLocation(), road);
		}
		Map<EdgeLocation, IEdgeValue> newRoadSet = new HashMap<>();
		for(IEdgeValue road: newRoads) {
			newRoadSet.put(road.getLocation().getNormalizedLocation(), road);
		}
		
		Set<EdgeLocation> newKeys = new HashSet<>(newRoadSet.keySet());
		newKeys.removeAll(oldRoadSet.keySet());
		
		for(EdgeLocation roadLocation: newKeys) {
			updateMapAddRoad(newRoadSet.get(roadLocation));
		}
	}
	private static void detectChangesHexes(List<IHex> oldHexes, List<IHex> newHexes) {
		Map<HexLocation, IHex> oldHexMap = new HashMap<>();
		for(IHex hex: oldHexes) {
			oldHexMap.put(hex.getLocation(), hex);
		}
		Map<HexLocation, IHex> newHexMap = new HashMap<>();
		for(IHex hex: newHexes) {
			newHexMap.put(hex.getLocation(), hex);
		}
		Set<HexLocation> newKeys = new HashSet<>(newHexMap.keySet());
		newKeys.removeAll(oldHexMap.keySet());

		for(HexLocation hexLoc: newKeys) {
			updateMapAddHex(newHexMap.get(hexLoc));
		}
	}
	private static void detectChangesSettlement(List<ICommunity> oldSettlements, List<ICommunity> newSettlements) {
		Map<VertexLocation, ICommunity> oldSettlmentMap = new HashMap<>();
		for(ICommunity settlement: oldSettlements) {
			oldSettlmentMap.put(settlement.getLocation().getNormalizedLocation(), settlement);
		}
		Map<VertexLocation, ICommunity> newSettlmentMap = new HashMap<>();
		for(ICommunity settlement: newSettlements) {
			newSettlmentMap.put(settlement.getLocation().getNormalizedLocation(), settlement);
		}
		Set<VertexLocation> newKeys = new HashSet<>(newSettlmentMap.keySet());
		newKeys.removeAll(oldSettlmentMap.keySet());

		for(VertexLocation hexLoc: newKeys) {
			updateMapAddSettlement(newSettlmentMap.get(hexLoc));
		}
	}
	private static void detectChangesCity(List<ICommunity> oldCities, List<ICommunity> newCities) {
		Map<VertexLocation, ICommunity> oldSettlmentMap = new HashMap<>();
		for(ICommunity settlement: oldCities) {
			oldSettlmentMap.put(settlement.getLocation().getNormalizedLocation(), settlement);
		}
		Map<VertexLocation, ICommunity> newSettlmentMap = new HashMap<>();
		for(ICommunity settlement: newCities) {
			newSettlmentMap.put(settlement.getLocation().getNormalizedLocation(), settlement);
		}
		Set<VertexLocation> newKeys = new HashSet<>(newSettlmentMap.keySet());
		newKeys.removeAll(oldSettlmentMap.keySet());

		for(VertexLocation hexLoc: newKeys) {
			updateMapAddCity(newSettlmentMap.get(hexLoc));
		}
	}
	private static void detectChangesPort(List<IPort> oldPorts, List<IPort> newPorts) {
		//Very basic check but should really change after first...
		for(int i=oldPorts.size(); i<newPorts.size(); i++) {
			updateMapAddPort(newPorts.get(i));
		}
	}
	private static void detectChangesLocalPlayer(IPlayer oldPlayer, IPlayer newPlayer) {
		if(oldPlayer == null && newPlayer == null) {
			return;
		}
		if(oldPlayer == null) {
			updateWholeLocalPlayer();
			return;
		}
		detectChangesLocalPlayerOldDevCards(oldPlayer.getOldDevCards(), newPlayer.getOldDevCards());
		detectChangesLocalPlayerNewDevCards(oldPlayer.getNewDevCards(), newPlayer.getNewDevCards());
		detectChangesLocalPlayerResources(oldPlayer.getResources(), newPlayer.getResources());
		
				
	}
	private static void detectChangesLocalPlayerOldDevCards(IDevCardList iDevCardList, IDevCardList iDevCardList2) {
		//TODO update just based on card if important. I'm not sure how the GUI works.
		if(iDevCardList.getDevCardCount(DevCardType.MONOPOLY) != iDevCardList2.getDevCardCount(DevCardType.MONOPOLY))
			updateLocalPlayerOldDevCards();
		else if(iDevCardList.getDevCardCount(DevCardType.MONUMENT) != iDevCardList2.getDevCardCount(DevCardType.MONUMENT))
			updateLocalPlayerOldDevCards();
		else if(iDevCardList.getDevCardCount(DevCardType.ROAD_BUILD) != iDevCardList2.getDevCardCount(DevCardType.ROAD_BUILD))
			updateLocalPlayerOldDevCards();
		else if(iDevCardList.getDevCardCount(DevCardType.SOLDIER) != iDevCardList2.getDevCardCount(DevCardType.SOLDIER))
			updateLocalPlayerOldDevCards();
		else if(iDevCardList.getDevCardCount(DevCardType.YEAR_OF_PLENTY) != iDevCardList2.getDevCardCount(DevCardType.YEAR_OF_PLENTY))
			updateLocalPlayerOldDevCards();
	}
	private static void detectChangesLocalPlayerNewDevCards(IDevCardList oldOldDevCards, IDevCardList newOldDevCards) {
		//TODO update just based on card if important. I'm not sure how the GUI works.
		if(oldOldDevCards.getDevCardCount(DevCardType.MONOPOLY) != newOldDevCards.getDevCardCount(DevCardType.MONOPOLY))
			updateLocalPlayerNewDevCards();
		else if(oldOldDevCards.getDevCardCount(DevCardType.MONUMENT) != newOldDevCards.getDevCardCount(DevCardType.MONUMENT))
			updateLocalPlayerNewDevCards();
		else if(oldOldDevCards.getDevCardCount(DevCardType.ROAD_BUILD) != newOldDevCards.getDevCardCount(DevCardType.ROAD_BUILD))
			updateLocalPlayerNewDevCards();
		else if(oldOldDevCards.getDevCardCount(DevCardType.SOLDIER) != newOldDevCards.getDevCardCount(DevCardType.SOLDIER))
			updateLocalPlayerNewDevCards();
		else if(oldOldDevCards.getDevCardCount(DevCardType.YEAR_OF_PLENTY) != newOldDevCards.getDevCardCount(DevCardType.YEAR_OF_PLENTY))
			updateLocalPlayerNewDevCards();
	}
	private static void detectChangesLocalPlayerResources(IResourceList oldResources, IResourceList newResources) {
		if(oldResources.getQuantity(ResourceType.BRICK) != newResources.getQuantity(ResourceType.BRICK)) {
			updateLocalPlayerResourceList();
		}
		else if(oldResources.getQuantity(ResourceType.ORE) != newResources.getQuantity(ResourceType.ORE)) {
			updateLocalPlayerResourceList();
		}
		else if(oldResources.getQuantity(ResourceType.SHEEP) != newResources.getQuantity(ResourceType.SHEEP)) {
			updateLocalPlayerResourceList();
		}
		else if(oldResources.getQuantity(ResourceType.WHEAT) != newResources.getQuantity(ResourceType.WHEAT)) {
			updateLocalPlayerResourceList();
		}
		else if(oldResources.getQuantity(ResourceType.WOOD) != newResources.getQuantity(ResourceType.WOOD)) {
			updateLocalPlayerResourceList();
		}
	}
	private static void detectTradeOfferChange(ITradeOffer oldTradeOffer, ITradeOffer newTradeOffer) {
		if(oldTradeOffer == null && newTradeOffer == null)
			return;
		if(oldTradeOffer == null) {
			updateTradeOffer();
			return;
		}
        if(newTradeOffer == null) {
            updateTradeOffer();
            return;
        }
		
		if(oldTradeOffer.getReceiverId() != newTradeOffer.getReceiverId()) {
			updateTradeOffer();
			return;
		}
		if(oldTradeOffer.getSenderId() != newTradeOffer.getSenderId()) {
			updateTradeOffer();
			return;
		}
		
		IResourceList oldOffer = oldTradeOffer.getResources();
		IResourceList newOffer = newTradeOffer.getResources();
		
		if(oldOffer.getQuantity(ResourceType.BRICK) != newOffer.getQuantity(ResourceType.BRICK)) {
			updateTradeOffer();
		}
		else if(oldOffer.getQuantity(ResourceType.ORE) != newOffer.getQuantity(ResourceType.ORE)) {
			updateTradeOffer();
		}
		else if(oldOffer.getQuantity(ResourceType.SHEEP) != newOffer.getQuantity(ResourceType.SHEEP)) {
			updateTradeOffer();
		}
		else if(oldOffer.getQuantity(ResourceType.WHEAT) != newOffer.getQuantity(ResourceType.WHEAT)) {
			updateTradeOffer();
		}
		else if(oldOffer.getQuantity(ResourceType.WOOD) != newOffer.getQuantity(ResourceType.WOOD)) {
			updateTradeOffer();
		}
	}
	
	//Multiple Update Methods
	private static void updatedWholeModel(IClientModel newModel) {
//		System.out.println("Updated whole model");
		updateWholePlayerList(newModel.getPlayers().size());
		updateWholeTurnTracker();
		updateWinner();
		updateCurrentTurn();
		updateStatus();
		updateWholeBonusTracker();
		updateLargestArmy();
		updateLongestRoad();
		updateChat(0);
		updateLog(0);
		updateTradeOffer();
		updateWholeMap(newModel.getCatanMap());
		updateWholeLocalPlayer();		
	}
	
	private static void updateWholePlayerList(int listSize) {
//		System.out.println("Update whole player list");
		updateAddPlayerOnAndAfter(0, listSize);
	}
	
	private static void updateAddPlayerOnAndAfter(int index, int listSize) {
//		System.out.println("Add player after..."+index);
		for(int i=index; i<listSize; i++) {
			updateAddBlankPlayer();
			updateWholePlayer(i);
		}
	}
	
	private static void updateWholeMap(ICatanMap map) {
//		System.out.println("Update whole map");
		for(IHex hex: map.getHexes()) {
			updateMapAddHex(hex);
		}
		for(IEdgeValue road: map.getRoads()) {
			updateMapAddRoad(road);
		}
		for(ICommunity settlement: map.getSettlements()) {
			updateMapAddSettlement(settlement);
		}
		for(ICommunity city: map.getCities()) {
			updateMapAddCity(city);
		}
		for(IPort port: map.getPorts()) {
			updateMapAddPort(port);
		}
		updateMapRobber();
		
	}
	
	private static void updateWholeLocalPlayer() {
//		System.out.println("Update whole local player");
		updateLocalPlayerResourceList();
		updateLocalPlayerNewDevCards();
		updateLocalPlayerOldDevCards();
	}
	
	private static void updateWholePlayer(int playerIndex) {
//		System.out.println("Update whole player "+playerIndex);
		updatePlayerCities(playerIndex);
		updatePlayerSettlements(playerIndex);
		updatePlayerColor(playerIndex);
		updatePlayerName(playerIndex);
		updatePlayerRoads(playerIndex);
		updatePlayerVictoryPoints(playerIndex);
		updatePlayerDiscarded(playerIndex);
		updatePlayerPlayedDevCard(playerIndex);
		updatePlayerMonuments(playerIndex);
		updatePlayerSoldiers(playerIndex);
	}		
	
	private static void updateWholeBonusTracker() {
//		System.out.println("Update whole bonus tracker");
		updateLongestRoad();
		updateLargestArmy();
	}
	
	private static void updateWholeTurnTracker(){
//		System.out.println("Update whole turn tracker");
		updateStatus();
		updateCurrentTurn();
	}	
	
	
	//Single Update Methods
	private static <T> void signalEvent(Event<T> event, T metadata){
//		System.out.println("Signalling event: " + event.toString());
		try{
			EventObservable.getSingleton().signalEvent(event, metadata);
		}
		catch(EventNotRegisteredException e){
//			System.out.println(e);
		}
	}
	
	private static void updateTradeOffer() {
		signalEvent(Event.UpdateTradeOffer, null);
	}
	
	private static void updateLocalPlayerResourceList() {
		signalEvent(Event.UpdateLocalPlayerResourceList,null);	
	}
	
	private static void updateLocalPlayerNewDevCards() {
		signalEvent(Event.UpdateLocalPlayerNewDevCards,null);
	}
	
	private static void updateLocalPlayerOldDevCards() {
		signalEvent(Event.UpdateLocalPlayerOldDevCards,null);	
	}
	
	private static void updateMapAddPort(IPort port) {
		signalEvent(Event.UpdateMapAddPort, port);	
	}
	
	private static void updateMapAddCity(ICommunity city) {
		signalEvent(Event.UpdateMapAddCity, city);	
	}
	
	private static void updateMapAddSettlement(ICommunity settlement) {
		signalEvent(Event.UpdateMapAddSettlement, settlement);	
	}
	
	private static void updateMapAddHex(IHex hex) {
		signalEvent(Event.UpdateMapAddHex, hex);	
	}
	
	private static void updateMapAddRoad(IEdgeValue newRoad) {
		signalEvent(Event.UpdateMapAddRoad, newRoad);	
	}
	
	private static void updateMapRobber() {
		signalEvent(Event.UpdateMapRobber, null);
	}
	
	private static void updatePlayerSoldiers(int playerIndex) {
		signalEvent(Event.UpdatePlayerSoldiers, playerIndex);	
	}
	
	private static void updatePlayerMonuments(int playerIndex) {
		signalEvent(Event.UpdatePlayerMonuments, playerIndex);	
	}
	
	private static void updatePlayerPlayedDevCard(int playerIndex) {
		signalEvent(Event.UpdatePlayerPlayedDevCard, playerIndex);
	}
	
	private static void updatePlayerDiscarded(int playerIndex) {
		signalEvent(Event.UpdatePlayerDiscarded, playerIndex);
	}
	
	private static void updatePlayerVictoryPoints(int playerIndex) {
		signalEvent(Event.UpdatePlayerVictoryPoints, playerIndex);
	}
	
	private static void updatePlayerRoads(int playerIndex) {
		signalEvent(Event.UpdatePlayerRoads, playerIndex);	
	}
	
	private static void updatePlayerName(int playerIndex) {
		signalEvent(Event.UpdatePlayerName, playerIndex);
	}
	
	private static void updatePlayerColor(int playerIndex) {
		signalEvent(Event.UpdatePlayerColor, playerIndex);
	}
	
	private static void updatePlayerSettlements(int playerIndex) {
		signalEvent(Event.UpdatePlayerSettlements, playerIndex);
	}
	
	private static void updatePlayerCities(int playerIndex) {
		signalEvent(Event.UpdatePlayerCities, playerIndex);
	}
	
	private static void updateAddBlankPlayer() {
		signalEvent(Event.UpdateAddBlankPlayer, null);		
	}
	
	private static void updateLog(int count) {
		signalEvent(Event.UpdateLog, count);		
	}
	
	private static void updateChat(int count) {
		signalEvent(Event.UpdateChat, count);		
	}
	
	private static void updateLongestRoad() {
		signalEvent(Event.UpdateLongestRoad, null);
	}
	
	private static void updateLargestArmy() {
		signalEvent(Event.UpdateLargestArmy, null);	
	}
	
	private static void updateStatus() {		
		signalEvent(Event.UpdateStatus, null);
	}
	
	private static void updateCurrentTurn() {
		signalEvent(Event.UpdateCurrentTurn, null);		
	}
	
	private static void updateWinner() {
		signalEvent(Event.UpdateWinner, null);	
	}
}
