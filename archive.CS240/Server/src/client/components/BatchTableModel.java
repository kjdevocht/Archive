package client.components;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import client.Cell;
import client.Controller;
import client.listeners.BatchStateListener;

import shared.model.Field;

@SuppressWarnings("serial")
public class BatchTableModel extends AbstractTableModel{
	
	private ArrayList<Field> fields = new ArrayList<Field>();
	int recordsperimage = 0;
	
	public BatchTableModel( ) {
		
		BatchTableModel.this.fields = Controller.instance().getBatchState().getResults().getFields();
		BatchTableModel.this.recordsperimage = Controller.instance().getBatchState().getResults().getRecordsperimage();
		BatchTableModel.this.fireTableDataChanged();
		Controller.instance().getBatchState().addListener(l);
	}


	@Override
	public int getRowCount() {
		return recordsperimage;
		//return 8;
	}

	@Override
	public int getColumnCount() {
		return fields.size()+1;
		//return 7;
	}
	
	@Override
	public String getColumnName(int column) {
		//System.out.println("Getting Titles: "+column);
		if(fields.size() == 0){
			return null;
		}
		if(column == 0){
			return "Record Number";
		}
		//System.out.println("Column Title: "+fields.get(column-1).getTitle());
		return fields.get(column-1).getTitle();
		//return "TITLE";
	}

	@Override
	public String getValueAt(int rowIndex, int columnIndex) {
		Cell temp = new Cell(rowIndex, columnIndex);
		
		
		if(columnIndex == 0){
			return ""+(rowIndex+1);
		}
		else;
		{
			return Controller.instance().getBatchState().getValue(temp);
		}

		
		//return "CELL";
	}
	
	@Override
	public void setValueAt(Object value, int row, int column) {
		Cell temp = new Cell(row, column);
		String sValue = (String) value;
		Controller.instance().getBatchState().setValue(temp, sValue);
			
	}
	
	@Override
	public boolean isCellEditable(int row, int column) {
		if(column == 0){
			return false;
		}
		else{
			return true;
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
			BatchTableModel.this.fields = Controller.instance().getBatchState().getResults().getFields();
			BatchTableModel.this.recordsperimage = Controller.instance().getBatchState().getResults().getRecordsperimage();
			BatchTableModel.this.fireTableDataChanged();
			BatchTableModel.this.fireTableStructureChanged();
		}

		@Override
		public void inverted() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void submited() {
			BatchTableModel.this.fields = new ArrayList<Field>();
			BatchTableModel.this.recordsperimage = 0;
			BatchTableModel.this.fireTableDataChanged();
			BatchTableModel.this.fireTableStructureChanged();
			
		}
	
	};
}
