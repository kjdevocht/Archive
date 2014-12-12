package serverProxy.request;

import com.google.gson.JsonObject;
import server.model.game.ResourceList;
import serverProxy.IRequest;
import serverProxy.IResponse;
import shared.definitions.ResourceType;

/**
 * Represents a server command to the /moves/Year_of_Plenty endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre The state of the client model is 'Discarding'
 * @.pre You have over 7 cards
 * @.pre You have the cards you're choosing to discard 
 * @.post If you're the last one to discard, the client model status changes to 'Robbing' 
 * @.post You give up the specified resources 
 */
public class DiscardCardsRequestServer implements IRequest {

	private ResourceList discardedCards;
	private int playerIndex;

	public DiscardCardsRequestServer(ResourceList discardedCards, int playerIndex){
		this.setDiscardedCards(discardedCards);
		this.setPlayerIndex(playerIndex);
	}
	
	@Override
	public String getEndpoint() {
		return "/moves/discardCards";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("type", "discardCards");
		obj.addProperty("playerIndex", playerIndex);
		
		JsonObject resources = new JsonObject();

        resources.addProperty("brick", discardedCards.getQuantity(ResourceType.BRICK));
        resources.addProperty("ore", discardedCards.getQuantity(ResourceType.ORE));
        resources.addProperty("sheep", discardedCards.getQuantity(ResourceType.SHEEP));
        resources.addProperty("wheat", discardedCards.getQuantity(ResourceType.WHEAT));
        resources.addProperty("wood", discardedCards.getQuantity(ResourceType.WOOD));
		
		obj.add("discardedCards", resources);
		
		return obj.toString();
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the discardedCards
	 */
	public ResourceList getDiscardedCards() {
		return discardedCards;
	}

	/**
	 * @param discardedCards the discardedCards to set
	 */
	private void setDiscardedCards(ResourceList discardedCards) {
		this.discardedCards = discardedCards;
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
