
import java.util.*;

public class CollectionChoiceReport {

    public static void main(String[] args) {
        System.out.println("================ 集合選擇報告與實作 ================\n");

        // 需求 1: 保留搜尋記錄並允許重複
        System.out.println("1. 需求：保留搜尋記錄並允許重複");
        System.out.println("   選擇：List<String> (實作：ArrayList)");
        System.out.println("   原因：保持插入順序、支援重複值、尾端插入 O(1)。");
        List<String> searchHistory = new ArrayList<>();
        searchHistory.add("Java 教學");
        searchHistory.add("資料結構");
        searchHistory.add("Java 教學");
        System.out.println("   操作結果: " + searchHistory + "\n");

        // 需求 2: 儲存不重複的會員號碼
        System.out.println("2. 需求：儲存不重複的會員號碼");
        System.out.println("   選擇：Set<String> (實作：HashSet)");
        System.out.println("   原因：保證元素唯一性、新增與查詢均攤 O(1)。");
        Set<String> memberIds = new HashSet<>();
        memberIds.add("M1001");
        memberIds.add("M1002");
        memberIds.add("M1001"); // 重複略過
        System.out.println("   操作結果: " + memberIds + "\n");

        // 需求 3: 以學號查詢成績
        System.out.println("3. 需求：以學號查詢成績");
        System.out.println("   選擇：Map<String, Integer> (實作：HashMap)");
        System.out.println("   原因：提供 Key-Value 映射，以學號進行快速 O(1) 檢索。");
        Map<String, Integer> studentScores = new HashMap<>();
        studentScores.put("S101", 95);
        studentScores.put("S102", 88);
        System.out.println("   操作結果: S101 的成績為 " + studentScores.get("S101") + " 分\n");

        // 需求 4: 依照順序處理印刷工作
        System.out.println("4. 需求：依照順序處理印刷工作 (FIFO)");
        System.out.println("   選擇：Queue<String> (實作：ArrayDeque)");
        System.out.println("   原因：標準先進先出排程，無節點配置開銷且無容量限制。");
        Queue<String> printJobs = new ArrayDeque<>();
        printJobs.offer("Doc1.pdf");
        printJobs.offer("Doc2.pdf");
        System.out.println("   操作結果: 處理列印 -> " + printJobs.poll() + "，剩餘隊列: " + printJobs + "\n");

        // 需求 5: 最近多次操作 (LIFO)
        System.out.println("5. 需求：最近多次操作 (Undo/堆疊)");
        System.out.println("   選擇：Deque<String> (實作：ArrayDeque 作為 Stack)");
        System.out.println("   原因：後進先出存取，效能優於古老的 Vector-based Stack。");
        Deque<String> recentActions = new ArrayDeque<>();
        recentActions.push("輸入文字");
        recentActions.push("變更字體顏色");
        System.out.println("   操作結果: 撤銷最近操作 -> " + recentActions.pop() + "，剩餘棧: " + recentActions);
    }
}
