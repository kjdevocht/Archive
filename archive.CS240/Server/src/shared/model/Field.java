package shared.model;
/** This class is a single field in database table.
 * It contains all information to access the field in the table */
public class Field {
	/** the field id in the field table */
	int id = -1;
	
	/**Field Number of the field for a given project*/
	int fieldNum = -1;
	
	/** Title of the field */
	String title = "";
	
	/** The x coordinate of the field on the batch */
	int xcoord = -1;
	
	/** The width of the field on the batch */
	int width = -1;
	
	/** The location of the help file for this field */
	String helpHTML = "";
	
	/**The location of the knowndata file for this field */
	String knownData = "";
	
	int projectID = 0;
	
	public Field(){
		
	}
	
	public Field(int fieldNum0, String title0, int xcoord0, int width0, String helpHtml0, String knownData0, int projectID0){
		fieldNum = fieldNum0;
		title = title0;
		xcoord = xcoord0;
		width = width0;
		helpHTML = helpHtml0;
		knownData = knownData0;
		projectID = projectID0;
	}

	public Field(int id0, int fieldNum0, String title0, int xcoord0, int width0, String helphtml0, String knowndata0, int newprojectid0) {
		id = id0;
		fieldNum = fieldNum0;
		title = title0;
		xcoord = xcoord0;
		width = width0;
		helpHTML = helphtml0;
		knownData = knowndata0;
		projectID = newprojectid0;
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
	 * @return the fieldNum
	 */
	public int getFieldNum() {
		return fieldNum;
	}

	/**
	 * @param fieldNum the fieldNum to set
	 */
	public void setFieldNum(int fieldNum) {
		this.fieldNum = fieldNum;
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
	 * @return the xcoord
	 */
	public int getXcoord() {
		return xcoord;
	}
	/**
	 * @param xcoord the xcoord to set
	 */
	public void setXcoord(int xcoord) {
		this.xcoord = xcoord;
	}
	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @return the helpHTML
	 */
	public String getHelpHTML() {
		return helpHTML;
	}
	/**
	 * @param helpHTML the helpHTML to set
	 */
	public void setHelpHTML(String helpHTML) {
		this.helpHTML = helpHTML;
	}
	/**
	 * @return the knownData
	 */
	public String getKnownData() {
		return knownData;
	}
	/**
	 * @param knownData the knownData to set
	 */
	public void setKnownData(String knownData) {
		this.knownData = knownData;
	}
	
	/**
	 * @return the projectID
	 */
	public int getProjectID() {
		return projectID;
	}

	/**
	 * @param projectID the projectID to set
	 */
	public void setProjectID(int projectID) {
		this.projectID = projectID;
	}
}
