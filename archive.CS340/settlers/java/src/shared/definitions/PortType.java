package shared.definitions;

import client.model.map.IPort;

public enum PortType {
	WOOD, BRICK, SHEEP, WHEAT, ORE, THREE;
    public static PortType portToPortType(IPort port) {

        if(port.getRatio() == 3)
            return THREE;

        ResourceType resource = port.getResource();
        switch(resource) {
            case WOOD: return WOOD;
            case BRICK: return BRICK;
            case SHEEP: return SHEEP;
            case WHEAT: return WHEAT;
            case ORE: return ORE;
        }
        return ORE;
    }
}
