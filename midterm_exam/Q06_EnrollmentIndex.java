
import java.util.*;

public class Q06_EnrollmentIndex {

    private final Map<String, Set<String>> courseMap = new HashMap<>();

    public boolean enroll(String courseCode, String studentId) {
        if (courseCode == null || courseCode.isBlank() || studentId == null || studentId.isBlank()) {
            return false;
        }
        Set<String> students = courseMap.computeIfAbsent(courseCode, k -> new HashSet<>());
        return students.add(studentId);
    }

    public boolean drop(String courseCode, String studentId) {
        if (courseCode == null || courseCode.isBlank() || studentId == null || studentId.isBlank()) {
            return false;
        }
        Set<String> students = courseMap.get(courseCode);
        if (students == null || !students.remove(studentId)) {
            return false;
        }
        if (students.isEmpty()) {
            courseMap.remove(courseCode);
        }
        return true;
    }

    public int courseSize(String courseCode) {
        if (courseCode == null || !courseMap.containsKey(courseCode)) {
            return 0;
        }
        return courseMap.get(courseCode).size();
    }

    public List<String> studentsOf(String courseCode) {
        if (courseCode == null || !courseMap.containsKey(courseCode)) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>(courseMap.get(courseCode));
        Collections.sort(list);
        return Collections.unmodifiableList(list);
    }

    public List<String> coursesOf(String studentId) {
        if (studentId == null || studentId.isBlank()) {
            return Collections.emptyList();
        }
        List<String> list = new ArrayList<>();
        for (Map.Entry<String, Set<String>> entry : courseMap.entrySet()) {
            if (entry.getValue().contains(studentId)) {
                list.add(entry.getKey());
            }
        }
        Collections.sort(list);
        return Collections.unmodifiableList(list);
    }

    public Map<String, Integer> summary() {
        Map<String, Integer> sortedMap = new TreeMap<>();
        for (Map.Entry<String, Set<String>> entry : courseMap.entrySet()) {
            sortedMap.put(entry.getKey(), entry.getValue().size());
        }
        return Collections.unmodifiableMap(sortedMap);
    }
}
