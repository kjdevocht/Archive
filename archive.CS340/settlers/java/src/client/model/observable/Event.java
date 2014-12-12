package client.model.observable;

import client.model.map.ICommunity;
import client.model.map.IEdgeValue;
import client.model.map.IHex;
import client.model.map.IPort;
/**
 * 
 * @author Jacob Glad
 *
 * Represents an event that can be fired
 *
 * @param <T> The metadata type associated with a given event
 */
public class Event<T> {
	private String name;
	
	/**
	 * 
	 * @param name The name of the event
	 */
	public Event(String name){
		this.name = name;
	}
	
	public static Event<Object> 	UpdateTradeOffer 				= new Event<Object>("UpdateTradeOffer");
	public static Event<Object> 	UpdateLocalPlayerResourceList 	= new Event<Object>("UpdateLocalPlayerResourceList"); 
	public static Event<Object> 	UpdateLocalPlayerNewDevCards  	= new Event<Object>("UpdateLocalPlayerNewDevCards");
	public static Event<Object> 	UpdateLocalPlayerOldDevCards  	= new Event<Object>("UpdateLocalPlayerOldDevCards");
	public static Event<IPort> 		UpdateMapAddPort 				= new Event<IPort>("UpdateMapAddPort");
	public static Event<ICommunity> UpdateMapAddCity 				= new Event<ICommunity>("UpdateMapAddCity");
	public static Event<ICommunity> UpdateMapAddSettlement 			= new Event<ICommunity>("UpdateMapAddSettlement");
	public static Event<IHex> 		UpdateMapAddHex 				= new Event<IHex>("UpdateMapAddHex");
	public static Event<IEdgeValue> UpdateMapAddRoad 				= new Event<IEdgeValue>("UpdateMapAddRoad");
	public static Event<Object> 	UpdateMapRobber 				= new Event<Object>("UpdateMapRobber");
	public static Event<Integer> 	UpdatePlayerSoldiers 			= new Event<Integer>("UpdatePlayerSoldiers");
	public static Event<Integer> 	UpdatePlayerMonuments 			= new Event<Integer>("UpdatePlayerMonuments");
	public static Event<Integer> 	UpdatePlayerPlayedDevCard 		= new Event<Integer>("UpdatePlayerPlayedDevCard");
	public static Event<Integer> 	UpdatePlayerDiscarded 			= new Event<Integer>("UpdatePlayerDiscarded");
	public static Event<Integer> 	UpdatePlayerVictoryPoints 		= new Event<Integer>("UpdatePlayerVictoryPoints");
	public static Event<Integer> 	UpdatePlayerRoads 				= new Event<Integer>("UpdatePlayerRoads");
	public static Event<Integer> 	UpdatePlayerName 				= new Event<Integer>("UpdatePlayerName");
	public static Event<Integer> 	UpdatePlayerColor 				= new Event<Integer>("UpdatePlayerColor");
	public static Event<Integer> 	UpdatePlayerSettlements 		= new Event<Integer>("UpdatePlayerSettlements");
	public static Event<Integer> 	UpdatePlayerCities 				= new Event<Integer>("UpdatePlayerCities");
	public static Event<Object> 	UpdateAddBlankPlayer 			= new Event<Object>("UpdateAddBlankPlayer");
	public static Event<Integer>	UpdateChat 						= new Event<Integer>("UpdateChat");
	public static Event<Integer> 	UpdateLog 						= new Event<Integer>("UpdateLog");
	public static Event<Object> 	UpdateLongestRoad 				= new Event<Object>("UpdateLongestRoad");
	public static Event<Object> 	UpdateLargestArmy 				= new Event<Object>("UpdateLargestArmy");
	public static Event<Object> 	UpdateStatus 					= new Event<Object>("UpdateStatus");
	public static Event<Object> 	UpdateCurrentTurn 				= new Event<Object>("UpdateCurrentTurn");
	public static Event<Object> 	UpdateWinner 					= new Event<Object>("UpdateWinner");
	
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return this.name;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Event other = (Event) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
}
