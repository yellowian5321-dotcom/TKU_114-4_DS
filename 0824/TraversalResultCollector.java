
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class TraversalResultCollector {

    static class Node {

        String val;
        Node left, right;

        Node(String val) {
            this.val = val;
        }
    }

    public static List<String> preOrder(Node root) {
        List<String> res = new ArrayList<>();
        preOrderHelper(root, res);
        return res;
    }

    private static void preOrderHelper(Node root, List<String> res) {
        if (root == null) {
            return;
        }
        res.add(root.val);
        preOrderHelper(root.left, res);
        preOrderHelper(root.right, res);
    }

    public static List<String> inOrder(Node root) {
        List<String> res = new ArrayList<>();
        inOrderHelper(root, res);
        return res;
    }

    private static void inOrderHelper(Node root, List<String> res) {
        if (root == null) {
            return;
        }
        inOrderHelper(root.left, res);
        res.add(root.val);
        inOrderHelper(root.right, res);
    }

    public static List<String> postOrder(Node root) {
        List<String> res = new ArrayList<>();
        postOrderHelper(root, res);
        return res;
    }

    private static void postOrderHelper(Node root, List<String> res) {
        if (root == null) {
            return;
        }
        postOrderHelper(root.left, res);
        postOrderHelper(root.right, res);
        res.add(root.val);
    }

    public static List<String> levelOrder(Node root) {
        List<String> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        while (!queue.isEmpty()) {
            Node curr = queue.poll();
            res.add(curr.val);
            if (curr.left != null) {
                queue.offer(curr.left);
            }
            if (curr.right != null) {
                queue.offer(curr.right);
            }
        }
        return res;
    }

    public static void test(String name, Node root) {
        System.out.println("=== " + name + " ===");
        System.out.println("PreOrder:   " + preOrder(root));
        System.out.println("InOrder:    " + inOrder(root));
        System.out.println("PostOrder:  " + postOrder(root));
        System.out.println("LevelOrder: " + levelOrder(root));
        System.out.println();
    }

    public static void main(String[] args) {
        test("空樹", null);
        test("單節點樹", new Node("A"));

        Node leftSkewed = new Node("A");
        leftSkewed.left = new Node("B");
        leftSkewed.left.left = new Node("C");
        test("左偏樹", leftSkewed);

        Node completeTree = new Node("A");
        completeTree.left = new Node("B");
        completeTree.right = new Node("C");
        completeTree.left.left = new Node("D");
        completeTree.left.right = new Node("E");
        test("完整樹", completeTree);
    }
}
