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
     * Evaluates the expression (theFirstVal & theSecondVal).
     *
     * @param theFirstVal Value 1
     * @param theSecondVal Value 2
     * @return Completed evaluation
     */
    public int evaluate(int theFirstVal, int theSecondVal) {
        return switch (myOperation) {
            case EXPONENT -> (int) Math.pow(theSecondVal, theFirstVal);
            case ADDITION -> theSecondVal + theFirstVal;
            case SUBTRACTION -> theSecondVal - theFirstVal;
            case MULTIPLICATION -> theSecondVal * theFirstVal;
            case DIVISION -> theSecondVal / theFirstVal;
            case MODULUS -> theSecondVal % theFirstVal;
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
        LEFT_PARENTHESIS('(', 3), // Redundant?
        RIGHT_PARENTHESIS(')', 3),
        EXPONENT('^', 2),
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
         * Checks whether a theCharacter is an enumerated operation.
         * @param theCharacter The char we are checking.
         * @return the operation that theCharacter represents.
         * @throws IllegalArgumentException if an invalid char is provided.
         */
        static Operation fromChar(final char theCharacter) {
            for (final Operation op : Operation.values()) {
                if (theCharacter == op.character) return op;
            }
            throw new IllegalArgumentException("Invalid theCharacter");
        }

    }

}
