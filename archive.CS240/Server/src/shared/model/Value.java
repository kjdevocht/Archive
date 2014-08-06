package shared.model;
/**A value is part of a record and corresponds with a given field in the project*/
public class Value {
	/** the value id in the value table */
	int id = -1;
	/** the value of the field stored as a string */
	String value = "";
	
	int imageID = -1;
	
	int recordID = -1;
	
	int fieldNum = -1;
	
	public Value(){
		
	}
	
	public Value(String value0, int imageID0, int recordID0, int fieldNum0){
		value = value0;
		imageID = imageID0;
		recordID = recordID0;
		fieldNum = fieldNum0;
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
	 * @return the value
	 */
	public String getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(String value) {
		this.value = value;
	}
	/**
	 * @return the imageID
	 */
	public int getImageID() {
		return imageID;
	}
	/**
	 * @param imageID the imageID to set
	 */
	public void setImageID(int imageID) {
		this.imageID = imageID;
	}
	/**
	 * @return the recordID
	 */
	public int getRecordID() {
		return recordID;
	}
	/**
	 * @param recordID the recordID to set
	 */
	public void setRecordID(int recordID) {
		this.recordID = recordID;
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
	public void setfieldNum(int fieldNum) {
		this.fieldNum = fieldNum;
	}
}
