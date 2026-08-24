
import java.util.*;
import java.util.stream.Collectors;

class StudentRecord {

    private final String studentId;
    private int score;
    private final String tag;

    public StudentRecord(String studentId, int score, String tag) {
        this.studentId = studentId;
        this.score = score;
        this.tag = (tag == null) ? "" : tag.trim();
    }

    public String getStudentId() {
        return studentId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getTag() {
        return tag;
    }

    @Override
    public String toString() {
        return String.format("[學號:%s, 成績:%d, 標籤:'%s']", studentId, score, tag);
    }
}

public class CourseCollectionManager {

    private final List<StudentRecord> list = new ArrayList<>();
    private final Map<String, StudentRecord> map = new LinkedHashMap<>();
    private final Set<String> tagSet = new HashSet<>();

    public void addRecord(StudentRecord record) {
        if (record == null) {
            return;
        }
        list.add(record);
        map.put(record.getStudentId(), record);
        if (!record.getTag().isEmpty()) {
            tagSet.add(record.getTag());
        }
    }

    public void updateScore(String studentId, int score) {
        if (map.containsKey(studentId)) {
            map.get(studentId).setScore(score);
        }
    }

    public List<StudentRecord> findByTag(String tag) {
        String targetTag = (tag == null) ? "" : tag.trim();
        return list.stream()
                .filter(r -> r.getTag().equalsIgnoreCase(targetTag))
                .collect(Collectors.toList());
    }

    public Map<String, Integer> scoreDistribution() {
        Map<String, Integer> dist = new LinkedHashMap<>();
        dist.put("A", 0);
        dist.put("B", 0);
        dist.put("C", 0);
        dist.put("D", 0);
        dist.put("F", 0);

        for (StudentRecord r : map.values()) {
            int s = r.getScore();
            if (s >= 90) {
                dist.put("A", dist.get("A") + 1); 
            }else if (s >= 80) {
                dist.put("B", dist.get("B") + 1); 
            }else if (s >= 70) {
                dist.put("C", dist.get("C") + 1); 
            }else if (s >= 60) {
                dist.put("D", dist.get("D") + 1); 
            }else {
                dist.put("F", dist.get("F") + 1);
            }
        }
        return dist;
    }

    public List<StudentRecord> top(int count) {
        return map.values().stream()
                .sorted(Comparator.comparingInt(StudentRecord::getScore).reversed())
                .limit(Math.max(0, count))
                .collect(Collectors.toList());
    }

    public void removeBelow(int minimum) {
        // 同步清理 List
        list.removeIf(r -> r.getScore() < minimum);
        // 同步清理 Map
        map.values().removeIf(r -> r.getScore() < minimum);
        // 重新同步 TagSet
        tagSet.clear();
        for (StudentRecord r : map.values()) {
            if (!r.getTag().isEmpty()) {
                tagSet.add(r.getTag());
            }
        }
    }

    public void printStatus() {
        System.out.println("當前資料庫紀錄: " + map.values());
        System.out.println("有效標籤集合: " + tagSet);
    }

    public static void main(String[] args) {
        CourseCollectionManager manager = new CourseCollectionManager();

        // 至少六筆資料（含重複學號覆蓋、同分、空白標籤）
        manager.addRecord(new StudentRecord("101", 95, "資管"));
        manager.addRecord(new StudentRecord("102", 82, "網頁"));
        manager.addRecord(new StudentRecord("103", 55, "資管"));
        manager.addRecord(new StudentRecord("104", 82, ""));
        manager.addRecord(new StudentRecord("105", 40, "   "));
        manager.addRecord(new StudentRecord("103", 75, "資管")); // 重複學號覆蓋

        System.out.println("--- 初始狀態 ---");
        manager.printStatus();

        System.out.println("\n--- 更新 104 成績為 88 ---");
        manager.updateScore("104", 88);

        System.out.println("\n--- 查詢標籤 '資管' ---");
        System.out.println(manager.findByTag("資管"));

        System.out.println("\n--- 等第分佈 ---");
        System.out.println(manager.scoreDistribution());

        System.out.println("\n--- 前 2 名 ---");
        System.out.println(manager.top(2));

        System.out.println("\n--- 移除 60 分以下 (保持一致性) ---");
        manager.removeBelow(60);
        manager.printStatus();
    }
}
