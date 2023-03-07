package model;

import model.SpreadSheet;

import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;


public class SpreadSheetGUI {

    public SpreadSheet mySpreadSheet;

    public JTable myTable;

    public JFrame myFrame;

    //  private final Timer myTimer;
    private boolean changeInProgress = false;

    /** The width of the column of row numbers in pixels. */
    public static final int WIDTH = 30;

    //	private JLabel myRowHeader;
    //
    //	private JLabel myColumnHeader;

    public SpreadSheetGUI() {
        promptUserForDimensions();
        initializeSpreadSheet();
        createTable();
        createFrame();
    }

    /**
     * Prompts the user for the number of rows and columns to create.
     */
    private void promptUserForDimensions() {
        JTextField rowsField = new JTextField(5);
        JTextField columnsField = new JTextField(5);

        JPanel panel = new JPanel(new GridLayout(0, 1));
        panel.add(new JLabel("Enter the number of rows:"));
        panel.add(rowsField);
        panel.add(new JLabel("Enter the number of columns:"));
        panel.add(columnsField);

        int result = JOptionPane.showConfirmDialog(null, panel, "Enter spreadsheet dimensions",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            int rows = Integer.parseInt(rowsField.getText());
            int columns = Integer.parseInt(columnsField.getText());
            mySpreadSheet = new SpreadSheet(columns, rows);
        } else {
            System.exit(0);
        }
    }

    /**
     * Creates the JTable component to display the spreadsheet data.
     */
    private void createTable() {
        DefaultTableModel model = new DefaultTableModel(mySpreadSheet.getMyColumns(), mySpreadSheet.getMyRows()){

            /**
             *
             */
            private static final long serialVersionUID = 1L;

            @Override
            public void setValueAt(Object value, int row, int column) {
                super.setValueAt(value, row, column);
                mySpreadSheet.addCell(value.toString(), column, row);
                System.out.println(mySpreadSheet.getCellAt(column, row).getCellValue());
                System.out.println(value);
                Cell cell = mySpreadSheet.getCellAt(row, column);
                if (cell != null) {
                    System.out.println("this is the value that is got");
                    System.out.println(value);
                    cell.refreshCell(value.toString(), mySpreadSheet.getCellAt(row,column));
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }

        };
        myTable = new JTable(model);
        //		for (int i = 0; i < mySpreadSheet.myNumRows; i++) {
        //		    for (int j = 0; j < mySpreadSheet.myNumCols; j++) {
        //		        model.setValueAt(mySpreadSheet.getCellAt(i, j).getCellValue(), i, j);
        //		    }
        //		}
        myTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // TODO Auto-generated method stub
                if (changeInProgress) return;
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                Object value = model.getValueAt(row, column);
                //updateSpreadsheet(row, column,value);
                //Prints to the console
//                System.out.println("this is the value");
//                System.out.println(value);
//                System.out.println("this is the row");
//                System.out.println(row);
//                System.out.println("this is the column");
//                System.out.println(column);

                //updateSpreadsheet( row, column,value.toString());
                //mySpreadSheet.setCell(row, column, value.toString());
            }
        });
        myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        //myTable.getColumnModel().getColumn(0).setPreferredWidth(WIDTH);
        myTable.getTableHeader().setReorderingAllowed(false);
    }
    /**
     * Initializes the cells in the spreadsheet with empty values.
     *
     */
    private void initializeSpreadSheet() {


    }
    /**
     * Creates the JFrame to hold the JTable.
     */
    private void createFrame() {
        myFrame = new JFrame("Spreadsheet");
        // add row header to table using JScrollPane
        JScrollPane table = new JScrollPane(myTable);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.add(table);
        myFrame.pack();
        myFrame.setVisible(true);
    }

    //	/**
    //	 * Returns the label for the given column index, using Excel-style labels (A, B, C, ..., Z, AA, AB, ...).
    //	 *
    //	 * @param column the column index.
    //	 * @return the label for the column.
    //	 */
    //	public String getColumnLabel(int column) {
    //		StringBuilder sb = new StringBuilder();
    //		while (column >= 0) {
    //			int remainder = column % 26;
    //			sb.append((char) ('A' + remainder));
    //			column = (column / 26) - 1;
    //		}
    //		return sb.reverse().toString();
    //	}

    //	/**
    //	 * Returns the label for the given row index, using Excel-style labels (1, 2, 3, ..., 26, 27, ...).
    //	 *
    //	 * @param row the row index.
    //	 * @return the label for the row.
    //	 */
    //	public String getRowLabel(int row) {
    //		StringBuilder sb = new StringBuilder();
    //		while (row >= 0) {
    //			int remainder = row % 26;
    //			sb.append(remainder + 1);
    //			row = (row / 26) - 1;
    //		}
    //		return sb.reverse().toString();
    //	}

    //	public void updateSpreadsheet(int row, int column, String value) {
    //		mySpreadSheet.setCell(row, column, value);
    //		mySpreadSheet.updateAllCells();
    //	}

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SpreadSheetGUI());
    }
}