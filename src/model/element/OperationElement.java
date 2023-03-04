package model.element;

/**
 * A class to store operations to be used for Cells and Literals.
 *
 * @author Matt Bauchspies mbauch72@uw.edu
 * @version 2/27/2023
 */
public class OperationElement implements Element {

    /**
     * The stored operation. (+, -, *, /, ))
     */
    private final Operation myOperation;

    /**
     * Constructor for the OperationElement.
     * @param theOperation the operation character we are processing and storing.
     */
    public OperationElement(final char theOperation){
        myOperation = Operation.fromChar(theOperation);
    }

    public char getOperator() {
        return myOperation.character;
    }

    public int getPriority() {
        return myOperation.priority;
    }

    public static int getPriority(final char theOperation) {
        return Operation.fromChar(theOperation).priority;
    }

    public boolean isParenthesis() {
        return myOperation == Operation.LEFT_PARENTHESIS;
    }

    public enum Operation {
        LEFT_PARENTHESIS(')', 2),
        MULTIPLICATION('*', 1),
        DIVISION('/', 1),
        ADDITION('+', 0),
        SUBTRACTION('-', 0);

        final char character;
        final int priority;

        Operation(char character, int priority) {
            this.character = character;
            this.priority = priority;
        }

        static Operation fromChar(final char character) {
            for (final Operation op : Operation.values()) {
                if (character == op.character) return op;
            }
            throw new IllegalArgumentException("Invalid character");
        }

    }

}
