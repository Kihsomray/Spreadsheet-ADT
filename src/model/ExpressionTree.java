package model;

import model.element.Element;
import model.element.OperationElement;
import model.element.value.CellElement;
import model.element.value.LiteralElement;
import model.element.value.ValueElement;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import java.util.stream.Collectors;

/**
 * 
 */
public class ExpressionTree {

    private final Node headerNode;

    Set<Character> operators = Set.of('+', '-', '*', '/', ')');

    public ExpressionTree(final String expression, final SpreadSheet ss) {
        headerNode = constructTree(expression, ss);
    }

    private Node constructTree(final String input, final SpreadSheet ss) {
        return null;
    }

    /**
     * getFormula
     *
     * Given a string that represents a formula that is an infix
     * expression, return a stack of Tokens so that the expression,
     * when read from the bottom of the stack to the top of the stack,
     * is a postfix expression.
     *
     * A formula is defined as a sequence of tokens that represents
     * a legal infix expression.
     *
     * A token can consist of a numeric literal, a cell reference, or an
     * operator (+, -, *, /).
     *
     * Multiplication (*) and division (/) have higher precedence than
     * addition (+) and subtraction (-).  Among operations within the same
     * level of precedence, grouping is from left to right.
     *
     * This algorithm follows the algorithm described in Weiss, pages 105-108.
     */
    private Stack<Element> expressionPostfix(final String formula, final SpreadSheet ss) {

        Stack<OperationElement> operatorStack = new Stack<>();
        Stack<Element> returnStack = new Stack<>();

        int index = 0;

        while (index < formula.length()) {
            char c = formula.charAt(index);

            // eliminate whitespace
            while (index < formula.length()) {
                if (Character.isWhitespace(formula.charAt(index))) break;
                index++;
            }

            if (index == formula.length()) throw new IndexOutOfBoundsException("Invalid formula passed for parsing");


            // ASSERT: ch now contains the first character of the next token.
            if (operators.contains(c)) {

                // push operatorTokens onto the output stack until
                // we reach an operator on the operator stack that has
                // lower priority than the current one.
                OperationElement operator;
                while (!operatorStack.isEmpty()) {
                    operator =  operatorStack.peek();
                    if (operator.getPriority() >= OperationElement.getPriority(c) && !operator.isParenthesis()) {
                        operatorStack.pop();
                        returnStack.push(operator);
                    } else break;
                }
                operatorStack.push(new OperationElement(c));
                index++;

            } else if (c == '(') {
                OperationElement operator;
                operator = operatorStack.pop();

                // This code does not handle operatorStack underflow.
                while (!operator.isParenthesis()) {

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

                while (index < formula.length()) {
                    if (Character.isDigit(c)) {
                        literal = (literal * 10) + (c - '0');
                        index++;
                    } else break;
                }

                // place the literal on the output stack
                returnStack.push(new LiteralElement(literal));

            } else if (Character.isAlphabetic(c)) {

                // We found a cell reference token
                CellElement cell = new CellElement(ss);
                index = CellElement.applyValues(formula, index, cell);
                returnStack.push(cell);

            } else throw new IllegalArgumentException("Invalid characters contained in formula");
        }

        // pop all remaining operators off the operator stack
        while (!operatorStack.isEmpty()) returnStack.push(operatorStack.pop());

        return returnStack;
    }

    private class Node {
        Element element;
        Node parent;

        Node right;
        Node left;

        public Node(Element element, Node parent) {
            this.element = element;
            this.parent = parent;
        }

    }

}
