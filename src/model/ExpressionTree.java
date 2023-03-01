package model;

import model.element.Element;
import model.element.value.LiteralElement;

import java.util.Set;
import java.util.Stack;

public class ExpressionTree {

    private final Node headerNode;

    Set<Character> operators = Set.of('+', '-', '*', '/');

    public ExpressionTree(final String expression) {
        headerNode = constructTree(expression);
    }


    private Node constructTree(final String input) {
        char[] charArray = input.toCharArray();
        Stack<Node> stack = new Stack<>();
        Node current = headerNode;
        StringBuilder sb = new StringBuilder();

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
                if (c == ' ') continue;
                sb.append(c);
                if (charArray.length - 1 >= i && Utility.isAlphaNumerical(charArray[i + 1])) {
                    continue;
                }
                String s = sb.toString();
                try {

                } catch (final Exception e) {
                    // still working on it
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
