package server.model.map;


import shared.definitions.ResourceType;
import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class CatanMap implements ICatanMap {

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
	public CatanMap(boolean randomTiles, boolean randomNumbers, boolean randomPorts){
		hexes = new ArrayList<>();
		ports = new ArrayList<>();
		robber = null;
		fillStandardBoard();
		roads = new ArrayList<IEdgeValue>();
		settlements = new ArrayList<ICommunity>();
		cities = new ArrayList<ICommunity>();

        if(randomTiles)
            randomizeTiles();

        if(randomNumbers)
            randomizeNumber();

        if(randomPorts)
            randomizePorts();;

		radius = 3;
	}
	public void reset() {
		//reset robber location
		for(IHex hex: hexes) {
			if(hex.getNumber() == -1) {
				robber = hex.getLocation();
				break;
			}
		}
		roads.clear();
		settlements.clear();
		cities.clear();
	}
	public void fillStandardBoard() {
		robber = new HexLocation(0, -2);
		hexes.add(new Hex(robber, null, -1));
		hexes.add(new Hex(new HexLocation(1, -2), ResourceType.BRICK, 4));
		hexes.add(new Hex(new HexLocation(2, -2), ResourceType.WOOD, 11));		
		hexes.add(new Hex(new HexLocation(-1, -1), ResourceType.BRICK, 8));
		hexes.add(new Hex(new HexLocation(0, -1), ResourceType.WOOD, 3));		
		hexes.add(new Hex(new HexLocation(1, -1), ResourceType.ORE, 9));		
		hexes.add(new Hex(new HexLocation(2, -1), ResourceType.SHEEP, 12));		
		hexes.add(new Hex(new HexLocation(-2, 0), ResourceType.ORE, 5));		
		hexes.add(new Hex(new HexLocation(-1, 0), ResourceType.SHEEP, 10));		
		hexes.add(new Hex(new HexLocation(0, 0), ResourceType.WHEAT, 11));		
		hexes.add(new Hex(new HexLocation(1, 0), ResourceType.BRICK, 5));
		hexes.add(new Hex(new HexLocation(2, 0), ResourceType.WHEAT, 6));
		hexes.add(new Hex(new HexLocation(-2, 1), ResourceType.WHEAT, 2));		
		hexes.add(new Hex(new HexLocation(-1, 1), ResourceType.SHEEP, 9));		
		hexes.add(new Hex(new HexLocation(0, 1), ResourceType.WOOD, 4));		
		hexes.add(new Hex(new HexLocation(1, 1), ResourceType.SHEEP, 10));
		hexes.add(new Hex(new HexLocation(-2, 2), ResourceType.WOOD, 6));		
		hexes.add(new Hex(new HexLocation(-1, 2), ResourceType.ORE, 3));
		hexes.add(new Hex(new HexLocation(0, 2), ResourceType.WHEAT, 8));
		
		ports.add(new Port(3, null, new EdgeLocation(new HexLocation(2, 1), EdgeDirection.NorthWest)));
		
		ports.add(new Port(2, ResourceType.WOOD, new EdgeLocation(new HexLocation(-3, 2), EdgeDirection.NorthEast)));
		ports.add(new Port(2, ResourceType.ORE, new EdgeLocation(new HexLocation(1, -3), EdgeDirection.South)));
		ports.add(new Port(2, ResourceType.SHEEP, new EdgeLocation(new HexLocation(3, -1), EdgeDirection.NorthWest)));
		
		ports.add(new Port(3, null, new EdgeLocation(new HexLocation(-3, 0), EdgeDirection.SouthEast)));
		
		ports.add(new Port(2, ResourceType.WHEAT, new EdgeLocation(new HexLocation(-1, -2), EdgeDirection.South)));		
	
		ports.add(new Port(3, null, new EdgeLocation(new HexLocation(3, -3), EdgeDirection.SouthWest)));		
		ports.add(new Port(3, null, new EdgeLocation(new HexLocation(0, 3), EdgeDirection.North)));
				
		ports.add(new Port(2, ResourceType.BRICK, new EdgeLocation(new HexLocation(-2, 3), EdgeDirection.NorthEast)));
		
	}
	public void randomizeNumber() {
		Random random = new Random();
		List<Integer> numbers = new ArrayList<Integer>();
		for(IHex hex : hexes) {
			if(hex.getNumber() != -1) {
				numbers.add(hex.getNumber());
			}
		}
		for(IHex hex : hexes) {
			if(hex.getNumber() == -1)
				continue;
			int number = numbers.remove(random.nextInt(numbers.size()));
			hex.setNumber(number);
		}
	}
	public void randomizeTiles() {
		Random random = new Random();
		List<HexLocation> locations = new ArrayList<HexLocation>();
		for(IHex hex : hexes) {
			locations.add(hex.getLocation());
		}
		for(IHex hex : hexes) {			
			HexLocation location = locations.remove(random.nextInt(locations.size()));
			if(hex.getNumber() == -1)
				robber = location;
			hex.setLocation(location);
		}
	}
	public void randomizePorts() {
        Random random = new Random();
		List<HexLocation> locations = new ArrayList<>();
        List<EdgeDirection> directions = new ArrayList<>();
        for(IPort port : ports) {
            HexLocation hex = port.getLocation();
            EdgeDirection dir = port.getDirection();
            locations.add(hex);
            directions.add(dir);
        }
        for(IPort port: ports) {
            int randomIndex = random.nextInt(locations.size());
            EdgeDirection dir = directions.remove(randomIndex);
            HexLocation location = locations.remove(randomIndex);

            port.setDirection(dir);
            port.setLocation(location);
        }

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

	public List<Integer> getVictims(HexLocation robber, int playerIndex){
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
					int localPlayerIndex = playerIndex;
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
					int localPlayerIndex = playerIndex;
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
    @Override
    public void placeRoad(IEdgeValue road) {
        roads.add(road);
    }
    @Override
    public void placeSettlement(Settlement settlement) {
        settlements.add(settlement);
    }
    @Override
    public void placeCity(City city) {
        ICommunity found = null;
        for(ICommunity settlement: settlements) {
            if(settlement.getOwner().getPlayerIndex() == city.getOwner().getPlayerIndex()) {
                if(settlement.getLocation().getNormalizedLocation().equals(city.getLocation().getNormalizedLocation())) {
                    found = settlement;
                }
            }
        }
        if(found == null)
            return;

        settlements.remove(found);

        cities.add(city);
    }
}