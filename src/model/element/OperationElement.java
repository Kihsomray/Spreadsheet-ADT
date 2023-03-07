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
    public static final Set<Character> OPERATORS = Set.of('+', '-', '*', '/', '%', '(', ')', '^');

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
     * Evaluates the expression (val1 & val2).
     *
     * @param val1 Value 1
     * @param val2 Value 2
     * @return Completed evaluation
     */
    public int evaluate(int val1, int val2) {
        return switch (myOperation) {
            case EXPONENT -> (int) Math.pow(val2, val1);
            case ADDITION -> val2 + val1;
            case SUBTRACTION -> val2 - val1;
            case MULTIPLICATION -> val2 * val1;
            case DIVISION -> val2 / val1;
            case MODULUS -> val2 % val1;
            default -> throw new IllegalArgumentException("Invalid expression");
        };
    }

    /**
     * A toString method for the OperationElement as a readable String.
     * @return A readable String of the OperationElement.
     */
    @Override
    public String toString() {
        return "[OE: " + myOperation.character + "]";
    }

    /**
     * Enums to define different operations and their priority of execution.
     */
    public enum Operation {
        EXPONENT('^', 3),
        LEFT_PARENTHESIS('(', 2), // Redundant?
        RIGHT_PARENTHESIS(')', 2),
        MULTIPLICATION('*', 1),
        DIVISION('/', 1),
        MODULUS('%', 1),
        ADDITION('+', 0),
        SUBTRACTION('-', 0);

        /**
         * The enumerated character.
         */
        final char character;

        /**
         * The operation's priority.
         */
        final int priority;

        /**
         * Constructor for the operation.
         * @param character The character.
         * @param priority The priority.
         */
        Operation(char character, int priority) {
            this.character = character;
            this.priority = priority;
        }

        /**
         * Checks whether a character is an enumerated operation.
         * @param character The character we are checking.
         * @return the operation that the character represents.
         * @throws IllegalArgumentException if an invalid character is provided.
         */
        static Operation fromChar(final char character) {
            for (final Operation op : Operation.values()) {
                if (character == op.character) return op;
            }
            throw new IllegalArgumentException("Invalid character");
        }

    }

}
