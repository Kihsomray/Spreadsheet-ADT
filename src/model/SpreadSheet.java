package model;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

public class SpreadSheet extends DefaultTableModel implements TableModelListener {

    private Cell[][] mySpreadsheet;

    private JTable myTable;

    private int myColumns;

    private int myRows;
    private int myValue;
    /** Count of letters. */
    public static final int NUM_LETTERS = 26;


    private final Object[] columnNames;

    public SpreadSheet(final int theColumns, final int theRows) {
        mySpreadsheet = new Cell[theColumns][theRows];
        myColumns = theColumns;
        myRows = theRows;
        columnNames = new String[myColumns + 1];

        fillColumnNames();
        initializeSpreadsheet();
        myTable = new JTable(mySpreadsheet, columnNames){
            /** A generated serial version UID. */
            private static final long serialVersionUID = -8427343693180623327L;
            @Override
            public boolean isCellEditable(int row, int columns){
                return true;
            }
        };
    }

    /**
     * This fine grain notification tells listeners the exact range
     * of cells, rows, or columns that changed.
     *
     * @param theEvent a {@code TableModelEvent} to notify listener that a table model
     *          has changed
     */
    @Override
    public void tableChanged(TableModelEvent theEvent) {
        int row = theEvent.getFirstRow();
        int column = theEvent.getColumn();
        Cell cell = mySpreadsheet[row][column];

        boolean displayFormulas = false;

    }

    public JTable getTable() {
        return myTable;
    }


    /**
     * Fills in the names of the columns.
     */
    private void fillColumnNames() {
        // Fill in the column names
        columnNames[0] = "";
        for (int i = 0; i < myColumns; i++) {
            columnNames[i + 1] = convertColumn(i);
        }
    }

    /**
     * Converts the given column index to the corresponding column name.
     *
     * @param theColumn the index of the column
     * @return the corresponding column name
     */
    private String convertColumn(final int theColumn) {
        final StringBuilder result = new StringBuilder();
        int column = theColumn;

        do {
            column--;
            result.insert(0, (char) ('A' + column % NUM_LETTERS));
            column /= NUM_LETTERS;
        }
        while (column > 0);
        return result.toString();
    }

    /**
     * Initialize the spreadsheet array.
     */
    public void initializeSpreadsheet() {
        mySpreadsheet = new Cell[myRows][myColumns];

    }

    public void addCell(final String theInput, final int theColumn, final int theRow) {
        if (mySpreadsheet[theColumn][theRow] == null) {
            mySpreadsheet[theColumn][theRow] = new Cell(theInput, this);
        } else {
            mySpreadsheet[theColumn][theRow].refreshCell(theInput, mySpreadsheet[theColumn][theRow]);
        }
    }

    /**
     * Gets a cell's formula.
     * @param theRow The row of the cell.
     * @param theColumn The column of the cell.
     */
    private String getCellFormula(final int theRow, final int theColumn) {
        return mySpreadsheet[theRow][theColumn].getFormula();
    }

    /**
     * Prints a cell's formula
     * @param theRow The row of the cell.
     * @param theColumn The column of the cell.
     */
    public void printCellFormula(final int theRow, final int theColumn) {
        System.out.println(getCellFormula(theRow, theColumn));
    }


    /**
     * Prints the formulas of all cells.
     */
    public void printAllFormulas() {

        for (int row = 0; row < myRows; row++) {
            for (int col = 1; col < myColumns; col++) {
                // Prints the Column and Row with colon (e.g. A4: )
                System.out.print(convertColumn(col - 1) + row + ": ");
                // Prints the formula for that cell
                System.out.print(mySpreadsheet[row][col].getFormula() + "   ");
            }
        }

    }

    public Cell getCellAt(final int theRow, final int theColumn) {
        return mySpreadsheet[theRow][theColumn];
    }

}
