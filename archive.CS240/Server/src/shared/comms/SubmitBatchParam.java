package shared.comms;

import java.util.ArrayList;
import java.util.Arrays;

public class SubmitBatchParam {
	String user = "";
	String password = "";
	int batch = -1;
	ArrayList<String> values = new ArrayList<String>();
	
	
	public SubmitBatchParam(){
		
	}
	
	public SubmitBatchParam(String user0, String password0, int batch0, String recordValues0){
		user = user0;
		password = password0;
		batch = batch0;
		values = new ArrayList<String>(Arrays.asList(recordValues0.split("[,;]+")));		
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
	 * @return the batch
	 */
	public int getBatch() {
		return batch;
	}

	/**
	 * @param batch the batch to set
	 */
	public void setBatch(int batch) {
		this.batch = batch;
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
