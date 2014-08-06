package data.importer;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import server.da.Database;
import shared.model.Field;
import shared.model.Image;
import shared.model.Project;
import shared.model.User;
import shared.model.Value;



public class DataImporter {

	Database db = new Database();
	
	
	public static void main(String[] args) {
		Database.initialize();
		DataImporter di = new DataImporter();
		Database db = new Database();
		db.clearDatabase();
		String FileName = args[0];
		di.scanXMLFile(new File(FileName));
		di.moveFiles(new File(FileName));
	}
	
	public void moveFiles(File file) {
		
		String srcFolder = file.getPath();
		int lastSeparator = srcFolder.lastIndexOf(File.separator);
		srcFolder = srcFolder.substring(0, lastSeparator+1);
		
		File srcFile = new File(srcFolder);
		File destFile = new File("db" + File.separator+"data");
		
		if(destFile.exists())
		{
			try {
				FileUtils.deleteDirectory(destFile);
			} catch (IOException e) {
				System.out.println("Failed to delete directory");
			}
		}
		 try {
			FileUtils.copyDirectory(srcFile, destFile);
		} catch (IOException e1) {
			System.out.println("Failed to copy directory");
		}
	}
	
	public void scanXMLFile(File file){
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = null;
		try {
			dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(file);
			
			NodeList userList = doc.getElementsByTagName("user");
			NodeList projectList = doc.getElementsByTagName("project");
			
			db.openConnection();
			createUsers(userList);
			createProjects(projectList);
			
		} catch (Exception e) {
			db.transResult = false;
		}
		finally{
			db.closeConnection("DataImporter.scanXMLFile");
		}
	}
	
	
	private void createUsers(NodeList userList){
		for (int temp = 0; temp < userList.getLength(); temp++) {
			 
			Node nNode = userList.item(temp);
	 
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				String userName =   eElement.getElementsByTagName("username").item(0).getTextContent();
				String password =  eElement.getElementsByTagName("password").item(0).getTextContent();
				String firstName =  eElement.getElementsByTagName("firstname").item(0).getTextContent();
				String lastName = eElement.getElementsByTagName("lastname").item(0).getTextContent();
				String email = eElement.getElementsByTagName("email").item(0).getTextContent();
				int indexedRecords = Integer.parseInt(eElement.getElementsByTagName("indexedrecords").item(0).getTextContent());
				
				User tmpUser = new User(userName, password, firstName, lastName, email, indexedRecords);
				db.transResult = db.user.addUser(tmpUser, db.getConnection());
				if(!db.transResult)
				{
					break;
				}
			}
		}
	}
	
	private void createProjects(NodeList projectList) throws SQLException{
		for (int temp = 0; temp < projectList.getLength(); temp++) {
			 
			Node tempNode = projectList.item(temp);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) tempNode;
				String title =   eElement.getElementsByTagName("title").item(0).getTextContent();
				int recordsPerImage =  Integer.parseInt(eElement.getElementsByTagName("recordsperimage").item(0).getTextContent());
				int firstYCoord =  Integer.parseInt(eElement.getElementsByTagName("firstycoord").item(0).getTextContent());
				int recordHeight = Integer.parseInt(eElement.getElementsByTagName("recordheight").item(0).getTextContent());
				
				Project tmpProject = new Project(title, recordsPerImage, firstYCoord, recordHeight);
				db.transResult = db.project.addProject(tmpProject, db.getConnection());
				
				if(!db.transResult)
				{
					break;
				}
				if (tempNode.hasChildNodes()) {
					NodeList childrenList = tempNode.getChildNodes();
					for (int childTemp = 0; childTemp < childrenList.getLength(); childTemp++) {
						Node childNode = childrenList.item(childTemp);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
	
							Element childElement = (Element) childNode;

							if(childElement.getNodeName().equals("fields")){
								createFields(childNode.getChildNodes(), tmpProject);
							}
							else if(childElement.getNodeName().equals("images")){
								createImages(childNode.getChildNodes(), tmpProject);
							}
						}
					}
				}
			}
		}	
	}
	
	private void createFields(NodeList fieldsList, Project tmpProject) throws SQLException{
		int fieldNum = 1;
		for (int temp = 0; temp < fieldsList.getLength(); temp++) {
			Node tempNode = fieldsList.item(temp);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) tempNode;
				
				String title =   eElement.getElementsByTagName("title").item(0).getTextContent();
				int xcoord =  Integer.parseInt(eElement.getElementsByTagName("xcoord").item(0).getTextContent());
				int width =  Integer.parseInt(eElement.getElementsByTagName("width").item(0).getTextContent());
				String helpHtml =   eElement.getElementsByTagName("helphtml").item(0).getTextContent();
				String knownData = "";
				
				if(tempNode.getTextContent().contains("knowndata"))
				{
					knownData =   eElement.getElementsByTagName("knowndata").item(0).getTextContent();
				}
				int projectId = tmpProject.getId();

				Field tmpField = new Field(fieldNum, title, xcoord, width, helpHtml, knownData, projectId);
				fieldNum++;
				db.transResult = db.field.addField(tmpField, db.getConnection());
				if(!db.transResult)
				{

					break;
				}
				
				ArrayList<Field> tmpFields = tmpProject.getFields();//Do I really need to do this at this point?  I don't think so
				tmpFields.add(tmpField);
				tmpProject.setFields(tmpFields);
			}
		}
	}
	
	private void createImages(NodeList imagesList, Project tmpProject) throws SQLException{
		for (int temp = 0; temp < imagesList.getLength(); temp++) {
			Node tempNode = imagesList.item(temp);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) tempNode;
				
				String file =   eElement.getElementsByTagName("file").item(0).getTextContent();
				int userid = -1;
				int projectId = tmpProject.getId();
				String status =   "available";
				if(tempNode.getChildNodes().getLength() == 5)
				{
					status = "indexed";
				}

				Image tmpImage = new Image(file, status, userid, projectId);
				
				db.transResult = db.image.addImage(tmpImage, db.getConnection());
				int id = db.image.getLastImageId(db.getConnection());
				tmpImage.setId(id);
				
				if(!db.transResult)
				{
					break;
				}
				if (tempNode.hasChildNodes()) {
					NodeList childrenList = tempNode.getChildNodes();
					for (int childTemp = 0; childTemp < childrenList.getLength(); childTemp++) {
						Node childNode = childrenList.item(childTemp);
						if (childNode.getNodeType() == Node.ELEMENT_NODE) {
	
							Element childElement = (Element) childNode;
							if(childElement.getNodeName().equals("records")){
								createRecords(childNode.getChildNodes(), tmpImage);
							}
						}
					}
				}
			}
		}
	}
	
	private void createRecords(NodeList recordsList, Image tmpImage) throws SQLException{
		int imageId = tmpImage.getId();
		int recordid = 1;
		for (int temp = 0; temp < recordsList.getLength(); temp++) {
			Node tempNode = recordsList.item(temp);
			if (tempNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) tempNode;
				for (int i = 0; i<eElement.getElementsByTagName("value").getLength(); i++){
					String valueContext =   eElement.getElementsByTagName("value").item(i).getTextContent();
					Value tmpValue = new Value(valueContext.toUpperCase(), imageId, recordid, i+1);
					db.transResult = db.value.addValue(tmpValue, db.getConnection());
			
					if(!db.transResult){
						break;
					}
				}
				if(!db.transResult){
					break;
				}
				recordid++;
			}
		}
	}
}
