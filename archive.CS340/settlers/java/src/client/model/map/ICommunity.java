package client.model.map;

import shared.locations.VertexLocation;
import client.model.IPlayer;

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
