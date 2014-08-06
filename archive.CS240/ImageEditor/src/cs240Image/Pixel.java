package cs240Image;

public class Pixel {


	int red;
	int green;
	int blue;
	
	public Pixel (int red0, int green0, int blue0)
	{
		red = red0;
		green = green0;
		blue = blue0;
	}
	
	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}
	

}
