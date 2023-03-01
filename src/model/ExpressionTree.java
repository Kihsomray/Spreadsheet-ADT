package model;

import model.element.Element;
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

    Set<Character> operators = Set.of('+', '-', '*', '/');

    public ExpressionTree(final String expression, final SpreadSheet ss) {
        headerNode = constructTree(expression, ss);
    }

    private Node constructTree(final String input, final SpreadSheet ss) {
        char[] charArray = input.toCharArray();
        Stack<Node> stack = new Stack<>();
        Node current = headerNode;
        List<Character> chars = new ArrayList<>();

        for (int i = 0; i < charArray.length; i++) {
            char c = charArray[i];

            if (c == '(') {
                stack.push(current);
                current = current.left = new Node(null, current);
            } else if (operators.contains(c)) {
                current.parent.element = new LiteralElement(Integer.parseInt(c + ""));
                stack.push(current);
                current = current.right = new Node(null, current);
            } else if (c == ')') {
                stack.pop();
            } else if (Utility.isAlphaNumerical(c)) {
                chars.add(c);
                if (charArray.length - 1 >= i || !Utility.isAlphaNumerical(charArray[i + 1])) {
                    current.parent.element = ValueElement.generateFromArray(chars.stream().map(val -> Character.toString(val)).collect(Collectors.joining()).toCharArray(), ss);
                    current.parent = stack.pop();
                    current = current.parent;
                    chars = new ArrayList<>();
                }
            }
        }



        return null;
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
