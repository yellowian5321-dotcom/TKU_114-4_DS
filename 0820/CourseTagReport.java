
import java.util.*;

public class CourseTagReport {

    public static void main(String[] args) {
        String[] rawTags = {"Java", "Web", "DB", "Java", "AI", "Web", "Java"};

        List<String> originalOrderList = new ArrayList<>();
        Set<String> uniqueTagsSet = new LinkedHashSet<>();
        Map<String, Integer> tagCountMap = new LinkedHashMap<>();

        for (String tag : rawTags) {
            originalOrderList.add(tag);
            uniqueTagsSet.add(tag);
            tagCountMap.put(tag, tagCountMap.getOrDefault(tag, 0) + 1);
        }

        System.out.println("1. List (保留原始順序與重複資料): " + originalOrderList);
        System.out.println("2. Set (儲存不重複的標籤集合): " + uniqueTagsSet);
        System.out.println("3. Map (統計每個標籤出現頻率): " + tagCountMap);
    }
}
