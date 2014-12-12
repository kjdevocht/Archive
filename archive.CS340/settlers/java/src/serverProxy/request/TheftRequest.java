package serverProxy.request;

import com.google.gson.JsonObject;
import serverProxy.IRequest;
import serverProxy.IResponse;
import shared.locations.*;

abstract class TheftRequest implements IRequest {

	private int playerIndex;
	private int victimIndex;
	private HexLocation location;
	
	/**
	 * @param playerIndex
	 * @param victimIndex
	 * @param location
	 */
	public TheftRequest(int playerIndex, int victimIndex, HexLocation location) {
		this.setLocation(location);
		this.setPlayerIndex(playerIndex);
		this.setVictimIndex(victimIndex);
	}

	@Override
	public abstract String getEndpoint();
	
	public abstract String getType();

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
        JsonObject object = new JsonObject();
        object.addProperty("type", this.getType());
        object.addProperty("playerIndex", this.getPlayerIndex());
        object.addProperty("victimIndex", this.getVictimIndex());

        JsonObject locationObject = new JsonObject();
        locationObject.addProperty("x", this.getLocation().getX());
        locationObject.addProperty("y", this.getLocation().getY());

        object.add("location", locationObject);
//		System.out.println(object.toString());
        return object.toString();
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the playerIndex
	 */
	public int getPlayerIndex() {
		return playerIndex;
	}

	/**
	 * @param playerIndex the playerIndex to set
	 */
	private void setPlayerIndex(int playerIndex) {
		this.playerIndex = playerIndex;
	}

	/**
	 * @return the victimIndex
	 */
	public int getVictimIndex() {
		return victimIndex;
	}

	/**
	 * @param victimIndex the victimIndex to set
	 */
	private void setVictimIndex(int victimIndex) {
		this.victimIndex = victimIndex;
	}

	/**
	 * @return the location
	 */
	public HexLocation getLocation() {
		return location;
	}

	/**
	 * @param location the location to set
	 */
	private void setLocation(HexLocation location) {
		this.location = location;
	}

	
}
