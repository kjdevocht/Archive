package client.model.map;

import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.HexLocation;

public class Port implements IPort{
	private ResourceType resource;
	private HexLocation location;
	private EdgeDirection direction;
	int ratio;

	/**
	 * @.pre none
	 * @.post A new Port is created
	 * @.post All variables are set to null or -1
	 */
	public Port() {
		this.ratio = -1;
		this.resource = null;
		this.location = null;
		this.direction = null;
	}

	/**
	 *
	 * @.obviousGetter
	 */
	public ResourceType getResource() {
		return resource;
	}

	/**
	 *
	 * @.obviousSetter
	 * @param resource
	 */
	public void setResource(ResourceType resource) {
		this.resource = resource;
	}

	/**
	 *
	 * @.obviousGetter
	 */
	public HexLocation getLocation() {
		return location;
	}

	/**
	 *
	 * @.obviousSetter
	 * @param location
	 */
	public void setLocation(HexLocation location) {
		this.location = location;
	}

	/**
	 *
	 * @.obviousGetter
	 */
	public EdgeDirection getDirection() {
		return direction;
	}

	/**
	 *
	 * @.obviousSetter
	 * @param direction
	 */
	public void setDirection(EdgeDirection direction) {
		this.direction = direction;
	}

	/**
	 *
	 * @.obviousGetter
	 */
	public int getRatio() {
		return ratio;
	}

	/**
	 *
	 * @.obviousSetter
	 * @param ratio
	 */
	public void setRatio(int ratio) {
		this.ratio = ratio;
	}
}