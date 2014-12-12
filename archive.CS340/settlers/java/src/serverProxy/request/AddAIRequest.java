package serverProxy.request;

import com.google.gson.JsonObject;

import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /game/addA endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre The player has a valid catan.user and catan.game id
 * @.pre There is space in the game for an AI player  
 * @.post The AI player is added to the next open spot in the game in the poster’s catan.game cookie 
 */
public class AddAIRequest implements IRequest {
	
	private String aiType;
	
	public AddAIRequest(String aiType){
		this.setAiType(aiType);
	}
	
	@Override
	public String getEndpoint() {
		return "/game/addAI";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("AIType", this.getAiType());
		
		return obj.toString();
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the aiType
	 */
	public String getAiType() {
		return aiType;
	}

	/**
	 * @param aiType the aiType to set
	 */
	private void setAiType(String aiType) {
		this.aiType = aiType;
	}	
	
	
}
