
import java.util.LinkedList;
import java.util.Queue;

public class LevelOrderByLine {

    static class Node {

        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    public static void printLevelOrder(Node root) {
        if (root == null) {
            System.out.println("樹為空");
            return;
        }

        Queue<Node> queue = new LinkedList<>();
        queue.offer(root);
        int level = 1;

        while (!queue.isEmpty()) {
            int levelSize = queue.size();
            System.out.print("Level " + level + " (節點數 " + levelSize + "): ");

            for (int i = 0; i < levelSize; i++) {
                Node curr = queue.poll();
                System.out.print(curr.val + " ");
                if (curr.left != null) {
                    queue.offer(curr.left);
                }
                if (curr.right != null) {
                    queue.offer(curr.right);
                }
            }
            System.out.println();
            level++;
        }
    }

    public static void main(String[] args) {
        System.out.println("--- 測試空樹 ---");
        printLevelOrder(null);

        System.out.println("\n--- 測試多層樹 ---");
        Node root = new Node(10);
        root.left = new Node(20);
        root.right = new Node(30);
        root.left.left = new Node(40);
        root.right.left = new Node(50);
        root.right.right = new Node(60);
        printLevelOrder(root);
    }
}
