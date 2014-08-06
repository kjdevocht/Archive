package server.da;

import java.io.File;
import java.sql.*;

public class Database {
	public FieldAccess field = null;
	public ImageAccess image = null;
	public ProjectAccess project = null;
	public UserAccess user = null;
	public ValueAccess value = null;
	Connection connection = null;
	public boolean transResult = false;
	
	public Database() {
		field = new FieldAccess();
		image = new ImageAccess();
		project = new ProjectAccess();
		user = new UserAccess();
		value = new ValueAccess();
	}
	
	
	/**
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}


	/**
	 * @param connection the connection to set
	 */
	public void setConnection(Connection connection) {
		this.connection = connection;
	}


	public static boolean initialize() {
		try {
			final String driver = "org.sqlite.JDBC";
			Class.forName(driver);
			return true;
		}
		catch(ClassNotFoundException e) {
		// ERROR! Could not load database driver
			System.out.println("failed to load driver");
			return false;
		}
	}
	
	public void clearDatabase(){
		try {
			this.openConnection();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.dropTables();
		this.createTables();
		this.closeConnection("DataBase.clearDataBase");
	}
	
	/**
	 * Opens a connection for an database transaction
	 */
	public void openConnection() throws SQLException {
		String dbName = "db" + File.separator + "indexer.sqlite";
		//System.out.println("Data Base Path: "+dbName);
		String connectionURL = "jdbc:sqlite:" + dbName;
		
		// Open a database connection
		connection = DriverManager.getConnection(connectionURL);
		// Start a transaction
		connection.setAutoCommit(false);
	}
	
	/**
	 * Closes a connection after a database transaction
	 */
	public void closeConnection(String methodName){
		try {
			//System.out.println("Method Calling: "+methodName);
			//System.out.println("Transaction Result: "+transResult);
				if (transResult) {
				connection.commit();
				}
				else {
				connection.rollback();
				}
			}
			catch (SQLException e) {
			// ERROR
				System.out.println("Problem doing either a rollbacl or commit: "+e.toString());
				e.printStackTrace();
			}
			finally {
				if(connection !=null)
				{
					try {
						connection.close();
					} catch (SQLException e) {
						System.out.println("There was a problem closing the database connection");
					}
				}
			}
			connection = null;
	}
	
	private void dropTables(){
		Connection connection = this.getConnection();
		Statement stmt;
		try {
			String sql = "DROP TABLE USERS";
			stmt = connection.createStatement();
			stmt.executeUpdate(sql);
			sql = "DROP TABLE PROJECTS";
			stmt.executeUpdate(sql);
			sql = "DROP TABLE FIELDS";
			stmt.executeUpdate(sql);
			sql = "DROP TABLE IMAGES";
			stmt.executeUpdate(sql);
			sql = "DROP TABLE RECORDVALUES";
			stmt.executeUpdate(sql);
		this.transResult = true;
		} catch (SQLException e) {
			System.out.println("Failed to delete all tables");
			this.transResult = false;
		}
	}
	
	private void createTables(){
		Connection connection  = this.getConnection();
		Statement stmt;   
		
		try {
			stmt = connection.createStatement();
			String sql = "create table users " +	
			"(id integer not null primary key autoincrement,"+
			"username varchar(255) not null,"+
			"password varchar(255) not null,"+
			"firstname varchar(255) not null,"+
			"lastname varchar(255) not null,"+
			"email varchar(255) not null,"+
			"indexedrecords integer not null,"+
			"imageid integer not null)";
			stmt.executeUpdate(sql);
			
			sql = "create table projects "+
			"(id integer not null primary key autoincrement,"+
			"title varchar(255) not null,"+
			"recordsperimage integer not null,"+
			"firstycoord integer not null,"+
			"recordheight integer not null)";
			stmt.executeUpdate(sql);
			
			sql = "create table fields " + 
			"(id integer not null primary key autoincrement,"+
			"fieldnum integer not null,"+
			"title varchar(255) not null,"+
			"xcoord integer not null,"+
			"width integer not null,"+
			"helphtml varchar(255) not null,"+
			"knowndata varchar(255) not null,"+
			"projectid integer not null)";
			stmt.executeUpdate(sql);
			
			sql = "create table images"+
			"(id integer not null primary key autoincrement,"+
			"file varchar(255) not null,"+
			"status varchar(255) not null,"+
			"userid integer not null,"+
			"projectid integer not null)";
			stmt.executeUpdate(sql);
			
			sql = "create table recordvalues"+
					"(id integer not null primary key autoincrement,"+
					"value varchar(255) not null,"+
					"imageid integer not null,"+
					"recordid integer not null,"+
					"fieldnum integer not null)";
			stmt.executeUpdate(sql);
					
			this.transResult = true;
		} catch (SQLException e) {
			System.out.println("Failed to create all tables");
			this.transResult = false;
		}
	}
}
