package server;

import java.io.*;
import java.net.*;
import java.sql.Connection;
import java.util.ArrayList;

import com.sun.net.httpserver.*;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import server.da.*;
import shared.comms.DownloadBatchParam;
import shared.comms.DownloadBatchResult;
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
import shared.model.Field;
import shared.model.Image;
import shared.model.Project;
import shared.model.User;
import shared.model.Value;


public class Server {

	private static int SERVER_PORT_NUMBER = 4545;
	private static final int MAX_WAITING_CONNECTIONS = 10;
	private Database db = null;
	private XStream xstream = new XStream(new DomDriver());
	
	private static void setPort(int port){
		SERVER_PORT_NUMBER = port;
	}

	
	private static  HttpServer server;
	
	private Server() {
		return;
	}
	
	public static void exit(){
		server.stop(0);
	}
	
	private void run() {
		
		Database.initialize();	
		this.db = new Database();

		try {
			server = HttpServer.create(new InetSocketAddress(SERVER_PORT_NUMBER),
											MAX_WAITING_CONNECTIONS);
		} 
		catch (IOException e) {
			
			System.out.println("Could not create HTTP server: " + e.getMessage());
			return;
		}

		server.setExecutor(null); // use the default executor
		
		server.createContext("/ValidateUser", validateUserHandler);
		server.createContext("/GetProjects", getProjectsHandler);
		server.createContext("/GetSampleImage", getSampleImageHandler);
		server.createContext("/DownloadBatch", downloadBatchHandler);
		server.createContext("/SubmitBatch", submitBatchHandler);
		server.createContext("/GetFields", getFieldsHandler);
		server.createContext("/Search", searchHandler);
		server.createContext("/Download", downloadFileHandler);
		

		server.start();
		
	}
	
	private HttpHandler validateUserHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			System.out.println("Made it to the Server");
			ValidateUserParam userParam = (ValidateUserParam)xstream.fromXML(exchange.getRequestBody());
			User getUser = new User(userParam.getUser(), userParam.getPassword());
			ValidateUserResult result = null;

			try {
				db.openConnection();
				User validatedUser = db.user.getUser(getUser, db.getConnection());

				result = new ValidateUserResult(true, validatedUser.getFirstName(), validatedUser.getLastName(), 
						validatedUser.getIndexedRecords(), "SUCCESS");
			} catch (Exception e) {
				if(e.getMessage().equals("Failed to Validate"))
				{
					result = new ValidateUserResult(false, "", "", -1, "FALSE");
				}
				else{
					result = new ValidateUserResult();
				}
			}
			finally
			{
				db.closeConnection("Server.validateUserHandler");
			}
			
			String xml = xstream.toXML(result);
			returnResponse(exchange, xml);
		}
	};

	private HttpHandler getProjectsHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			GetProjectsParam getProjectsParam = (GetProjectsParam)xstream.fromXML(exchange.getRequestBody());
			GetProjectsResult result = null;

			try {
				db.openConnection();
				User validatedUser = new User(getProjectsParam.getUser(), getProjectsParam.getPassword());
				validatedUser = db.user.getUser(validatedUser, db.getConnection());

				ArrayList<Project> allProjects = db.project.getProjects(db.getConnection());
				result = new GetProjectsResult(allProjects);
			} catch (Exception e) {
				result = new GetProjectsResult(new ArrayList<Project>());
			}
			finally
			{
				db.closeConnection("Server.getProjectsHandler");
			}

			String xml = xstream.toXML(result);
			returnResponse(exchange, xml);
		}
	};

	private HttpHandler getSampleImageHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			GetSampleImageParam getSampleImageParam = (GetSampleImageParam)xstream.fromXML(exchange.getRequestBody());
			GetSampleImageResult result = null;

			try {
				db.openConnection();
				User validatedUser = new User(getSampleImageParam.getUser(), getSampleImageParam.getPassword());
				validatedUser = db.user.getUser(validatedUser, db.getConnection());
				
				String url = db.image.getSampleImage(getSampleImageParam.getProjectId(), db.getConnection());
				result = new GetSampleImageResult(url);	
			} catch (Exception e) {
				result = new GetSampleImageResult("FAILED");
			}
			finally
			{
				db.closeConnection("Server.getProjectsHandler");
			}

			String xml = xstream.toXML(result);
			returnResponse(exchange, xml);
		}
	};

	private HttpHandler downloadBatchHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			DownloadBatchParam downloadParam = (DownloadBatchParam)xstream.fromXML(exchange.getRequestBody());
			DownloadBatchResult result = null;
			int projectId = downloadParam.getProjectId();

			try {
				db.openConnection();
				User validatedUser = new User(downloadParam.getUser(), downloadParam.getPassword());
				validatedUser = db.user.getUser(validatedUser, db.getConnection());
				
				Image downloadedBatch = db.image.downloadBatch(projectId, db.getConnection());
		
				if(downloadedBatch != null && validatedUser.getImageID() == -1)
				{
					downloadedBatch.setStatus("In Progress");
					downloadedBatch.setUserID(validatedUser.getId());
					db.transResult = db.image.editImage(downloadedBatch, db.getConnection());
					
					ArrayList<Field> fields = db.field.getFields(projectId, db.getConnection());
					Project project = db.project.getProject(downloadedBatch.getProjectID(), db.getConnection());
					validatedUser.setImageID(downloadedBatch.getId());
					db.transResult = db.user.editUser(validatedUser, db.getConnection());
					result = new DownloadBatchResult(downloadedBatch,project.getFirstYCoord(), project.getRecordHeight(), project.getRecordsPerImage(), fields);
				}
			} catch (Exception e) {
				//System.out.println("Download Batch Handler fialed");
			}
			finally{
				db.closeConnection("Server.downloadBatchHandler");
			}

			
			String xml = xstream.toXML(result);
			returnResponse(exchange, xml);
		}
	};
	
	private HttpHandler submitBatchHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			SubmitBatchParam submitBatchParam = (SubmitBatchParam)xstream.fromXML(exchange.getRequestBody());
			SubmitBatchResult result = new SubmitBatchResult();

			int imageid = submitBatchParam.getBatch();
			ArrayList<String> recordValues = submitBatchParam.getValues();

			try {
					db.openConnection();
					Connection connection = db.getConnection();
					//Make sure user is valid
					User validatedUser = new User(submitBatchParam.getUser(), submitBatchParam.getPassword());
					validatedUser = db.user.getUser(validatedUser, connection);
					
					//Get all Variables needed
					Image infoImage = db.image.getImage(imageid, connection);
					if(infoImage.getStatus().equals("indexed")){
						throw new Exception();
					}
					int projectid = infoImage.getProjectID();
					ArrayList<Field> fields = db.field.getFields(projectid, connection);
					int numFields = fields.size();
					int recordsperimage = db.project.getRecordsPerImage(projectid, connection);
					
					//Add values to DB
					int fieldnum = 1;
					int recordid = 1;
					for(int i = 0; i<recordValues.size(); i++){
						Value temp = new Value(recordValues.get(i), infoImage.getId(), recordid, fieldnum);
						db.transResult = db.value.addValue(temp, connection);
						fieldnum++;
						if(fieldnum > numFields){
							fieldnum = 1;
							recordid++;
						}
					}	
					//Edit image to indexed and un-assign image from user
					Image submitImage = new Image(imageid, "indexed", -1);
					db.transResult = db.image.editImage(submitImage, connection);
					
					if(db.transResult){
						//Edit user's indexedrecords number and change imageid to -1
						User submitUser = new User(submitBatchParam.getUser(), submitBatchParam.getPassword());
						submitUser = db.user.getUser(submitUser, connection);
						submitUser.setImageID(-1);
						submitUser.setIndexedRecords(submitUser.getIndexedRecords()+recordsperimage);
						db.transResult = db.user.editUser(submitUser, db.getConnection());
					}
				
					result.setSuccess(true);
			} catch (Exception e) {
				db.transResult = false;
			}
			finally{
				db.closeConnection("Server.downloadBatchHandler");
			}

			String xml = xstream.toXML(result);
			returnResponse(exchange, xml);
		}
	};
	
	private HttpHandler getFieldsHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			GetFieldsParam getFieldsParam = (GetFieldsParam)xstream.fromXML(exchange.getRequestBody());
			GetFieldsResult result = null;

			try {
				db.openConnection();
				User validatedUser = new User(getFieldsParam.getUser(), getFieldsParam.getPassword());
				validatedUser = db.user.getUser(validatedUser, db.getConnection());

				ArrayList<Field> fields = db.field.getFields(getFieldsParam.getProjectId(), db.getConnection());
				result = new GetFieldsResult(fields);
				
			} catch (Exception e) {
				result = new GetFieldsResult(new ArrayList<Field>());
			}
			finally{
				db.closeConnection("Server.getProjectsHandler");
			}

			String xml = xstream.toXML(result);
			returnResponse(exchange, xml);
		}
	};
	
	private HttpHandler searchHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			SearchParam searchParam = (SearchParam)xstream.fromXML(exchange.getRequestBody());
			SearchResult result = new SearchResult();

			try {
				db.openConnection();
				User validatedUser = new User(searchParam.getUser(), searchParam.getPassword());
				validatedUser = db.user.getUser(validatedUser, db.getConnection());
				result = db.value.search(searchParam, db.getConnection());
				
			} catch (Exception e) {
				System.out.println("Search Failed"+e.toString());
			}
			finally{
				db.closeConnection("Server.searchHandler");
			}

			String xml = xstream.toXML(result);
			returnResponse(exchange, xml);
		}
	};
	
	private void returnResponse(HttpExchange exchange, String xml){
		OutputStream os = null;
		try {
			exchange.sendResponseHeaders(200, xml.length());
			os = exchange.getResponseBody();
			os.write(xml.getBytes());

		} catch (IOException e) {
			e.printStackTrace();
		}
		finally{
			if(os != null){
				try {
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	private HttpHandler downloadFileHandler = new HttpHandler() {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			URI url = exchange.getRequestURI();
			String trimming = url.toString();
			trimming = trimming.substring(9, trimming.length());
			String filePath = "db"+File.separator+"data"+trimming;
			
			File file = new File(filePath);
			FileInputStream fileOuputStream = new FileInputStream(file); 
			
			byte[] bFile = new byte[(int)file.length()];
			fileOuputStream.read(bFile);
			fileOuputStream.close();

			exchange.sendResponseHeaders(200,bFile.length);
			OutputStream os = exchange.getResponseBody();
			os.write(bFile);
			os.close();
		}
	};
	
	public static void main(String[] args) {
		Server.setPort(Integer.parseInt(args[0]));
		new Server().run();
	}
}
