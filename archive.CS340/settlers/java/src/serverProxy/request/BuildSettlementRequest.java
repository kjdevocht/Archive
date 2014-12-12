package serverProxy.request;

import com.google.gson.JsonObject;
import serverProxy.IRequest;
import serverProxy.IResponse;
import shared.locations.VertexLocation;

/**
 * Represents a server command to the /moves/buildSettlement endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre It's your turn
 * @.pre The client model status is 'Playing'
 * @.pre The settlement location is open 
 * @.pre The settlement location is not on water 
 * @.pre The settlement location is connected to one of your roads 
 * @.pre You have the resources (1 wood, 1 brick, 1 wheat, 1 sheep; 1 settlement) 
 * @.post You expend the resources to play the settlement (1 wood, 1 brick, 1 wheat, 1 sheep; 1 settlement)
 * @.post The map lists the settlement correctly 
 */
public class BuildSettlementRequest implements IRequest {

	private int playerIndex;
	private VertexLocation vertexLocation;
	private boolean free;
	
	public BuildSettlementRequest(int playerIndex, VertexLocation vertexLocation, boolean free){
		this.setPlayerIndex(playerIndex);
		this.setVertexLocation(vertexLocation);
		this.setFree(free);
	}
	
	@Override
	public String getEndpoint() {
		return "/moves/buildSettlement";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "buildSettlement");
        object.addProperty("playerIndex", this.getPlayerIndex());
        object.addProperty("free", this.isFree());

        JsonObject vertexJson = new JsonObject();
        vertexJson.addProperty("x", this.getVertexLocation().getHexLoc().getX());
        vertexJson.addProperty("y", this.getVertexLocation().getHexLoc().getY());
        vertexJson.addProperty("direction", VertexLocation.getVertexString(this.getVertexLocation()));

        object.add("vertexLocation", vertexJson);
//        System.out.println("BUILD SETTLEMENT REQUEST:\n" + object.toString());
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
	 * @return the vertexLocation
	 */
	public VertexLocation getVertexLocation() {
		return vertexLocation;
	}

	/**
	 * @param vertexLocation the vertexLocation to set
	 */
	private void setVertexLocation(VertexLocation vertexLocation) {
		this.vertexLocation = vertexLocation;
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
