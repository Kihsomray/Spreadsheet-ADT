package model.element;

/**
 * A class to store operations to be used for Cells and Literals.
 *
 * @author Matt Bauchspies mbauch72@uw.edu
 * @version 2/27/2023
 */
public class OperationElement implements Element {

    /**
     * The stored operation. (+, -, *, /)
     */
    private char myOperation;

    /**
     * Constructor for the OperationElement.
     * @param theOperation the operation character we are processing and storing.
     */
    public OperationElement(final char theOperation){
        setOperation(theOperation);
    }

    /**
     * Setter for the OperationElement. Used by constructor.
     * @param theOperation Takes a character in, and if valid,
     *                     sets myOperation to that character.
     */
    private void setOperation(final char theOperation) {
        switch (theOperation) {
            case '+':
                myOperation = '+';
                break;
            case '-':
                myOperation = '-';
                break;
            case '*':
                myOperation = '*';
                break;
            case '/':
                myOperation = '/';
                break;

            default:throw new IllegalArgumentException("Invalid character"); // temporary
        }
    }
}
