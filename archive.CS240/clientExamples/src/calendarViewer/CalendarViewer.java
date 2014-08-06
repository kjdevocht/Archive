package calendarViewer;


import java.util.*;
import java.text.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;


@SuppressWarnings("serial")
public class CalendarViewer extends JFrame implements ActionListener {

    class CalendarTableModel extends AbstractTableModel {

        private String[] dayNames;
    
        public CalendarTableModel() {
            dayNames = getDayNames();
        }

        public int getColumnCount() {
            return 7;
        }

        public int getRowCount() {
            int requiredSquares = getLastDayOfMonth() + getFirstDayOfWeek() - 1;
            int requiredRows = requiredSquares / 7;
            if (requiredSquares % 7 != 0) {
                ++requiredRows;
            }
            return requiredRows;
        }

        public String getColumnName(int col) {
            return dayNames[col];
        }

        public Object getValueAt(int row, int col) {
            int dayOfMonth = row * 7 + col - getFirstDayOfWeek() + 2;
            if (dayOfMonth < 1 || dayOfMonth > getLastDayOfMonth()) {
                return "";
            }
            else {
                return new Integer(dayOfMonth).toString();
            }
        }

        public Class<?> getColumnClass(int col) {
            return String.class;
        }

        public boolean isCellEditable(int row, int col) {
            return false;
        }

        public void setValueAt(Object value, int row, int col) {
            throw new IllegalStateException();
        }

        private String[] getDayNames() {
            String[] dayNames = new String[7];
            SimpleDateFormat fmt = new SimpleDateFormat("EEEE");
            Calendar cal = Calendar.getInstance();

            cal.set(Calendar.DAY_OF_MONTH, 1);
            while (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
                cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
            }

            for (int i=0; i < 7; ++i) {
                cal.set(Calendar.DAY_OF_MONTH, cal.get(Calendar.DAY_OF_MONTH) + 1);
                dayNames[i] = fmt.format(cal.getTime());
            }

            return dayNames;
        }

        private int getFirstDayOfWeek() {
            return calendar.get(Calendar.DAY_OF_WEEK);
        }

        private int getLastDayOfMonth() {
            return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
        }
    }


    Calendar calendar;
    DateFormat dateFormatter;

    CalendarTableModel tableModel;

    JLabel monthLabel;
    JTable table;
    JButton prevButton;
    JButton nextButton;


    public CalendarViewer() {
        super("Calendar Viewer");

        calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        dateFormatter = new SimpleDateFormat("MMMM yyyy");

        createComponents();
    }

    private void createComponents() {
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        monthLabel = new JLabel(dateFormatter.format(calendar.getTime()));
        monthLabel.setFont(new Font(null, Font.PLAIN, 36));
        monthLabel.setVerticalAlignment(JLabel.CENTER);
        monthLabel.setHorizontalAlignment(JLabel.CENTER);

        tableModel = new CalendarTableModel();
        table = new JTable((TableModel)tableModel);
        table.setPreferredScrollableViewportSize(new Dimension(700, 600));
        table.setDefaultRenderer(String.class, new CalendarDayRenderer());
        table.setRowHeight(100);
        TableColumnModel tableColumnModel = table.getColumnModel();
        for (int i=0; i < tableColumnModel.getColumnCount(); ++i) {
            TableColumn tableColumn = tableColumnModel.getColumn(i);
            tableColumn.setPreferredWidth(100);
        }
        
        JScrollPane tableScrollPane = new JScrollPane(table);

        prevButton = new JButton("<<");
        prevButton.addActionListener(this);

        nextButton = new JButton(">>");
        nextButton.addActionListener(this);

        JPanel btnPanel = new JPanel();
        btnPanel.add(prevButton);
        btnPanel.add(nextButton);

        getContentPane().add(monthLabel, BorderLayout.NORTH);
        getContentPane().add(tableScrollPane, BorderLayout.CENTER);
        getContentPane().add(btnPanel, BorderLayout.SOUTH);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == prevButton) {
            int curMonth = calendar.get(Calendar.MONTH);
            if (curMonth == Calendar.JANUARY) {
                int curYear = calendar.get(Calendar.YEAR);
                calendar.set(Calendar.YEAR, curYear - 1);
                calendar.set(Calendar.MONTH, Calendar.DECEMBER);
            }
            else {
                calendar.set(Calendar.MONTH, curMonth - 1);
            }
            monthLabel.setText(dateFormatter.format(calendar.getTime()));
            tableModel.fireTableDataChanged();
        }
        else if (e.getSource() == nextButton) {
            int curMonth = calendar.get(Calendar.MONTH);
            if (curMonth == Calendar.DECEMBER) {
                int curYear = calendar.get(Calendar.YEAR);
                calendar.set(Calendar.YEAR, curYear + 1);
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
            }
            else {
                calendar.set(Calendar.MONTH, curMonth + 1);
            }
            monthLabel.setText(dateFormatter.format(calendar.getTime()));
            tableModel.fireTableDataChanged();
        }
    }

    public static void main(String[] args) {

		EventQueue.invokeLater(new Runnable() {
			public void run() {
		        CalendarViewer frame = new CalendarViewer();
		        frame.pack();
		        frame.setVisible(true);
			}
		});
    }
}


@SuppressWarnings("serial")
class CalendarDayRenderer extends JLabel implements TableCellRenderer {

    public CalendarDayRenderer() {
        super();
        setFont(new Font(null, Font.PLAIN, 28));
        setVerticalAlignment(JLabel.TOP);
        setHorizontalAlignment(JLabel.LEFT);
        setOpaque(true);
    }

    public Component getTableCellRendererComponent(JTable table, Object value,
                                                    boolean isSelected, boolean hasFocus,
                                                    int row, int column) {
        setText((String)value);
        return this;
    }
}