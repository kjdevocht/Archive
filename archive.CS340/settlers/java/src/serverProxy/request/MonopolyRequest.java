package serverProxy.request;

import com.google.gson.JsonObject;
import serverProxy.IRequest;
import serverProxy.IResponse;
import shared.definitions.*;

/**
 * Represents a server command to the /moves/Year_of_Plenty endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre You have the specific card you want to play in your 'old dev card hand' 
 * @.pre You haven't played a dev card this turn yet 
 * @.pre It's your turn 
 * @.pre The client model status is 'Playing' 
 * @.pre The two resources you specify are in the bank 
 * @.post All other players lose the resource card type chosen 
 * @.post The player of the card gets an equal number  
 */
public class MonopolyRequest implements IRequest {

	private int playerIndex;
	private ResourceType resource;
	
	
	/**
	 * @param playerIndex
	 * @param resource
	 */
	public MonopolyRequest(int playerIndex, ResourceType resource) {
		this.setPlayerIndex(playerIndex);
		this.setResource(resource);
	}

	@Override
	public String getEndpoint() {
		return "/moves/Monopoly";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "Monopoly");
        object.addProperty("resource", String.valueOf(this.getResource()).toLowerCase());
        object.addProperty("playerIndex", this.getPlayerIndex());

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
	 * @return the resource
	 */
	public ResourceType getResource() {
		return resource;
	}

	/**
	 * @param resource the resource to set
	 */
	private void setResource(ResourceType resource) {
		this.resource = resource;
	}

	
}
