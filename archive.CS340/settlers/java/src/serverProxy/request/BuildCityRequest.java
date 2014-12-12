package serverProxy.request;

import com.google.gson.JsonObject;

import serverProxy.IRequest;
import serverProxy.IResponse;
import shared.locations.*;

/**
 * Represents a server command to the /moves/buildCity endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre It's your turn
 * @.pre The client model status is 'Playing'
 * @.pre The city location is where you currently have a settlement 
 * @.pre You have the resources (2 wheat, 3 ore; 1 city) 
 * @.post You expend the resources to play the settlement (2 wheat, 3 ore; 1 city)
 * @.post You get a settlement back 
 * @.post The map lists the city correctly 
 */
public class BuildCityRequest implements IRequest {

	private int playerIndex;
	private VertexLocation vertexLocation;
	
	public BuildCityRequest(int playerIndex, VertexLocation vertexLocation){
		this.setPlayerIndex(playerIndex);
		this.setVertexLocation(vertexLocation);
	}
	
	@Override
	public String getEndpoint() {
		return "/moves/buildCity";
	}

	@Override
	public String getRequestMethod() {
		return "PUT";
	}

	@Override
	public String getBody() {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("type", "buildCity");
		obj.addProperty("playerIndex", this.getPlayerIndex());
		
		JsonObject vertex = new JsonObject();
		
		vertex.addProperty("x", this.getVertexLocation().getHexLoc().getX());
		vertex.addProperty("y", this.getVertexLocation().getHexLoc().getY());
		vertex.addProperty("direction", VertexLocation.getVertexString(this.getVertexLocation()));
		
		obj.add("vertexLocation", vertex);
		
		return obj.toString();
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
	
}
