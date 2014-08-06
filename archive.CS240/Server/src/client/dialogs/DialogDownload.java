package client.dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;

import javax.swing.JLabel;
import javax.swing.JPanel;

import shared.comms.GetProjectsResult;
import shared.model.Project;
import client.Controller;
import client.Comms.ClientCommunicatorImpl;

@SuppressWarnings("serial")
public class DialogDownload extends JDialog{
	
	private JButton downloadButton;
	private JButton cancelButton;
	private JButton sampleButton;
	private JComboBox<String> projectsCombo;

	
	public DialogDownload(Frame frame, GetProjectsResult resultProjects){
		
		super(frame, true);
		
		

		//Download Panel
		this.setResizable(false);
		this.setTitle("Download Batch");
		JLabel projectLabel = new JLabel("Project: ");
		

		String [] projects = new String[resultProjects.getProjects().size()];
		int i = 0;
		for(Project temp : resultProjects.getProjects()){
			projects[i] = temp.getTitle();
			i++;
		}
		projectsCombo = new JComboBox<String>(projects);
		
		sampleButton = new JButton("View Sample");
		sampleButton.addActionListener(actionListener);
		
		
		JPanel downloadPanel = new JPanel();
		downloadPanel.setLayout(new BoxLayout(downloadPanel, BoxLayout.X_AXIS));
		
		downloadPanel.add(projectLabel);
		downloadPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		downloadPanel.add(projectsCombo);
		downloadPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		downloadPanel.add(sampleButton);

		
		//Button Panel
		downloadButton = new JButton("Download");
		downloadButton.addActionListener(actionListener);
		
		cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(actionListener);
		
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		
		buttonPanel.add(Box.createRigidArea(new Dimension(45, 0)));
		//buttonPanel.add(Box.createHorizontalGlue());
		buttonPanel.add(cancelButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(5, 0)));
		buttonPanel.add(downloadButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(65, 0)));
		
		// Root panel
		
		JPanel rootPanel = new JPanel();
		rootPanel.setLayout(new BoxLayout(rootPanel, BoxLayout.Y_AXIS));
		
		rootPanel.add(downloadPanel);
		rootPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		rootPanel.add(buttonPanel);
		rootPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		this.add(rootPanel);
		
		this.pack();
		
	}
	
	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == cancelButton) {
				DialogDownload.this.setVisible(false);
			}
			else if(e.getSource() == downloadButton) {
				int projectID = projectsCombo.getSelectedIndex();
				DialogDownload.this.setVisible(false);
				Controller.instance().downloadBatch(projectID);
			}			
			else if(e.getSource() == sampleButton) {
				DialogSample sample = new DialogSample((Frame) DialogDownload.this.getParent(), projectsCombo.getSelectedIndex());
				sample.setVisible(true);
			}
		}
	};
}
