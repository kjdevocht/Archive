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
 * @.post You gain the two resources specified 
 */
public class YearOfPlentyRequest implements IRequest {

	private int playerIndex;
	private ResourceType resource1;
	private ResourceType resource2;
	
	
	/**
	 * @param playerIndex
	 * @param resource1
	 * @param resource2
	 */
	public YearOfPlentyRequest(int playerIndex, ResourceType resource1,
			ResourceType resource2) {
		this.setPlayerIndex(playerIndex);
		this.setResource1(resource1);
		this.setResource2(resource2);
	}

	@Override
	public String getEndpoint() {
		return "/moves/Year_of_Plenty";
	}

    @Override
    public String getRequestMethod() {
        return "POST";
    }

    @Override
    public String getBody() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "Year_of_Plenty");
        object.addProperty("playerIndex", this.getPlayerIndex());
        object.addProperty("resource1", String.valueOf(this.getResource1()).toLowerCase());
        object.addProperty("resource2", String.valueOf(this.getResource2()).toLowerCase());
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
	 * @return the resource1
	 */
	public ResourceType getResource1() {
		return resource1;
	}

	/**
	 * @param resource1 the resource1 to set
	 */
	private void setResource1(ResourceType resource1) {
		this.resource1 = resource1;
	}

	/**
	 * @return the resource2
	 */
	public ResourceType getResource2() {
		return resource2;
	}

	/**
	 * @param resource2 the resource2 to set
	 */
	private void setResource2(ResourceType resource2) {
		this.resource2 = resource2;
	}

	
}
