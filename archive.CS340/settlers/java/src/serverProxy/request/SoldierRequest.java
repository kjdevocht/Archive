package serverProxy.request;

import serverProxy.IRequest;
import shared.locations.HexLocation;
/**
 * Represents a server command to the /moves/Soldier endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre You have the specific card you want to play in your 'old dev card hand' 
 * @.pre You haven't played a dev card this turn yet 
 * @.pre It's your turn 
 * @.pre The client model status is 'Playing' 
 * @.pre The robber isn't being kept in the same place 
 * @.pre The player to rob has cards (­1 if you can’t rob anyone)
 * @.post The robber is in the new location
 * @.post The player to rob gives one random resource card to the player playing the soldier 
 */
public class SoldierRequest extends TheftRequest implements IRequest {

	public SoldierRequest(int playerIndex, int victimIndex, HexLocation location) {
		super(playerIndex, victimIndex, location);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getEndpoint() {
		return "/moves/Soldier";
	}

	@Override
	public String getType() {
		return "Soldier";
	}

}
