package server.da;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import shared.comms.SearchParam;
import shared.comms.SearchResult;
import shared.model.Value;
/**A DAO to specifically access a Value object in the Value table*/
public class ValueAccess {
	/**
	 * @param addValue the Value to get
	 * @return the results
	 */
	public boolean addValue(Value addValue, Connection connection) throws SQLException{
		PreparedStatement  stmt = null;
		boolean results = false;
		try {
			String sql = "INSERT INTO RECORDVALUES" +  "(value, imageid, recordid, fieldnum) VALUES"+"(?,?,?,?)";	
			stmt = connection.prepareStatement(sql);
			stmt.setString(1, addValue.getValue());
			stmt.setInt(2, addValue.getImageID());
			stmt.setInt(3, addValue.getRecordID());
			stmt.setInt(4, addValue.getFieldNum());
			if (stmt.executeUpdate() == 1){
				results =  true;
			}
			else{
				throw new SQLException();
			}
		}
		finally {
			if (stmt != null){
				stmt.close();
			}		
		}
		return results;
	}
	
	public SearchResult search(SearchParam searchParam, Connection connection) throws SQLException{
		ArrayList<Integer> batchId = new ArrayList<Integer>();
		ArrayList<String> imageUrl = new ArrayList<String>();
		ArrayList<Integer> recordNum = new ArrayList<Integer>();
		ArrayList<Integer> fieldNum = new ArrayList<Integer>();
		try {	
			for(int i = 0; i<searchParam.getFields().size(); i++){

				for(int k = 0; k<searchParam.getValues().size(); k++){

					String sql = "select images.id, images.file, recordvalues.recordid, fields.id from images "+
							"inner join fields on images.projectid = fields.projectid "+
							"inner join recordvalues on (recordvalues.fieldnum = fields.fieldnum and recordvalues.imageid = images.id) "+
							"where recordvalues.value = ? and fields.id = ?";
					PreparedStatement stmt = connection.prepareStatement(sql);
					
					String value = searchParam.getValues().get(k).toUpperCase();

					stmt.setString(1, value);
					stmt.setInt(2, Integer.parseInt(searchParam.getFields().get(i)));
					ResultSet rs = stmt.executeQuery();
					
					while (rs.next()) {
						int imageid = rs.getInt(1);
						String file = rs.getString(2);
						int recordnum = rs.getInt(3);
						int fieldnum = rs.getInt(4);
						
						batchId.add(imageid);
						imageUrl.add(file);
						recordNum.add(recordnum);
						fieldNum.add(fieldnum);
					}
					rs.close();
					stmt.close();
				}
			}
			return new SearchResult(batchId, imageUrl, recordNum, fieldNum);
		}	
		catch(SQLException e){
			System.out.println("Problem with Search: "+e.toString());
			throw e;
		}
	}
}
