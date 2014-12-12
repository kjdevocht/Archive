package shared.definitions;

import java.awt.Color;

public enum CatanColor {
	RED, ORANGE, YELLOW, BLUE, GREEN, PURPLE, PUCE, WHITE, BROWN;

	private Color color;

	static {
		RED.color = new Color(227, 66, 52);
		ORANGE.color = new Color(255, 165, 0);
		YELLOW.color = new Color(253, 224, 105);
		BLUE.color = new Color(111, 183, 246);
		GREEN.color = new Color(109, 192, 102);
		PURPLE.color = new Color(157, 140, 212);
		PUCE.color = new Color(204, 136, 153);
		WHITE.color = new Color(223, 223, 223);
		BROWN.color = new Color(161, 143, 112);
	}

	public Color getJavaColor() {
		return color;
	}
	
	public static CatanColor fromString(String input){
		CatanColor returnVal;
		switch(input.toLowerCase()){
			case "red" :
				returnVal = RED;
				break;
			case "orange" :
				returnVal = ORANGE;
				break;
			case "yellow" :
				returnVal = YELLOW;
				break;
			case "blue" :
				returnVal = BLUE;
				break;
			case "green" :
				returnVal = GREEN;
				break;
			case "purple" :
				returnVal = PURPLE;
				break;
			case "puce" :
				returnVal = PUCE;
				break;
			case "white" :
				returnVal = WHITE;
				break;
			case "brown" :
				returnVal = BROWN;
				break;
			default :
				returnVal = null;
				break;
		}
		return returnVal;
	}
	public static String toString(CatanColor input) {
		String returnVal;
		switch(input){
			case RED :
				returnVal = "red";
				break;
			case ORANGE :
				returnVal = "orange";
				break;
			case YELLOW :
				returnVal = "yellow";
				break;
			case BLUE :
				returnVal = "blue";
				break;
			case GREEN :
				returnVal = "green";
				break;
			case PURPLE:
				returnVal = "purple";
				break;
			case PUCE:
				returnVal ="puce";
				break;
			case WHITE:
				returnVal = "white";
				break;
			case BROWN:
				returnVal = "brown";
				break;
			default :
				returnVal = "red";
				break;
		}
		return returnVal;
	}
}
