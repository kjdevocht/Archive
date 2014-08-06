package shared.comms;

public class GetSampleImageParam {
	String user = "";
	String password = "";
	int projectId = -1;
	
	public GetSampleImageParam() {
		
	}
	
	public GetSampleImageParam(String user0, String password0, int projectId0) {
		user = user0;
		password = password0;
		projectId = projectId0;
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
}
