
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Q11_BstHashDirectory {

    private static class Node {

        int id;
        Node left;
        Node right;

        Node(int id) {
            this.id = id;
        }
    }

    private Node root = null;
    private final Map<Integer, String> hashIndex = new HashMap<>();

    public boolean add(int id, String name) {
        if (id <= 0 || name == null) {
            return false;
        }
        String trimmed = name.trim();
        if (trimmed.isEmpty()) {
            return false;
        }
        if (hashIndex.containsKey(id)) {
            return false;
        }

        hashIndex.put(id, trimmed);
        root = insert(root, id);
        return true;
    }

    private Node insert(Node current, int id) {
        if (current == null) {
            return new Node(id);
        }
        if (id < current.id) {
            current.left = insert(current.left, id);
        } else if (id > current.id) {
            current.right = insert(current.right, id);
        }
        return current;
    }

    public String findName(int id) {
        return hashIndex.get(id);
    }

    public boolean remove(int id) {
        if (!hashIndex.containsKey(id)) {
            return false;
        }
        hashIndex.remove(id);
        root = delete(root, id);
        return true;
    }

    private Node delete(Node current, int id) {
        if (current == null) {
            return null;
        }

        if (id < current.id) {
            current.left = delete(current.left, id);
        } else if (id > current.id) {
            current.right = delete(current.right, id);
        } else {
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }

            Node minNode = findMin(current.right);
            current.id = minNode.id;
            current.right = delete(current.right, minNode.id);
        }
        return current;
    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }

    public List<Integer> idsBetween(int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low > high) {
            return result;
        }
        inOrderRange(root, low, high, result);
        return result;
    }

    private void inOrderRange(Node node, int low, int high, List<Integer> result) {
        if (node == null) {
            return;
        }
        if (node.id > low) {
            inOrderRange(node.left, low, high, result);
        }
        if (node.id >= low && node.id <= high) {
            result.add(node.id);
        }
        if (node.id < high) {
            inOrderRange(node.right, low, high, result);
        }
    }

    public int size() {
        return hashIndex.size();
    }
}
