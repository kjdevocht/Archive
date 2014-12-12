package serverProxy.request;

import com.google.gson.JsonObject;

import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /games/join endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre The player has a valid catan.user cookie set. 
 * @.pre The player may join the game because there is space, or they are already in the game 
 */
public class JoinGameRequest implements IRequest {

	private int gameId;
	private String color;
	
	public JoinGameRequest(int gameId, String color){
		this.setGameId(gameId);
		this.setColor(color);
	}
	
	@Override
	public String getEndpoint() {
		return "/games/join";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("id", this.getGameId());
		obj.addProperty("color", this.getColor());
		
		return obj.toString();
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the gameId
	 */
	public int getGameId() {
		return gameId;
	}

	/**
	 * @param gameId the gameId to set
	 */
	private void setGameId(int gameId) {
		this.gameId = gameId;
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color the color to set
	 */
	private void setColor(String color) {
		this.color = color;
	}

	
}
