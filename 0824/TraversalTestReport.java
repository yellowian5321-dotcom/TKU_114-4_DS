
import java.util.*;

public class TraversalTestReport {

    static class Node {

        String val;
        Node left, right;

        Node(String val) {
            this.val = val;
        }
    }

    public static List<String> preOrder(Node root) {
        List<String> res = new ArrayList<>();
        preHelper(root, res);
        return res;
    }

    private static void preHelper(Node root, List<String> res) {
        if (root == null) {
            return;
        }
        res.add(root.val);
        preHelper(root.left, res);
        preHelper(root.right, res);
    }

    public static List<String> inOrder(Node root) {
        List<String> res = new ArrayList<>();
        inHelper(root, res);
        return res;
    }

    private static void inHelper(Node root, List<String> res) {
        if (root == null) {
            return;
        }
        inHelper(root.left, res);
        res.add(root.val);
        inHelper(root.right, res);
    }

    public static List<String> postOrder(Node root) {
        List<String> res = new ArrayList<>();
        postHelper(root, res);
        return res;
    }

    private static void postHelper(Node root, List<String> res) {
        if (root == null) {
            return;
        }
        postHelper(root.left, res);
        postHelper(root.right, res);
        res.add(root.val);
    }

    public static List<String> levelOrder(Node root) {
        List<String> res = new ArrayList<>();
        if (root == null) {
            return res;
        }
        Queue<Node> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            Node curr = q.poll();
            res.add(curr.val);
            if (curr.left != null) {
                q.offer(curr.left);
            }
            if (curr.right != null) {
                q.offer(curr.right);
            }
        }
        return res;
    }

    public static void runReport(String treeName, Node root,
            List<String> expPre, List<String> expIn,
            List<String> expPost, List<String> expLevel) {
        System.out.println("--------------------------------------------------");
        System.out.println("測試項目: " + treeName);
        verify("Pre-order  ", preOrder(root), expPre);
        verify("In-order   ", inOrder(root), expIn);
        verify("Post-order ", postOrder(root), expPost);
        verify("Level-order", levelOrder(root), expLevel);
    }

    private static void verify(String type, List<String> actual, List<String> expected) {
        boolean match = actual.equals(expected);
        System.out.printf("%s -> 實際: %-15s | 預期: %-15s | 相符: %s\n",
                type, actual, expected, match ? "PASS" : "FAIL");
    }

    public static void main(String[] args) {
        // 1. 空樹
        runReport("1. 空樹", null,
                List.of(), List.of(), List.of(), List.of());

        // 2. 單節點
        runReport("2. 單節點樹", new Node("A"),
                List.of("A"), List.of("A"), List.of("A"), List.of("A"));

        // 3. 唯左樹
        Node leftTree = new Node("A");
        leftTree.left = new Node("B");
        leftTree.left.left = new Node("C");
        runReport("3. 唯左偏樹", leftTree,
                List.of("A", "B", "C"), List.of("C", "B", "A"), List.of("C", "B", "A"), List.of("A", "B", "C"));

        // 4. 唯右樹
        Node rightTree = new Node("A");
        rightTree.right = new Node("B");
        rightTree.right.right = new Node("C");
        runReport("4. 唯右偏樹", rightTree,
                List.of("A", "B", "C"), List.of("A", "B", "C"), List.of("C", "B", "A"), List.of("A", "B", "C"));

        // 5. 完全二元樹
        Node complete = new Node("A");
        complete.left = new Node("B");
        complete.right = new Node("C");
        complete.left.left = new Node("D");
        complete.left.right = new Node("E");
        complete.right.left = new Node("F");
        runReport("5. 完全二元樹", complete,
                List.of("A", "B", "D", "E", "C", "F"),
                List.of("D", "B", "E", "A", "F", "C"),
                List.of("D", "E", "B", "F", "C", "A"),
                List.of("A", "B", "C", "D", "E", "F"));

        // 6. 不規則樹
        Node irregular = new Node("A");
        irregular.left = new Node("B");
        irregular.left.right = new Node("C");
        irregular.right = new Node("D");
        irregular.right.left = new Node("E");
        runReport("6. 不規則樹", irregular,
                List.of("A", "B", "C", "D", "E"),
                List.of("B", "C", "A", "E", "D"),
                List.of("C", "B", "E", "D", "A"),
                List.of("A", "B", "D", "C", "E"));
    }
}
