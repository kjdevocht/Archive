package server.da;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import shared.model.Project;
/**A DAO to specifically access a Project object in the Project table*/
public class ProjectAccess {
	/**
	 * @param getProject the Project to get
	 * @return the Project
	 */
	public int getRecordsPerImage(int projectid, Connection connection) throws SQLException{
		PreparedStatement stmt = null;
		ResultSet rs = null;
	
		try {
			String sql = "select recordsperimage from projects "+
					"where id = ?";
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, projectid);
			
			
			rs = stmt.executeQuery();
			int recordsperimage = -1;
			while (rs.next()) {
				recordsperimage = rs.getInt(1);
			}
			return recordsperimage;
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
	 * @param connection need to get the projects from the database
	 * @return A list of projects
	 */
	public Project getProject(int projectid, Connection connection){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		Project project = null;
		try {
			String sql = "select id, title, recordsperimage, firstycoord, recordheight from projects where id = ?";
			
			stmt = connection.prepareStatement(sql);
			stmt.setInt(1, projectid);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int recordsperimage = rs.getInt(3);
				int firstycoord = rs.getInt(4);
				int recordheight = rs.getInt(5);
				
				project = new Project(id, title, recordsperimage, firstycoord, recordheight);
				
			}
			return project;
		}
		catch (SQLException e) {
			System.out.println("Problem Getting Projects: " + e.toString());
			return project;
		}
		finally {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	/**
	 * @param connection need to get the projects from the database
	 * @return A list of projects
	 */
	public ArrayList<Project> getProjects(Connection connection){
		PreparedStatement stmt = null;
		ResultSet rs = null;
		ArrayList<Project> projects = new ArrayList<Project>();
		try {
			String sql = "select id, title, recordsperimage, firstycoord, recordheight from projects ";
			
			stmt = connection.prepareStatement(sql);
			rs = stmt.executeQuery();
			while (rs.next()) {
				int id = rs.getInt(1);
				String title = rs.getString(2);
				int recordsperimage = rs.getInt(3);
				int firstycoord = rs.getInt(4);
				int recordheight = rs.getInt(5);
				
				Project project = null;
				project = new Project(id, title, recordsperimage, firstycoord, recordheight);
				
				projects.add(project);
			}
			return projects;
		}
		catch (SQLException e) {
			System.out.println("Problem Getting Projects: " + e.toString());
			projects.clear();
			return projects;
		}
		finally {
		if (rs != null)
			try {
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		if (stmt != null)
			try {
				stmt.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * @param addProject the Project to get
	 * @return the results
	 */
	public boolean addProject(Project addProject, Connection connection)throws SQLException{
		PreparedStatement  stmt = null;
		Statement keyStmt = null;
		ResultSet keyRS = null;
		boolean results = false;
		try {
			String sql = "INSERT INTO PROJECTS" +  "(title, recordsPerImage, firstYCoord, recordHeight) VALUES"+"(?,?,?,?)";	
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, addProject.getTitle());
			stmt.setInt(2, addProject.getRecordsPerImage());
			stmt.setInt(3, addProject.getFirstYCoord());
			stmt.setInt(4, addProject.getRecordHeight());

			if (stmt.executeUpdate() == 1){
				stmt.close();
				stmt =null;
				keyStmt = connection.createStatement();
				keyRS = keyStmt.executeQuery("select last_insert_rowid()");
				keyRS.next();
				int id = keyRS.getInt(1);
				addProject.setId(id);
				results =  true;
			}
			else{
				throw new SQLException();
			}
		}
		catch (SQLException e) {
			System.out.println("Problem adding Project to the DataBase "+e.toString());
			throw e;
		}
		finally {
			if (stmt != null){
				stmt.close();
			}
			if (keyRS != null){
				keyRS.close();
			}
			if (keyStmt != null){
				keyStmt.close();
			}
		}
		return results;
	}
}
