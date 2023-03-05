package model;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;

/**
 * A class that defines the SpreadSheet operations.
 *
 * @author Matt Bauchspies mbauch72@uw.edu
 * @author Mike Yarmoshik
 * @author Elroy Mbabazi
 * @author Max Yim
 *
 * @version 3/5/2023
 */
public class SpreadSheet extends DefaultTableModel implements TableModelListener {

    /**
     * The underlying 2D Array of the SpreadSheet, contains Cells.
     */
    private Cell[][] mySpreadsheet;

    /**
     * The JTable used for the GUI of the SpreadSheet.
     */
    private JTable myTable;

    /**
     * The total columns of the SpreadSheet.
     */
    private int myColumns;

    /**
     * The total rows of the SpreadSheet.
     */
    private int myRows;

    /**
     * Static definition of "base" for columns based on English alphabet.
     */
    public static final int NUM_LETTERS = 26;

    /**
     * An Array of Strings for the column names.
     */
    private final String[] columnNames;

    /**
     * The constructor for the SpreadSheet.
     * @param theColumns The initial Columns in the SpreadSheet.
     * @param theRows The initial Rows in the SpreadSheet.
     */
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
        Cell cell = mySpreadsheet[column][row];

        boolean displayFormulas = false;

    }

    /**
     * Getter for the JTable.
     * @return The SpreadSheet's JTable.
     */
    public JTable getTable() {
        return myTable;
    }


    /**
     * Fills in the names of the columns.
     */
    private void fillColumnNames() {
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
        mySpreadsheet = new Cell[myColumns][myRows];
    }

    /**
     * Method for adding a Cell to the SpreadSheet at a given location.
     * If a cell already exists, refresh the values instead.
     *
     * @param theInput The String input for the formula provided.
     * @param theColumn The Column we want to add the Cell at.
     * @param theRow The Row we want to add the Cell at.
     */
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
    private String getCellFormula(final int theColumn, final int theRow) {
        return mySpreadsheet[theColumn][theRow].getFormula();
    }

    /**
     * Prints a cell's formula
     * @param theColumn The column of the cell.
     * @param theRow The row of the cell.
     */
    public void printCellFormula(final int theColumn, final int theRow) {
        System.out.println(getCellFormula(theColumn, theRow));
    }


    /**
     * Prints the formulas of all cells.
     */
    //TODO confirm this works
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

    /**
     * Getter for a cell at a particular location of the SpreadSheet.
     * @param theColumn The Column we want to get the Cell from.
     * @param theRow The Row we want to get the Cell from.
     * @return The Cell contained at SpreadSheet[theColumn][theRow]
     */
    public Cell getCellAt(final int theColumn, final int theRow) {
        return mySpreadsheet[theColumn][theRow];
    }

}
