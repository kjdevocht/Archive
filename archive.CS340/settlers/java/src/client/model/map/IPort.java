package client.model.map;

import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;
/**
 * 
 * Represents the port in a Catan game
 * Domain: Resource type, Hex location, the trade ratio
 * @.pre None
 * @.post creates a <code>IPort</code>
 */
public interface IPort {
	/**
	 *
	 * @.obviousGetter
	 */
	public ResourceType getResource();

	/**
	 *
	 * @.obviousSetter
	 * @param resource
	 */
	public void setResource(ResourceType resource);

	/**
	 *
	 * @.obviousGetter
	 */
	public HexLocation getLocation();

	/**
	 *
	 * @.obviousSetter
	 * @param location
	 */
	public void setLocation(HexLocation location);

	/**
	 *
	 * @.obviousGetter
	 */
	public EdgeDirection getDirection();

	/**
	 *
	 * @.obviousSetter
	 * @param direction
	 */
	public void setDirection(EdgeDirection direction);

	/**
	 *
	 * @.obviousGetter
	 */
	public int getRatio();

	/**
	 *
	 * @.obviousSetter
	 * @param ratio
	 */
	public void setRatio(int ratio);
}