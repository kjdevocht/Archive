package server.model.map;


import shared.definitions.ResourceType;
import shared.locations.HexLocation;

public class Hex implements IHex {

	private HexLocation location;
	private ResourceType resource;
	int number;

	/**
	 * @.pre none
	 * @.post A new Hex is created
	 * @.post All variables are set to null or -1
	 */
	public Hex(){
		location = null;
		resource = null;
		number = -1;

	}
	public Hex(HexLocation location, ResourceType resource, int number){
		this.location = location;
		this.resource = resource;
		this.number = number;
	}

	/**
	 *
	 * @.obviousGetter
	 */
	public HexLocation getLocation() {
		return location;
	}

	/**
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
	public ResourceType getResource() {
		return resource;
	}

	/**
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
	public int getNumber() {
		return number;
	}

	/**
	 * @.obviousSetter
	 * @param number
	 */
	public void setNumber(int number) {
		this.number = number;
	}



}