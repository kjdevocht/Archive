package servertester.controllers;

import java.util.*;
import client.Comms.*;

import servertester.views.*;
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

public class Controller implements IController {

	private IView _view;
	
	public Controller() {
		return;
	}
	
	public IView getView() {
		return _view;
	}
	
	public void setView(IView value) {
		_view = value;
	}
	
	// IController methods
	//
	
	@Override
	public void initialize() {
		getView().setHost("localhost");
		getView().setPort("39640");
		operationSelected();
	}

	@Override
	public void operationSelected() {
		ArrayList<String> paramNames = new ArrayList<String>();
		paramNames.add("User");
		paramNames.add("Password");
		
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			break;
		case GET_PROJECTS:
			break;
		case GET_SAMPLE_IMAGE:
			paramNames.add("Project");
			break;
		case DOWNLOAD_BATCH:
			paramNames.add("Project");
			break;
		case GET_FIELDS:
			paramNames.add("Project");
			break;
		case SUBMIT_BATCH:
			paramNames.add("Batch");
			paramNames.add("Record Values");
			break;
		case SEARCH:
			paramNames.add("Fields");
			paramNames.add("Search Values");
			break;
		default:
			assert false;
			break;
		}
		
		getView().setRequest("");
		getView().setResponse("");
		getView().setParameterNames(paramNames.toArray(new String[paramNames.size()]));
	}

	@Override
	public void executeOperation() {
		switch (getView().getOperation()) {
		case VALIDATE_USER:
			validateUser();
			break;
		case GET_PROJECTS:
			getProjects();
			break;
		case GET_SAMPLE_IMAGE:
			getSampleImage();
			break;
		case DOWNLOAD_BATCH:
			downloadBatch();
			break;
		case GET_FIELDS:
			getFields();
			break;
		case SUBMIT_BATCH:
			submitBatch();
			break;
		case SEARCH:
			search();
			break;
		default:
			assert false;
			break;
		}
	}
	
	private void validateUser() {
		ClientCommunicatorImpl client = intialClient();
		String[] params = getView().getParameterValues();
		getView().setRequest(params[0]+"\n"+params[1]);
		ValidateUserParam validate = new ValidateUserParam(params[0], params[1]);
		ValidateUserResult result = null;
		result = client.validateUser(validate);
		StringBuilder toView = new StringBuilder();
		if(result == null || result.getStatus().endsWith("FAILED"))
		{
			getView().setResponse("FAILED\n");
		}
		else if(result.getStatus().endsWith("FALSE"))
		{
			getView().setResponse("FALSE\n");
		}
		else
		{
			toView.append("TRUE\n");
			toView.append(result.getUserFirstName());
			toView.append("\n");
			toView.append(result.getUserLastName());
			toView.append("\n");
			toView.append(result.getNumRecords());
			toView.append("\n");
			getView().setResponse(toView.toString());
		}
	}
	
	private void getProjects() {
		ClientCommunicatorImpl client = intialClient();
		String[] params = getView().getParameterValues();
		getView().setRequest(params[0]+"\n"+params[1]);
		GetProjectsParam projects = new GetProjectsParam(params[0], params[1]);
		GetProjectsResult result = null;
		result = client.getProjects(projects);
		StringBuilder toView = new StringBuilder();
		if(result == null || result.getProjects().size() == 0){
			getView().setResponse("FAILED\n");
		}
		else{
			for(int i = 0; i<result.getProjects().size(); i++)
			{
				toView.append(result.getProjects().get(i).getId());
				toView.append("\n");
				toView.append(result.getProjects().get(i).getTitle());
				toView.append("\n");
			}
			getView().setResponse(toView.toString());
		}
		
	}
	
	private void getSampleImage() {
		ClientCommunicatorImpl client = intialClient();
		String[] params = getView().getParameterValues();
		getView().setRequest(params[0]+"\n"+params[1]+"\n"+params[2]);
		GetSampleImageParam images = new GetSampleImageParam(params[0], params[1], Integer.parseInt(params[2]));
		GetSampleImageResult result = null;
		result = client.getSampleImage(images);
		StringBuilder toView = new StringBuilder();
		if(result == null ||result.getImageURl().endsWith("FAILED")){
			getView().setResponse("FAILED\n");
		}
		else{
			toView.append(client.getServerUrl()+"/"+"Download/"+result.getImageURl());
			getView().setResponse(toView.toString());
		}
	}
	
	private void downloadBatch() {
		ClientCommunicatorImpl client = intialClient();
		String[] params = getView().getParameterValues();
		getView().setRequest(params[0]+"\n"+params[1]+"\n"+params[2]);
		DownloadBatchParam images = new DownloadBatchParam(params[0], params[1], Integer.parseInt(params[2]));
		DownloadBatchResult result = null;
		result = client.downLoadBatch(images);
		StringBuilder toView = new StringBuilder();
		if(result==null){
			getView().setResponse("FAILED\n");
		}
		else{
			toView.append(result.getImage().getId());
			toView.append("\n");
			toView.append(result.getImage().getProjectID());
			toView.append("\n");
			toView.append(client.getServerUrl()+"/"+"Download/"+result.getImage().getFile());
			toView.append("\n");
			toView.append(result.getFirstycoord());
			toView.append("\n");
			toView.append(result.getRecordheight());
			toView.append("\n");
			toView.append(result.getRecordsperimage());
			toView.append("\n");
			toView.append(result.getFields().size());
			toView.append("\n");
			for(int i = 0; i<result.getFields().size(); i++){
				toView.append(result.getFields().get(i).getId());
				toView.append("\n");
				toView.append(result.getFields().get(i).getFieldNum());
				toView.append("\n");
				toView.append(result.getFields().get(i).getTitle());
				toView.append("\n");
				toView.append(client.getServerUrl()+"/"+"Download/"+result.getFields().get(i).getHelpHTML());
				toView.append("\n");
				toView.append(result.getFields().get(i).getXcoord());
				toView.append("\n");
				toView.append(result.getFields().get(i).getWidth());
				toView.append("\n");
				if(!(result.getFields().get(i).getKnownData().equals(""))){
					toView.append(client.getServerUrl()+"/"+"Download/"+result.getFields().get(i).getKnownData());
					toView.append("\n");
				}
			}
			
			getView().setResponse(toView.toString());
		}
	}
	
	private void getFields() {
		ClientCommunicatorImpl client = intialClient();
		String[] params = getView().getParameterValues();
		GetFieldsParam fields = null;
		if(params[2].equals("")){
			getView().setRequest(params[0]+"\n"+params[1]+"\n"+params[2]);
			fields = new GetFieldsParam(params[0], params[1], params[2]);
		}
		else{
			getView().setRequest(params[0]+"\n"+params[1]+"\n"+params[2]);
			fields = new GetFieldsParam(params[0], params[1], Integer.parseInt(params[2]));
		}

		GetFieldsResult result = null;
				result = client.getFields(fields);
		StringBuilder toView = new StringBuilder();
		if(result == null ||result.getFields().size() == 0){
			getView().setResponse("FAILED\n");
		}
		else{
			for(int i = 0; i<result.getFields().size(); i++){
				toView.append(result.getFields().get(i).getProjectID());
				toView.append("\n");
				toView.append(result.getFields().get(i).getId());
				toView.append("\n");
				toView.append(result.getFields().get(i).getTitle());
				toView.append("\n");

			}
			getView().setResponse(toView.toString());
		}
		
	}
	
	private void submitBatch() {
		ClientCommunicatorImpl client = intialClient();
		String[] params = getView().getParameterValues();
		getView().setRequest(params[0]+"\n"+params[1]+"\n"+params[2]);
		SubmitBatchParam submit = new SubmitBatchParam(params[0], params[1], Integer.parseInt(params[2]), params[3]);
		SubmitBatchResult result = null;
		result = client.submitBatch(submit);
		if(result == null || result.isSuccess()){
			getView().setResponse("TRUE\n");
		}
		else{
			getView().setResponse("FAILED\n");
		}
		
	}
	
	private void search() {
		ClientCommunicatorImpl client = intialClient();
		String[] params = getView().getParameterValues();
		getView().setRequest(params[0]+"\n"+params[1]+"\n"+params[2]);
		SearchParam search = new SearchParam(params[0], params[1], params[2], params[3]);
		SearchResult result = null;
		result = client.searh(search);
		StringBuilder toView = new StringBuilder();
		if(result == null || result.getBatchId().size() == 0){
			getView().setResponse("FAILED\n");
		}
		else{
			for(int i = 0; i<result.getBatchId().size(); i++){
				toView.append(result.getBatchId().get(i));
				toView.append("\n");
				toView.append(client.getServerUrl()+"/"+"Download/"+result.getImageUrl().get(i));
				toView.append("\n");
				toView.append(result.getRecordNum().get(i));
				toView.append("\n");
				toView.append(result.getFieldNum().get(i));
				toView.append("\n");
			}
			getView().setResponse(toView.toString());
		}
		
	}
	
	private ClientCommunicatorImpl intialClient(){
		ClientCommunicatorImpl client = new ClientCommunicatorImpl();
		client.setBaseUrl(getView().getHost());
		client.setPortNum(getView().getPort());
		client.setServerUrl("http://"+getView().getHost()+":"+getView().getPort());
		return client;
	}

}

