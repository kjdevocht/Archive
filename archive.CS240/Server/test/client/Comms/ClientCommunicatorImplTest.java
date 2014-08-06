package client.Comms;

import static org.junit.Assert.*;

import java.io.File;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import data.importer.DataImporter;
import server.Server;
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

public class ClientCommunicatorImplTest {

	@Before
	public void setUp() throws Exception {
		String[] args = new String[1];
		args[0] = "testdata"+File.separator+"Records.xml";
		DataImporter.main(args);
		String[] args2 = new String[1];
		args2[0] = "4545";
		Server.main(args2);
	}

	@After
	public void tearDown() throws Exception {
		Server.exit();	
	}

	@Test
	public final void testDownLoadBatch() {
		//First try valid user how can download a batch
		ClientCommunicatorImpl client = new ClientCommunicatorImpl();
		DownloadBatchParam batch = new DownloadBatchParam("test1", "test1", 1);
		client.setServerUrl("http://localhost:4545");
		DownloadBatchResult testBatch = null;
		testBatch =	client.downLoadBatch(batch);
		assertNotNull(testBatch);
		assertTrue(testBatch.getImage().getFile().equals("images/1890_image0.png"));
		
		//Second try and download a patch for the same user
		testBatch =	client.downLoadBatch(batch);
		assertNull(testBatch);
		
		//Third try and download with an unauthorized user
		batch = new DownloadBatchParam("test5", "test1", 1);
		testBatch =	client.downLoadBatch(batch);
		assertNull(testBatch);
		
		//Fourth try with bad port
		batch = new DownloadBatchParam("test1", "test1", 1);
		client.setServerUrl("http://localhost:5050");
		testBatch = null;
		testBatch =	client.downLoadBatch(batch);
		assertNull(testBatch);
		
	}

	@Test
	public final void testGetFields() {
		//First test for proper authorization and specific project
		ClientCommunicatorImpl client = new ClientCommunicatorImpl();
		GetFieldsParam fields = new GetFieldsParam("test1", "test1", 1);
		client.setServerUrl("http://localhost:4545");
		GetFieldsResult testFields = null;
		testFields =	client.getFields(fields);
		assertNotNull(testFields);
		assertTrue(testFields.getFields().size() == 4);
		
		//Second test for proper authorization and no projectid
		fields = new GetFieldsParam("test1", "test1", "");
		testFields =	client.getFields(fields);
		assertTrue(testFields.getFields().size() == 13);
		
		//Third test for invaild user
		fields = new GetFieldsParam("fail1", "test1", "");
		testFields =	client.getFields(fields);
		assertTrue(testFields.getFields().size() == 0);
		
		//Fourth test for bad port
		fields = new GetFieldsParam("test1", "test1", 1);
		client.setServerUrl("http://localhost:5050");
		testFields = null;
		testFields =	client.getFields(fields);
		assertNull(testFields);
	}


	@Test
	public final void testGetProjects() {
		//First test for proper authorization
		ClientCommunicatorImpl client = new ClientCommunicatorImpl();
		GetProjectsParam projects = new GetProjectsParam("test1", "test1");
		client.setServerUrl("http://localhost:4545");
		GetProjectsResult testProjects = null;
		testProjects =	client.getProjects(projects);
		assertNotNull(testProjects);
		assertTrue(testProjects.getProjects().size() == 3);
		
		//Second test for failed authorization
		projects = new GetProjectsParam("fail1", "test1");
		testProjects =	client.getProjects(projects);
		assertTrue(testProjects.getProjects().size() == 0);
		
		//Third test for bad port
		projects = new GetProjectsParam("test1", "test1");
		client.setServerUrl("http://localhost:5050");
		testProjects = null;
		testProjects =	client.getProjects(projects);
		assertNull(testProjects);
	}

	@Test
	public final void testGetSampleImage() {
		//First test for proper authorization
		ClientCommunicatorImpl client = new ClientCommunicatorImpl();
		GetSampleImageParam sample = new GetSampleImageParam("test1", "test1", 1);
		client.setServerUrl("http://localhost:4545");
		GetSampleImageResult testSample = null;
		testSample =	client.getSampleImage(sample);
		assertNotNull(testSample);
		assertTrue(testSample.getImageURl().equals("images/1890_image0.png"));
		
		//Second test for failed authorization
		sample = new GetSampleImageParam("fail1", "test1", 1);
		testSample =	client.getSampleImage(sample);
		assertTrue(testSample.getImageURl().equals("FAILED"));
		
		//Third test another project just to make sure
		sample = new GetSampleImageParam("test1", "test1", 3);
		testSample =	client.getSampleImage(sample);
		assertTrue(testSample.getImageURl().equals("images/draft_image0.png"));
		
		//Fourth test failure to reach server
		client.setServerUrl("http://localhost:6666");
		testSample = null;
		testSample =	client.getSampleImage(sample);
		assertNull(testSample);
	}

	@Test
	public final void testSearch() {
		//First test with a fully batched image
		ClientCommunicatorImpl client = new ClientCommunicatorImpl();
		String fields = "10,11,12";
		String values = "Hall,28,TED";
		SearchParam search = new SearchParam("test1", "test1", fields, values);
		client.setServerUrl("http://localhost:4545");
		SearchResult testSearch = null;
		testSearch =	client.searh(search);
		assertNotNull(testSearch);

		assertTrue(testSearch.getBatchId().size() == 13);
		assertTrue(testSearch.getImageUrl().size() == 13);
		assertTrue(testSearch.getRecordNum().size() == 13);
		assertTrue(testSearch.getFieldNum().size() == 13);
		
		//Second test from an non-indexed image
		fields = "1,2,3";
		values = "Hall,28,TED";
		search = new SearchParam("test1", "test1", fields, values);
		client.setServerUrl("http://localhost:4545");
		testSearch = null;
		testSearch =	client.searh(search);
		assertTrue(testSearch.getBatchId().size() == 0);
		assertTrue(testSearch.getImageUrl().size() == 0);
		assertTrue(testSearch.getRecordNum().size() == 0);
		assertTrue(testSearch.getFieldNum().size() == 0);
		
		//Third test for unauthorized user
		fields = "10,11,12";
		values = "Hall,28,TED";
		search = new SearchParam("test2", "test1", fields, values);
		client.setServerUrl("http://localhost:4545");
		testSearch = null;
		testSearch =	client.searh(search);
		assertTrue(testSearch.getBatchId().size() == 0);
		assertTrue(testSearch.getImageUrl().size() == 0);
		assertTrue(testSearch.getRecordNum().size() == 0);
		assertTrue(testSearch.getFieldNum().size() == 0);
		
		//Fourth test for failure to connect to server
		fields = "10,11,12";
		values = "Hall,28,TED";
		search = new SearchParam("test2", "test1", fields, values);
		client.setServerUrl("http://localhost:5050");
		testSearch = null;
		testSearch =	client.searh(search);
		assertNull(testSearch);


	}

	@Test
	public final void testSubmitBatch() {
		ClientCommunicatorImpl client = new ClientCommunicatorImpl();
		
		//First test with a fully indexed image
		String values = "last1,first1,male,1;last2,first2,female,2;last3,first3,male,3;last4,first4,female,4;last5,first5,male,5;last6,first6,female,6;last7,first7,male,7;last8,first8,female,8";
		SubmitBatchParam submit = new SubmitBatchParam("test1", "test1", 1, values);
		client.setServerUrl("http://localhost:4545");
		SubmitBatchResult testSubmit = null;
		testSubmit = client.submitBatch(submit);
		assertNotNull(testSubmit);
		assertTrue(testSubmit.isSuccess());
		
		//Second resubmit the same batch
		testSubmit =	client.submitBatch(submit);
		assertFalse(testSubmit.isSuccess());
		
		//Third test bad batch id
		submit = new SubmitBatchParam("test1", "test1", 689, values);
		testSubmit = null;
		testSubmit = client.submitBatch(submit);
		assertNotNull(testSubmit);
		assertFalse(testSubmit.isSuccess());
		
		//Fourth test bad port
		submit = new SubmitBatchParam("test1", "test1",1, values);
		client.setServerUrl("http://localhost:5454");
		testSubmit = null;
		testSubmit = client.submitBatch(submit);
		assertNull(testSubmit);
	}

	@Test
	public final void testValidateUser() {
		//Frist test a valid user
		ClientCommunicatorImpl client = new ClientCommunicatorImpl();
		ValidateUserParam user = new ValidateUserParam("test1", "test1");
		client.setServerUrl("http://localhost:4545");
		ValidateUserResult testValidate = null;
		testValidate =	client.validateUser(user);
		assertNotNull(testValidate);
		
		//Second test invalid user
		assertTrue(testValidate.getUserLastName().equals("One"));
		user = new ValidateUserParam("test1", "test2");
		testValidate =	client.validateUser(user);
		assertFalse(testValidate.getClass().equals("FALSE"));
		
		//Third test for bad port
		client.setServerUrl("http://localhost:1234");
		user = new ValidateUserParam("test1", "test1");
		testValidate =	client.validateUser(user);
		assertNull(testValidate);
	}

	@Test
	public final void testDownloadFile() {
		//First should be successful
		ClientCommunicatorImpl client = new ClientCommunicatorImpl();
		DownloadFileParam file = new DownloadFileParam("images/1890_image0.png");
		client.setServerUrl("http://localhost:4545");
		DownloadFileResult testDownloadFile = null;
		testDownloadFile =	client.downloadFile(file);
		assertTrue(testDownloadFile.getByteFile().length == 36298);
		
		//Second test for bad port
		client.setServerUrl("http://localhost:5050");
		testDownloadFile = null;
		testDownloadFile =	client.downloadFile(file);
		assertNull(testDownloadFile);
	}

}
