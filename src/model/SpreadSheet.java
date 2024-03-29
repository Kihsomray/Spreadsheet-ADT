package model;

import controller.SpreadSheetBuilder;
import view.SSFrame;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

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
public class SpreadSheet {

    /**
     * Reference to the SpreadSheet builder.
     */
    private final SpreadSheetBuilder mySpreadSheetBuilder;

    /**
     * The underlying 2D Array of the SpreadSheet, contains Cells.
     */
    private Cell[][] myCells;

    /**
     * The total columns of the SpreadSheet.
     */
    private int myColumns;

    /**
     * The total rows of the SpreadSheet.
     */
    private int myRows;

    /**
     * The constructor for the SpreadSheet.
     * @param theRows The initial Rows in the SpreadSheet.
     * @param theColumns The initial Columns in the SpreadSheet.
     */
    public SpreadSheet(final SpreadSheetBuilder theSpreadSheetBuilder, final int theRows, final int theColumns) {
        if (theColumns <= 0 || theRows <= 0) {
            throw new IllegalArgumentException("SpreadSheet dimensions must be positive.");
        }
        mySpreadSheetBuilder = theSpreadSheetBuilder;
        myCells = new Cell[theRows][theColumns];
        myRows = theRows;
        myColumns = theColumns;
    }

    /**
     * Method for adding a Cell to the SpreadSheet at a given location.
     * If a cell already exists, refresh the values instead.
     *
     * @param theInput The String input for the formula provided.
     * @param theRow The Row we want to add the Cell at.
     * @param theColumn The Column we want to add the Cell at.
     * @param updateGUI Should GUI update with the value.
     */
    public void addCell(final String theInput, final int theRow, final int theColumn, final boolean updateGUI) {
        if (checkBounds(theRow, theColumn)) {
            return;
        }
        if (myCells[theRow][theColumn] == null) {
            myCells[theRow][theColumn] = new Cell(theInput, this, theRow, theColumn).initialize(updateGUI);
        } else {
            myCells[theRow][theColumn].refreshCell(theInput);
        }
    }

    /**
     * Getter for a cell at a particular location of the SpreadSheet.
     * @param theRow The Row we want to get the Cell from.
     * @param theColumn The Column we want to get the Cell from.
     * @return The Cell contained at SpreadSheet[theColumn][theRow]
     */
    public Cell getCellAt(final int theRow, final int theColumn) {
        if (checkBounds(theRow, theColumn)) {
            return null;
        }
        return myCells[theRow][theColumn];
    }

    private boolean checkBounds(final int theRow, final int theColumn) {
        return theRow < 0 || theColumn >= myColumns || theColumn < 0 || theRow >= myRows;
    }

    public int getMyColumns() {
        return myColumns;
    }

    public int getMyRows() {
        return myRows;
    }

    /**
     * Saves the contents of the sheet into a text file.
     *
     * @param theFileName name of the file.
     */
    public void saveSheet(String theFileName) {
        //build string, first two elements are the col and row size.
        StringBuilder result = new StringBuilder(getMyColumns() + ", " + getMyRows() + ", ");


        for (int col = 0; col < getMyColumns(); col++) {
            for (int row = 0; row < getMyRows(); row++) { //add string representation of cell into string.
                final Cell cell = getCellAt(row, col);

                // nulls are ignored
                if (cell != null) {
                    result.append(row).append(";").append(col).append(";").append(cell.getFormula()).append(", ");
                } //append cells to result
            }
        }

        try {
            FileWriter writer = new FileWriter(theFileName);
            writer.write(result.toString()); //write result to text file.
            writer.close();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Loads a spreadsheet. (WIP)
     *
     * @param theFileName the text file that holds the data for the spreadsheet.
     */
    public String[] loadSheet(String theFileName) {
        try {
            FileReader inputFile = new FileReader(theFileName);
            BufferedReader reader = new BufferedReader(inputFile);

            //separate elements of text file by comma
            String[] cells = (reader.readLine().split(", "));

            reader.close();
            inputFile.close();

            return cells;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * clears the contents of the cells.
     */
    // Not currently used.
    public void clearCells(){
        for (int col = 0; col < getMyColumns(); col++) {
            for (int row = 0; row < getMyRows(); row++) {
                myCells[row][col] = null;
            }
        }
    }


    /**
     * Gets SpreadSheet JFrame instance.
     *
     * @return SpreadSheet JFrame instance
     */
    public SSFrame getSpreadSheetFrame() {
        return mySpreadSheetBuilder.getMySpreadSheetFrame();
    }

}
