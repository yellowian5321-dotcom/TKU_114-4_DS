
import java.util.ArrayList;
import java.util.List;

public class BstRangeStatistics {

    static class Node {

        int val;
        Node left, right;

        Node(int val) {
            this.val = val;
        }
    }

    private Node root;

    public void insert(int val) {
        root = insertRec(root, val);
    }

    private Node insertRec(Node current, int val) {
        if (current == null) {
            return new Node(val);
        }
        if (val < current.val) {
            current.left = insertRec(current.left, val); 
        }else if (val > current.val) {
            current.right = insertRec(current.right, val);
        }
        return current;
    }

    public List<Integer> valuesBetween(int low, int high) {
        List<Integer> result = new ArrayList<>();
        if (low > high) {
            return result;
        }
        valuesBetweenRec(root, low, high, result);
        return result;
    }

    private void valuesBetweenRec(Node node, int low, int high, List<Integer> res) {
        if (node == null) {
            return;
        }
        if (node.val > low) {
            valuesBetweenRec(node.left, low, high, res);
        }
        if (node.val >= low && node.val <= high) {
            res.add(node.val);
        }
        if (node.val < high) {
            valuesBetweenRec(node.right, low, high, res);
        }
    }

    public int countBetween(int low, int high) {
        if (low > high) {
            return 0;
        }
        return countBetweenRec(root, low, high);
    }

    private int countBetweenRec(Node node, int low, int high) {
        if (node == null) {
            return 0;
        }
        int count = (node.val >= low && node.val <= high) ? 1 : 0;
        if (node.val > low) {
            count += countBetweenRec(node.left, low, high);
        }
        if (node.val < high) {
            count += countBetweenRec(node.right, low, high);
        }
        return count;
    }

    public int sumBetween(int low, int high) {
        if (low > high) {
            return 0;
        }
        return sumBetweenRec(root, low, high);
    }

    private int sumBetweenRec(Node node, int low, int high) {
        if (node == null) {
            return 0;
        }
        int sum = (node.val >= low && node.val <= high) ? node.val : 0;
        if (node.val > low) {
            sum += sumBetweenRec(node.left, low, high);
        }
        if (node.val < high) {
            sum += sumBetweenRec(node.right, low, high);
        }
        return sum;
    }

    public static void main(String[] args) {
        BstRangeStatistics bst = new BstRangeStatistics();
        int[] vals = {50, 20, 70, 10, 35, 60, 90, 30, 40};
        for (int v : vals) {
            bst.insert(v);
        }

        System.out.println("=== 正常範圍測試 [25, 65] ===");
        System.out.println("Values: " + bst.valuesBetween(25, 65));
        System.out.println("Count:  " + bst.countBetween(25, 65));
        System.out.println("Sum:    " + bst.sumBetween(25, 65));

        System.out.println("\n=== 空範圍測試 [100, 150] ===");
        System.out.println("Values: " + bst.valuesBetween(100, 150));
        System.out.println("Count:  " + bst.countBetween(100, 150));
        System.out.println("Sum:    " + bst.sumBetween(100, 150));

        System.out.println("\n=== 邊界反向測試 [80, 20] (low > high) ===");
        System.out.println("Values: " + bst.valuesBetween(80, 20));
        System.out.println("Count:  " + bst.countBetween(80, 20));
        System.out.println("Sum:    " + bst.sumBetween(80, 20));
    }
}
