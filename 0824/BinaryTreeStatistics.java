
import java.util.NoSuchElementException;

public class BinaryTreeStatistics {

    static class Node {

        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    public static int size(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + size(root.left) + size(root.right);
    }

    public static int sum(Node root) {
        if (root == null) {
            return 0;
        }
        return root.val + sum(root.left) + sum(root.right);
    }

    public static int maximum(Node root) {
        if (root == null) {
            throw new NoSuchElementException("空樹無最大值");
        }
        return maxHelper(root);
    }

    private static int maxHelper(Node root) {
        int max = root.val;
        if (root.left != null) {
            max = Math.max(max, maxHelper(root.left));
        }
        if (root.right != null) {
            max = Math.max(max, maxHelper(root.right));
        }
        return max;
    }

    public static int leafCount(Node root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return leafCount(root.left) + leafCount(root.right);
    }

    public static int height(Node root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(height(root.left), height(root.right));
    }

    public static boolean contains(Node root, int val) {
        if (root == null) {
            return false;
        }
        if (root.val == val) {
            return true;
        }
        return contains(root.left, val) || contains(root.right, val);
    }

    public static void main(String[] args) {
        Node root = new Node(-5);
        root.left = new Node(-10);
        root.right = new Node(-3);
        root.left.left = new Node(-20);

        System.out.println("Size: " + size(root));
        System.out.println("Sum: " + sum(root));
        System.out.println("Max: " + maximum(root));
        System.out.println("Leaf count: " + leafCount(root));
        System.out.println("Height: " + height(root));
        System.out.println("Contains -10: " + contains(root, -10));
    }
}
