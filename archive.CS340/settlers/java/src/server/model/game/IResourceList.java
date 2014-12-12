package server.model.game;

import shared.definitions.ResourceType;

/**
 * The ResourceList class represents a list of ResourceCards, and the quantity
 * of each.
 * 
 * Domain: a quantity (positive or negative) for all of the ResourceTypes
 * 
 * Constructor: An resource list with all of the values set to zero.
 */
public interface IResourceList {

	/**
	 * @.pre Type must be a valid ResourceType.
	 * @.post Result represents the number of cards of the given ResourceType.
	 * @param type the ResourceType to get the quantity of
	 */
	public int getQuantity(ResourceType type);

	/**
	 * @.pre Type must be a valid ResourceType
	 * @.pre Quantity must represent a valid quantity (positive or negative)
	 * @.pre The quantity specified must be allowable per the game rules.
	 * @.post The quantity representing the given ResourceType will be updated
	 *        to the given quantity.
	 * @param type The ResourceType to set the quantity of.
	 * @param quantity The value to set the quantity to.
	 */
	public void setQuantity(ResourceType type, int quantity);
	/**
	 * @.pre None
	 * @.post the total amount of resources in the list.
	 */
	public int getTotalResourceCount();
	/**
	 * @.pre A none null input
	 * @.post True is this list has more resources for every resource than the input resource list
	 */
	public boolean isGreaterOrEqualThanInput(IResourceList input);
}
