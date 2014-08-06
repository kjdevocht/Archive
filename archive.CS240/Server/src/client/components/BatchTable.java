package client.components;

import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;

import client.BatchState;
import client.Cell;
import client.Controller;
import client.listeners.BatchStateListener;

import shared.model.Field;

@SuppressWarnings("serial")
public class BatchTable extends JPanel{
	private BatchTableModel tableModel;
	private JTable table;
	private ArrayList<Field> fields = new ArrayList<Field>();
	private JScrollPane scroll;
	
	public BatchTable() throws HeadlessException {
		Controller.instance().getBatchState().addListener(l);
		//table.addMouseListener(mouseAdapter);

		//TableColumnModel columnModel = table.getColumnModel();	
		
		
/*		for (int i = 0; i < tableModel.getColumnCount(); ++i) {
			TableColumn column = columnModel.getColumn(i);
			column.setPreferredWidth(150);
		}*/
	}
	
	
/*	private MouseAdapter mouseAdapter = new MouseAdapter() {

		@Override
		public void mouseReleased(MouseEvent e) {

			if (e.isPopupTrigger()) {
				
				final int row = table.rowAtPoint(e.getPoint());
				final int column = table.columnAtPoint(e.getPoint());
				
				if (row >= 0 && row < tableModel.getRowCount() &&
						column >= 1 && column < tableModel.getColumnCount()) {
										
					final JColorChooser colorChooser = new JColorChooser();
					
					ActionListener okListener = new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e2) {							
							Color newColor = colorChooser.getColor();						
							tableModel.setValueAt(ColorUtils.toString(newColor), row, column);
							tableModel.fireTableCellUpdated(row, column);
						}					
					};
					
					JDialog dialog = JColorChooser.createDialog(table, "Pick a Color", true, colorChooser, okListener, null);
					dialog.setVisible(true);
				}
			}
		}
		
	};*/
	

	
	
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
			if (Controller.instance().getBatchState().getResults() != null && BatchTable.this.table == null){
				int rows = Controller.instance().getBatchState().getResults().getRecordsperimage();
				int cols = Controller.instance().getBatchState().getResults().getFields().size();
				Controller.instance().getBatchState().setValues(rows, cols);
	
				tableModel = new BatchTableModel();
				BatchTable.this.table = new JTable(tableModel);
				//BatchTable.this.table.setPreferredSize(new Dimension(300,200));
				BatchTable.this.table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
				BatchTable.this.table.getSelectionModel().addListSelectionListener(s);
				BatchTable.this.table.setCellSelectionEnabled(true);
				BatchTable.this.table.getTableHeader().setReorderingAllowed(false);
				scroll = new JScrollPane(table);
				BatchTable.this.add(scroll);
				BatchTable.this.revalidate();
			}
			else
			{
				//BatchTable.this.setVisible(true);
			}
			
		}

		@Override
		public void inverted() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void submited() {
			if(Controller.instance().getBatchState().isCheckedOut()){
/*				System.out.println("Setting header to null");
				BatchTable.this.table.setTableHeader(null);
				BatchTable.this.table.repaint();
				BatchTable.this.table.revalidate();
				BatchTable.this.repaint();
				BatchTable.this.revalidate();*/
			}
			if(BatchTable.this.table != null){
				BatchTable.this.table = null;
				BatchTable.this.remove(scroll);
				
				BatchTable.this.revalidate();
				BatchTable.this.repaint();
				BatchTable.this.getParent().revalidate();
				BatchTable.this.getParent().repaint();
			}

			

		}
	
	};
	
	
	
	private ListSelectionListener s  = new ListSelectionListener() {
		

		@Override
		public void valueChanged(ListSelectionEvent e) {
			System.out.println("******************************\nListSelectionEvent************************************************\n "+e.toString());
			if(BatchTable.this.table !=null){
				int rowIndex = BatchTable.this.table.getSelectedRow();
				int colIndex = BatchTable.this.table.getSelectedColumn();
				Cell temp = new Cell(rowIndex, colIndex);
				Controller.instance().getBatchState().setSelectedCell(temp);	
			}

			
		}
	};
}
