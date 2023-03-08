package model.element.value;

import model.Cell;
import model.SpreadSheet;
import model.element.OperationElement;

public class CellElement implements ValueElement {

    /**
     * The row of the Cell that CellElement represents.
     */
    private int myRow;

    /**
     * The column of the Cell that CellElement represents.
     */
    private int myColumn;

    /**
     * The SpreadSheet that the Cell being represented resides in.
     */
    private final SpreadSheet mySpreadSheet;

    /**
     * Is the result negative?
     */
    private boolean myNegative = false;

    /**
     * Constructor for the CellElement, only defines the Spreadsheet.
     * @param theSpreadSheet The Spreadsheet that the Cell resides in.
     */
    public CellElement(final SpreadSheet theSpreadSheet, final boolean theNegative) {
        mySpreadSheet = theSpreadSheet;
        myNegative = theNegative;
    }

    /**
     * Sets the row and column of the CellElement, as well as performing calculations on theFormula contained.
     *
     * @param theFormula Formula passed to parse
     * @param theIndex Index to check element
     * @param theElement Created cell element
     * @return Index after element
     */
    public static int applyValues(final String theFormula, int theIndex, final CellElement theElement) {
        int column = 0;
        while (theFormula.length() > theIndex && Character.isAlphabetic(theFormula.charAt(theIndex))) {
            final char c = theFormula.charAt(theIndex);
            column = column * 26 + c - (Character.isLowerCase(c) ? 'a' : 'A') + 1;
            theIndex++;
        }
        column -= 1;

        boolean noRow = true;
        int row = 0;
        while (theFormula.length() > theIndex && Character.isDigit(theFormula.charAt(theIndex))) {
            noRow = false;
            final char c = theFormula.charAt(theIndex);
            row = row * 10 + c - '0';
            theIndex++;
        }
        if (theFormula.length() > theIndex) {
            char c = theFormula.charAt(theIndex);
            if (!OperationElement.OPERATORS.contains(theFormula.charAt(theIndex)) && c != ')' && !Character.isWhitespace(c))
                throw new IllegalArgumentException("Invalid theFormula!");
        }
        if (noRow) throw new IllegalArgumentException("Cell references must contain a Column and Row!");
        if (row < 0) throw new IllegalArgumentException("Rows cannot be less than 0!");
        theElement.myRow = row;
        theElement.myColumn = column;
        return theIndex;
    }

    /**
     * Getter method for the value in this CellElement.
     *
     * @return The integer value this CellElement represents.
     */
    @Override
    public int getValue() {
        return myNegative ? -getCell().getCellValue() : getCell().getCellValue();
    }

    /**
     * Gets the Cell that this CellElement references.
     *
     * @return The cell that CellElement references.
     */
    public Cell getCell() {
        //System.out.println("r-" + myRow + ", c-" + myColumn + " is " + mySpreadSheet.getCellAt(myRow, myColumn));

        System.out.println((mySpreadSheet.getCellAt(myRow, myColumn) == null));
        if (mySpreadSheet.getCellAt(myRow, myColumn) == null) {
            mySpreadSheet.addCell(null, myRow, myColumn, false);
        }
        System.out.println((mySpreadSheet.getCellAt(myRow, myColumn) == null));
        return mySpreadSheet.getCellAt(myRow, myColumn);
    }

    @Override
    public String toString() {
        return "[CE: r-" + myRow + ", c-" + myColumn + "]";
    }

}
