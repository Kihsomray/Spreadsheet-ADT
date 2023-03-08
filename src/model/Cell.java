package model;

import view.table.SSTableModel;

import java.util.LinkedList;
import java.util.Objects;

/**
 * A Cell class that contains a formula, an ExpressionTree that defines the Cell's value,
 * and an integer that is the calculated value of that ExpressionTree.
 * Contains a LinkedList of other Cells that depend on this one.
 *
 * @author Max Yim
 * @author Matt Bauchspies mbauch72@uw.edu
 * @version 3/5/2023
 */
public class Cell {

    /**
     * The integer value of the cell.
     */
    private int myCellValue;

    /**
     * Row location of this cell.
     */
    private final int myRow;

    /**
     * Column location of this cell.
     */
    private final int myColumn;

    /**
     * The expression tree that defines the cell's value.
     */
    private ExpressionTree myExpressionTree;

    /**
     * A LinkedList of cells that depend on this one.
     */
    private final LinkedList<Cell> myDependents = new LinkedList<>();

    /**
     * The original formula entered by the user as a String.
     */
    private String myFormulaInput;

    /**
     * Reference to the spreadsheet this cell is in.
     */
    private final SpreadSheet mySpreadSheet;

    /**
     * Constructor for the cell object.
     *
     * @param theInput the expression stored in the cell.
     */
    Cell(final String theInput, final SpreadSheet theSpreadSheet, final int theRow, final int theColumn) {
        myFormulaInput = theInput;
        mySpreadSheet = theSpreadSheet;
        myRow = theRow;
        myColumn = theColumn;
    }

    /**
     * Initializes the cell object
     *
     * @param updateGUI Should GUI update
     * @return reference to current cell
     */
    public Cell initialize(final boolean updateGUI) {
        if (myFormulaInput != null) myExpressionTree = new ExpressionTree(myFormulaInput, this);
        updateCellValue(updateGUI);
        return this;
    }

    /**
     * Updates the cell with the new formula, if it's indeed a new formula.
     *
     * @param theInput The String input from the table.
     */
    void refreshCell(final String theInput) {
        if (!Objects.equals(theInput, myFormulaInput)) {
            if (theInput != null) myExpressionTree = new ExpressionTree(theInput, this);
            myFormulaInput = theInput;
            updateCellValue(false);
            updateDependents();
        }
    }

    /**
     * Getter method for the cell's integer value.
     * @return the integer cell value.
     */
    public int getCellValue() {
        return myCellValue;
    }

    /**
     * Method to update the values of dependent cells.
     */
    void updateDependents(){
        for (Cell c : myDependents) {
            c.updateCellValue(true);
            c.updateDependents();
        }
    }

    public boolean checkCycle(){
        for (Cell c : myDependents) {
            if (c.checkCycle(this)) {
                return true;
            }
        }
        return false;
    }

    private boolean checkCycle(final Cell theCell) {
        if (myDependents.isEmpty()) {
            return false;
        }
        if (theCell == this) {
            return true;
        }
        for (Cell c : myDependents) {
            if (c.checkCycle(theCell)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Helper method to update the value of the cell.
     * Calls updateDependents to also update any necessary dependencies after this.
     *
     * @param updateGUI Should GUI update with myCellValue
     */
    private void updateCellValue(boolean updateGUI) {
        myCellValue = myFormulaInput == null ? 0 : myExpressionTree.calculate();
        if (updateGUI) {
            ((SSTableModel) mySpreadSheet
                    .getSpreadSheetFrame()
                    .getTable()
                    .getModel())
                    .setGUIValue(String.valueOf(myCellValue), myRow, myColumn + 1);
        }
    }


    /**
     * Getter method for the original formula used for the Cell.
     *
     * @return The original String input.
     */
    public String getFormula() {
        return myFormulaInput;
    }

    /**
     * Add a dependency to the current cell.
     *
     * @param theCell The Cell that depends on this cell's value.
     */
    void addDependency(final Cell theCell) {
        myDependents.add(theCell);
    }

    /**
     * Gets the cell's spreadsheet.
     *
     * @return cell's spreadsheet
     */
    SpreadSheet getSpreadSheet() {
        return mySpreadSheet;
    }

}
