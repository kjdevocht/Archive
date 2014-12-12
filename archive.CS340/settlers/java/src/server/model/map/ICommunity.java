package server.model.map;

import server.model.game.IPlayer;
import shared.locations.VertexLocation;

/**
 * Interface for both cities and settlements
 * @.pre None
 * @.post creates a <code>ICommunity</code>
 */
public interface ICommunity {


	/**
	 *
	 * @.obviousGetter
	 */
	public IPlayer getOwner();

	/**
	 * @.obviousSetter
	 * @param owner
	 */
	public void setOwner(IPlayer owner);
	/**
	 *
	 * @.obviousGetter
	 */
	public VertexLocation getLocation();
	/**
	 * @.obviousSetter
	 * @param location
	 */
	public void setLocation(VertexLocation location);

}
