package shared.comms;

import java.util.ArrayList;
import java.util.Arrays;

public class SearchParam {
	String user ="";
	String password = "";
	ArrayList<String> fields = new ArrayList<String>();
	ArrayList<String> values = new ArrayList<String>();
	
	
	public SearchParam(){
		
	}
	
	public SearchParam(String user0, String password0, String fields0, String values0){
		user = user0;
		password = password0;
		fields = new ArrayList<String>(Arrays.asList(fields0.split(",")));
		values = new ArrayList<String>(Arrays.asList(values0.split(",")));
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
	 * @return the fields
	 */
	public ArrayList<String> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(ArrayList<String> fields) {
		this.fields = fields;
	}

	/**
	 * @return the values
	 */
	public ArrayList<String> getValues() {
		return values;
	}

	/**
	 * @param values the values to set
	 */
	public void setValues(ArrayList<String> values) {
		this.values = values;
	}
}
