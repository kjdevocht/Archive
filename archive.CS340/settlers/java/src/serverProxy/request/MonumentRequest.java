package serverProxy.request;

import com.google.gson.JsonObject;
import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /moves/Monument endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre You have the specific card you want to play in your 'old dev card hand' 
 * @.pre You haven't played a dev card this turn yet 
 * @.pre It's your turn 
 * @.pre The client model status is 'Playing' 
 * @.post You gain a victory point 
 */
public class MonumentRequest implements IRequest {

	private int playerIndex;
	
	public MonumentRequest(int playerIndex) {
		this.setPlayerIndex(playerIndex);
	}

	@Override
	public String getEndpoint() {
		return "/moves/Monument";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "Monument");
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

	
}
