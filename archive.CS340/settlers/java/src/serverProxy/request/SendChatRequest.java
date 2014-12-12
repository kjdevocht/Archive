package serverProxy.request;

import com.google.gson.JsonObject;
import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /games/sendChat endpoint
 * @author Jacob Glad
 *
 * 
 * @.post The chat contains your message at the end
 */
public class SendChatRequest implements IRequest {

	private String message;
	private int playerIndex;
	
	/**
	 * @param message
	 */
	public SendChatRequest(String message, int playerIndex) {
		this.setMessage(message);
		this.setPlayerIndex(playerIndex);
	}
	
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	private void setMessage(String message) {
		this.message = message;
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

	@Override
	public String getEndpoint() {
		return "/moves/sendChat";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
		JsonObject object = new JsonObject();
        object.addProperty("type", "sendChat");
        object.addProperty("content", this.getMessage());
        object.addProperty("playerIndex", this.getPlayerIndex());
        return object.toString();
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}

}
