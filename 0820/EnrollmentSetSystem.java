
import java.util.*;

class Enrollment {

    private final String studentId;
    private final String courseCode;

    public Enrollment(String studentId, String courseCode) {
        this.studentId = studentId;
        this.courseCode = courseCode;
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
        return Objects.equals(studentId, that.studentId)
                && Objects.equals(courseCode, that.courseCode);
    }

    @Override
    public int hashCode() {
        return Objects.hash(studentId, courseCode);
    }

    @Override
    public String toString() {
        return "[" + studentId + " -> " + courseCode + "]";
    }
}

public class EnrollmentSetSystem {

    public static void main(String[] args) {
        Set<Enrollment> enrollments = new HashSet<>();

        System.out.println("加入 S01 - CS101: " + enrollments.add(new Enrollment("S01", "CS101")));
        System.out.println("加入 S01 - CS102 (同人不同課): " + enrollments.add(new Enrollment("S01", "CS102")));
        System.out.println("加入 S01 - CS101 (同人同課重複): " + enrollments.add(new Enrollment("S01", "CS101")));

        Enrollment checkTarget = new Enrollment("S01", "CS101");
        System.out.println("檢查是否包含新實例化的 (S01, CS101): " + enrollments.contains(checkTarget));

        System.out.println("取消/移除 (S01, CS101): " + enrollments.remove(new Enrollment("S01", "CS101")));
        System.out.println("再次移除已不存在的 (S01, CS101): " + enrollments.remove(new Enrollment("S01", "CS101")));
    }
}
