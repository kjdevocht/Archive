package serverProxy.request;

import client.model.map.IEdgeValue;

import com.google.gson.JsonObject;

import serverProxy.IRequest;
import serverProxy.IResponse;
import shared.locations.*;

/**
 * Represents a server command to the /moves/Road_Building endpoint
 * @author Jacob Glad
 *
 * 
 * @.pre You have the specific card you want to play in your 'old dev card hand' 
 * @.pre You haven't played a dev card this turn yet 
 * @.pre It's your turn 
 * @.pre The client model status is 'Playing' 
 * @.pre The first road location is connected to one of your roads 
 * @.pre The second road location is connected to one of your roads or the Previous location 
 * @.pre Neither location is on water 
 * @.pre You have two roads 
 * @.post You use two roads 
 * @.post The map lists the roads correctly 
 */
public class RoadBuildingRequest implements IRequest {

	private int playerIndex;
	private EdgeLocation spot1;
	private EdgeLocation spot2;

	/**
	 * @param playerIndex
	 * @param spot1
	 * @param spot2
	 */
	public RoadBuildingRequest(int playerIndex, EdgeLocation spot1,
			EdgeLocation spot2) {
		this.setPlayerIndex(playerIndex);
		this.setSpot1(spot1);
		this.setSpot2(spot2);
	}

	@Override
	public String getEndpoint() {
		return "/moves/Road_Building";
	}

    @Override
    public String getRequestMethod() {
        return "POST";
    }

    @Override
    public String getBody() {
        JsonObject object = new JsonObject();
        object.addProperty("type", "Road_Building");
        object.addProperty("playerIndex", this.getPlayerIndex());

        JsonObject spot1Json = new JsonObject();
        spot1Json.addProperty("x", spot1.getHexLoc().getX());
        spot1Json.addProperty("y", spot1.getHexLoc().getY());
        spot1Json.addProperty("direction", EdgeLocation.getEdgeString(spot1));

        JsonObject spot2Json = new JsonObject();
        spot2Json.addProperty("x", spot2.getHexLoc().getX());
        spot2Json.addProperty("y", spot2.getHexLoc().getY());
        spot2Json.addProperty("direction", EdgeLocation.getEdgeString(spot2));

        object.add("spot1", spot1Json);
        object.add("spot2", spot2Json);
        
//        System.out.println(object.toString());
        
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

	/**
	 * @return the spot1
	 */
	public EdgeLocation getSpot1() {
		return spot1;
	}

	/**
	 * @param spot1 the spot1 to set
	 */
	private void setSpot1(EdgeLocation spot1) {
		this.spot1 = spot1;
	}

	/**
	 * @return the spot2
	 */
	public EdgeLocation getSpot2() {
		return spot2;
	}

	/**
	 * @param spot2 the spot2 to set
	 */
	private void setSpot2(EdgeLocation spot2) {
		this.spot2 = spot2;
	}

	
}
