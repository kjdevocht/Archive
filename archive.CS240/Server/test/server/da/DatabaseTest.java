package server.da;

import static org.junit.Assert.*;

import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.importer.DataImporter;
import shared.model.Field;
import shared.model.Image;
import shared.model.Project;
import shared.model.User;
import shared.model.Value;

public class DatabaseTest {

	@Before
	public void setUp() throws Exception {
		String[] args = new String[1];
		args[0] = "testdata"+File.separator+"Records.xml";
		DataImporter.main(args);
	}

	@After
	public void tearDown() throws Exception {
		
		return;
	}


	@Test
	public final void testInitialize() {
		boolean condition = Database.initialize();
		assertTrue(condition);
	}
		
	@Test
	public final void testOpenConnection() {
		Database db = new Database();
		try {
			db.openConnection();
		} catch (SQLException e) {
			System.out.println("Connection failed to open: " + e.toString());
		}
		assertNotNull(db.connection);
	}

	@Test
	public final void testCloseConnection() {
		Database db = new Database();
		try {
			db.openConnection();
		} catch (SQLException e) {
			System.out.println("Connection failed to open: " + e.toString());
		}
		assertNotNull(db.connection);
		db.closeConnection("DatabaseTest.testClostConnection");
		assertNull(db.connection);
	}

	@Test
	public void testAddUser(){
		Database db = new Database();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			//Add a user
			User user = new User("kdevocht", "test", "Kevin", "DeVocht","kjdevocht@gmail.com", 50);
			db.transResult = db.user.addUser(user, db.connection);
			assertTrue(db.transResult);
		} catch (SQLException e) {
			System.out.println("Connection failed to open: " + e.toString());
		}
		finally{
			db.closeConnection("DatabaseTest.testAddUser");
			assertNull(db.connection);
		}
	}
	
	
	@Test
	public void testGetUser(){
		Database db = new Database();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			//Test one should validate user
			User user = new User("test1", "test1");
			user = db.user.getUser(user, db.getConnection());
			assertTrue(user.getFirstName().equals("Test"));
			
			user = new User("kdevocht", "test2", "Kevin", "DeVocht","kjdevocht@gmail.com", 50);

			user = db.user.getUser(user, db.getConnection());
			
		} catch (Exception e) {
			assertTrue(e.getMessage().equals("Failed to Validate"));
		}
		finally{
			db.closeConnection("DatabaseTest.testGetUser");
		}

		
	}
	
	@Test
	public void testEditUser(){
		Database db = new Database();
		try {
			db.openConnection();
		} catch (SQLException e) {
			System.out.println("Connection failed to open: " + e.toString());
		}
		assertNotNull(db.connection);
		User user = new User("kdevocht", "test", "Kevin", "DeVocht","kjdevocht@gmail.com", 75);
		db.transResult = db.user.addUser(user, db.connection);
		assertTrue(db.transResult);
		try {
			user = db.user.getUser(user, db.getConnection());
			user.setImageID(44);
			db.transResult = db.user.editUser(user, db.getConnection());
			assertTrue(db.transResult);
			user = db.user.getUser(user, db.connection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		assertTrue(user.getImageID() == 44);
		db.closeConnection("DatabaseTest.testEditUser");
		assertNull(db.connection);
	}
	
	
	@Test
	public void testAddField(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			Field field = new Field(1, "Last Name", 0, 168, "helphtml\file","kjdevocht@gmail.com", 1);
			db.transResult = db.field.addField(field,db.connection);
			assertTrue(db.transResult);

		} catch (SQLException e) {
			System.out.println("Failed to add Field: " + e.toString());
		}
		finally{
			db.closeConnection("DatabaseTest.testAddField");
			assertNull(db.connection);
		}
	}
	
	
	@Test
	public void testgetFields(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			ArrayList<Field> fields = null;
			fields = db.field.getFields(-1,db.connection);
			assertTrue(fields.size() == 13);
			fields = null;
			fields = db.field.getFields(2,db.connection);
			assertTrue(fields.size() == 5);
		} catch (SQLException e) {
			System.out.println("Failed to add Field: " + e.toString());
		}
		finally{
			db.closeConnection("DatabaseTest.testAddField");
			assertNull(db.connection);
		}
	}
	
	
	@Test
	public void testgetImage(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			Image image = null;
			image = db.image.getImage(1,db.connection);
			assertTrue(image.getFile().equals("images/1890_image0.png"));
		} catch (SQLException e) {
			System.out.println("Connection failed to open: " + e.toString());
		}
		finally{
			db.closeConnection("DatabaseTest.testAddImage");
			assertNull(db.connection);	
		}
	}
	
	@Test
	public void testgetSampleImage(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			String image = null;
			image = db.image.getSampleImage(1,db.connection);
			assertTrue(image.equals("images/1890_image0.png"));
		} catch (SQLException e) {
			System.out.println("Connection failed to open: " + e.toString());
		}
		finally{
			db.closeConnection("DatabaseTest.testAddImage");
			assertNull(db.connection);	
		}
	}
	
	@Test
	public void testdownloadBatch(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			Image image = null;
			image = db.image.downloadBatch(1,db.connection);
			assertTrue(image.getFile().equals("images/1890_image0.png"));
		} catch (SQLException e) {
			System.out.println("Connection failed to open: " + e.toString());
		}
		finally{
			db.closeConnection("DatabaseTest.testAddImage");
			assertNull(db.connection);	
		}
	}
	
	@Test
	public void testAddImage(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			Image image = new Image("image\\file", "available", 1, 1);
			db.transResult = db.image.addImage(image,db.connection);
			assertTrue(db.transResult);
		} catch (SQLException e) {
			System.out.println("Connection failed to open: " + e.toString());
		}
		finally{
			db.closeConnection("DatabaseTest.testAddImage");
			assertNull(db.connection);	
		}

	}
	
	@Test
	public void testEditImage(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			Image image = new Image(1, "images/1890_image0.png", "indexed", 1, 1);
			db.transResult = db.image.editImage(image,db.connection);
			assertTrue(db.transResult);
			image = null;
			image = db.image.getImage(1,db.connection);
			assertTrue(image.getStatus().equals("indexed"));
		} catch (SQLException e) {
			System.out.println("Connection failed to open: " + e.toString());
		}
		finally{
			db.closeConnection("DatabaseTest.testAddImage");
			assertNull(db.connection);	
		}

	}
	
	@Test
	public void testGetLastImageId(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			Image image = new Image("image\\file", "available", 1, 1);
			db.transResult = db.image.addImage(image,db.connection);
			assertTrue(db.transResult);
			int id = db.image.getLastImageId(db.getConnection());
			assertTrue(id !=-1);
		} catch (SQLException e) {
			System.out.println("Connection failed to open: " + e.toString());
		}
		finally{
			db.closeConnection("DatabaseTest.testAddImage");
			assertNull(db.connection);
		}
	}
	
	@Test
	public void testAddProject(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			Project project = new Project("Census", 40, 1, 1);
			db.transResult = db.project.addProject(project,db.connection);
			assertTrue(db.transResult);
		} catch (SQLException e) {
			System.out.println("Connection failed to open: " + e.toString());
		}
		finally{
			db.closeConnection("DatabaseTest.testAddProject");
			assertNull(db.connection);
		}
	}
	
	@Test
	public void testGetRecordsPerImage(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			int imageid = 1;
			int testResults;
			testResults = db.project.getRecordsPerImage(imageid,db.connection);
			assertTrue(testResults == 8);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			db.closeConnection("DatabaseTest.testAddProject");
			assertNull(db.connection);
		}
	}
	
	@Test
	public void testGetProject(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			Project testResults = null;
			testResults = db.project.getProject(1, db.connection);
			assertTrue(testResults.getRecordHeight() == 60);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			db.closeConnection("DatabaseTest.testAddProject");
			assertNull(db.connection);
		}
	}
	
	@Test
	public void testGetProjects(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			ArrayList<Project> testResults = null;
			testResults = db.project.getProjects(db.connection);
			assertTrue(testResults.size() == 3);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			db.closeConnection("DatabaseTest.testAddProject");
			assertNull(db.connection);
		}
	}
	
	@Test
	public void testAddValue(){
		Database db = new Database();
		Database.initialize();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			Value value = new Value("Johnson", 40, 1, 1);
			db.transResult = db.value.addValue(value,db.connection);
			assertTrue(db.transResult);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			db.closeConnection("DatabaseTest.testAddValue");
			assertNull(db.connection);
		}
	}
	
	@Test
	public void testAddTransaction(){
		Database db = new Database();
		try {
			db.openConnection();
			assertNotNull(db.connection);
			User user = new User("s4d3", "Marie", "Sade", "DeVocht","sade@gmail.com", 50);
			db.transResult = db.user.addUser(user, db.connection);
			assertTrue(db.transResult);
			Field field = new Field(4, "First Name", 5, 155, "helphtml\file2","test", 2);
			db.transResult = db.field.addField(field,db.connection);
			assertTrue(db.transResult);
			Image image = new Image("image\\file2", "available", 2, 1);
			db.transResult = db.image.addImage(image,db.connection);
			assertTrue(db.transResult);
			Project project = new Project("Military", 500, 4, 25);
			db.transResult = db.project.addProject(project,db.connection);
			assertTrue(db.transResult);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			db.closeConnection("DatabaseTest.testAddTransaction");
			assertNull(db.connection);	
		}
	}
}
