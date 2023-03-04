package model.element;

import java.util.Set;

/**
 * A class to store operations to be used for Cells and Literals.
 *
 * @author Matt Bauchspies mbauch72@uw.edu
 * @author Michael Yarmoshik myarmo@uw.edu
 * @version 3/4/2023
 */
public class OperationElement implements Element {

    /**
     * All operators in a set.
     */
    public static final Set<Character> OPERATORS = Set.of('+', '-', '*', '/', '(', ')');

    /**
     * The stored operation. (+, -, *, /, (, ))
     */
    private final Operation myOperation;

    /**
     * Constructor for the OperationElement.
     * @param theOperation the operation character we are processing and storing.
     */
    public OperationElement(final char theOperation){
        myOperation = Operation.fromChar(theOperation);
    }

    /**
     * Gets operator associated with this element.
     *
     * @return Operator associated with this element
     */
    public char getOperator() {
        return myOperation.character;
    }

    /**
     * Gets the priority of the operator associated with this element.
     *
     * @return Priority of the operator associated with this element
     */
    public int getPriority() {
        return myOperation.priority;
    }

    /**
     * Gets the priority of the operator associated with an element.
     *
     * @param theOperator Operator in question
     * @return Priority of the operator associated with this element
     */
    public static int getPriority(final char theOperator) {
        return Operation.fromChar(theOperator).priority;
    }

    /**
     * Evaluates the expression (val1 on val2).
     *
     * @param val1 Value 1
     * @param val2 Value 2
     * @return Completed evaluation
     */
    public int evaluate(int val1, int val2) {
        return switch (myOperation) {
            case ADDITION -> val1 + val2;
            case SUBTRACTION -> val1 - val2;
            case MULTIPLICATION -> val1 * val2;
            case DIVISION -> val1 / val2;
            default -> throw new IllegalArgumentException("Invalid expression");
        };
    }

    @Override
    public String toString() {
        return "[OE: " + myOperation.character + "]";
    }

    public enum Operation {
        LEFT_PARENTHESIS('(', 2), // Redundant?
        RIGHT_PARENTHESIS(')', 2),
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
