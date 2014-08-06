package client.Comms;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import shared.comms.DownloadBatchParam;
import shared.comms.DownloadBatchResult;
import shared.comms.DownloadFileParam;
import shared.comms.DownloadFileResult;
import shared.comms.GetFieldsParam;
import shared.comms.GetFieldsResult;
import shared.comms.GetProjectsParam;
import shared.comms.GetProjectsResult;
import shared.comms.GetSampleImageParam;
import shared.comms.GetSampleImageResult;
import shared.comms.SearchParam;
import shared.comms.SearchResult;
import shared.comms.SubmitBatchParam;
import shared.comms.SubmitBatchResult;
import shared.comms.ValidateUserParam;
import shared.comms.ValidateUserResult;
/**This classes allows the client to communicate with the server for anything it might need*/
public class ClientCommunicatorImpl {
	String baseUrl = "";
	String portNum = "";
	String serverUrl = "";

	/**
	 * @return the baseUrl
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @param baseUrl the baseUrl to set
	 */
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}

	/**
	 * @return the portNum
	 */
	public String getPortNum() {
		return portNum;
	}

	/**
	 * @param portNum the portNum to set
	 */
	public void setPortNum(String portNum) {
		this.portNum = portNum;
	}

	/**
	 * @return the serverUrl
	 */
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * @param serverUrl the serverUrl to set
	 */
	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	/**
	 * @param projectId the projectId to download the batch from
	 * @return the results
	 */
	public DownloadBatchResult downLoadBatch(DownloadBatchParam batch){
		DownloadBatchResult result = (DownloadBatchResult) doGet(serverUrl+"/DownloadBatch", batch);
		return result;
		
	}

	/**
	 * @param fields  the parameters for the fields you want returned
	 * @return a list of matching fields
	 */
	public GetFieldsResult getFields(GetFieldsParam fields){
		GetFieldsResult result = (GetFieldsResult) doGet(serverUrl+"/GetFields", fields);
		return result;
	}
	
	/**
	 * @param projects just contains username and password 
	 * @return a list of all projects
	 */
	public GetProjectsResult getProjects(GetProjectsParam projects){
		GetProjectsResult result = (GetProjectsResult) doGet(serverUrl+"/GetProjects", projects);
		return result;
	}
	
	/**
	 * @param sample the param to get the Sample image from
	 * @return an Image
	 */
	public GetSampleImageResult getSampleImage(GetSampleImageParam sample){
		GetSampleImageResult result = (GetSampleImageResult) doGet(serverUrl+"/GetSampleImage", sample);
		return result;
	}
	/**
	 * @param fields a comma separated string with the field ids to search
	 * @param searchValues a comma separated string with the search values to search for
	 * @return a SearchResult
	 */
	
	public SearchResult searh(SearchParam search){
		SearchResult result = (SearchResult) doGet(serverUrl+"/Search", search);
		
		return result;
	}
	
	/**
	 * @param batch the image to submit to the server
	 * @param recordValues the string of all the values of the batch
	 * @return a boolean determining if the submission was successful
	 */
	public SubmitBatchResult submitBatch(SubmitBatchParam batch)
	{
		SubmitBatchResult result = (SubmitBatchResult) doGet(serverUrl+"/SubmitBatch",batch);
		return result;
		
	}
	
	/**
	 * @param user the string given by the user
	 * @param password the string given by the user
	 * @return a ValidateUserResult with the results of the validation
	 */
	public ValidateUserResult validateUser(ValidateUserParam user){
		ValidateUserResult result = (ValidateUserResult) doGet(serverUrl+"/ValidateUser",user);
		return result;
		
	}
	
	/**
	 * @param url the string of the url for the file to download
	 * @return a File
	 */
	public DownloadFileResult downloadFile(DownloadFileParam download ){
		DownloadFileResult file = null;
		try {
			URL url = new URL(serverUrl+"/Download/"+download.getUrl());
			System.out.println("URL CC: "+url);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();
			
			// Write request body to OutputStream ...
			OutputStream outFile = new BufferedOutputStream(connection.getOutputStream());
			outFile.write(download.getUrl().getBytes());
			outFile.close();
			
			
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream inFile = new BufferedInputStream(connection.getInputStream());
				ByteArrayOutputStream buffer = new ByteArrayOutputStream();
				int nRead;
				byte[] data = new byte[16384];
				while ((nRead = inFile.read(data, 0, data.length)) != -1) {
					buffer.write(data, 0, nRead);
				}
				buffer.flush();

				file = new DownloadFileResult(buffer.toByteArray());
				inFile.close();
			}
			else {
			// SERVER RETURNED AN HTTP ERROR
			}
		}
		catch (IOException e) {
		System.out.println("Something failed "+e.toString());
		} 
		return file;
	}
	
	private Object doGet(String urlPath, Object getData) {

		Object newObject = null;
		XStream xstream = new XStream(new DomDriver());
		try {
			URL url = new URL(urlPath);
			HttpURLConnection connection = (HttpURLConnection)url.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoOutput(true);
			connection.connect();
			
			// Write request body to OutputStream ...
			OutputStream outFile = new BufferedOutputStream(connection.getOutputStream());
			xstream.toXML(getData, outFile);
			outFile.close();
			
			if (connection.getResponseCode() == HttpURLConnection.HTTP_OK) {
				InputStream inFile = new BufferedInputStream(connection.getInputStream());
				newObject = (Object)xstream.fromXML(inFile);
				inFile.close();
			}
			else {
			// SERVER RETURNED AN HTTP ERROR
			}
			}
			catch (IOException e) {
			System.out.println("Something failed "+e.toString());
			} 
		return newObject;
	}
}
