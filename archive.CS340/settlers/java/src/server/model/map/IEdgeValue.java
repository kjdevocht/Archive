package server.model.map;


import server.model.game.IPlayer;
import shared.locations.EdgeLocation;

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