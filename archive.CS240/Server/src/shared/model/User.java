package shared.model;
/**An authorized user in the system*/
public class User {
	/** the user id in the user table */
	int id = -1;
	
	/** the username */
	String userName = "";
	
	/** the password */
	String password = "";
	
	/** the users first name */
	String firstName = "";
	
	/** the users last name */
	String lastName = "";
	
	/** the user email address */
	String email = "";
	
	/** the number of records this user has indexed */
	int indexedRecords = 0;
	
	/** The index of the image the user currently has check out.  It will be -1 if the user has no images checked out*/
	int imageID = -1;
	
	public User()
	{
		
	}
	
	public User(String userName0, String password0)
	{
		userName = userName0;
		password = password0;	
	}
	
	public User(String userName0, String password0, String firstName0, String lastName0, String email0, int indexedRecords0)
	{
		userName = userName0;
		password = password0;
		firstName = firstName0;
		lastName = lastName0;
		email = email0;
		indexedRecords = indexedRecords0;
	}
	
	public User(int id0, String userName0, String password0, String firstName0, String lastName0, String email0, int indexedRecords0, int imageid0)
	{
		id = id0;
		userName = userName0;
		password = password0;
		firstName = firstName0;
		lastName = lastName0;
		email = email0;
		indexedRecords = indexedRecords0;
		imageID = imageid0;
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
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
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
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	/**
	 * @return the email
	 */
	public String getEmail() {
		return email;
	}
	/**
	 * @param email the email to set
	 */
	public void setEmail(String email) {
		this.email = email;
	}
	/**
	 * @return the indexedRecords
	 */
	public int getIndexedRecords() {
		return indexedRecords;
	}
	/**
	 * @param indexedRecords the indexedRecords to set
	 */
	public void setIndexedRecords(int indexedRecords) {
		this.indexedRecords = indexedRecords;
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
}
