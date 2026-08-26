
import java.util.ArrayList;
import java.util.List;

public class BinaryTreeStructureReport {

    static class TreeNode {

        int val;
        TreeNode left, right;

        TreeNode(int val) {
            this.val = val;
        }
    }

    public static int size(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + size(root.left) + size(root.right);
    }

    public static int leafCount(TreeNode root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return leafCount(root.left) + leafCount(root.right);
    }

    public static int height(TreeNode root) {
        if (root == null) {
            return 0;
        }
        return 1 + Math.max(height(root.left), height(root.right));
    }

    public static void collectLeaves(TreeNode root, List<Integer> leaves) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            leaves.add(root.val);
        }
        collectLeaves(root.left, leaves);
        collectLeaves(root.right, leaves);
    }

    public static void report(String name, TreeNode root) {
        System.out.println("=== " + name + " ===");
        System.out.println("Root: " + (root != null ? root.val : "None"));
        List<Integer> leaves = new ArrayList<>();
        collectLeaves(root, leaves);
        System.out.println("Leaves: " + leaves);
        System.out.println("Size: " + size(root));
        System.out.println("Leaf Count: " + leafCount(root));
        System.out.println("Height: " + height(root));
        System.out.println();
    }

    public static void main(String[] args) {
        report("空樹", null);
        report("單節點樹", new TreeNode(10));

        // 7 個節點的樹
        TreeNode root = new TreeNode(1);
        root.left = new TreeNode(2);
        root.right = new TreeNode(3);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(5);
        root.right.left = new TreeNode(6);
        root.right.right = new TreeNode(7);
        report("7 節點樹", root);
    }
}
