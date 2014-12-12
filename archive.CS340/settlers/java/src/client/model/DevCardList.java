package client.model;

import java.util.HashMap;
import java.util.Map;

import shared.definitions.DevCardType;

public class DevCardList implements IDevCardList{
	private Map<DevCardType, Integer> devCards;
	public DevCardList() {
		devCards = new HashMap<DevCardType, Integer>();
		devCards.put(DevCardType.MONOPOLY, 0);
		devCards.put(DevCardType.MONUMENT, 0);
		devCards.put(DevCardType.ROAD_BUILD, 0);
		devCards.put(DevCardType.SOLDIER, 0);
		devCards.put(DevCardType.YEAR_OF_PLENTY, 0);
	}
	public int getDevCardCount(DevCardType type) {
		return devCards.get(type);
	}
	public void setDevCardCount(DevCardType type, int amount) {
		devCards.put(type, amount);
	}
	public int getTotalCardCount() {
		int count = 0;
		for(Map.Entry<DevCardType, Integer> devCard: devCards.entrySet()) {
			count += devCard.getValue();
		}
		return count;
	}
}
