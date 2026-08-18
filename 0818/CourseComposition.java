
class Instructor {

    private String id;
    private String name;

    public Instructor(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}

class Course {

    private String code;
    private String title;
    private Instructor instructor;

    public Course(String code, String title, Instructor instructor) {
        this.code = code;
        this.title = title;
        this.instructor = instructor;
    }

    public String summary() {
        String instructorInfo = (instructor != null)
                ? instructor.getName() + " (ID: " + instructor.getId() + ")"
                : "未指派講師";
        return "課程代碼: " + code + ", 課程名稱: " + title + ", 授課講師: " + instructorInfo;
    }
}

public class CourseComposition {

    public static void main(String[] args) {
        Instructor instructor = new Instructor("INS101", "王教授");

        Course course1 = new Course("CS101", "物件導向程式設計", instructor);
        Course course2 = new Course("CS102", "資料結構", instructor);

        System.out.println(course1.summary());
        System.out.println(course2.summary());
    }
}
