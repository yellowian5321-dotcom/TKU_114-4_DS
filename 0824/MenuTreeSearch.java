
public class MenuTreeSearch {

    static class MenuItem {

        String name;
        MenuItem left, right;

        MenuItem(String name) {
            this.name = name;
        }
    }

    public static boolean contains(MenuItem root, String target) {
        if (root == null) {
            return false;
        }
        if (root.name.equals(target)) {
            return true;
        }
        return contains(root.left, target) || contains(root.right, target);
    }

    public static int findDepth(MenuItem root, String target) {
        return depthHelper(root, target, 0);
    }

    private static int depthHelper(MenuItem root, String target, int depth) {
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

    public static int countLeaves(MenuItem root) {
        if (root == null) {
            return 0;
        }
        if (root.left == null && root.right == null) {
            return 1;
        }
        return countLeaves(root.left) + countLeaves(root.right);
    }

    public static void printPreOrder(MenuItem root, String indent) {
        if (root == null) {
            return;
        }
        System.out.println(indent + "- " + root.name);
        printPreOrder(root.left, indent + "  ");
        printPreOrder(root.right, indent + "  ");
    }

    public static void main(String[] args) {
        MenuItem menu = new MenuItem("檔案");
        menu.left = new MenuItem("開啟");
        menu.right = new MenuItem("匯出");
        menu.left.left = new MenuItem("從雲端開啟");
        menu.left.right = new MenuItem("從本機開啟");
        menu.right.left = new MenuItem("PDF");
        menu.right.right = new MenuItem("PNG");

        System.out.println("目錄結構：");
        printPreOrder(menu, "");

        System.out.println("\nContains 'PDF': " + contains(menu, "PDF"));
        System.out.println("Contains '刪除': " + contains(menu, "刪除"));
        System.out.println("Depth '從雲端開啟': " + findDepth(menu, "從雲端開啟"));
        System.out.println("Depth '未命名': " + findDepth(menu, "未命名"));
        System.out.println("Leaves Count: " + countLeaves(menu));
    }
}
