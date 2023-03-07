package view;

import model.Cell;
import model.SpreadSheet;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SpreadSheetGUI {

    public SpreadSheet mySpreadSheet;

    public JTable myTable;

    public JFrame myFrame;

    private Menu myMenu;
    private JTextField textField;

    private JButton insertButton;

    public JMenuBar myMenuBar;

    private JPanel insertPanel;


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
        addMenu();
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


    private void addMenu() {
        JMenu formulaMenu = new JMenu("Formula");
        JMenuItem getFormulaItem = new JMenuItem("Get Formula");
        getFormulaItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = myTable.getSelectedRow();
                int col = myTable.getSelectedColumn();
                if (row != -1 && col != -1) {
                    String formula = mySpreadSheet.getCellAt(col-1, row).getFormula();
                    textField.setText(formula);
                }
            }
        });
        formulaMenu.add(getFormulaItem);
        myMenuBar.add(formulaMenu);
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
            public Object getValueAt(int row, int column) {
                if (column == 0) {
                    return row ;
                }
                return super.getValueAt(row, column - 1);
            }

            @Override
            public void setValueAt(Object value, int row, int column) {
                try {
                    if (column == 0) {
                        return;
                    }
                    super.setValueAt(value, row, column - 1);
                    mySpreadSheet.addCell(value.toString(), column - 1, row);
                    Cell cell = mySpreadSheet.getCellAt(column - 1, row);
                    if (cell != null) {
                        System.out.println("this is the row");
                        System.out.println(row);
                        System.out.println("this is the column");
                        System.out.println(column);
                        super.setValueAt(cell.getCellValue(), row, column - 1); // update cell value in JTable
                    }
                } catch (Exception e) {
                    System.err.println("Error setting cell value: " + e.getMessage());
                }
            }

            @Override
            public boolean isCellEditable(int row, int column) {
                return column > 0;
            }

            @Override
            public Class<?> getColumnClass(int column) {
                if (column == 0) {
                    return Integer.class;
                }
                return super.getColumnClass(column - 1);
            }

        };
        myTable = new JTable(model);
        myTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            {
                setHorizontalAlignment(SwingConstants.RIGHT);
                setBackground(new Color(219, 216, 216)); // set background color of column 0
            }
        });
        myTable.getModel().addTableModelListener(new TableModelListener() {
            @Override
            public void tableChanged(TableModelEvent e) {
                // TODO Auto-generated method stub
                int row = e.getFirstRow();
                int column = e.getColumn();
                TableModel model = (TableModel) e.getSource();
                Object value = model.getValueAt(row, column);
            }
        });
        // set column labels to begin from column 2
        TableColumnModel columnModel = myTable.getColumnModel();
        for (int i = 0; i < columnModel.getColumnCount(); i++) {
            if (i == 0) {
                columnModel.getColumn(i).setHeaderValue("");
            } else if (i == 1) {
                columnModel.getColumn(i).setHeaderValue("A");
            } else {
                String[] columnNames = new String[mySpreadSheet.getMyColumns()];
                for (int j = 0; j < mySpreadSheet.getMyColumns(); j++) {
                    columnNames[j] = Character.toString((char) (j + 65));
                }
            }
        }
        myTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        myTable.getColumnModel().getColumn(0).setPreferredWidth(WIDTH);
        myTable.getTableHeader().setReorderingAllowed(false);
    }
    /**
     * Initializes the cells in the spreadsheet with empty values.
     *
     */
    private void initializeSpreadSheet() {
    }
    /** Adds the menuBar to the Frame*/
    private void addMenuBar() {
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

}
