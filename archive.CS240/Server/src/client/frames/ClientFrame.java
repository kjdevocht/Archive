package client.frames;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.*;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import client.Cell;
import client.Controller;
import client.Comms.ClientCommunicatorImpl;
import client.components.BatchComponent;
import client.components.BatchTable;
import client.dialogs.DialogDownload;
import client.dialogs.DialogValidate;
import client.listeners.BatchStateListener;
import client.listeners.validateUserListener;
import shared.comms.DownloadBatchResult;
import shared.comms.DownloadFileParam;
import shared.comms.DownloadFileResult;
import shared.comms.GetProjectsParam;
import shared.comms.GetProjectsResult;
import shared.comms.ValidateUserParam;
import shared.comms.ValidateUserResult;

@SuppressWarnings("serial")
public class ClientFrame extends JFrame{
	
	private JMenuItem logoutMenuItem;
	private JMenuItem downloadMenuItem;
	private JMenuItem exitMenuItem;
	private BatchComponent batchImage;
	private JButton zoomInButton;
	private JButton zoomOutButton;
	private JButton invertButton;
	private JButton toggleButton;
	private JButton saveButton;
	private JButton submitButton;
	private BatchTable entryTable;
	private JDialog parent;
	private JEditorPane help;
	
	private ArrayList<validateUserListener> listeners =new ArrayList<validateUserListener>();


	public ClientFrame(JDialog parent0, String host, String port) {
		super();
		
		parent = parent0;
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		

		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();

		this.setPreferredSize(new Dimension(900, 700));

		// Set the window's title
		this.setTitle("Record Indexer");

		// Specify what should happen when the user clicks the window's close
		// icon
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		//addWindowListener(windowAdapter);
		
		//Create Menu
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu menu = new JMenu("File");
        menuBar.add(menu);
        

        downloadMenuItem = new JMenuItem("Download Batch", KeyEvent.VK_B);
        downloadMenuItem.addActionListener(actionListener);
        menu.add(downloadMenuItem);

        logoutMenuItem = new JMenuItem("Logout", KeyEvent.VK_L);
        logoutMenuItem.addActionListener(actionListener);
        menu.add(logoutMenuItem);

        exitMenuItem = new JMenuItem("Exit", KeyEvent.VK_X);
        exitMenuItem.addActionListener(actionListener);
        menu.add(exitMenuItem);
		
		//Create Panels
		JPanel buttonPanel=new JPanel();
		buttonPanel.setPreferredSize(new Dimension(screenSize.width/2,40));
		JPanel batchPanel=new JPanel();
		batchPanel.setPreferredSize(new Dimension(screenSize.width/4, 450));
		
		//DrawingFrame batchFrame = new DrawingFrame(this);
		batchImage = new BatchComponent(null);
		batchImage.setPreferredSize(new Dimension(600, 600));
		batchPanel.setLayout(new BorderLayout());
		batchPanel.add(batchImage);
		
		//Create buttons
		zoomInButton = new JButton("Zoom In");
		zoomInButton.addActionListener(actionListener);
		
		zoomOutButton = new JButton("Zoom Out");
		zoomOutButton.addActionListener(actionListener);
		
		invertButton = new JButton("Invert Image");
		invertButton.addActionListener(actionListener);
		
		toggleButton = new JButton("Toggle Highlights");
		toggleButton.addActionListener(actionListener);
		
		saveButton = new JButton("Save");
		saveButton.addActionListener(actionListener);
		
		submitButton = new JButton("Submit");
		submitButton.addActionListener(actionListener);
		
		//Add buttons to panel
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		buttonPanel.add(zoomInButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		buttonPanel.add(zoomOutButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 5)));;
		buttonPanel.add(invertButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		buttonPanel.add(toggleButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		buttonPanel.add(saveButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		buttonPanel.add(submitButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		
		//Create Table and Form Pane
		entryTable = new BatchTable();
		entryTable.setPreferredSize(new Dimension(400,1));
		JTabbedPane tableFormPane = new JTabbedPane();
		//tableFormPane.setPreferredSize(new Dimension(400,1));
		tableFormPane.setTabPlacement(JTabbedPane.TOP);
		tableFormPane.addTab("Table Entry", entryTable);
		tableFormPane.addTab("Form Entry", new JLabel("Hello"));
		
		//Create Help Field and Image Navigator Pane
		help = new JEditorPane();
		JTabbedPane fieldImagePane = new JTabbedPane();
		fieldImagePane.setPreferredSize(new Dimension(1,1));
		fieldImagePane.setTabPlacement(JTabbedPane.TOP);
		fieldImagePane.addTab("Field Help", new JScrollPane(help));
		fieldImagePane.addTab("Image Navigation", new JLabel("Image"));
		
		//Start adding things together
		JSplitPane infoPanel=new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,tableFormPane ,fieldImagePane);
		infoPanel.setPreferredSize(new Dimension(1, 1));
		JSplitPane mainBody = new JSplitPane(JSplitPane.VERTICAL_SPLIT, batchPanel, infoPanel);
		mainBody.resetToPreferredSizes();
		//mainBody.setPreferredSize(new Dimension(screenSize.width/4, 660));


		// Add subcomponents to the window
		this.add(buttonPanel, BorderLayout.NORTH);
		this.add(mainBody, BorderLayout.CENTER);

		// Set the location of the window on the desktop
		this.setLocation(100, 100);

		// Size the window according to the preferred sizes and layout of its
		// subcomponents
		this.pack();
		
		//Add listeners
		Controller.instance().getBatchState().addListener(l);
		this.addWindowListener(listener);
		//validateUser();
	}
	
	
	public void addListener(validateUserListener l) {
		if(listeners == null)
		{
			listeners = new ArrayList<validateUserListener>();
		}
		listeners.add(l);
	}
	
    private ActionListener actionListener = new ActionListener() {
    	
	    public void actionPerformed(ActionEvent e) {
	        if (e.getSource() == logoutMenuItem) {
	        	System.out.println("Num of listeners: "+ClientFrame.this.listeners.size());
	        	Controller.instance().saveState();
	        	ClientFrame.this.batchImage.setImage(null);
	        	ClientFrame.this.setVisible(false);
	        	
	    		for (validateUserListener l : ClientFrame.this.listeners) {
	    			l.becameVisible();
	    		}
	    		ClientFrame.this.parent.setVisible(true);
	        }
	        else if (e.getSource() == downloadMenuItem) {
	        	downloadBatch();
	        	
	        }
	        else if (e.getSource() == exitMenuItem) {
	        	System.out.println("Saving state before exiting");
	        	Controller.instance().saveState();
	            System.exit(0);
	        }
	        else if (e.getSource() == saveButton) {
	        	System.out.println("Saving state but not Exiting");
	        	Controller.instance().saveState();
	        }
	        else if (e.getSource() == zoomInButton) {
	        	double scale = Controller.instance().getBatchState().getZoomLevel() +0.3;
/*	        	if(scale > 2.25){
	    			scale = 2.25;
	    		}*/
	        	Controller.instance().getBatchState().setZoomLevel(scale);
	        }
	        else if (e.getSource() == zoomOutButton) {
	        	double scale = Controller.instance().getBatchState().getZoomLevel()-0.3;
/*	    		if(scale < 0.25){
	    			scale = 0.25;
	    		}*/
	        	Controller.instance().getBatchState().setZoomLevel(scale);
	        }
	        else if (e.getSource() == invertButton) {

	        	if(Controller.instance().getBatchState().isInverted()){
	        		Controller.instance().getBatchState().setInverted(false);
	        	}
	        	else{
	        		Controller.instance().getBatchState().setInverted(true);
	        	}
	        	
	        }
	        else if (e.getSource() == submitButton) {

	        	if(!(Controller.instance().getBatchState().isCheckedOut())){
	        		JOptionPane.showMessageDialog(ClientFrame.this,
	        			    "You cannot submit if you don't download a batch",
	        			    "Cannot Submit",
	        			    JOptionPane.ERROR_MESSAGE);
	        	}
	        	else{
	        		Controller.instance().SubmitBatch();
	        	}
	        	
	        }
	    }
    };
    
    private validateUserListener v  = new validateUserListener(){

		@Override
		public void becameVisible() {
			// TODO Auto-generated method stub
			
		}
    	
    };

    private void downloadBatch(){
		GetProjectsParam paramProjects = new GetProjectsParam(Controller.instance().getUser(), Controller.instance().getPassword());
		GetProjectsResult resultProjects = Controller.instance().getComm().getProjects(paramProjects);
		DialogDownload downDia = new DialogDownload(this, resultProjects);
		downDia.setLocationRelativeTo(this);
		downDia.setVisible(true);
    }
    
/*	private void validateUser(){
		DialogValidate valDia = new DialogValidate(this);
		valDia.setLocationRelativeTo(this);
		valDia.setVisible(true);


	}*/

	/////////////
	//Listeners//
	/////////////
	private BatchStateListener l  = new BatchStateListener() {

		@Override
		public void valueChanged(Cell cell, String newValue) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			System.out.println("Getting help file");
			int col = newSelectedCell.getField()-1;
			String url = Controller.instance().getBatchState().getResults().getFields().get(col).getHelpHTML();
			String serverUrl = Controller.instance().getServerUrl()+"/Download/"+url;
/*			DownloadFileParam param = new DownloadFileParam(url);
			DownloadFileResult result = Controller.instance().getComm().downloadFile(param);*/
			
			  try {
				  System.out.println("Help url: "+serverUrl);
				  ClientFrame.this.help.setContentType("text/html");
				ClientFrame.this.help.setPage(serverUrl);
				ClientFrame.this.help.repaint();
				ClientFrame.this.help.revalidate();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		@Override
		public void updated() {
			if(Controller.instance().getBatchState().getResults() != null){
				downloadMenuItem.setEnabled(false);
				

				
			}
			
		}

		@Override
		public void inverted() {
			// TODO Auto-generated method stub
			
		}
		
		@Override
		public void submited() {
			downloadMenuItem.setEnabled(true);
			ClientFrame.this.help = new JEditorPane();
			ClientFrame.this.help.repaint();
			ClientFrame.this.help.revalidate();
			
		}
    };
    
    
    private static final WindowAdapter listener = new WindowAdapter() {

        @Override
        public void windowClosed(WindowEvent e) {
 
        }

        @Override
        public void windowClosing(WindowEvent e) {
        	System.out.println("Window Closing");
        	Controller.instance().saveState();
        }

        @Override
        public void windowDeactivated(WindowEvent e) {

        }
    };  
}