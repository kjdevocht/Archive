package shared.comms;

public class ValidateUserParam {
	String user = "";
	String password = "";
	
	public ValidateUserParam(){
		
	}
	
	public ValidateUserParam(String user0, String password0){
		user = user0;
		password = password0;
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

}
