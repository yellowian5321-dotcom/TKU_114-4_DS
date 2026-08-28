
import java.util.ArrayList;
import java.util.List;

public class DirectoryTreeReport {

    static class FsNode {

        String name;
        boolean isDirectory;
        long fileSize; // 若為目錄則由計算得出
        List<FsNode> children = new ArrayList<>();

        FsNode(String name, long fileSize) {
            this.name = name;
            this.isDirectory = false;
            this.fileSize = fileSize;
        }

        FsNode(String name) {
            this.name = name;
            this.isDirectory = true;
            this.fileSize = 0;
        }

        void addChild(FsNode child) {
            if (this.isDirectory) {
                children.add(child);
            }
        }
    }

    static class Stats {

        int totalNodes = 0;
        int fileCount = 0;
        int dirCount = 0;
        long maxFileSize = 0;
        String maxFileName = "";
    }

    // 後序走訪：由下而上彙總容量
    public static long calculateSizesAndStats(FsNode node, Stats stats) {
        if (node == null) {
            return 0;
        }

        stats.totalNodes++;
        if (node.isDirectory) {
            stats.dirCount++;
            long dirTotal = 0;
            for (FsNode child : node.children) {
                dirTotal += calculateSizesAndStats(child, stats);
            }
            node.fileSize = dirTotal;
            return dirTotal;
        } else {
            stats.fileCount++;
            if (node.fileSize > stats.maxFileSize) {
                stats.maxFileSize = node.fileSize;
                stats.maxFileName = node.name;
            }
            return node.fileSize;
        }
    }

    public static int getHeight(FsNode node) {
        if (node == null) {
            return 0;
        }
        if (node.children.isEmpty()) {
            return 1;
        }
        int maxChildHeight = 0;
        for (FsNode child : node.children) {
            maxChildHeight = Math.max(maxChildHeight, getHeight(child));
        }
        return 1 + maxChildHeight;
    }

    public static void printHierarchy(FsNode node, String indent) {
        if (node == null) {
            return;
        }
        System.out.printf("%s[%s] %s (大小: %d bytes)%n", indent, node.isDirectory ? "DIR" : "FILE", node.name, node.fileSize);
        for (FsNode child : node.children) {
            printHierarchy(child, indent + "  ");
        }
    }

    public static void main(String[] args) {
        FsNode root = new FsNode("root");
        FsNode home = new FsNode("home");
        FsNode var = new FsNode("var");

        home.addChild(new FsNode("resume.pdf", 1200));
        home.addChild(new FsNode("photo.jpg", 4500));

        FsNode logDir = new FsNode("log");
        logDir.addChild(new FsNode("system.log", 8900));
        logDir.addChild(new FsNode("access.log", 3200));
        var.addChild(logDir);

        root.addChild(home);
        root.addChild(var);
        root.addChild(new FsNode("config.sys", 300));

        Stats stats = new Stats();
        calculateSizesAndStats(root, stats);

        System.out.println("=== 檔案目錄結構與容量 ===");
        printHierarchy(root, "");
        System.out.println("\n=== 統計報告 ===");
        System.out.println("總節點數: " + stats.totalNodes);
        System.out.println("檔案數量: " + stats.fileCount);
        System.out.println("目錄數量: " + stats.dirCount);
        System.out.println("樹的高度: " + getHeight(root));
        System.out.println("最大檔案: " + stats.maxFileName + " (" + stats.maxFileSize + " bytes)");
    }
}
