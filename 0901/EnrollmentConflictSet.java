
import java.util.*;

public class EnrollmentConflictSet {

    public static class Enrollment {

        private final String studentId;
        private final String courseId;

        public Enrollment(String studentId, String courseId) {
            this.studentId = studentId;
            this.courseId = courseId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (!(o instanceof Enrollment)) {
                return false;
            }
            Enrollment that = (Enrollment) o;
            return Objects.equals(studentId, that.studentId) && Objects.equals(courseId, that.courseId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(studentId, courseId);
        }

        @Override
        public String toString() {
            return String.format("[%s - %s]", studentId, courseId);
        }
    }

    private final Set<Enrollment> uniqueEnrollments = new HashSet<>();
    private final List<Enrollment> duplicates = new ArrayList<>();
    private final Map<String, Set<String>> studentCourses = new HashMap<>();
    private final Map<String, Integer> courseHeadcounts = new HashMap<>();

    public void processEnrollment(String studentId, String courseId) {
        Enrollment record = new Enrollment(studentId, courseId);
        if (!uniqueEnrollments.add(record)) {
            duplicates.add(record);
        } else {
            studentCourses.computeIfAbsent(studentId, k -> new HashSet<>()).add(courseId);
            courseHeadcounts.put(courseId, courseHeadcounts.getOrDefault(courseId, 0) + 1);
        }
    }

    public void printReport() {
        System.out.println("========== 選課重複檢查與統計報告 ==========");
        System.out.println("1. 重複選課記錄 (" + duplicates.size() + " 筆): " + duplicates);
        System.out.println("\n2. 每位學生選修清單:");
        studentCourses.forEach((stu, courses)
                -> System.out.printf("   學生 %s: %s (共 %d 門)%n", stu, courses, courses.size())
        );
        System.out.println("\n3. 各課程修課人數:");
        courseHeadcounts.forEach((course, count)
                -> System.out.printf("   課程 %s: %d 人%n", course, count)
        );
    }

    public static void main(String[] args) {
        EnrollmentConflictSet checker = new EnrollmentConflictSet();
        checker.processEnrollment("S001", "CS101");
        checker.processEnrollment("S001", "IM201");
        checker.processEnrollment("S002", "CS101");
        checker.processEnrollment("S001", "CS101"); // 重複選課

        checker.printReport();
    }
}
