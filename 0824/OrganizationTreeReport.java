
import java.util.*;

public class OrganizationTreeReport {

    static class OrgNode {

        String name;
        OrgNode left, right;

        OrgNode(String name) {
            this.name = name;
        }
    }

    public static String findParent(OrgNode root, String target) {
        if (root == null || root.name.equals(target)) {
            return null;
        }
        if ((root.left != null && root.left.name.equals(target))
                || (root.right != null && root.right.name.equals(target))) {
            return root.name;
        }
        String leftSearch = findParent(root.left, target);
        if (leftSearch != null) {
            return leftSearch;
        }
        return findParent(root.right, target);
    }

    public static int findDepth(OrgNode root, String target) {
        return depthHelper(root, target, 0);
    }

    private static int depthHelper(OrgNode root, String target, int depth) {
        if (root == null) {
            return -1;
        }
        if (root.name.equals(target)) {
            return depth;
        }
        int left = depthHelper(root.left, target, depth + 1);
        if (left != -1) {
            return left;
        }
        return depthHelper(root.right, target, depth + 1);
    }

    public static List<String> pathFromRoot(OrgNode root, String target) {
        List<String> path = new ArrayList<>();
        findPath(root, target, path);
        return path;
    }

    private static boolean findPath(OrgNode root, String target, List<String> path) {
        if (root == null) {
            return false;
        }
        path.add(root.name);
        if (root.name.equals(target)) {
            return true;
        }
        if (findPath(root.left, target, path) || findPath(root.right, target, path)) {
            return true;
        }
        path.remove(path.size() - 1);
        return false;
    }

    public static void printByLevel(OrgNode root) {
        if (root == null) {
            System.out.println("組織樹為空");
            return;
        }
        Queue<OrgNode> q = new LinkedList<>();
        q.offer(root);
        int level = 1;
        while (!q.isEmpty()) {
            int size = q.size();
            System.out.print("階層 " + level + ": ");
            for (int i = 0; i < size; i++) {
                OrgNode curr = q.poll();
                System.out.print(curr.name + " ");
                if (curr.left != null) {
                    q.offer(curr.left);
                }
                if (curr.right != null) {
                    q.offer(curr.right);
                }
            }
            System.out.println();
            level++;
        }
    }

    public static void main(String[] args) {
        OrgNode ceo = new OrgNode("CEO");
        ceo.left = new OrgNode("VP-Tech");
        ceo.right = new OrgNode("VP-Sales");
        ceo.left.left = new OrgNode("Dev-Team");
        ceo.left.right = new OrgNode("QA-Team");

        System.out.println("=== 組織架構階層 ===");
        printByLevel(ceo);

        System.out.println("\nParent of Dev-Team: " + findParent(ceo, "Dev-Team"));
        System.out.println("Parent of CEO: " + findParent(ceo, "CEO"));
        System.out.println("Parent of Unknown: " + findParent(ceo, "Unknown"));

        System.out.println("Depth of QA-Team: " + findDepth(ceo, "QA-Team"));
        System.out.println("Path to Dev-Team: " + pathFromRoot(ceo, "Dev-Team"));
        System.out.println("Path to Unknown: " + pathFromRoot(ceo, "Unknown"));
    }
}
