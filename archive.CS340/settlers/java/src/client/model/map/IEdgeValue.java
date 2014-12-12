package client.model.map;


import shared.locations.EdgeLocation;
import client.model.IPlayer;
/**
 * Represents the values used where a road is placed
 * Domain: a player and edge location
 * @.pre None
 * @.post creates a <code>IEdgeValue</code>
 */
public interface IEdgeValue{
	/**
	 *
	 * @.obviousGetter
	 */
	public IPlayer getOwner();

	/**
	 *
	 * @.obviousGetter
	 * @param owner
	 */
	public void setOwner(IPlayer owner);

	/**
	 *
	 * @.obviousGetter
	 */
	public EdgeLocation getLocation();

	/**
	 *
	 * @.obviousGetter
	 * @param location
	 */
	public void setLocation(EdgeLocation location);
}