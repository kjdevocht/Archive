package serverProxy.request;

import serverProxy.IRequest;
import serverProxy.IResponse;

/**
 * Represents a server command to the /game/commands [GET] endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre The player has a valid catan.user and catan.game id 
 */
public class GetGameCommandsRequest implements IRequest {

	private int userId;
	private int gameId;

    public GetGameCommandsRequest() {}

	public GetGameCommandsRequest(int userId, int gameId){
		this.setGameId(gameId);
		this.setUserId(userId);
	}
	
	@Override
	public String getEndpoint() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getRequestMethod() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getBody() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IResponse getDefaultResponse() {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the userId
	 */
	public int getUserId() {
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	private void setUserId(int userId) {
		this.userId = userId;
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
}
