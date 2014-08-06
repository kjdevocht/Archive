package cs240Image;



public class CSImage {
	String fileFormat = "";
	int width = 0;
	int height = 0;
	Pixel [] [] img;
	
	public CSImage (int width0, int height0)
	{
		width = width0;
		height = height0;
		img = new Pixel [height] [width];
		
	}

	public String getFileFormat() {
		return fileFormat;
	}

	public void setFileFormat(String fileFormat) {
		this.fileFormat = fileFormat;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public Pixel[][] getImg() {
		return img;
	}

	public void setImg(Pixel[][] img) {
		this.img = img;
	}
	
}
