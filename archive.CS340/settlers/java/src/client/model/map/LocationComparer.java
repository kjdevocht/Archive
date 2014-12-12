package client.model.map;

import java.util.ArrayList;
import java.util.List;

import shared.locations.EdgeDirection;
import shared.locations.EdgeLocation;
import shared.locations.HexLocation;
import shared.locations.VertexDirection;
import shared.locations.VertexLocation;
/**
 * General utility for comparing different types of locations on a map.
 */
public class LocationComparer {
	/**
	 * @.pre valid edge and hex locaiton
	 * @.post True if an edgelocation is an edge of a hexlocation.
	 */
	public static boolean edgeIsOnHex(EdgeLocation edge, HexLocation hex) {
		
		edge = edge.getNormalizedLocation();
		
		if(hex.equals(edge.getHexLoc()))
			return true;

		if(hex.equals(edge.getHexLoc().getNeighborLoc(edge.getDir())))
			return true;
		
		return false;
	}
	/**
	 * @.pre valid vertex and hex locaiton
	 * @.post True if an vertex is one of the six vertices on a hex location
	 */
	public static boolean vertexIsOnHex(VertexLocation vertex, HexLocation hex) {
		vertex = vertex.getNormalizedLocation();
		
		VertexLocation west = new VertexLocation(hex, VertexDirection.West);
		VertexLocation northWest = new VertexLocation(hex, VertexDirection.NorthWest);
		VertexLocation northEast = new VertexLocation(hex, VertexDirection.NorthEast);
		VertexLocation east = new VertexLocation(hex, VertexDirection.East);
		VertexLocation southEast = new VertexLocation(hex, VertexDirection.SouthEast);
		VertexLocation southWest = new VertexLocation(hex, VertexDirection.SouthWest);
		ArrayList<VertexLocation> dirs = new ArrayList<VertexLocation>();

		dirs.add(west);
		dirs.add(northWest);
		dirs.add(northEast);
		dirs.add(east);
		dirs.add(southEast);
		dirs.add(southWest);
		
		for(VertexLocation loc: dirs) {
			VertexLocation normal = loc.getNormalizedLocation();
			if(normal.equals(vertex))
				return true;
		}
		return false;
	}
	/**
	 * @.pre valid vertex and edge locaiton
	 * @.post True if a vertex lies on an edge.
	 */
	public static boolean vertexIsOnEdge(EdgeLocation edge, VertexLocation vertex) {
		edge = edge.getNormalizedLocation();
		vertex = vertex.getNormalizedLocation();
		
		HexLocation hex1 = edge.getHexLoc();
		HexLocation hex2 = edge.getHexLoc().getNeighborLoc(edge.getDir());
		
		if(vertexIsOnHex(vertex, hex1) && vertexIsOnHex(vertex, hex2))
			return true;
		return false;
	}
	/**
	 * @.pre valid edge and edge locaiton
	 * @.post if an edge touches end to end with another edge, but is not on top of it.
	 */
	public static boolean edgeTouchesEdge(EdgeLocation edge1, EdgeLocation edge2) {
		edge1 = edge1.getNormalizedLocation();
		edge2 = edge2.getNormalizedLocation();

		if(edge1.equals(edge2))
			return false;
		
		List<VertexLocation> list1 = edgeToVertices(edge1);	
		List<VertexLocation> list2 = edgeToVertices(edge2);	
		
		for(VertexLocation vertex1: list1) {
			for(VertexLocation vertex2: list2) {
				if(vertex1.equals(vertex2))
					return true;
			}
		}
		
		return false;
	}
	/**
	 * @.pre valid edge and edge locaiton edgeTouchesEdge() must return true
	 * @.post The vertex where the edges touch
	 */
	public static VertexLocation whereEdgeTouchesEdge(EdgeLocation edge1, EdgeLocation edge2) {
		edge1 = edge1.getNormalizedLocation();
		edge2 = edge2.getNormalizedLocation();
		
		List<VertexLocation> list1 = edgeToVertices(edge1);	
		List<VertexLocation> list2 = edgeToVertices(edge2);	
		
		for(VertexLocation vertex1: list1) {
			for(VertexLocation vertex2: list2) {
				if(vertex1.equals(vertex2))
					return vertex1;
			}
		}
		
		return null;
	}
	/**
	 * @.pre valid edge location
	 * @.post A List of 2 vertices that represent the edge
	 */
	public static List<VertexLocation> edgeToVertices(EdgeLocation edge) {
		edge = edge.getNormalizedLocation();
		List<VertexLocation> list = new ArrayList<VertexLocation>();
		if(edge.getDir() == EdgeDirection.NorthWest) {
			list.add(new VertexLocation(edge.getHexLoc(), VertexDirection.West).getNormalizedLocation());
			list.add(new VertexLocation(edge.getHexLoc(), VertexDirection.NorthWest));
		}
		else if(edge.getDir() == EdgeDirection.North) {
			list.add(new VertexLocation(edge.getHexLoc(), VertexDirection.NorthEast));
			list.add(new VertexLocation(edge.getHexLoc(), VertexDirection.NorthWest));
		}
		else if(edge.getDir() == EdgeDirection.NorthEast) {
			list.add(new VertexLocation(edge.getHexLoc(), VertexDirection.NorthEast));
			list.add(new VertexLocation(edge.getHexLoc(), VertexDirection.East).getNormalizedLocation());
		}
		return list;
	}
	/**
	 * @.pre 2 valid vertices
	 * @.post True if the vertices share two hexes.
	 */
	public static boolean verticesSharesTwoHexes(VertexLocation vertex1, VertexLocation vertex2) {
		List<HexLocation> list1 = getHexLocations(vertex1.getNormalizedLocation());
		List<HexLocation> list2 = getHexLocations(vertex2.getNormalizedLocation());
		int count = 0;
		for(HexLocation hex1: list1) {
			for(HexLocation hex2: list2) {
				if(hex1.equals(hex2))
					count++;
				if(count == 2)
					return true;
			}
		}
		return false;
	}
	/**
	 * @.pre valid vertix
	 * @.post List of hex locations that touch a vertex.
	 */
	public static List<HexLocation> getHexLocations(VertexLocation vertex) {
		vertex = vertex.getNormalizedLocation();
		List<HexLocation> list = new ArrayList<HexLocation>();
		if(vertex.getDir() ==  VertexDirection.NorthWest) {
			HexLocation hex = vertex.getHexLoc();
			list.add(hex);
			list.add(new HexLocation(hex.getX()-1, hex.getY()));
			list.add(new HexLocation(hex.getX(), hex.getY()-1));
		}
		else if(vertex.getDir() ==  VertexDirection.NorthEast) {
			HexLocation hex = vertex.getHexLoc();
			list.add(hex);
			list.add(new HexLocation(hex.getX(), hex.getY()-1));
			list.add(new HexLocation(hex.getX()+1, hex.getY()-1));
		}
		return list;
	}
}
