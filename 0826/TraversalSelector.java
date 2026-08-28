
public class TraversalSelector {

    static class ExpressionNode {

        String value;
        ExpressionNode left;
        ExpressionNode right;

        ExpressionNode(String value) {
            this.value = value;
        }

        ExpressionNode(String value, ExpressionNode left, ExpressionNode right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        boolean isLeaf() {
            return left == null && right == null;
        }
    }

    public static String toPrefix(ExpressionNode root) {
        if (root == null) {
            return "";
        }
        String left = toPrefix(root.left);
        String right = toPrefix(root.right);
        return root.value + (left.isEmpty() ? "" : " " + left) + (right.isEmpty() ? "" : " " + right);
    }

    public static String toInfix(ExpressionNode root) {
        if (root == null) {
            return "";
        }
        if (root.isLeaf()) {
            return root.value;
        }
        return "(" + toInfix(root.left) + " " + root.value + " " + toInfix(root.right) + ")";
    }

    public static String toPostfix(ExpressionNode root) {
        if (root == null) {
            return "";
        }
        String left = toPostfix(root.left);
        String right = toPostfix(root.right);
        return (left.isEmpty() ? "" : left + " ") + (right.isEmpty() ? "" : right + " ") + root.value;
    }

    public static void main(String[] args) {
        // 建構表達式: (A + (B * C)) - (D / E)
        ExpressionNode nodeBC = new ExpressionNode("*", new ExpressionNode("B"), new ExpressionNode("C"));
        ExpressionNode nodeLeft = new ExpressionNode("+", new ExpressionNode("A"), nodeBC);
        ExpressionNode nodeRight = new ExpressionNode("/", new ExpressionNode("D"), new ExpressionNode("E"));
        ExpressionNode root = new ExpressionNode("-", nodeLeft, nodeRight);

        System.out.println("前序 (前綴 Prefix):  " + toPrefix(root));
        System.out.println("中序 (中綴 Infix):   " + toInfix(root));
        System.out.println("後序 (後綴 Postfix): " + toPostfix(root));
    }
}
