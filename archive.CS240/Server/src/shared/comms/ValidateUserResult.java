package shared.comms;

public class ValidateUserResult {
	boolean canCheckOut = false;
	String userFirstName = "";
	String userLastName = "";
	int numRecords = 0;
	String status = "FAILED";
	
	public ValidateUserResult(){
		
	}
	
	public ValidateUserResult(String status0){
		status = status0;
	}
	
	public ValidateUserResult(boolean canCheckOut0, String userFirstname0, String userLastName0, int numRecords0, String status0){
		canCheckOut = canCheckOut0;
		userFirstName = userFirstname0;
		userLastName = userLastName0;
		numRecords = numRecords0;
		status = status0;
	}
	
	/**
	 * @return the output
	 */
	public boolean isCanCheckOut() {
		return canCheckOut;
	}
	/**
	 * @param output the output to set
	 */
	public void setCanCheckOut(boolean canCheckOut) {
		this.canCheckOut = canCheckOut;
	}
	/**
	 * @return the userFirstName
	 */
	public String getUserFirstName() {
		return userFirstName;
	}
	/**
	 * @param userFirstName the userFirstName to set
	 */
	public void setUserFirstName(String userFirstName) {
		this.userFirstName = userFirstName;
	}
	/**
	 * @return the userLastName
	 */
	public String getUserLastName() {
		return userLastName;
	}
	/**
	 * @param userLastName the userLastName to set
	 */
	public void setUserLastName(String userLastName) {
		this.userLastName = userLastName;
	}
	/**
	 * @return the numRecords
	 */
	public int getNumRecords() {
		return numRecords;
	}
	/**
	 * @param numRecords the numRecords to set
	 */
	public void setNumRecords(int numRecords) {
		this.numRecords = numRecords;
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
}
