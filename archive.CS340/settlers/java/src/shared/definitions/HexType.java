package shared.definitions;

public enum HexType {
	WOOD, BRICK, SHEEP, WHEAT, ORE, DESERT, WATER;
	public static HexType resourceTypeToHexType(ResourceType resource) {
        if(resource == null)
            return DESERT;
		switch(resource) {
		case WOOD: return WOOD;
		case BRICK: return BRICK;
		case SHEEP: return SHEEP;
		case WHEAT: return WHEAT;
		case ORE: return ORE;
		}
		return DESERT;
	}
}
