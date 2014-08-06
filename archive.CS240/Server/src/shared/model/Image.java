package shared.model;

/** An Image or Batch,  The image file containing the picture of 
 * the scanned document */
public class Image {
	/** the image id in the image table */
	int id = -1;
	
	/** The location of the image file */
	String file = "";
	
	/**The status of the image file*/
	String status = "";
	
	/** The index of the user currently working on this batch.  It will be -1 if no user has it checked out*/
	int userID = -1;
	
	/** The project this image is attached to*/
	int projectID = -1;
	
	public Image(){
		
	}
	
	public Image(int id0, String status0, int userID0){
		id = id0;
		status = status0;
		userID = userID0;
	}
	
	public Image(String file0, String status0, int userID0, int projectID0){
		file = file0;
		status = status0;
		userID = userID0;
		projectID = projectID0;
	}
	
	public Image(int id0, String file0, String status0, int userID0, int projectID0){
		id  = id0;
		file = file0;
		status = status0;
		userID = userID0;
		projectID = projectID0;
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
	 * @return the file
	 */
	public String getFile() {
		return file;
	}
	/**
	 * @param file the file to set
	 */
	public void setFile(String file) {
		this.file = file;
	}
	
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	
	/**
	 * @return the userID
	 */
	public int getUserID() {
		return userID;
	}
	
	/**
	 * @param userID the userID to set
	 */
	public void setUserID(int userID) {
		this.userID = userID;
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
