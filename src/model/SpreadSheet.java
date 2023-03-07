package model;

import javax.swing.*;
import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.DefaultTableModel;
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

    public String setFormula (String s) {
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

}
