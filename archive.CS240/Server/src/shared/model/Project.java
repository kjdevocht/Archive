package shared.model;

import java.util.ArrayList;

/**A project contains many fields and batchs*/
public class Project {
	/** the project id in the project table */
	int id = -1;
	
	/** Title of the project */
	String title = "";
	
	/** Indicates how man records or on each Batch */
	int recordsPerImage = -1;
	
	/** Gives the y coordinate of the first field */
	int firstYCoord = -1;
	
	/** Indicates the height of each record*/
	int recordHeight = -1;
	
	ArrayList<Field> fields = new ArrayList<Field>();
	ArrayList<Image> images = new ArrayList<Image>();
	
	public Project(){
		
	}
	
	public Project(String title0, int recordsPerImage0, int firstYCoord0, int recordHeight0){
		title = title0;
		recordsPerImage = recordsPerImage0;
		firstYCoord = firstYCoord0;
		recordHeight = recordHeight0;
	}
	
	public Project(int id0, String title0, int recordsPerImage0, int firstYCoord0, int recordHeight0){
		id = id0;
		title = title0;
		recordsPerImage = recordsPerImage0;
		firstYCoord = firstYCoord0;
		recordHeight = recordHeight0;
	}
	
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the recordsPerImage
	 */
	public int getRecordsPerImage() {
		return recordsPerImage;
	}
	/**
	 * @param recordsPerImage the recordsPerImage to set
	 */
	public void setRecordsPerImage(int recordsPerImage) {
		this.recordsPerImage = recordsPerImage;
	}
	/**
	 * @return the firstYCoord
	 */
	public int getFirstYCoord() {
		return firstYCoord;
	}
	/**
	 * @param firstYCoord the firstYCoord to set
	 */
	public void setFirstYCoord(int firstYCoord) {
		this.firstYCoord = firstYCoord;
	}
	/**
	 * @return the recordHeight
	 */
	public int getRecordHeight() {
		return recordHeight;
	}
	/**
	 * @param recordHeight the recordHeight to set
	 */
	public void setRecordHeight(int recordHeight) {
		this.recordHeight = recordHeight;
	}

	/**
	 * @return the fields
	 */
	public ArrayList<Field> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<Field> fields) {
		this.fields = fields;
	}

	/**
	 * @return the images
	 */
	public ArrayList<Image> getImages() {
		return images;
	}

	/**
	 * @param images the images to set
	 */
	public void setImages(ArrayList<Image> images) {
		this.images = images;
	}
}
