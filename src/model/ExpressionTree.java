public class ExpressionTree<Element> {

    private class Node {
        Element element;

        Node right;
        Node left;

        public Node(Node right, Node left) {
            this.right = right;
            this.left = left;
        }

    }

}
