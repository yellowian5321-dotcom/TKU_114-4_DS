
import java.util.*;

public class EnrollmentCleanup {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>(Arrays.asList(
                "Alice", "Bob", null, "Charlie", "  ", "Bob", "David", null, "Alice", ""
        ));

        System.out.println("清理前名單: " + list);

        // 使用 Iterator 移除 null 與空白字串
        Iterator<String> it = list.iterator();
        while (it.hasNext()) {
            String s = it.next();
            if (s == null || s.trim().isEmpty()) {
                it.remove();
            }
        }

        System.out.println("清理後名單 (已移除無效值): " + list);

        // 使用 Set 找出重複的項目
        Set<String> seen = new HashSet<>();
        Set<String> duplicates = new LinkedHashSet<>();
        for (String name : list) {
            if (!seen.add(name)) {
                duplicates.add(name);
            }
        }

        System.out.println("重複名稱報告: " + duplicates);
    }
}
