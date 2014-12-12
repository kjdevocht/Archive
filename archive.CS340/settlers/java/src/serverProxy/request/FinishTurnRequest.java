package serverProxy.request;

import com.google.gson.JsonObject;
import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /moves/finishTurn endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre It's your turn
 * @.pre The client model status is 'Playing'
 * @.post It’s the next players turn 
 */
public class FinishTurnRequest implements IRequest {

	private int playerIndex;
			
	/**
	 * @param playerIndex
	 */
	public FinishTurnRequest(int playerIndex) {
		this.setPlayerIndex(playerIndex);
	}

	@Override
	public String getEndpoint() {
		return "/moves/finishTurn";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "finishTurn");
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
