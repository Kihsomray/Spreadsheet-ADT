package model;

import model.element.Element;
import model.element.OperationElement;
import model.element.value.CellElement;
import model.element.value.LiteralElement;
import model.element.value.ValueElement;

import java.util.Iterator;
import java.util.Stack;

/**
 * Expression Tree Class: Used for parsing various
 * expressions with easy recalculation.
 *
 * @author Michael N Yarmoshik
 * @version 3/4/2023
 */
public class ExpressionTree {

    /**
     * The header node of the Tree.
     */
    private final Node myHeaderNode;

    /**
     * The parent Cell the Tree is contained in.
     */
    private final Cell myCell;

    /**
     * Generates an expression tree given a formula and spreadsheet.
     *
     * @param theExpression Expression to parse
     * @param theCell Parent cell of this tree
     */
    public ExpressionTree(final String theExpression, final Cell theCell) {
        myCell = theCell;
        myHeaderNode = constructTree(expressionPostfix(theExpression), theCell);
        if (myCell.checkCycle()) {
            throw new IllegalArgumentException("Setting this value will result in a cycle!");
        }
    }

    /**
     * Constructs a tree from a stack based post-fix expression.
     *
     * @param theStack Postfix expression stack
     * @param theCell Cell that contains this tree
     * @return Converted tree from stack
     */
    private Node constructTree(final Stack<Element> theStack, final Cell theCell) {
        if (theStack.isEmpty()) throw new IndexOutOfBoundsException(
                "The entered expression is not a valid formula!"
        );

        Element element = theStack.pop();  // need to handle stack underflow
        //System.out.println(element);
        if (element instanceof ValueElement) {
            if (element instanceof CellElement cellElement) {
                try {
                    cellElement.getCell().addDependency(theCell);
                } catch (NullPointerException e) {

                    e.printStackTrace();
                    // this should never be thrown
                    throw new IllegalArgumentException(
                            "Only a reference to a non-empty cell can be made!"
                    );
                }
            }
            // Literals and Cells are leaves in the expression tree
            return new Node(element);
        } else {
            // Continue finding tokens that will form the
            // right subtree and left subtree.
            Node rightSubtree = constructTree(theStack, theCell);
            Node leftSubtree  = constructTree(theStack, theCell);
            return new Node(element, leftSubtree, rightSubtree);
        }
    }

    /**
     * Creates postfix stack from infix expression. Errors will be thrown
     * if illegal expression is passed.
     *
     * A token can consist of a numeric literal, a cell reference, or an
     * operator (+, -, *, /).
     *
     * This algorithm follows the algorithm described in Weiss, pages 105-108.
     * Forked from method in utils class provided by Donald Chinn.
     *
     * @param theExpression String formula to parse
     * @return Stack of postfix read for tree construction
     */
    private Stack<Element> expressionPostfix(String theExpression) {

        Stack<OperationElement> operatorStack = new Stack<>();
        Stack<Element> returnStack = new Stack<>();

        // index value
        int index = 0;

        // used to check if previous was a value (ignores parenthesis)
        boolean previousValue = false;

        // previous operator
        OperationElement previousOperation = null;

        // is negative
        boolean isNegative = false;

        while (index < theExpression.length()) {

            // eliminate whitespace
            while (index < theExpression.length()) {
                if (!Character.isWhitespace(theExpression.charAt(index))) break;
                index++;
            }

            if (index == theExpression.length()) break;

            char c = theExpression.charAt(index);

            if ((returnStack.isEmpty() && c == '-') || (previousOperation != null && previousOperation.getPriority() <= 1 && c == '-')) {
                index++;
                while (index < theExpression.length()) {
                    if (!Character.isWhitespace(theExpression.charAt(index))) break;
                    index++;
                }
                c = theExpression.charAt(index);
                previousValue = false;
                isNegative = true;
            }

            // ASSERT: ch now contains the first character of the next token.
            if (OperationElement.OPERATORS.contains(c) && c != ')') {

                // push operatorTokens onto the output stack until
                // we reach an operator on the operator stack that has
                // lower priority than the current one.
                OperationElement operator;
                while (!operatorStack.isEmpty()) {
                    operator = operatorStack.peek();
                    if (operator.getPriority() >= OperationElement.getPriority(c) && operator.getOperator() != '(') {
                        operatorStack.pop();
                        returnStack.push(operator);
                    } else break;
                }
                operatorStack.push(new OperationElement(c));
                index++;

                if (c != '(') {
                    previousValue = false;
                    previousOperation = operatorStack.peek();
                }

            } else if (c == ')') {
                OperationElement operator;
                operator = operatorStack.pop();

                // This code does not handle operatorStack underflow.
                while (operator.getOperator() != '(') {

                    // pop operators off the stack until a LeftParen appears and
                    // place the operators on the output stack
                    returnStack.push(operator);
                    operator = operatorStack.pop();
                }
                index++;

            } else if (Character.isDigit(c)) {

                if (previousValue) throw new ArithmeticException(
                        "You cannot have two values without an operator between."
                );

                // We found a literal token
                int literal = c - '0';
                index++;

                while (index < theExpression.length()) {
                    c = theExpression.charAt(index);
                    if (Character.isDigit(c)) {
                        literal = (literal * 10) + (c - '0');
                        index++;
                    } else break;
                }

                // place the literal on the output stack
                returnStack.push(new LiteralElement(isNegative ? -literal : literal));

                previousValue = true;
                isNegative = false;
                previousOperation = null;

            } else if (Character.isAlphabetic(c)) {

                if (previousValue) throw new ArithmeticException(
                        "You cannot have two values without an operator between."
                );

                // We found a cell reference token
                CellElement cell = new CellElement(myCell.getSpreadSheet(), isNegative);
                index = CellElement.applyValues(theExpression, index, cell);
                returnStack.push(cell);

                previousValue = true;
                isNegative = false;
                previousOperation = null;

            } else throw new IllegalArgumentException("Invalid characters contained in formula!");

        }

        // pop all remaining operators off the operator stack
        while (!operatorStack.isEmpty()) returnStack.push(operatorStack.pop());

        return returnStack;
    }

    /**
     * Performs calculation on current expression tree.
     *
     * @return calculated value of the expression tree.
     */
    public int calculate() {
        return calculate(myHeaderNode);
    }

    // calculates recursively
    private int calculate(final Node node) {
        if (node == null) throw new IllegalArgumentException("The expression is invalid!");

        if (node.element instanceof OperationElement operationElement) {
            return operationElement.evaluate(calculate(node.left), calculate(node.right));
        } else {
            return ((ValueElement) node.element).getValue();
        }
    }

    /**
     * Helper Node class for the ExpressionTree.
     */
    private class Node {

        /**
         * The element in the Node.
         */
        Element element;

        /**
         * The right child of the Node.
         */
        Node right;

        /**
         * The left child of the Node.
         */
        Node left;

        /**
         * Constructor for the Node.
         * @param theElement The element within the Node.
         */
        public Node(final Element theElement) {
            element = theElement;
        }

        /**
         * A constructor for the Node when there are child Nodes.
         * @param theElement The element within the Node.
         * @param theRight The right child.
         * @param theLeft The left child.
         */
        public Node(final Element theElement, final Node theRight, final Node theLeft) {
            element = theElement;
            right = theRight;
            left = theLeft;
        }

    }

}
