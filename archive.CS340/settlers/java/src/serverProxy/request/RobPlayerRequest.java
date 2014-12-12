package serverProxy.request;


import serverProxy.IRequest;
import shared.locations.HexLocation;

/**
 * Represents a server command to the /moves/robPlayer endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre It's your turn 
 * @.pre The client model status is 'Playing' 
 * @.pre The robber isn't being kept in the same place 
 * @.pre The player to rob has cards (­1 if you can’t rob anyone)
 * @.post The robber is in the new location
 * @.post The player to rob gives one random resource card to the robbing player
 */
public class RobPlayerRequest extends TheftRequest implements IRequest {

	public RobPlayerRequest(int playerIndex, int victimIndex, HexLocation location) {
		super(playerIndex, victimIndex, location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEndpoint() {
		return "/moves/robPlayer";
	}

	@Override
	public String getType() {
		return "robPlayer";
	}

}
