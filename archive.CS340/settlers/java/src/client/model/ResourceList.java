package client.model;

import java.util.HashMap;
import java.util.Map;

import shared.definitions.ResourceType;

/**
 * The ResourceList class represents a list of ResourceCards, and the quantity
 * of each.
 * 
 * Domain: a quantity (positive or negative) for all of the ResourceTypes
 */
public class ResourceList implements IResourceList{
	private Map<ResourceType, Integer> values = null;

	/**
	 * Creates a new ResourceList.
	 * 
	 * @.post A new ResourceList is created
	 * @.post All quantities in the new ResourceList are set to 0.
	 */
	public ResourceList() {
		values = new HashMap<ResourceType, Integer>();

	}

	/**
	 * @.pre Type must be a valid ResourceType.
	 * @.post Result represents the number of cards of the given ResourceType.
	 * @param type the ResourceType to get the quantity of
	 */
	public int getQuantity(ResourceType type) {
		if(!values.containsKey(type))
			return 0;
		return values.get(type);
	}

	/**
	 * @.pre Type must be a valid ResourceType
	 * @.pre Quantity must represent a valid quantity (positive or negative)
	 * @.pre The quantity specified must be allowable per the game rules.
	 * @.post The quantity representing the given ResourceType will be updated
	 *        to the given quantity.
	 * @param type The ResourceType to set the quantity of.
	 * @param quantity The value to set the quantity to.
	 */
	public void setQuantity(ResourceType type, int quantity) {
		values.put(type, quantity);
	}
	//TODO pre and post conditions
	public int getTotalResourceCount() {
		int count = 0;
		for(Map.Entry<ResourceType, Integer> entry : values.entrySet()) {
			count += Math.abs(entry.getValue());
		}
		return count;
	}
	//TODO pre and post
	public boolean isGreaterOrEqualThanInput(IResourceList input) {
		
		if(!isGreaterOrEqualThanInput(ResourceType.BRICK, input.getQuantity(ResourceType.BRICK)))
			return false;
		if(!isGreaterOrEqualThanInput(ResourceType.SHEEP, input.getQuantity(ResourceType.SHEEP)))
			return false;
		if(!isGreaterOrEqualThanInput(ResourceType.ORE, input.getQuantity(ResourceType.ORE)))
			return false;
		if(!isGreaterOrEqualThanInput(ResourceType.WHEAT, input.getQuantity(ResourceType.WHEAT)))
			return false;
		if(!isGreaterOrEqualThanInput(ResourceType.WOOD, input.getQuantity(ResourceType.WOOD)))
			return false;
		return true;
	}
	private boolean isGreaterOrEqualThanInput(ResourceType input, int qty) {
		return qty <= getQuantity(input);
	}
}
