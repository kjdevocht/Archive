package client.model.map;


import client.model.ClientModel;
import shared.locations.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CatanMap implements ICatanMap{

	private List<IHex> hexes;
	private List<IPort> ports;
	private List<IEdgeValue> roads;
	private List<ICommunity> settlements;
	private List<ICommunity> cities;
	private HexLocation robber;
	private int radius;

	/**
	 * @.pre none
	 * @.post A new CatanMap is created
	 * @.post All variables are set to null or -1
	 */
	public CatanMap(){
		hexes = null;
		ports = null;
		roads = null;
		settlements = null;
		cities = null;
		robber = null;
		radius = -1;
	}

	public List<IHex> getHexes() {
		return hexes;
	}

	public void setHexes(List<IHex> hexes) {
		this.hexes = hexes;
	}

	public List<IPort> getPorts() {
		return ports;
	}

	public void setPorts(List<IPort> ports) {
		this.ports = ports;
	}

	public List<IEdgeValue> getRoads() {
		return roads;
	}

	public void setRoads(List<IEdgeValue> roads) {
		this.roads = roads;
	}

	public List<ICommunity> getSettlements() {
		return settlements;
	}

	public void setSettlements(List<ICommunity> settlements) {
		this.settlements = settlements;
	}

	public List<ICommunity> getCities() {
		return cities;
	}

	public void setCities(List<ICommunity> cities) {
		this.cities = cities;
	}

	public HexLocation getRobber() {
		return robber;
	}

	public void setRobber(HexLocation robber) {
		this.robber = robber;
	}

	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	private boolean isSettlementNearRoad(EdgeLocation location, int playerIndex){
		
		for(ICommunity settlement: settlements) {
			if(LocationComparer.vertexIsOnEdge(location, settlement.getLocation())) {
				if(settlement.getOwner().getPlayerIndex() == playerIndex) {
					if(LocationComparer.vertexIsOnEdge(location, settlement.getLocation())) {
						return true;
					}
				}
			}
		}
		
		

		return false;
	}
	
	private boolean isRoadConnectedToSettlement(ICommunity settlement) {
		for(IEdgeValue road: roads) {
			if(road.getOwner().getPlayerIndex() == settlement.getOwner().getPlayerIndex()) {
				if(LocationComparer.vertexIsOnEdge(road.getLocation(), settlement.getLocation()))
					return false;
			}
		}
		return true;
	}

	public boolean canPlaceRoadAtLocation(EdgeLocation location, int playerIndex, String status){
		
		boolean touchedHex =false;
		for(IHex hex: hexes) {
			if(LocationComparer.edgeIsOnHex(location, hex.getLocation())) {
				touchedHex = true;
			}
		}
		if(!touchedHex)
			return false;

		//See if there is already a road at the requested location
		for (int i = 0; i<roads.size(); i++){
			IEdgeValue road = roads.get(i);
			if(road.getLocation().equals(location)){
				return false;
			}
		}
        boolean isBuyEmptySettlement= false;
        if(status.toLowerCase().equals("secondround")) {
            for(ICommunity settlement: settlements) {
                if(LocationComparer.vertexIsOnEdge(location, settlement.getLocation())) {
                    if(settlement.getOwner().getPlayerIndex() == playerIndex) {
                        for(IEdgeValue road: roads) {
                            if(!LocationComparer.vertexIsOnEdge(road.getLocation(), settlement.getLocation())) {
                                if(road.getOwner().getPlayerIndex() == playerIndex) {
                                    isBuyEmptySettlement = true;
                                }
                            }
                        }
                    }
                }
            }
            if(!isBuyEmptySettlement)
                return false;
        }


		//If it is the first or second you may place a road next to just a settlement
		if(status.toLowerCase().equals("firstround") || status.toLowerCase().equals("secondround")){
            if(isSettlementNearRoad(location, playerIndex)){
				return true;
			}
		}
		for(IEdgeValue road: roads) {
			if(LocationComparer.edgeTouchesEdge(location, road.getLocation())) {
				if(road.getOwner().getPlayerIndex() == playerIndex) {
					VertexLocation vertex = LocationComparer.whereEdgeTouchesEdge(location, road.getLocation());
					List<ICommunity> communties = new ArrayList<>();
					communties.addAll(settlements);
					communties.addAll(cities);
					for(ICommunity settlement: communties) {
						if(settlement.getLocation().getNormalizedLocation().equals(vertex)){
							if(settlement.getOwner().getPlayerIndex() != playerIndex) {
								return false;
							}
						}							
					}
					return true;
				}
			}
		}
        return false;
	}

	public boolean canPlaceSettlementAtLocation(VertexLocation location, int playerIndex, String status) {
		if(canPlaceOnVertex(settlements, location, playerIndex, status) && canPlaceOnVertex(cities, location,playerIndex, status)){
			return true;
		}
		else{
			return false;
		}
	}

	public boolean canPlaceCityAtLocation(VertexLocation location, int playerIndex, String status){
		if(!status.equals("playing"))
			return false;
		return isSettlementIsOnLocation(location, settlements, playerIndex);
	}

	private boolean isSettlementIsOnLocation(VertexLocation location, List<ICommunity> list, int playerIndex) {
		for(ICommunity settlement : settlements) {
			if(location.getNormalizedLocation().equals(settlement.getLocation().getNormalizedLocation())) {
				if(settlement.getOwner().getPlayerIndex() == playerIndex)
					return true;
			}
		}			
		return false;
	}

	private boolean canPlaceOnVertex(List<ICommunity> list, VertexLocation location, int playerIndex, String status)
	{
		boolean touchedHex =false;
		for(IHex hex: hexes) {
			if(LocationComparer.vertexIsOnHex(location, hex.getLocation())) {
				touchedHex = true;
			}
		}
		if(!touchedHex)
			return false;
		
		for(ICommunity community: list) {
			if(community.getLocation().getNormalizedLocation().equals(location))
				return false;
			
			if(LocationComparer.verticesSharesTwoHexes(community.getLocation().getNormalizedLocation(), location))
				return false;
		}
		
		if(!(status.toLowerCase().equals("firstround") || status.toLowerCase().equals("secondround"))) {
			for(IEdgeValue road : roads) {
				if(LocationComparer.vertexIsOnEdge(road.getLocation(), location)) {
					if(road.getOwner().getPlayerIndex() == playerIndex) {
						return true;
					}
				}
			}
			return false;
		}
		else {
			return true;
		}
	}

	public boolean canPlaceRobberAtLocation(HexLocation location){
		if(robber.equals(location)){
            return false;
        }
        else{
            for(IHex hex: hexes) {
                if(hex.getLocation().equals(location)) {
                    return true;
                }
            }
        }
        return false;
	}

	public List<Integer> getVictims(HexLocation robber){
		//West, NorthWest, NorthEast, East, SouthEast, SouthWest;
		VertexLocation west = new VertexLocation(robber, VertexDirection.West);
		VertexLocation northWest = new VertexLocation(robber, VertexDirection.NorthWest);
		VertexLocation northEast = new VertexLocation(robber, VertexDirection.NorthEast);
		VertexLocation east = new VertexLocation(robber, VertexDirection.East);
		VertexLocation southEast = new VertexLocation(robber, VertexDirection.SouthEast);
		VertexLocation southWest = new VertexLocation(robber, VertexDirection.SouthWest);
		ArrayList<VertexLocation> dirs = new ArrayList<VertexLocation>();
		Set<Integer> victims = new HashSet<Integer>();

		dirs.add(west);
		dirs.add(northWest);
		dirs.add(northEast);
		dirs.add(east);
		dirs.add(southEast);
		dirs.add(southWest);

		//Normalize all vertices
		for(int i = 0; i<dirs.size(); i++)
		{
			dirs.set(i, dirs.get(i).getNormalizedLocation());

		}

		//Check all settlements to see if they are near the new robber
		for(int i = 0; i<dirs.size(); i++)
		{
			for(int j = 0; j<settlements.size(); j++)
			{
				if (dirs.get(i).equals(settlements.get(j).getLocation().getNormalizedLocation()))
				{
					int ownerIndex = settlements.get(j).getOwner().getPlayerIndex();
					int localPlayerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
					if((ownerIndex != localPlayerIndex)&& settlements.get(j).getOwner().getResources().getTotalResourceCount()>0){
						victims.add(settlements.get(j).getOwner().getPlayerIndex());
					}
				}
			}
		}

		//Check all cities to see if they are near the new robber
		for(int i = 0; i<dirs.size(); i++)
		{
			for(int j = 0; j<cities.size(); j++)
			{
				if (dirs.get(i).equals(cities.get(j).getLocation().getNormalizedLocation()))
				{
					int ownerIndex = cities.get(j).getOwner().getPlayerIndex();
					int localPlayerIndex = ClientModel.getUpdatableModel().getLocalPlayer().getPlayerIndex();
					if((ownerIndex != localPlayerIndex)&& cities.get(j).getOwner().getResources().getTotalResourceCount()>0){
						victims.add(cities.get(j).getOwner().getPlayerIndex());
					}
				}
			}
		}

        List<Integer> result = new ArrayList<>();
        for (int victim : victims) {
            result.add(victim);
        }

		return result;
	}
}