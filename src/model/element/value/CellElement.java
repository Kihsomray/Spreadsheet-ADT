package model.element.value;

public class CellElement implements ValueElement {

    /**
     * The integer representation of the CellElement.
     */
    private int myCellValue;

    /**
     * Constructor for the CellElement.
     * @param theValue The value
     */
    public CellElement(final int theValue) {
        setValue(theValue);
    }

    @Override
    public int getValue() {
        return myCellValue;
    }

    /**
     * Private setter for the integer value, primarily used by constructor.
     * @param theValue the integer we are setting the CellValue to represent.
     */
    private void setValue(final int theValue) {
        myCellValue = theValue;
    }
}
