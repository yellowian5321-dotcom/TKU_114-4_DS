
import java.util.*;

public class ListImplementationLab {

    public static void runListOperations(List<Integer> list) {
        list.add(10);
        list.add(20);
        list.add(30);
        list.add(1, 15); // 插入指定位置

        int searchIdx = list.indexOf(20);
        list.remove(Integer.valueOf(10)); // 刪除特定值

        int sum = 0;
        for (int num : list) {
            sum += num;
        }

        System.out.println("內容: " + list + ", 搜尋 20 索引: " + searchIdx + ", 總和: " + sum);
    }

    public static void main(String[] args) {
        System.out.println("--- ArrayList 測試 ---");
        List<Integer> arrayList = new ArrayList<>();
        runListOperations(arrayList);

        System.out.println("--- LinkedList 測試 ---");
        List<Integer> linkedList = new LinkedList<>();
        runListOperations(linkedList);

        System.out.println("\n--- 內部成本差異說明 ---");
        System.out.println("1. 尾端新增 (add): ArrayList 均攤 O(1)，擴容需搬移；LinkedList 為 O(1)。");
        System.out.println("2. 指定位置插入 (add(idx, val)): ArrayList 需連續位移記憶體 O(n)；LinkedList 需遍歷指標定位 O(n) 後 O(1) 插入。");
        System.out.println("3. 搜尋 (indexOf): 兩者皆為 O(n) 線性搜尋，但 ArrayList 具備快取行（Cache Line）局部性優勢。");
        System.out.println("4. 隨機存取 (get): ArrayList 支援 O(1) 隨機存取；LinkedList 需 O(n) 巡訪。");
    }
}
