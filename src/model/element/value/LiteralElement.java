package model.element.value;

/**
 * A class utilized to represent Literals in the Spreadsheet in combination with
 * Cells and Operations to accurately represent a spreadsheet with formulas and
 * dependencies.
 *
 * @author Matt Bauchspies mbauch72@uw.edu
 * @version 2/27/2023
 */
public class LiteralElement implements ValueElement {

    /**
     * The integer value this LiteralElement contains.
     */
    private int myLiteralValue;

    /**
     * Constructor for the LiteralElement
     * @param theValue The integer value we want this LiteralElement to represent.
     */
    public LiteralElement(final int theValue) {
        setValue(theValue);
    }

    /**
     * Getter method for the LiteralElement.
     * @return The integer value stored in this LiteralElement.
     */
    @Override
    public int getValue() {
        return myLiteralValue;
    }

    /**
     * Private setter method for the LiteralElement
     * Only currently used in the constructor but included for consistency.
     * @param theValue The value to set the LiteralElement to.
     */
    private void setValue(final int theValue) {
        myLiteralValue = theValue;
    }

    /**
     * A toString method for a readable representation of what LiteralElement contains.
     * @return A readable String of LiteralElement.
     */
    @Override
    public String toString() {
        return "[LE: " + myLiteralValue + "]";
    }

}
