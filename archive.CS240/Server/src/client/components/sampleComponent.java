package client.components;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

import client.Cell;
import client.Controller;
import client.listeners.BatchStateListener;

@SuppressWarnings("serial")
public class sampleComponent extends JComponent{

	@SuppressWarnings("serial")

		private static BufferedImage NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
		
		
		private BufferedImage image;
		private Rectangle2D rect;
		private double centerX = 0;
		private double centerY = 0;
		private double scale = 0.90;
		
		public BufferedImage getImage() {
			return image;
		}

		public void setImage(BufferedImage image) {
			this.image = image;
		}


		public sampleComponent(BufferedImage image) {
			if(image == null){
				this.image = NULL_IMAGE;
			}
			else{
				this.image = image;
			}
			
			this.rect = new Rectangle2D.Double(0, 20, 552, 418);
		}


		public void draw(Graphics2D g2) {
			Rectangle2D bounds = rect.getBounds2D();

			//scale = Controller.instance().getBatchState().getZoomLevel();
			/*g2.translate(this.getWidth()/2, this.getHeight()/2 );*/
			g2.scale(scale, scale);
			/*g2.translate(centerX, centerY);*/
			g2.drawImage(image, (int)bounds.getMinX(), (int)bounds.getMinY(), (int)bounds.getMaxX(), (int)bounds.getMaxY(),
							0, 0, image.getWidth(null), image.getHeight(null), null);
		}	
		

		@Override
		protected void paintComponent(Graphics g) {

			super.paintComponent(g);
			
			Graphics2D g2 = (Graphics2D)g;
			drawBackground(g2);
			draw(g2);
		}
		
		private void drawBackground(Graphics2D g2) {
			g2.setColor(getBackground());
			g2.fillRect(0,  0, getWidth(), getHeight());
		}
	}