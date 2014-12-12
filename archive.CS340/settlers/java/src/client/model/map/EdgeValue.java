package client.model.map;


import shared.locations.EdgeLocation;
import client.model.IPlayer;

public class EdgeValue implements IEdgeValue{

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