package shared.comms;

public class GetFieldsParam {
	String user = "";
	String password = "";
	int projectId = -1;
	String empty = "";
	
	public GetFieldsParam(){
		
	}
	
	public GetFieldsParam(String user0, String password0, String empty0){
		user = user0;
		password = password0;
		empty = empty0;
	}
	
	public GetFieldsParam(String user0, String password0, int projectid0){
		user = user0;
		password = password0;
		projectId = projectid0;
	}
	
	/**
	 * @return the user
	 */
	public String getUser() {
		return user;
	}
	/**
	 * @param user the user to set
	 */
	public void setUser(String user) {
		this.user = user;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return the projectId
	 */
	public int getProjectId() {
		return projectId;
	}
	/**
	 * @param projectId the projectId to set
	 */
	public void setProjectId(int projectId) {
		this.projectId = projectId;
	}
	/**
	 * @return the empty
	 */
	public String getEmpty() {
		return empty;
	}
	/**
	 * @param empty the empty to set
	 */
	public void setEmpty(String empty) {
		this.empty = empty;
	}
}
