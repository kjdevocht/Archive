package client;



import java.util.ArrayList;

import client.listeners.BatchStateListener;

import shared.comms.*;




public class BatchState {
	private String user;
	private String password;
	private boolean checkedOut;
	private boolean inverted = false;
	private DownloadBatchResult results;
	private String[][] values;
	private Cell selectedCell;
	private ArrayList<BatchStateListener> listeners =new ArrayList<BatchStateListener>();
	private double zoomLevel = 0.45;

	public BatchState(){
		listeners = new ArrayList<BatchStateListener>();
		
	}
	

	
	public BatchState(int records, int fields) {
		//values = new String[records][fields];
		selectedCell = null;
	}
	
	public void addListener(BatchStateListener l) {
		if(listeners == null)
		{
			listeners = new ArrayList<BatchStateListener>();
		}
		listeners.add(l);
	}
	
	public void setValue(Cell cell, String value) {
		
		values[cell.record][cell.field] = value;
		
		for (BatchStateListener l : listeners) {
			l.valueChanged(cell, value);
		}
	}
	
	public void setValues(int rows, int cols){
		int realRows = rows;
		int realCols = cols+1;
		 System.out.println("Row: "+realRows+" Col: "+realCols);
		values = new String [realRows] [realCols];
		
		for(int row = 0; row < realRows; row++) {
			  for(int col = 0; col < realCols; col++){
				 
					  BatchState.this.values[row][col] = "";
				  }
			  }
		}
	
	public String[][] getValues(){
		return this.values;
	}
	
	
	public String getValue(Cell cell) {
		return values[cell.record][cell.field];
	}
	
	public void setSelectedCell(Cell selCell) {
		
		selectedCell = selCell;
		
		for (BatchStateListener l : listeners) {
			l.selectedCellChanged(selCell);
		}
	}
	
	public Cell getSelectedCell() {
		return selectedCell;
	}
	

	public boolean isCheckedOut() {
		return checkedOut;
	}

	public void setCheckedOut(boolean checkedOut) {
		this.checkedOut = checkedOut;
		if(checkedOut){
			for (int i = 0; i<listeners.size(); i++) {
				listeners.get(i).updated();
			}
		}
		else{
			for (int i = 0; i<listeners.size(); i++) {
				listeners.get(i).submited();
			}
		}
	}

	public double getZoomLevel() {
		return zoomLevel;
	}

	public void setZoomLevel(double zoomLevel) {
    	if(zoomLevel > 2.25){
    		zoomLevel = 2.25;
		}
    	if(zoomLevel < 0.25){
    		zoomLevel = 0.25;
    	}
		
		this.zoomLevel = zoomLevel;
		for (int i = 0; i<listeners.size(); i++) {
			listeners.get(i).updated();
		}
	}

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

	public DownloadBatchResult getResults() {
		return results;
	}

	public void setResults(DownloadBatchResult results) {
		this.results = results;
/*		for (BatchStateListener l : listeners) {
			l.batchDownloaded(results);
		}*/
	}

	public ArrayList<BatchStateListener> getListeners() {
		return listeners;
	}

	public void setListeners(ArrayList<BatchStateListener> listeners) {
		this.listeners = listeners;
	}

	public boolean isInverted() {
		return inverted;
	}

	public void setInverted(boolean inverted) {
		System.out.println("Listener Fired setting Invert");
		this.inverted = inverted;
		for (BatchStateListener l : listeners) {
			l.inverted();
		}
		

	}



	private BatchStateListener l  = new BatchStateListener() {

		@Override
		public void valueChanged(Cell cell, String newValue) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void selectedCellChanged(Cell newSelectedCell) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updated() {
			System.out.println("***************************************************************************\n**************************************************************************: ");
			BatchState.this.values = new String [BatchState.this.getResults().getRecordsperimage()] [BatchState.this.getResults().getFields().size()];
			
			for(int row = 0; row < BatchState.this.getResults().getRecordsperimage(); row++) {     
				BatchState.this.values[row][0] = ""+(row+1);         
				  for(int col = 0; col < row+1; col++) {
					  System.out.println("Setting to empty String");
					  if(col == 0){
						  System.out.println("Adding record number");
						  BatchState.this.values[row][col] = ""+(row+1); 
					  }
					  BatchState.this.values[row][col] = "";
				  }
			}
			
		}
		
		@Override
		public void inverted() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void submited() {
			BatchState.this.setResults(null);
			BatchState.this.checkedOut = false;
			BatchState.this.setValues(0, 0);
			
		}
    };
	
}
