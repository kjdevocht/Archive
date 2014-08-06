package client.components;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.geom.*;
import java.awt.image.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import shared.comms.DownloadBatchResult;

import client.Cell;
import client.Controller;
import client.components.DrawingComponent.DrawingShape;
import client.listeners.BatchStateListener;
import client.listeners.DrawingListener;



@SuppressWarnings("serial")
public class BatchComponent extends JComponent{

	private static BufferedImage NULL_IMAGE = new BufferedImage(10, 10, BufferedImage.TYPE_INT_ARGB);
	
	
	private BufferedImage image;
	private DrawingImage batch;
	private Rectangle2D rect;
	private int w_originX = 0;
	private int w_originY = 0;
	private double scale = 0.45;
	
	private boolean dragging;
	private int w_dragStartX;
	private int w_dragStartY;
	private int w_dragStartOriginX;
	private int w_dragStartOriginY;
	

	//private ArrayList<DrawingShape> shapes;

	
	private ArrayList<DrawingListener> listeners;
	

	
	public BufferedImage getImage() {
		return image;
	}

	public void setImage(BufferedImage image) {
		this.image = image;
	}


	public BatchComponent(BufferedImage image) {
		
		initDrag();
		Controller.instance().batchState.addListener(l);
		listeners = new ArrayList<DrawingListener>();
		this.addMouseListener(mouseAdapter);
		this.addMouseMotionListener(mouseAdapter);
		this.addMouseWheelListener(mouseAdapter);
		
		if(image == null){
			this.image = NULL_IMAGE;
		}
		else{
			this.image = image;
		}
		
		
		this.rect = new Rectangle2D.Double(0, 0, this.image.getWidth(null), this.image.getHeight(null));
		batch = new DrawingImage(this.image, rect);
	}
	
	private void initDrag() {
		dragging = false;
		w_dragStartX = 0;
		w_dragStartY = 0;
		w_dragStartOriginX = 0;
		w_dragStartOriginY = 0;
	}
	
	public void setScale(double newScale) {
		scale = newScale;
		this.repaint();
	}
	
	public void setOrigin(int w_newOriginX, int w_newOriginY) {
		w_originX = w_newOriginX;
		w_originY = w_newOriginY;
		this.repaint();
	}
	
	public void addDrawingListener(DrawingListener listener) {
		listeners.add(listener);
	}
	
	
	private void notifyOriginChanged(int w_newOriginX, int w_newOriginY) {
		for (DrawingListener listener : listeners) {
			listener.originChanged(w_newOriginX, w_newOriginY);
		}
	}
	


/*	public void draw(Graphics2D g2) {
		Rectangle2D bounds = rect.getBounds2D();

		scale = Controller.instance().getBatchState().getZoomLevel();
		g2.translate(this.getWidth()/2, this.getHeight()/2 );
		g2.scale(scale, scale);
		g2.translate(w_originX, w_originY);
		g2.drawImage(batch, (int)bounds.getMinX(), (int)bounds.getMinY(), (int)bounds.getMaxX(), (int)bounds.getMaxY(),
						0, 0, image.getWidth(null), image.getHeight(null), null);
	}*/
	

	@Override
	protected void paintComponent(Graphics g) {

		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		drawBackground(g2);
		batch.draw(g2);
	}
	
	private void drawBackground(Graphics2D g2) {
		g2.setColor(getBackground());
		g2.fillRect(0,  0, getWidth(), getHeight());
	}
	
	
    public  void invertImage() {

        for (int x = 0; x < batch.image.getWidth(); x++) {
            for (int y = 0; y < batch.image.getHeight(); y++) {
                int rgba = batch.image.getRGB(x, y);
                Color col = new Color(rgba, true);
                col = new Color(255 - col.getRed(),
                                255 - col.getGreen(),
                                255 - col.getBlue());
                batch.image.setRGB(x, y, col.getRGB());
            }
        }
    }
	
    /////////////
    //Listeners//
    /////////////
	private BatchStateListener l  = new BatchStateListener() {

		@Override
		public void valueChanged(Cell cell, String newValue) {
			
		}

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			
		}

		@Override
		public void updated() {
			if(Controller.instance().getBatchState().getResults() == null){
				System.out.println("Result is null");
			}
			if(BatchComponent.this.batch.image == null)
			{
				System.out.println("Batch image is null");
				
			}
			if(BatchComponent.this.batch.image == NULL_IMAGE)
			{
				System.out.println("Batch image is null");
				
			}
			
			if(Controller.instance().getBatchState().getResults() != null && (BatchComponent.this.batch.image == null || BatchComponent.this.batch.image == NULL_IMAGE)){
				System.out.println("Are you resetting?");
				BatchComponent.this.batch.image = (BufferedImage) Controller.instance().downloadFile(Controller.instance().
						getBatchState().getResults().getImage().getFile());
				BatchComponent.this.batch.rect = new Rectangle2D.Double(0,0, BatchComponent.this.batch.image.getWidth(null), BatchComponent.this.batch.image.getHeight(null));
				w_originX = (BatchComponent.this.batch.image.getWidth()/2);
				w_originY = (BatchComponent.this.batch.image.getHeight()/2);
			}
			BatchComponent.this.repaint();
		}

		@Override
		public void inverted() {
			System.out.println("Inverterd Fired");
			BatchComponent.this.invertImage();
			BatchComponent.this.repaint();
		}

		@Override
		public void submited() {
			BatchComponent.this.batch.image = NULL_IMAGE;
			BatchComponent.this.image = NULL_IMAGE;
			BatchComponent.this.repaint();
			
		}
    };
    
    private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mousePressed(MouseEvent e) {
			int d_X = e.getX();
			int d_Y = e.getY();
			
			AffineTransform transform = new AffineTransform();
			transform.translate(BatchComponent.this.getWidth()/2, BatchComponent.this.getHeight()/2 );
			transform.scale(scale, scale);
			transform.translate(-w_originX, -w_originY);
			
			Point2D d_Pt = new Point2D.Double(d_X, d_Y);
			Point2D w_Pt = new Point2D.Double();
			try
			{
				transform.inverseTransform(d_Pt, w_Pt);
			}
			catch (NoninvertibleTransformException ex) {
				return;
			}
			int w_X = (int)w_Pt.getX();
			int w_Y = (int)w_Pt.getY();
			
			boolean hitShape = false;
			
			Graphics2D g2 = (Graphics2D)getGraphics();

			if (batch.contains(g2, w_Pt.getX(), w_Pt.getY())) {
				hitShape = true;
			}

			
			if (hitShape) {
				dragging = true;		
				w_dragStartX = w_X;
				w_dragStartY = w_Y;		
				w_dragStartOriginX = w_originX;
				w_dragStartOriginY = w_originY;
			}
		}

		@Override
		public void mouseDragged(MouseEvent e) {		
			if (dragging) {
				int d_X = e.getX();
				int d_Y = e.getY();
				
				AffineTransform transform = new AffineTransform();
				transform.translate(BatchComponent.this.getWidth()/2, BatchComponent.this.getHeight()/2 );
				transform.scale(scale, scale);
				transform.translate(-w_dragStartOriginX, -w_dragStartOriginY);
				
				Point2D d_Pt = new Point2D.Double(d_X, d_Y);
				Point2D w_Pt = new Point2D.Double();
				try
				{
					transform.inverseTransform(d_Pt, w_Pt);
				}
				catch (NoninvertibleTransformException ex) {
					return;
				}
				int w_X = (int)w_Pt.getX();
				int w_Y = (int)w_Pt.getY();
				
				int w_deltaX = w_X - w_dragStartX;
				int w_deltaY = w_Y - w_dragStartY;
				
				w_originX = w_dragStartOriginX - w_deltaX;
				w_originY = w_dragStartOriginY - w_deltaY;
				
				notifyOriginChanged(w_originX, w_originY);
				
				repaint();
			}
		}

		@Override
		public void mouseReleased(MouseEvent e) {
			initDrag();
			//dragging = false;
		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			
			double clicks = e.getPreciseWheelRotation();
			double scaleClicks = clicks/10;
			scaleClicks = scaleClicks*3;

			double scale  = Controller.instance().getBatchState().getZoomLevel();
			Controller.instance().getBatchState().setZoomLevel(scaleClicks+scale);
			BatchComponent.this.repaint();
			return;
		}	
	};
	
	interface DrawingShape {
		boolean contains(Graphics2D g2, double x, double y);
		void draw(Graphics2D g2);
		Rectangle2D getBounds(Graphics2D g2);
	}
	
	class DrawingImage implements DrawingShape {

		 public BufferedImage image;
		 public Rectangle2D rect;
		
		public DrawingImage(BufferedImage image, Rectangle2D rect) {
			this.image = image;
			this.rect = rect;
		}

		@Override
		public boolean contains(Graphics2D g2, double x, double y) {

			rect = getBounds(g2);
			return rect.contains(x, y);
		}

		@Override
		public void draw(Graphics2D g2) {

			scale = Controller.instance().getBatchState().getZoomLevel();
	

			g2.translate(BatchComponent.this.getWidth()/2, BatchComponent.this.getHeight()/2 );
			g2.scale(scale, scale);
			g2.translate(-w_originX, -w_originY);
			g2.drawImage(image, (int)rect.getMinX(), (int)rect.getMinY(), (int)rect.getMaxX(), (int)rect.getMaxY(),
							0, 0, image.getWidth(null), image.getHeight(null), null);	
		}	
		
		@Override
		public Rectangle2D getBounds(Graphics2D g2) {
			return rect.getBounds2D();
		}
	
	}
}
	



