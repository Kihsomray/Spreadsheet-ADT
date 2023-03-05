package model;

import java.util.LinkedList;
import java.util.Objects;

public class Cell {

    /**
     * The integer value of the cell.
     */
    private int myCellValue;

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
    Cell(final String theInput, final SpreadSheet theSpreadSheet) {
        myExpressionTree = new ExpressionTree(theInput, this);
        myFormulaInput = theInput;
        mySpreadSheet = theSpreadSheet;
        updateCellValue();
    }

    /**
     * Updates the cell with the new formula, if it's indeed a new formula.
     *
     * @param theInput The String input from the table.
     */
    void refreshCell(final String theInput, final Cell theCell) {
        if (!Objects.equals(theInput, myFormulaInput)) {
            myExpressionTree = new ExpressionTree(theInput, this);
            myFormulaInput = theInput;
            updateCellValue();
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
            c.updateCellValue();
        }
    }

    /**
     * Helper method to update the value of the cell.
     * Calls updateDependents to also update any necessary dependencies after this.
     */
    private void updateCellValue() {
        myCellValue = myExpressionTree.calculate();
        updateDependents(); //TODO: confirm this actually works
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
