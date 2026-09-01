
import java.util.*;

public class CourseDependencyGraph {

    private final Map<String, Set<String>> outEdges = new HashMap<>(); // 先修 -> 後續
    private final Map<String, Set<String>> inEdges = new HashMap<>();  // 後續 -> 先修

    public void addCourse(String course) {
        outEdges.putIfAbsent(course, new HashSet<>());
        inEdges.putIfAbsent(course, new HashSet<>());
    }

    public void addDependency(String preCourse, String nextCourse) {
        addCourse(preCourse);
        addCourse(nextCourse);
        outEdges.get(preCourse).add(nextCourse);
        inEdges.get(nextCourse).add(preCourse);
    }

    public int getInDegree(String course) {
        return inEdges.getOrDefault(course, Collections.emptySet()).size();
    }

    public int getOutDegree(String course) {
        return outEdges.getOrDefault(course, Collections.emptySet()).size();
    }

    public void printGraphReport() {
        System.out.println("================ 課程相依圖報告 ================");
        List<String> courses = new ArrayList<>(outEdges.keySet());
        Collections.sort(courses);

        for (String c : courses) {
            Set<String> pre = inEdges.get(c);
            Set<String> next = outEdges.get(c);
            System.out.printf("課程: %-10s | 入度: %d (先修: %s) | 出度: %d (後續: %s)%n",
                    c, pre.size(), pre, next.size(), next);
        }
    }

    public static void main(String[] args) {
        CourseDependencyGraph graph = new CourseDependencyGraph();
        graph.addDependency("計概", "資料結構");
        graph.addDependency("程式設計", "資料結構");
        graph.addDependency("資料結構", "演算法");
        graph.addDependency("資料結構", "高等資料庫");

        graph.printGraphReport();
    }
}
