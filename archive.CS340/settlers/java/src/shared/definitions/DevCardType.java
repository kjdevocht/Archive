package shared.definitions;

public enum DevCardType {
	SOLDIER, YEAR_OF_PLENTY, MONOPOLY, ROAD_BUILD, MONUMENT;
    public static String toString(DevCardType type) {
        switch (type){
            case SOLDIER:
                return "soldier";
            case YEAR_OF_PLENTY:
                return "yearOfPlenty";
            case MONUMENT:
                return "monument";
            case MONOPOLY:
                return "monopoly";
            case ROAD_BUILD:
                return "roadBuilding";
        }
        return null;
    }
}
