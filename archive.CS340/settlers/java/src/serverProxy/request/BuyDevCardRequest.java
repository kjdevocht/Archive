package serverProxy.request;

import com.google.gson.JsonObject;
import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /moves/buyDevCard  endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre It's your turn
 * @.pre The client model status is 'Playing'
 * @.pre You have the resources (1 ore, 1 wheat, 1 sheep)
 * @.pre There are dev cards left in the deck 
 * @.post You have the new card
 * @.post If it is a monument card, it goes into the old devcard hand
 * @.post If it’s any other card, it goes into the new devcard hand (unplayable this turn)
 */
public class BuyDevCardRequest implements IRequest {

	private int playerIndex; 
	
	/**
	 * @param playerIndex
	 */
	public BuyDevCardRequest(int playerIndex) {
		this.setPlayerIndex(playerIndex);
	}

	@Override
	public String getEndpoint() {
		return "/moves/buyDevCard";
	}

    @Override
    public String getRequestMethod() {
        return "POST";
    }

    @Override
    public String getBody() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "buyDevCard");
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
