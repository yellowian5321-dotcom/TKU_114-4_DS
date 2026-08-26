
import java.util.ArrayList;
import java.util.List;

public class FolderSizeTree {

    static class FolderNode {

        String name;
        long ownSize;
        FolderNode left, right;

        FolderNode(String name, long ownSize) {
            this.name = name;
            this.ownSize = ownSize;
        }
    }

    static class SubtreeResult {

        long totalSize;
        String maxFolderName;
        long maxFolderSize;

        SubtreeResult(long totalSize, String maxFolderName, long maxFolderSize) {
            this.totalSize = totalSize;
            this.maxFolderName = maxFolderName;
            this.maxFolderSize = maxFolderSize;
        }
    }

    public static SubtreeResult calculateSizes(FolderNode root) {
        if (root == null) {
            return new SubtreeResult(0, null, 0);
        }

        SubtreeResult leftRes = calculateSizes(root.left);
        SubtreeResult rightRes = calculateSizes(root.right);

        long currentTotal = root.ownSize + leftRes.totalSize + rightRes.totalSize;

        String maxName = root.name;
        long maxSize = currentTotal;

        if (leftRes.maxFolderSize > maxSize) {
            maxSize = leftRes.maxFolderSize;
            maxName = leftRes.maxFolderName;
        }
        if (rightRes.maxFolderSize > maxSize) {
            maxSize = rightRes.maxFolderSize;
            maxName = rightRes.maxFolderName;
        }

        return new SubtreeResult(currentTotal, maxName, maxSize);
    }

    public static void getLeafFolders(FolderNode root, List<String> leaves) {
        if (root == null) {
            return;
        }
        if (root.left == null && root.right == null) {
            leaves.add(root.name + " (" + root.ownSize + " bytes)");
            return;
        }
        getLeafFolders(root.left, leaves);
        getLeafFolders(root.right, leaves);
    }

    public static void main(String[] args) {
        FolderNode root = new FolderNode("Root", 100);
        root.left = new FolderNode("Docs", 200);
        root.right = new FolderNode("Media", 500);
        root.left.left = new FolderNode("Work", 300);
        root.right.left = new FolderNode("Music", 400);
        root.right.right = new FolderNode("Videos", 1200);

        SubtreeResult res = calculateSizes(root);
        System.out.println("全樹總大小: " + res.totalSize + " bytes");
        System.out.println("容量最大的子樹根目錄: " + res.maxFolderName + " (子樹總計 " + res.maxFolderSize + " bytes)");

        List<String> leaves = new ArrayList<>();
        getLeafFolders(root, leaves);
        System.out.println("葉子資料夾清單: " + leaves);
    }
}
