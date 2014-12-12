package client.model.map;


import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexLocation;

import java.util.List;

/**
 * The datastructure that represent the Map in a Catan Game
 * Domain: 	hexes, ports, roads, settlements, cities, robber, radius.
 * The map is in charge of determining if a requested move in the game is valid i.e. if
 * a player can place a road at a given location or if a city can be built at a specific vertex
 *
 */
public interface ICatanMap {

	/**
	 *
	 * @.obviousGetter
	 */
	public List<IHex> getHexes();

	/**
	 * @.obviousSetter
	 * @param hexes
	 */
	public void setHexes(List<IHex> hexes);

	/**
	 *
	 * @.obviousGetter
	 */
	public List<IPort> getPorts();

	/**
	 * @.obviousSetter
	 * @param ports
	 */
	public void setPorts(List<IPort> ports);

	/**
	 * @.obviousGetter
	 */
	public List<IEdgeValue> getRoads();

	/**
	 * @.obviousSetter
	 * @param roads
	 */
	public void setRoads(List<IEdgeValue> roads);

	/**
	 *
	 * @.obviousGetter
	 */
	public List<ICommunity> getSettlements();

	/**
	 * @.obviousSetter
	 * @param settlements
	 */
	public void setSettlements(List<ICommunity> settlements);

	/**
	 *
	 * @.obviousGetter
	 */
	public List<ICommunity> getCities();

	/**
	 * @.obviousSetter
	 * @param cities
	 */
	public void setCities(List<ICommunity> cities);

	/**
	 *
	 * @.obviousGetter
	 */
	public HexLocation getRobber();

	/**
	 * @.obviousSetter
	 * @param robber
	 */
	public void setRobber(HexLocation robber);

	/**
	 *
	 * @.obviousGetter
	 */
	public int getRadius();

	/**
	 * @.obviousSetter
	 * @param radius
	 */
	public void setRadius(int radius);

	/**
	 *
	 * @.pre location must be a valid, non-null EdgeLocation
	 * @.pre playerIndex must be an int between 0 and 3
	 * @.post a boolean indicating whether or not the current player can place a road on the provided location
	 * @param location an EdgeLocation where the player is trying to place a road
	 * @param playerIndex The index of the Current Player
	 * @param status The status of the game
	 */
	public boolean canPlaceRoadAtLocation(EdgeLocation location, int playerIndex, String status);

	/**
	 *
	 * @.pre location must be a valid, non-null VertexLocation
	 * @.post a boolean indicating whether or not the current player can place a settlement on the provided location
	 * @param location an VertexLocation where the player is trying to place a settlement
	 * @param playerIndex The index of the Current Player
	 * @param status The status of the game
	 */
	public boolean canPlaceSettlementAtLocation(VertexLocation location, int playerIndex, String status);

	/**
	 *
	 * @.pre location must be a valid, non-null VertexLocation
	 * @.post a boolean indicating whether or not the current player can place a city on the provided location
	 * @param location an VertexLocation where the player is trying to place a city
	 * @param playerIndex The index of the Current Player
	 * @param status The status of the game
	 */
	public boolean canPlaceCityAtLocation(VertexLocation location, int playerIndex, String status);

	/**
	 *
	 * @.pre location must be a valid, non-null HexLocation
	 * @.post a boolean indicating whether or not the current player can place the robber on the provided location
	 * @param location an HexLocation where the player is trying to place a robber
	 */
	public boolean canPlaceRobberAtLocation(HexLocation location);

	/**
	 * 
	 * @.pre location must be a valid, non-null HexLocation
	 * @.post an array of playerid's that are affected by the new placement of the robber
	 * @param robber The HexLocation where the robber is located
	 */
	public List<Integer> getVictims(HexLocation robber);


	
}