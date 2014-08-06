package server.da;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import shared.model.Image;
/**A DAO to specifically access a Image object in the Image table*/
public class ImageAccess {
	
	/**
	 * @param imageId the id of the Image to get
	 * @return the Image
	 */
	public Image getImage(int imageid, Connection connection)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Image getImage = null;
		try {
		String sql = "select id, file, status, userid, projectid from images "+
				"where id = ?";
		stmt = connection.prepareStatement(sql);
		stmt.setInt(1, imageid);
		
		rs = stmt.executeQuery();
		while (rs.next()) {
			int id = rs.getInt(1);
			String file = rs.getString(2);
			String status = rs.getString(3);
			int userid = rs.getInt(4);
			int projectid = rs.getInt(5);
			
			getImage = new Image(id, file, status, userid, projectid);

		}
		return getImage;
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
	
	public int getLastImageId(Connection connection){
		Statement keyStmt = null;
		ResultSet keyRS = null;
		int id = -1;
		try {
			keyStmt = connection.createStatement();
			keyRS = keyStmt.executeQuery("select last_insert_rowid()");
			keyRS.next();
			id = keyRS.getInt(1);
			return id;
		}
		catch (SQLException e) {
		return id;
		}
		finally {
		if (keyRS != null)
			try {
				keyRS.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (keyStmt != null)
			try {
				keyStmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getSampleImage(int projectId, Connection connection)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select file from images "+
					"where projectid = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, projectId);
			
			
			rs = stmt.executeQuery();
			String url =  "FAILED";
			while (rs.next()) {
				String file = rs.getString(1);
				
				url = file;
				if(!(url.equals("FAILED"))){
					break;
				}
			}
			return url;
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
	

	public Image downloadBatch(int projectId, Connection connection)throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try {
			String sql = "select id, file, status, userid, projectid from images "+
					"where projectid = ? and "+
					"status = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, projectId);
			stmt.setString(2, "available");
			
			
			rs = stmt.executeQuery();
			Image checkoutImage = null;
			while (rs.next()) {
				int id = rs.getInt(1);
				String file = rs.getString(2);
				String status = rs.getString(3);
				int userid = rs.getInt(4);
				int projectid = rs.getInt(5);
				
				checkoutImage = new Image(id, file, status, userid, projectid);
				if(checkoutImage !=null){
					break;
				}
			}
			return checkoutImage;
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
	 * @param addImage the Image to get
	 * @return the results
	 */
	public boolean addImage(Image addImage, Connection connection){
		PreparedStatement  stmt = null;
		boolean results = false;
		try {
			String sql = "INSERT INTO IMAGES" +  "(file, status, userid, projectid) VALUES"+"(?,?,?,?)";	
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, addImage.getFile());
			stmt.setString(2, addImage.getStatus());
			stmt.setInt(3, addImage.getUserID());
			stmt.setInt(4, addImage.getProjectID());

			if (stmt.executeUpdate() == 1){
				results =  true;
			}
			else{
			// ERROR
				results =  false;
			}
		}
		catch (SQLException e) {
		// ERROR
			System.out.println("Problem adding Image to the DataBase" + e.toString());
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
	 * @param editImage the Image to get
	 * @return the results
	 */
	public boolean editImage(Image editImage, Connection connection) throws SQLException{
		PreparedStatement stmt = null;
		try {
			String sql = "update images " + 
			"set status = ?, userid = ? " +
			"where id = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, editImage.getStatus());
			stmt.setInt(2, editImage.getUserID());
			stmt.setInt(3, editImage.getId());
			if (stmt.executeUpdate() == 1){
				return true;
			}
			else{
				return false;
			}
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
	}
}
