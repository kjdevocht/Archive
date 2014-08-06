package client;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.Comms.ClientCommunicatorImpl;
import client.listeners.BatchStateListener;

import shared.comms.DownloadBatchParam;
import shared.comms.DownloadBatchResult;
import shared.comms.DownloadFileParam;
import shared.comms.DownloadFileResult;
import shared.comms.SubmitBatchParam;
import shared.comms.SubmitBatchResult;

public class Controller{
	
	private String user;
	private String password;
	private String host;
	private String port;
	private String serverUrl;
	private ClientCommunicatorImpl comm;
	public BatchState batchState;
	
	///////////////////////
	//Getters and Setters//
	///////////////////////
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public ClientCommunicatorImpl getComm() {
		return comm;
	}

	public void setComm(ClientCommunicatorImpl comm) {
		this.comm = comm;
	}


	public  BatchState getBatchState() {
		return batchState;
	}

	public  void setBatchState(BatchState batchState) {
		this.batchState = batchState;
		this.batchState.addListener(Controller.instance().l);
	}


	///////////////////
	//Singleton Setup//
	///////////////////
	private static Controller instance1;
	public static Controller instance(){
		if(instance1 == null){
			instance1 = new Controller();
		}
		return instance1;
	}
	
	
	///////////////////////////////
	//Client Communicator Classes//
	///////////////////////////////
	public void downloadBatch(int projectID){
		System.out.println("Project ID: "+projectID);
		DownloadBatchParam param = new DownloadBatchParam(Controller.instance().getUser(), Controller.instance().getPassword(), projectID+1);
		System.out.println("Param Project ID: "+param.getProjectId());
		System.out.println("Param user: "+param.getUser());
		System.out.println("Param pass: "+param.getPassword());
		DownloadBatchResult results = Controller.instance().getComm().downLoadBatch(param);
		System.out.println("URL: "+results.getImage().getFile());
		Controller.instance().getBatchState().setResults(results);
		Controller.instance().getBatchState().setCheckedOut(true);
		
	}
	
	public Object downloadFile(String url){
		DownloadFileParam downloadParam = new DownloadFileParam(url);
		DownloadFileResult downloadResult = Controller.instance().getComm().downloadFile(downloadParam);
		Object newObject = null;
		if(url.contains("images"));{
			try {
				// convert byte array back to Object
				InputStream in = new ByteArrayInputStream(downloadResult.getByteFile());
				newObject = ImageIO.read(in);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return newObject;
	}
	
	public void SubmitBatch(){
		System.out.println("Submitting Batch");
		StringBuilder results = new StringBuilder();
		String [] []values = Controller.instance().getBatchState().getValues();
		for (String[] inner : values) {
		    for (String a : inner) {
		    	if(!(a.equals(""))){
		    		a.equals("NULLVALUE");
		    	}
		        results.append(a);
		        results.append(",");
		    }
		}
		int batch = Controller.instance().getBatchState().getResults().getImage().getId();
		
		System.out.println("Batch Submit items: "+results.toString());
		SubmitBatchParam param = new SubmitBatchParam(Controller.instance().getBatchState().getUser(), 
				Controller.instance().getBatchState().getPassword(), batch,results.toString());
		SubmitBatchResult result = Controller.instance().getComm().submitBatch(param);
		if(result.isSuccess()){
			Controller.instance().getBatchState().setCheckedOut(false);
			//Controller.instance().getBatchState().setInverted(false);
		}
		
	}
	
	public void validateUser(){
		
	}
	
	
	public void saveState(){
       	XStream xstream = new XStream(new DomDriver());
    	String xml = xstream.toXML(Controller.instance().getBatchState());
    	
        try {
            FileOutputStream fs = new FileOutputStream("users/"+Controller.instance().getBatchState().getUser());
            xstream.omitField(Controller.instance().getBatchState().getClass(), "listeners");
            xstream.toXML(Controller.instance().getBatchState(), fs);
            fs.close();
        } catch (IOException e) {
           System.out.println("File not Found: "+ e.toString());
        }
	}
	
	public void loadState(){
        XStream xs = new XStream(new DomDriver());

        try {
        	System.out.println("users/"+Controller.instance().getBatchState().getUser());
            FileInputStream fis = new FileInputStream("users/"+Controller.instance().getBatchState().getUser());
            Object newObject = (Object)xs.fromXML(fis);
            ArrayList<BatchStateListener> listeners = Controller.instance().getBatchState().getListeners();
            Controller.instance().batchState = (BatchState) newObject;
            Controller.instance().getBatchState().setListeners(listeners);
            Controller.instance().getBatchState().setCheckedOut(Controller.instance().getBatchState().isCheckedOut());
            System.out.println("Controller.instance().getBatchState().isInverted(): "+ Controller.instance().getBatchState().isInverted());
            if(Controller.instance().getBatchState().isInverted()){
            	Controller.instance().getBatchState().setInverted(true);
            }

            fis.close();


        } catch (IOException e) {
        	System.out.println("Load File not Found: "+ e.toString());
        }
	}
	

	
	////////////////////
	//Listener Methods//
	////////////////////
	private BatchStateListener l  = new BatchStateListener() {

		@Override
		public void valueChanged(Cell cell, String newValue) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			// TODO Auto-generated method stub
			
		}

/*		@Override
		public void batchDownloaded(DownloadBatchResult result) {
			// TODO Auto-generated method stub
			
		}*/

		@Override
		public void updated() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void inverted() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void submited() {
			// TODO Auto-generated method stub
			
		}
    };

}
