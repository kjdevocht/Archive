package server.da;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shared.model.Field;

/**A DAO to specifically access a Field object in the Field table*/
public class FieldAccess {
	/**
	 * @param connection to the database
	 * @return the list of fields for the 
	 */
	public ArrayList<Field> getFields(int projectid, Connection connection) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Field> fields = new ArrayList<Field>();
		try {
			String sql = "";
			if(projectid == -1)
			{
				sql = "select id, fieldnum, title, xcoord, width, helphtml, knowndata, projectid from fields ";
			}
			else
			{
				sql = "select id, fieldnum, title, xcoord, width, helphtml, knowndata, projectid from fields "+
				"where projectid = ?";
			}
			
			stmt = connection.prepareStatement(sql);
			if(projectid !=-1){
				stmt.setInt(1, projectid);
			}

			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				int fieldnum = rs.getInt(2);
				String title = rs.getString(3);
				int xcoord = rs.getInt(4);
				int width = rs.getInt(5);
				String helphtml = rs.getString(6);
				String knowndata = rs.getString(7);
				int newprojectid = rs.getInt(8);
				
				Field field = null;
				field = new Field(id, fieldnum, title, xcoord, width, helphtml, knowndata, newprojectid);
				fields.add(field);
			}
			return fields;
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
	 * @param addField the Field to get
	 * @return the results
	 */
	public boolean addField(Field addField, Connection connection) throws SQLException{
		PreparedStatement  stmt = null;
		boolean results = false;
		try {
			String sql = "INSERT INTO FIELDS" +  "(fieldnum, title, xcoord, width, helphtml, knowndata, projectid) VALUES"+"(?,?,?,?,?,?,?)";	
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, addField.getFieldNum());
			stmt.setString(2, addField.getTitle());
			stmt.setInt(3, addField.getXcoord());
			stmt.setInt(4, addField.getWidth());
			stmt.setString(5, addField.getHelpHTML());
			stmt.setString(6, addField.getKnownData());
			stmt.setInt(7, addField.getProjectID());
			if (stmt.executeUpdate() == 1){
				results =  true;
			}
			// OK
			else{
				throw new SQLException();
			}
		}
		catch (SQLException e) {
		// ERROR
			System.out.println("Problem adding Field to the DataBase: "+e.toString());
			throw e;
		}
		finally {
			if (stmt != null)
				stmt.close();
		}
		return results;
	}
}
