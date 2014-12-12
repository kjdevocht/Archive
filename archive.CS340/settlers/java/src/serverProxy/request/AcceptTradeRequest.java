package serverProxy.request;

import com.google.gson.JsonObject;

import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /moves/acceptTrade endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre You have been offered a domestic trade
 * @.pre You have the  resources 
 * @.post If you accepted, you and the player who offered swap the specified resouces
 * @.post If you declined no resources are changed
 * @.post The trade offer is removed 
 */
public class AcceptTradeRequest implements IRequest {

	private int playerIndex;
	private boolean willAccept;
	
	public AcceptTradeRequest(int playerIndex, boolean willAccept){
		this.setPlayerIndex(playerIndex);
		this.setWillAccept(willAccept);
	}
	
	@Override
	public String getEndpoint() {
		return "/moves/acceptTrade";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("type", "acceptTrade");
		obj.addProperty("playerIndex", this.getPlayerIndex());
		obj.addProperty("willAccept", this.isWillAccept());

//        System.out.println(obj.toString());

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
	 * @return the willAccept
	 */
	public boolean isWillAccept() {
		return willAccept;
	}

	/**
	 * @param willAccept the willAccept to set
	 */
	private void setWillAccept(boolean willAccept) {
		this.willAccept = willAccept;
	}

	
}
