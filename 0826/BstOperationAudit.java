
import java.util.ArrayList;
import java.util.List;

public class BstOperationAudit {

    static class Node {

        int key;
        Node left, right;

        Node(int key) {
            this.key = key;
        }
    }

    private Node root;

    public boolean insert(int key) {
        int initialSize = size();
        root = insertRec(root, key);
        boolean success = size() > initialSize;
        audit("INSERT " + key, success);
        return success;
    }

    private Node insertRec(Node current, int key) {
        if (current == null) {
            return new Node(key);
        }
        if (key < current.key) {
            current.left = insertRec(current.left, key); 
        }else if (key > current.key) {
            current.right = insertRec(current.right, key);
        }
        return current;
    }

    public boolean delete(int key) {
        int initialSize = size();
        root = deleteRec(root, key);
        boolean success = size() < initialSize;
        audit("DELETE " + key, success);
        return success;
    }

    private Node deleteRec(Node current, int key) {
        if (current == null) {
            return null;
        }
        if (key < current.key) {
            current.left = deleteRec(current.left, key);
        } else if (key > current.key) {
            current.right = deleteRec(current.right, key);
        } else {
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }
            Node minNode = findMin(current.right);
            current.key = minNode.key;
            current.right = deleteRec(current.right, minNode.key);
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public int size() {
        return sizeRec(root);
    }

    private int sizeRec(Node n) {
        return n == null ? 0 : 1 + sizeRec(n.left) + sizeRec(n.right);
    }

    public int height() {
        return heightRec(root);
    }

    private int heightRec(Node n) {
        return n == null ? 0 : 1 + Math.max(heightRec(n.left), heightRec(n.right));
    }

    public List<Integer> inorder() {
        List<Integer> list = new ArrayList<>();
        inorderRec(root, list);
        return list;
    }

    private void inorderRec(Node n, List<Integer> list) {
        if (n == null) {
            return;
        }
        inorderRec(n.left, list);
        list.add(n.key);
        inorderRec(n.right, list);
    }

    public boolean isValidBST() {
        return isValidBSTRec(root, Long.MIN_VALUE, Long.MAX_VALUE);
    }

    private boolean isValidBSTRec(Node n, long min, long max) {
        if (n == null) {
            return true;
        }
        if (n.key <= min || n.key >= max) {
            return false;
        }
        return isValidBSTRec(n.left, min, n.key) && isValidBSTRec(n.right, n.key, max);
    }

    private void audit(String op, boolean result) {
        System.out.printf("[Audit] Op: %-12s | Result: %-5s | Inorder: %-20s | Size: %d | Height: %d | Valid: %s%n",
                op, result ? "OK" : "FAIL", inorder(), size(), height(), isValidBST());
    }

    public static void main(String[] args) {
        BstOperationAudit tree = new BstOperationAudit();
        tree.insert(50);
        tree.insert(30);
        tree.insert(70);
        tree.insert(20);
        tree.insert(40);
        tree.insert(60);
        tree.insert(80);
        tree.insert(30); // 測試重複插入
        tree.delete(100); // 測試刪除缺失節點
        tree.delete(20);  // 刪除葉節點
        tree.delete(30);  // 刪除具有一個子節點/兩個子節點的情境
        tree.delete(50);  // 刪除根節點 (兩個子節點)
    }
}
