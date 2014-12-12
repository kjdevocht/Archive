package client.model.map;


import shared.definitions.ResourceType;
import shared.locations.HexLocation;
/**
 * Represents a Hex in the Catan Map
 * Domain: HexLocation, Resource type, and the value of chit
 * @.pre None
 * @.post creates a <code>IHex</code>
 */
public interface IHex {
	/**
	 *
	 * @.obviousGetter
	 */
	public HexLocation getLocation();
	/**
	 * @.obviousSetter
	 * @param location
	 */
	public void setLocation(HexLocation location);

	/**
	 *
	 * @.obviousGetter
	 */
	public ResourceType getResource();

	/**
	 * @.obviousSetter
	 * @param resource
	 */
	public void setResource(ResourceType resource);

	/**
	 *
	 * @.obviousGetter
	 */
	public int getNumber();

	/**
	 * @.obviousSetter
	 * @param number
	 */
	public void setNumber(int number);



}