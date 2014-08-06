package server.da;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import shared.model.User;
/**A DAO to specifically access a User object in the User table*/
public class UserAccess {
	/**
	 * @param getUser the User to get
	 * @return the User
	 */
	public User getUser(User getUser, Connection connection) throws Exception{

		PreparedStatement stmt = null;
		ResultSet rs = null;
		User user = null;

		try {

			String sql = "select id, username, password, firstname, lastname, email, indexedrecords, imageid from users "+
					"where username = ? and "+
					"password = ?";
			
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, getUser.getUserName());
			stmt.setString(2, getUser.getPassword());
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				int id = rs.getInt(1);
				String username = rs.getString(2);
				String password = rs.getString(3);
				String firstname = rs.getString(4);
				String lastname = rs.getString(5);
				String email = rs.getString(6);
				int indexedrecords = rs.getInt(7);
				int imageid = rs.getInt(8);
				user = new User(id, username, password, firstname, lastname, email, indexedrecords, imageid);
			}
			if(user == null){
				throw new Exception("Failed to Validate");
			}
			else{
				return user;
			}
		
		}
		finally {
			if (rs != null){
				rs.close();
			}
			if (stmt != null){
				stmt.close();
			}
		}
	}
	/**
	 * @param addUser the User to get
	 * @return the results
	 */
	public boolean addUser(User addUser, Connection connection){
		PreparedStatement  stmt = null;
		boolean results = false;
		try {
			String sql = "INSERT INTO USERS" +  "(username, password, firstname, lastname, email, indexedrecords, imageid) VALUES"+"(?,?,?,?,?,?,?)";	
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, addUser.getUserName());
			stmt.setString(2, addUser.getPassword());
			stmt.setString(3, addUser.getFirstName());
			stmt.setString(4, addUser.getLastName());
			stmt.setString(5, addUser.getEmail());
			stmt.setInt(6, addUser.getIndexedRecords());
			stmt.setInt(7, -1);
			if (stmt.executeUpdate() == 1){
				results =  true;
			}
			// OK
			else{
			// ERROR
				results =  false;
			}
		}
		catch (SQLException e) {
		// ERROR
			System.out.println("Problem adding User to the DataBase "+e.toString());
		}
		finally {
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return results;
	}

	/**
	 * @param editUser the User to get
	 * @return the results
	 */
	public boolean editUser(User editUser, Connection connection) throws SQLException{
		PreparedStatement stmt = null;
		try {
			String sql = "update users " + 
			"set indexedrecords = ?, imageid = ? " +
			"where id = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, editUser.getIndexedRecords());
			stmt.setInt(2, editUser.getImageID());
			stmt.setInt(3, editUser.getId());
			if (stmt.executeUpdate() == 1){
				return true;
				
			}
			else{
				System.out.println("Edit User Failed: "+editUser.getId());
				throw new SQLException();
			}
		}
		finally {
			if (stmt != null){
				stmt.close();
			}
		}
	}
}
