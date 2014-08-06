package client.dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import client.BatchState;
import client.Cell;
import client.Controller;
import client.frames.ClientFrame;
import client.listeners.BatchStateListener;
import client.listeners.validateUserListener;

import shared.comms.DownloadBatchResult;
import shared.comms.ValidateUserParam;
import shared.comms.ValidateUserResult;

@SuppressWarnings("serial")
public class DialogValidate extends JDialog{
	
	JLabel username;
	JLabel password;
	JTextField textField;
	JPasswordField passwordField;
	JButton loginButton;
	JButton exitButton;
	ClientFrame frame;
	String host;
	String port;
	
	
	public DialogValidate(String host, String port){
		
		this.setLocationRelativeTo(null);
		this.host = host;
		this.port = port;
		
		System.out.println("URL(3): "+Controller.instance().getComm().getServerUrl());
		this.setModal(true);
		this.setTitle("Login to Indexer");
		this.setResizable(false);
		
		//Text fields and labels
		username=new JLabel("Username:   ");
		password=new JLabel("Password:   ");
		textField=new JTextField(25);
		passwordField=new JPasswordField(25);
		
		//Buttons
		loginButton = new JButton("Login");
		loginButton.addActionListener(actionListener);
		exitButton = new JButton("Exit");
		exitButton.addActionListener(actionListener);
		
		//Panels
		JPanel userPanel=new JPanel();
		userPanel.setLayout(new BoxLayout(userPanel, BoxLayout.X_AXIS));
		
		JPanel passwordPanel=new JPanel();
		passwordPanel.setLayout(new BoxLayout(passwordPanel, BoxLayout.X_AXIS));
		
		
		JPanel buttonPanel=new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		
		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
		
		//Add everything together
		userPanel.add(username);
		userPanel.add(textField);
		
		passwordPanel.add(password);
		passwordPanel.add(passwordField);
		
		buttonPanel.add(loginButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		buttonPanel.add(exitButton);
		
		
		rootPanel.add(userPanel);
		rootPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		rootPanel.add(passwordPanel);
		rootPanel.add(Box.createRigidArea(new Dimension(5, 5)));
		rootPanel.add(buttonPanel);
		rootPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		this.add(rootPanel);
		
		this.pack();
		this.setVisible(true);
		
	}
	
	
	
	
	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			System.out.println("ActionEvent: "+e.toString());
			if (e.getSource() == loginButton) {
				 Controller.instance().setUser(textField.getText());
				 Controller.instance().setPassword(new String(passwordField.getPassword()));
				 ValidateUserParam userParam = new ValidateUserParam(textField.getText(), new String(passwordField.getPassword()));
				 ValidateUserResult result = null;
				 System.out.println("URL: "+Controller.instance().getComm().getServerUrl());
				 result = Controller.instance().getComm().validateUser(userParam);
				  if(result.getStatus().equals("SUCCESS"))  
				  {  
					  frame = new ClientFrame(DialogValidate.this, host, port);
					  DialogValidate.this.frame.addListener(v);
					  JOptionPane.showMessageDialog(DialogValidate.this, 
							  "Welcome "+result.getUserFirstName()+" "+result.getUserLastName()+"\n You have indexed "+result.getNumRecords()+" records.",
							  "Welcome to Indexer",JOptionPane.INFORMATION_MESSAGE);

					  File f = new File("users/"+userParam.getUser());
					  System.out.println("DialogValidate: "+"users/"+userParam.getUser());
					  Controller.instance().batchState.setUser(userParam.getUser());
					  Controller.instance().batchState.setPassword(userParam.getPassword());
					  if(f.exists()){
						  System.out.println("File existed");
						  Controller.instance().loadState();
					  }

					  DialogValidate.this.setVisible(false);
					  DialogValidate.this.frame.setVisible(true);
				  }  
				  else  
				  {  
					   JOptionPane.showMessageDialog(DialogValidate.this,"Invaild username and/or password","Login Failed",JOptionPane.ERROR_MESSAGE);
					   passwordField.setText("");

				  }
			}
			else if(e.getSource() == exitButton) {
				System.exit(0);
			}
		}
	};
	
    private validateUserListener v  = new validateUserListener(){

		@Override
		public void becameVisible() {
			System.out.println("Became Visible");
			DialogValidate.this.passwordField.setText("");
			DialogValidate.this.textField.setText("");
		}
    	
    };

}
