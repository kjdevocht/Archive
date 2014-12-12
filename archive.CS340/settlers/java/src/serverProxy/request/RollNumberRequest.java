package serverProxy.request;

import com.google.gson.JsonObject;
import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /moves/rollNumber endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre The client model’s status is ‘rolling’ 
 * @.pre It’s your turn 
 * @.post The client model’s status is now in ‘discarding’ or ‘robbing’ or ‘playing’  
 */
public class RollNumberRequest implements IRequest {

	private int playerIndex;
	private int number;
	
	/**
	 * @param playerIndex
	 * @param number
	 */
	public RollNumberRequest(int playerIndex, int number) {
		this.setNumber(number);
		this.setPlayerIndex(playerIndex);
	}

	@Override
	public String getEndpoint() {
		return "/moves/rollNumber";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "rollNumber");
        object.addProperty("playerIndex", this.getPlayerIndex());
        object.addProperty("number", this.getNumber());
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
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	private void setNumber(int number) {
		this.number = number;
	}

	
}
