package client;

import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.Comms.ClientCommunicatorImpl;
import client.dialogs.DialogValidate;
import client.frames.ClientFrame;


public class Client {

	public static void main(String[] args) {

		final String host = args[0];
		final String port = args[1];
		EventQueue.invokeLater(new Runnable() {		
			public void run() {
				ClientCommunicatorImpl comm = new ClientCommunicatorImpl();
				Controller.instance().setComm(comm);
				BatchState batchState = new BatchState();
				Controller.instance().setBatchState(batchState);
				Controller.instance().setHost(host);
				Controller.instance().setPort(port);
				Controller.instance().setServerUrl("http://" + host + ":"+ port);
				Controller.instance().getComm().setBaseUrl(host);
				Controller.instance().getComm().setPortNum(port);
				Controller.instance().getComm().setServerUrl("http://" + host + ":"+ port);
				
				// Create the frame window object
				DialogValidate dialog = new DialogValidate(host, port);


			}
		});
	}
}