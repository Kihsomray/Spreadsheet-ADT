package model;

import model.element.Element;
import model.element.OperationElement;
import model.element.value.CellElement;
import model.element.value.LiteralElement;
import model.element.value.ValueElement;

import java.util.Stack;

/**
 * Expression Tree Class: Used for parsing various
 * expressions with easy recalculation.
 *
 * @author Michael N Yarmoshik
 * @version 1.0.0
 */
public class ExpressionTree {

    private final Node myHeaderNode;
    private final Cell myCell;

    /**
     * Generates an expression tree given a formula and spreadsheet.
     *
     * @param theExpression Expression to parse
     * @param theCell Parent cell of this tree
     */
    public ExpressionTree(final String theExpression, final Cell theCell) {
        myHeaderNode = constructTree(expressionPostfix(theExpression), theCell);
        myCell = theCell;
    }

    /**
     * Constructs a tree from a stack based post-fix expression.
     *
     * @param theStack Postfix expression stack
     * @param theCell Cell that contains this tree
     * @return Converted tree from stack
     */
    private Node constructTree(final Stack<Element> theStack, final Cell theCell) {
        if (theStack.isEmpty()) throw new IndexOutOfBoundsException("Expression cannot be empty");

        final Element element = theStack.pop();  // need to handle stack underflow
        if (element instanceof ValueElement) {
            if (element instanceof CellElement) {
                ((CellElement) element).getCell().addDependency(theCell);
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
    private Stack<Element> expressionPostfix(final String theExpression) {

        Stack<OperationElement> operatorStack = new Stack<>();
        Stack<Element> returnStack = new Stack<>();

        int index = 0;

        while (index < theExpression.length()) {

            // eliminate whitespace
            while (index < theExpression.length()) {
                if (!Character.isWhitespace(theExpression.charAt(index))) break;
                index++;
            }

            if (index == theExpression.length()) break;

            char c = theExpression.charAt(index);

            // ASSERT: ch now contains the first character of the next token.
            if (OperationElement.OPERATORS.contains(c) && c != ')') {

                // push operatorTokens onto the output stack until
                // we reach an operator on the operator stack that has
                // lower priority than the current one.
                OperationElement operator;
                while (!operatorStack.isEmpty()) {
                    operator =  operatorStack.peek();
                    if (operator.getPriority() >= OperationElement.getPriority(c) && operator.getOperator() != '(') {
                        operatorStack.pop();
                        returnStack.push(operator);
                    } else break;
                }
                operatorStack.push(new OperationElement(c));
                index++;

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

                System.out.println(literal);

                // place the literal on the output stack
                returnStack.push(new LiteralElement(literal));

            } else if (Character.isAlphabetic(c)) {

                // We found a cell reference token
                CellElement cell = new CellElement(myCell.getSpreadSheet());
                index = CellElement.applyValues(theExpression, index, cell);
                returnStack.push(cell);

            } else throw new IllegalArgumentException("Invalid characters contained in formula");

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
        System.out.println(node.element);
        if (node.element instanceof OperationElement) {
            return ((OperationElement) node.element).evaluate(calculate(node.left), calculate(node.right));
        } else {
            return ((ValueElement) node.element).getValue();
        }
    }

    // Helper class for Expression Tree
    private class Node {
        Element element;
        Node right;
        Node left;

        public Node(final Element theElement) {
            element = theElement;
        }

        public Node(final Element theElement, final Node theRight, final Node theLeft) {
            element = theElement;
            right = theRight;
            left = theLeft;
        }

    }

}
