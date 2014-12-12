package serverProxy.request;

import com.google.gson.JsonObject;

import serverProxy.IRequest;
import serverProxy.IResponse;
import shared.definitions.ResourceType;

/**
 * Represents a server command to the /moves/maritimeTrade endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre It's your turn
 * @.pre The client model status is 'Playing'
 * @.pre You have the resources
 * @.pre You have access to the specified ratio for the specified resource
 * @.post You ratio of the input resource
 * @.post You gain 1 of the output resource
 */
public class MaritimeTradeRequest implements IRequest {

	private int ratio;
	private int playerIndex;
	private ResourceType inputResource;
	private ResourceType outputResource;
	
	public MaritimeTradeRequest(int playerIndex, int ratio, ResourceType inputResource, ResourceType outputResource) {
		this.setInputResource(inputResource);
		this.setOutputResource(outputResource);
		this.setRatio(ratio);
		this.setPlayerIndex(playerIndex);
	}

	@Override
	public String getEndpoint() {
		return "/moves/maritimeTrade";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("type", "maritimeTrade");
		obj.addProperty("ratio", this.getRatio());
		obj.addProperty("playerIndex", this.getPlayerIndex());
		obj.addProperty("inputResource", this.getInputResource().toString().toLowerCase());
		obj.addProperty("outputResource", this.getOutputResource().toString().toLowerCase());

//        System.out.println()

		return obj.toString();
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the ratio
	 */
	public int getRatio() {
		return ratio;
	}

	/**
	 * @param ratio the ratio to set
	 */
	private void setRatio(int ratio) {
		this.ratio = ratio;
	}

	/**
	 * @return the inputResource
	 */
	public ResourceType getInputResource() {
		return inputResource;
	}

	/**
	 * @param inputResource the inputResource to set
	 */
	private void setInputResource(ResourceType inputResource) {
		this.inputResource = inputResource;
	}

	/**
	 * @return the outputResource
	 */
	public ResourceType getOutputResource() {
		return outputResource;
	}

	/**
	 * @param outputResource the outputResource to set
	 */
	private void setOutputResource(ResourceType outputResource) {
		this.outputResource = outputResource;
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
