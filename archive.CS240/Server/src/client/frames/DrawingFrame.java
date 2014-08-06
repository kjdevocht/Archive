package client.frames;



import java.awt.*;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import client.components.DrawingComponent;
import client.listeners.DrawingListener;


@SuppressWarnings("serial")
public class DrawingFrame extends JPanel {

	private DrawingComponent component;
	private JSlider slider;
	
	public DrawingFrame(Frame frame) {
		
		component = new DrawingComponent();
		this.add(component, BorderLayout.CENTER);
		
		//slider = new JSlider(1, 100, 20);
		//slider.addChangeListener(sliderChangeListener);
		//this.add(slider, BorderLayout.SOUTH);
		
		this.setLocation(100, 100);

	}
	

	
}