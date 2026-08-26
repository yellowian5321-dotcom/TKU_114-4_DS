
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Q10_BstDirectory {

    private static class Node {

        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    private Node root;
    private int size = 0;

    public boolean add(int value) {
        if (contains(value)) {
            return false;
        }
        root = addHelper(root, value);
        size++;
        return true;
    }

    private Node addHelper(Node node, int value) {
        if (node == null) {
            return new Node(value);
        }
        if (value < node.val) {
            node.left = addHelper(node.left, value); 
        }else if (value > node.val) {
            node.right = addHelper(node.right, value);
        }
        return node;
    }

    public boolean contains(int value) {
        Node curr = root;
        while (curr != null) {
            if (value == curr.val) {
                return true;
            }
            curr = (value < curr.val) ? curr.left : curr.right;
        }
        return false;
    }

    public int size() {
        return size;
    }

    public List<Integer> searchPath(int target) {
        List<Integer> path = new ArrayList<>();
        Node curr = root;
        while (curr != null) {
            path.add(curr.val);
            if (target == curr.val) {
                break;
            }
            curr = (target < curr.val) ? curr.left : curr.right;
        }
        return path;
    }

    public List<Integer> inorder() {
        if (root == null) {
            return Collections.emptyList();
        }
        List<Integer> list = new ArrayList<>();
        inorderHelper(root, list);
        return list;
    }

    private void inorderHelper(Node node, List<Integer> list) {
        if (node == null) {
            return;
        }
        inorderHelper(node.left, list);
        list.add(node.val);
        inorderHelper(node.right, list);
    }

    public boolean isValid() {
        return validate(root, null, null);
    }

    private boolean validate(Node node, Integer low, Integer high) {
        if (node == null) {
            return true;
        }
        if ((low != null && node.val <= low) || (high != null && node.val >= high)) {
            return false;
        }
        return validate(node.left, low, node.val) && validate(node.right, node.val, high);
    }
}
