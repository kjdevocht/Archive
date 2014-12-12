package server.model.map;


import server.model.game.IPlayer;
import shared.locations.VertexLocation;

/**
 * Created by Kevin on 9/30/2014.
 */
public class City implements ICommunity {

    private IPlayer owner;
    private VertexLocation location;

	/**
	 * @.pre none
	 * @.post A new City is created
	 * @.post All variables are set to null
	 */
    public City() {
        this.owner = null;
        this.location = null;

    }

	/**
	 * @.pre none
	 * @.post A new City is created
	 */
    public City(IPlayer owner, VertexLocation location) {
        this.owner = owner;
        this.location = location;
    }


    public IPlayer getOwner() {
        return owner;
    }

    public void setOwner(IPlayer owner) {
        this.owner = owner;
    }

    public VertexLocation getLocation() {
        return location;
    }

    public void setLocation(VertexLocation location) {
        this.location = location;
    }




}
