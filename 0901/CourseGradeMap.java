
import java.util.*;

public class CourseGradeMap {

    private final Map<String, List<Integer>> courseGrades = new HashMap<>();

    public void addGrade(String courseId, int grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("成績必須介於 0 到 100 之間");
        }
        courseGrades.computeIfAbsent(courseId, k -> new ArrayList<>()).add(grade);
    }

    public double getAverage(String courseId) {
        List<Integer> grades = courseGrades.get(courseId);
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }
        return grades.stream().mapToInt(Integer::intValue).average().orElse(0.0);
    }

    public int getMaxGrade(String courseId) {
        List<Integer> grades = courseGrades.get(courseId);
        if (grades == null || grades.isEmpty()) {
            return -1;
        }
        return grades.stream().mapToInt(Integer::intValue).max().orElse(-1);
    }

    public void printSortedReport() {
        List<String> sortedCourses = new ArrayList<>(courseGrades.keySet());
        Collections.sort(sortedCourses);

        System.out.println("================ 課程成績統計報告 ================");
        for (String cId : sortedCourses) {
            List<Integer> grades = courseGrades.get(cId);
            double avg = getAverage(cId);
            int max = getMaxGrade(cId);
            System.out.printf("課號: %-10s | 修課人數: %2d | 平均: %6.2f | 最高分: %3d | 成績列表: %s%n",
                    cId, grades.size(), avg, max, grades);
        }
        System.out.println("================================================");
    }

    public static void main(String[] args) {
        CourseGradeMap report = new CourseGradeMap();
        report.addGrade("IM301", 85);
        report.addGrade("IM301", 92);
        report.addGrade("CS101", 78);
        report.addGrade("CS101", 88);
        report.addGrade("CS101", 95);
        report.addGrade("IM205", 60);

        report.printSortedReport();
    }
}
