package server.model.map;


import server.model.game.IPlayer;
import shared.locations.EdgeLocation;

public class EdgeValue implements IEdgeValue {

	private IPlayer owner;
	private EdgeLocation location;

	/**
	 * @.pre none
	 * @.post A new EdgeValue is created
	 * @.post All variables are set to null
	 */
	public EdgeValue(){
		owner = null;
		location = null;
	}

	/**
	 *
	 * @.obviousGetter
	 */
	public IPlayer getOwner() {
		return owner;
	}

	/**
	 *
	 * @.obviousGetter
	 * @param owner
	 */
	public void setOwner(IPlayer owner) {
		this.owner = owner;
	}

	/**
	 *
	 * @.obviousGetter
	 */
	public EdgeLocation getLocation() {
		return location;
	}

	/**
	 *
	 * @.obviousGetter
	 * @param location
	 */
	public void setLocation(EdgeLocation location) {
		this.location = location;
	}
}