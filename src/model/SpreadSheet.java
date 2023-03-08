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

    private String myCurrentInput; // most recent input into GUI

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
     */
    public void addCell(final String theInput, final int theRow, final int theColumn) {
        if (checkBounds(theRow, theColumn)) {
            return;
        }
        if (myCells[theRow][theColumn] == null) {
            myCells[theRow][theColumn] = new Cell(theInput, this, theRow, theColumn).initialize();
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

    public String setFormula(String s) {
        //TODO bound checking
        myCurrentInput=s;
        System.out.println(myCurrentInput);
        return myCurrentInput;
    }

    public int getMyColumns() {
        return myColumns;
    }

    public int getMyRows() {
        return myRows;
    }

    public void setMyColumns(int theColSize){
        myColumns = theColSize;
    }
    public void setMyRows(int theRowSize){
        myColumns = theRowSize;
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
                final Cell cell = getCellAt(col, row);

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
    public void loadSheet(String theFileName) {
        try {
            FileReader inputFile = new FileReader(theFileName);
            BufferedReader reader = new BufferedReader(inputFile);

            //seperate elements of text file by comma
            String[] cells = (reader.readLine().split(", "));

            reader.close();
            inputFile.close();

            clearCells();
            setMyRows(Integer.parseInt(cells[0])); //spot 0 contains row
            setMyColumns(Integer.parseInt(cells[1])); //spo1 contains col

            for (int i = 2; i < cells.length; i++) {
                String[] elements = (cells[i].split(";")); //split the string representation up again, based on the elements of the cell.
                int row = Integer.parseInt(elements[0]); //first element is row
                int column = Integer.parseInt(elements[1]); //second is col
                String input = elements[2]; //third is the actual input of the cell.
                addCell(input, row, column);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * clears the contents of the cells.
     */
    public void clearCells(){
        for (int col = 0; col < getMyColumns(); col++) {
            for (int row = 0; row < getMyRows(); row++) {
                myCells[row][col] = null;
            }
        }
    }

    /**
     * Method to print all the cells of the spreadsheet.
     */
    public void printAllFormulas() {
        for (int col = 0; col < myColumns; col++) {
            System.out.println();
            for (int row = 0; row < myRows; row++) {
                if (myCells[row][col] != null) {
                    System.out.println(myCells[row][col].getFormula());
                } else {
                    System.out.println("null");
                }
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
