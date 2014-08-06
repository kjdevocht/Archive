package shared.comms;

import java.util.ArrayList;

public class SearchResult {
	ArrayList<Integer> batchId = new ArrayList<Integer>();
	ArrayList<String> imageUrl = new ArrayList<String>();
	ArrayList<Integer> recordNum = new ArrayList<Integer>();
	ArrayList<Integer> fieldNum = new ArrayList<Integer>();
	
	public SearchResult(){
		
	}
	
	public SearchResult(ArrayList<Integer> batchId0, ArrayList<String> imageUrl0, ArrayList<Integer> recordNum0, ArrayList<Integer> fieldNum0){
		batchId = batchId0;
		imageUrl = imageUrl0;
		recordNum = recordNum0;
		fieldNum = fieldNum0;
	}
	/**
	 * @return the batchId
	 */
	public ArrayList<Integer> getBatchId() {
		return batchId;
	}
	/**
	 * @param batchId the batchId to set
	 */
	public void setBatchId(ArrayList<Integer> batchId) {
		this.batchId = batchId;
	}
	/**
	 * @return the imageUrl
	 */
	public ArrayList<String> getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(ArrayList<String> imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the recordNum
	 */
	public ArrayList<Integer> getRecordNum() {
		return recordNum;
	}
	/**
	 * @param recordNum the recordNum to set
	 */
	public void setRecordNum(ArrayList<Integer> recordNum) {
		this.recordNum = recordNum;
	}
	/**
	 * @return the fieldNum
	 */
	public ArrayList<Integer> getFieldNum() {
		return fieldNum;
	}
	/**
	 * @param fieldNum the fieldNum to set
	 */
	public void setFieldNum(ArrayList<Integer> fieldNum) {
		this.fieldNum = fieldNum;
	}
}
