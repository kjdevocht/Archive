package serverProxy.request;

import com.google.gson.JsonObject;
import serverProxy.IRequest;
import serverProxy.IResponse;
import shared.locations.*;

/**
 * Represents a server command to the /moves/buildRoad endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre It's your turn
 * @.pre The client model status is 'Playing'
 * @.pre The road location is open 
 * @.pre The road location is connected to another road you own 
 * @.pre The road location is not on water 
 * @.pre You have the resources (1 wood, 1 brick; 1 road) 
 * @.post You expend the resources to play the road (1 wood, 1 brick; 1 road)
 * @.post The map lists the road correctly 
 */
public class BuildRoadRequest implements IRequest {

	private int playerIndex;
	private EdgeLocation roadLocation;
	private boolean free;
	
	public BuildRoadRequest(int playerIndex, EdgeLocation roadLocation, boolean free){
		this.setFree(free);
		this.setPlayerIndex(playerIndex);
		this.setRoadLocation(roadLocation);
	}
	@Override
	public String getEndpoint() {
		return "/moves/buildRoad";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "buildRoad");
        object.addProperty("playerIndex", this.getPlayerIndex());
        object.addProperty("free", this.isFree());

        JsonObject roadJson = new JsonObject();
        roadJson.addProperty("x", this.getRoadLocation().getHexLoc().getX());
        roadJson.addProperty("y", this.getRoadLocation().getHexLoc().getY());
        roadJson.addProperty("direction", EdgeLocation.getEdgeString(this.getRoadLocation()));

        object.add("roadLocation", roadJson);


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
	 * @return the roadLocation
	 */
	public EdgeLocation getRoadLocation() {
		return roadLocation;
	}
	/**
	 * @param roadLocation the roadLocation to set
	 */
	private void setRoadLocation(EdgeLocation roadLocation) {
		this.roadLocation = roadLocation;
	}
	/**
	 * @return the free
	 */
	public boolean isFree() {
		return free;
	}
	/**
	 * @param free the free to set
	 */
	private void setFree(boolean free) {
		this.free = free;
	}

	
}
