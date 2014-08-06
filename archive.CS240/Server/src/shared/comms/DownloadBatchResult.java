package shared.comms;

import java.util.ArrayList;

import shared.model.Field;
import shared.model.Image;

public class DownloadBatchResult {
	Image image = null;
	int firstycoord = -1;
	int recordheight = -1;
	int recordsperimage = -1;
	ArrayList<Field> fields = new ArrayList<Field>();


	public DownloadBatchResult(){
		
	}
	
	public DownloadBatchResult(Image image0, int first0, int height0, int num0, ArrayList<Field> fields0){
		image = image0;
		firstycoord = first0;
		recordheight = height0;
		recordsperimage = num0;
		fields = fields0; 
	}

	/**
	 * @return the image
	 */
	public Image getImage() {
		return image;
	}

	/**
	 * @param image the image to set
	 */
	public void setImage(Image image) {
		this.image = image;
	}

	/**
	 * @return the recordsperimage
	 */
	public int getRecordsperimage() {
		return recordsperimage;
	}

	/**
	 * @param recordsperimage the recordsperimage to set
	 */
	public void setRecordsperimage(int recordsperimage) {
		this.recordsperimage = recordsperimage;
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
	 * @return the firstycoord
	 */
	public int getFirstycoord() {
		return firstycoord;
	}

	/**
	 * @param firstycoord the firstycoord to set
	 */
	public void setFirstycoord(int firstycoord) {
		this.firstycoord = firstycoord;
	}

	/**
	 * @return the recordheight
	 */
	public int getRecordheight() {
		return recordheight;
	}

	/**
	 * @param recordheight the recordheight to set
	 */
	public void setRecordheight(int recordheight) {
		this.recordheight = recordheight;
	}
}
