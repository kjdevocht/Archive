package serverProxy.request;

import com.google.gson.JsonObject;

import client.model.*;
import serverProxy.IRequest;
import serverProxy.IResponse;
import shared.definitions.ResourceType;

/**
 * Represents a server command to the /moves/offerTrade endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre It's your turn
 * @.pre The client model status is 'Playing'
 * @.pre You have the resource 
 * @.post The trade is offered to the other player (stored in the model)  
 */
public class OfferTradeRequest implements IRequest {

	private int playerIndex;
	private ResourceList offer;
	private int receiver;
	
	public OfferTradeRequest(int playerIndex, ResourceList offer, int receiver){
		this.setOffer(offer);
		this.setPlayerIndex(playerIndex);
		this.setReceiver(receiver);
	}
	
	@Override
	public String getEndpoint() {
		return "/moves/offerTrade";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("type", "offerTrade");
		obj.addProperty("playerIndex", this.getPlayerIndex());
		obj.addProperty("receiver", this.getReceiver());
		

		JsonObject resources = new JsonObject();

        //THIS IS A HACK BECAUSE WHEN I MADE THIS TE SWAGGER PAGE HAD +/- FLIPPED...
        ResourceList flipped = new ResourceList();
        //flipped = getOffer();
        flipped.setQuantity(ResourceType.BRICK, -1*this.getOffer().getQuantity(ResourceType.BRICK));
        flipped.setQuantity(ResourceType.WOOD, -1*this.getOffer().getQuantity(ResourceType.WOOD));
        flipped.setQuantity(ResourceType.WHEAT, -1*this.getOffer().getQuantity(ResourceType.WHEAT));
        flipped.setQuantity(ResourceType.ORE, -1*this.getOffer().getQuantity(ResourceType.ORE));
        flipped.setQuantity(ResourceType.SHEEP, -1*this.getOffer().getQuantity(ResourceType.SHEEP));

        resources.addProperty("brick", flipped.getQuantity(ResourceType.BRICK));
        resources.addProperty("ore", flipped.getQuantity(ResourceType.ORE));
        resources.addProperty("sheep", flipped.getQuantity(ResourceType.SHEEP));
        resources.addProperty("wheat", flipped.getQuantity(ResourceType.WHEAT));
        resources.addProperty("wood", flipped.getQuantity(ResourceType.WOOD));



		obj.add("offer", resources);

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
	 * @return the offer
	 */
	public ResourceList getOffer() {
		return offer;
	}

	/**
	 * @param offer the offer to set
	 */
	private void setOffer(ResourceList offer) {
		this.offer = offer;
	}

	/**
	 * @return the receiver
	 */
	public int getReceiver() {
		return receiver;
	}

	/**
	 * @param receiver the receiver to set
	 */
	private void setReceiver(int receiver) {
		this.receiver = receiver;
	}

}
