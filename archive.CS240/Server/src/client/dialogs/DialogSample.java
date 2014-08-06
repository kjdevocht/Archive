package client.dialogs;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;

import shared.comms.GetSampleImageParam;
import shared.comms.GetSampleImageResult;

import client.Controller;
import client.components.sampleComponent;

@SuppressWarnings("serial")
public class DialogSample extends JDialog{
	
	private JButton okButton;
	
	public DialogSample(Frame frame, int projectID){
		super();
		this.setModal(true);
		this.setResizable(false);
		//this.setSize(new Dimension(600,600));
		okButton = new JButton("Close");
		okButton.addActionListener(actionListener);
		
		GetSampleImageParam param = new GetSampleImageParam(Controller.instance().getUser(), Controller.instance().getPassword(), projectID+1);
		GetSampleImageResult results = Controller.instance().getComm().getSampleImage(param);
		BufferedImage image = (BufferedImage) Controller.instance().downloadFile(results.getImageURl());
		sampleComponent sampleComp = new sampleComponent(image);
		sampleComp.setPreferredSize(new Dimension(500,400)); 
		JPanel samplePanel = new JPanel();
		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
		buttonPanel.add(Box.createRigidArea(new Dimension(185, 5)));
		buttonPanel.add(okButton);
		buttonPanel.add(Box.createRigidArea(new Dimension(185, 5)));
		samplePanel.setPreferredSize(new Dimension(500,400));
		samplePanel.setLayout(new BoxLayout(samplePanel, BoxLayout.Y_AXIS));
		samplePanel.add(sampleComp);
		samplePanel.add(Box.createRigidArea(new Dimension(5, 5)));
		samplePanel.add(buttonPanel);
		
		this.add(samplePanel);
		this.pack();
	}
	
	
	private ActionListener actionListener = new ActionListener() {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == okButton) {
				DialogSample.this.setVisible(false);
			}
		}
	};
	

}
