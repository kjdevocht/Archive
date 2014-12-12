package serverProxy.request;

import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /game/model?version=x endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre The player has a valid catan.user and catan.game id
 * @.post The model is updated if necessary 
 */
public class GameModelRequest implements IRequest {

	private Integer versionNumber;
	
	public GameModelRequest(Integer versionNumber){
		this.setVersionNumber(versionNumber);
	}
	
	@Override
	public String getEndpoint() {
		String endpoint = "/game/model";
		if(versionNumber != null){
			endpoint += "?version=" + versionNumber;
		}
		return endpoint;
	}

	@Override
	public String getRequestMethod() {
		return "GET";
	}

	@Override
	public String getBody() {
		return "";
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the versionNumber
	 */
	public Integer getVersionNumber() {
		return versionNumber;
	}

	/**
	 * @param versionNumber the versionNumber to set
	 */
	private void setVersionNumber(Integer versionNumber) {
		this.versionNumber = versionNumber;
	}

	
}
