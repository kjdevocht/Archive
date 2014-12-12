package serverProxy.request;

import serverProxy.IRequest;
import serverProxy.IResponse;
import com.google.gson.JsonObject;

/**
 * Represents a server command to the /games/create  endpoint
 * @author Jacob Glad
 *
 * 
 */
public class CreateGamesRequest implements IRequest {

	private String name;
	private boolean randomTiles;
	private boolean randomNumbers;
	private boolean randomPorts;
	

	public CreateGamesRequest(String name, boolean randomTiles, boolean randomNumbers, boolean randomPorts){
		this.setName(name);
		this.setRandomNumbers(randomNumbers);
		this.setRandomPorts(randomPorts);
		this.setRandomTiles(randomTiles);
	}
	
	@Override
	public String getEndpoint() {
		return "/games/create";
	}

	@Override
	public String getRequestMethod() {
		return "POST";
	}

	@Override
	public String getBody() {
		JsonObject obj = new JsonObject();
		
		obj.addProperty("randomTiles", this.isRandomTiles());
		obj.addProperty("randomNumbers", this.isRandomNumbers());
		obj.addProperty("randomPorts", this.isRandomPorts());
		obj.addProperty("name", this.getName());
		
		return obj.toString();
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	private void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the randomTiles
	 */
	public boolean isRandomTiles() {
		return randomTiles;
	}

	/**
	 * @param randomTiles the randomTiles to set
	 */
	private void setRandomTiles(boolean randomTiles) {
		this.randomTiles = randomTiles;
	}

	/**
	 * @return the randomNumbers
	 */
	public boolean isRandomNumbers() {
		return randomNumbers;
	}

	/**
	 * @param randomNumbers the randomNumbers to set
	 */
	private void setRandomNumbers(boolean randomNumbers) {
		this.randomNumbers = randomNumbers;
	}

	/**
	 * @return the randomPorts
	 */
	public boolean isRandomPorts() {
		return randomPorts;
	}

	/**
	 * @param randomPorts the randomPorts to set
	 */
	private void setRandomPorts(boolean randomPorts) {
		this.randomPorts = randomPorts;
	}

	
}
