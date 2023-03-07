package model;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
     * Static definition of "base" for columns based on English alphabet.
     */
    public static final int NUM_LETTERS = 26;

    private String myCurrentInput; // most recent input into GUI

    /**
     * The constructor for the SpreadSheet.
     * @param theColumns The initial Columns in the SpreadSheet.
     * @param theRows The initial Rows in the SpreadSheet.
     */
    public SpreadSheet(final int theColumns, final int theRows) {
        if (theColumns <= 0 || theRows <= 0) {
            throw new IllegalArgumentException("SpreadSheet dimensions must be positive.");
        }
        myCells = new Cell[theColumns][theRows];
        myColumns = theColumns;
        myRows = theRows;
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
        checkBounds(theColumn, theRow);
        if (myCells[theColumn][theRow] == null) {
            myCells[theColumn][theRow] = new Cell(theInput, this).initialize();
        } else {
            myCells[theColumn][theRow].refreshCell(theInput, myCells[theColumn][theRow]);
        }
    }

    /**
     * Gets a cell's formula.
     * @param theRow The row of the cell.
     * @param theColumn The column of the cell.
     */
    private String getCellFormula(final int theColumn, final int theRow) {
        return myCells[theColumn][theRow].getFormula();
    }

    /**
     * Prints a cell's formula
     * @param theColumn The column of the cell.
     * @param theRow The row of the cell.
     */
    public void printCellFormula(final int theColumn, final int theRow) {
        System.out.println(getCellFormula(theColumn, theRow));
    }


//    /**
//     * Prints the formulas of all cells.
//     */
//    //TODO confirm this works
//    public void printAllFormulas() {
//
//        for (int row = 0; row < myRows; row++) {
//            for (int col = 1; col < myColumns; col++) {
//                // Prints the Column and Row with colon (e.g. A4: )
//                System.out.print(convertColumn(col - 1) + row + ": ");
//                // Prints the formula for that cell
//                System.out.print(myCells[row][col].getFormula() + "   ");
//            }
//        }
//
//    }

    /**
     * Getter for a cell at a particular location of the SpreadSheet.
     * @param theColumn The Column we want to get the Cell from.
     * @param theRow The Row we want to get the Cell from.
     * @return The Cell contained at SpreadSheet[theColumn][theRow]
     */
    public Cell getCellAt(final int theColumn, final int theRow) {
        checkBounds(theColumn, theRow);
        // TODO null check
        return myCells[theColumn][theRow];
    }


    private void checkBounds(final int theCol, final int theRow) {
        if (theRow < 0 || theCol >= myColumns || theCol < 0 || theRow >= myRows) {
            throw new IllegalArgumentException("Invalid cell index.");
        }
    }

//
//    public List<Cell> getCells() {
//        final List<Cell> cells = new ArrayList<>();
//        for (int i = 0; i < myNumRows; i++) {
//            for (int j = 0; j < myNumCols; j++) {
//                cells.add(myCells[i][j]);
//            }
//        }
//        return cells;
//    }

    /**
     * Saves the contents of the sheet into a text file.
     *
     * @param theFileName name of the file.
     */
    public void saveSheet(String theFileName) {
        StringBuilder result = new StringBuilder(getMyColumns() + ", " + getMyRows() + ", ");

        for (int col = 0; col < getMyColumns(); col++) {
            for (int row = 0; row < getMyRows(); row++) {
                if (getCellAt(col,row) != null) {
                    result.append(col +";" + row + ";" + getCellFormula(col,row) + ", ");
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

            String[] cells = (reader.readLine().split(", "));

            reader.close();
            inputFile.close();

            clearCells();
            setMyColumns(Integer.parseInt(cells[0])); //spot 0 contains col
            setMyRows(Integer.parseInt(cells[1])); //spo1 contains row

            for (int i = 2; i < cells.length; i++) {
                String[] elements = (cells[i].split(";"));
                int column = Integer.parseInt(elements[0]);
                int row = Integer.parseInt(elements[1]);
                String input = elements[2];
                addCell(input, column, row);
            }

            } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public String getColumnLabel(int column) {
        StringBuilder sb = new StringBuilder();
        while (column >= 0) {
            int remainder = column % 26;
            sb.append((char) ('A' + remainder));
            column = (column / 26) - 1;
        }
        return sb.reverse().toString();
    }

    /**
     * clears the contents of the cells.
     */
    public void clearCells(){
        for (int col = 0; col < getMyColumns(); col++) {
            for (int row = 0; row < getMyRows(); row++) {
                myCells[col][row] = null;
            }
        }
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

}
