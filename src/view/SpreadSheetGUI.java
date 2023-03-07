package view;

import model.Cell;
import model.SpreadSheet;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;


public class SpreadSheetGUI {

    public SpreadSheet mySpreadSheet;

    public JTable myTable;

    public JFrame myFrame;


    private JTextField textField;

    private JButton insertButton;

    public JMenuBar myMenuBar;

    private JPanel insertPanel;

    private boolean changeInProgress = false;

    /** The width of the column of row numbers in pixels. */
    public static final int WIDTH = 30;


    public SpreadSheetGUI() {
        myMenuBar = new JMenuBar();
        textField = new JTextField(50);
        insertPanel= new JPanel(new FlowLayout());
        promptUserForDimensions();
        initializeSpreadSheet();
        insertPanel();
        createTable();
        createFrame();
    }

    private void insertPanel() {
        insertPanel.add(textField);
        insertButton = new JButton("Insert");
        insertButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent theEvent) {
                insertData();
            }
        });
        insertPanel.add(insertButton);

    }

    private void insertData() {
        //retrieve the indices of the selected cell
        int rowIndex = myTable.getSelectedRow();
        int columnIndex = myTable.getSelectedColumn();

        //checking if the cell has been selected
        if (rowIndex == -1 || columnIndex == -1) {
            JOptionPane.showMessageDialog(myFrame, "Please select a cell to insert the data.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        DefaultTableModel model = (DefaultTableModel)myTable.getModel();
        model.setValueAt(textField.getText(),rowIndex,columnIndex);
        textField.setText("");
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
            public void setValueAt(Object value, int column, int row) {
                try {
                super.setValueAt(value, column, row);
                mySpreadSheet.addCell(value.toString(), column, row);
                Cell cell = mySpreadSheet.getCellAt(column, row);
                if (cell != null) {
                    super.setValueAt(cell.getCellValue(), column, row); // update cell value in JTable
                }
                } catch (Exception e) {
                    System.err.println("Error setting cell value: " + e.getMessage());
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return true;
            }

        };
        myTable = new JTable(model);

        myTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // TODO Auto-generated method stub
                if (changeInProgress) return;
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                Object value = model.getValueAt(row, column);
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

    /** Adds the menuBar to the Frame*/
    private void addMenuBar(){
        myMenuBar.add(new FileMenu(myFrame).getFileMenu());
        myMenuBar.add(new OptionsMenu(myFrame,mySpreadSheet,myTable).getOptionsMenu());
        myMenuBar.add(new HelpMenu().getHelpMenu());
        myFrame.setJMenuBar(myMenuBar);
    }

    /**
     * Creates the JFrame to hold the JTable.
     */
    private void createFrame() {
        myFrame = new JFrame("Spreadsheet");
        // add row header to table using JScrollPane
        JScrollPane table = new JScrollPane(myTable,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        addMenuBar();
        myFrame.add(insertPanel,BorderLayout.NORTH);
        Dimension tableSize = myTable.getSize();
        myFrame.setSize(tableSize);
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.add( table, BorderLayout.CENTER);
        myFrame.pack();
        myFrame.setVisible(true);
        myFrame.setLocationRelativeTo(null);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SpreadSheetGUI());
    }
}