package server.model.game;

import shared.definitions.DevCardType;

/**
 * Represents a list of dev cards in a Catan game
 * 
 * Domain: A quantity for each of the dev card types
 * 
 * Constructor: A Dev card list with all of the quantities as zero.
 *
 */
public interface IDevCardList {

	/**
	 * @.obviousGetter
	 */
	public int getDevCardCount(DevCardType type);
	/**
	 * @.obviousSetter
	 */
	public void setDevCardCount(DevCardType type, int amount);
	/**
	 * @.pre None
	 * @.post the total count of all the dev cards in the list
	 */
	public int getTotalCardCount();
}
