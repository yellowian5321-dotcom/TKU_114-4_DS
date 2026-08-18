
class CourseGrade {

    private String studentId;
    private String name;
    private double dailyScore;   // 平時 50%
    private double midtermScore; // 期中 20%
    private double finalScore;   // 期末 20%
    private double attendance;   // 出席 10%

    public CourseGrade(String studentId, String name, double dailyScore, double midtermScore, double finalScore, double attendance) {
        this.studentId = studentId;
        this.name = name;
        this.dailyScore = clamp(dailyScore);
        this.midtermScore = clamp(midtermScore);
        this.finalScore = clamp(finalScore);
        this.attendance = clamp(attendance);
    }

    private double clamp(double score) {
        if (score < 0) {
            return 0.0;
        }
        if (score > 100) {
            return 100.0;
        }
        return score;
    }

    public double calculateFinalScore() {
        return (dailyScore * 0.50) + (midtermScore * 0.20) + (finalScore * 0.20) + (attendance * 0.10);
    }

    public String getLevel() {
        double total = calculateFinalScore();
        if (total >= 90.0) {
            return "A";
        }
        if (total >= 80.0) {
            return "B";
        }
        if (total >= 70.0) {
            return "C";
        }
        if (total >= 60.0) {
            return "D";
        }
        return "F";
    }

    public boolean isPassed() {
        return calculateFinalScore() >= 60.0;
    }

    public String getName() {
        return name;
    }

    public String getStudentId() {
        return studentId;
    }

    @Override
    public String toString() {
        return String.format("學號: %-9s | 姓名: %-4s | 平時: %5.1f | 期中: %5.1f | 期末: %5.1f | 出席: %5.1f | 總分: %5.1f | 等級: %s",
                studentId, name, dailyScore, midtermScore, finalScore, attendance, calculateFinalScore(), getLevel());
    }
}

public class CourseGradeManager {

    public static void main(String[] args) {
        CourseGrade[] grades = {
            new CourseGrade("412631001", "陳同學", 92, 88, 95, 100),
            new CourseGrade("412631002", "李同學", 78, 65, 70, 80),
            new CourseGrade("412631003", "王同學", 40, 50, 45, 60),
            new CourseGrade("412631004", "張同學", 85, 90, 88, 90),
            new CourseGrade("412631005", "趙同學", 55, 45, 50, 70)
        };

        System.out.println("=== 學生學期成績總覽 ===");
        double totalClassScore = 0.0;
        CourseGrade highestGrade = grades[0];

        for (CourseGrade g : grades) {
            System.out.println(g);
            double currentFinal = g.calculateFinalScore();
            totalClassScore += currentFinal;
            if (currentFinal > highestGrade.calculateFinalScore()) {
                highestGrade = g;
            }
        }

        double averageClassScore = totalClassScore / grades.length;
        System.out.println("\n=== 班級統計分析 ===");
        System.out.printf("全班平均總分: %.2f 分\n", averageClassScore);
        System.out.printf("最高分學生: %s (學號: %s，總分: %.1f)\n",
                highestGrade.getName(), highestGrade.getStudentId(), highestGrade.calculateFinalScore());

        System.out.println("\n=== 不及格名單 (總分 < 60) ===");
        boolean hasFailed = false;
        for (CourseGrade g : grades) {
            if (!g.isPassed()) {
                System.out.printf("-> 學號: %s | 姓名: %s | 總分: %.1f (等級: %s)\n",
                        g.getStudentId(), g.getName(), g.calculateFinalScore(), g.getLevel());
                hasFailed = true;
            }
        }
        if (!hasFailed) {
            System.out.println("全數及格！");
        }
    }
}
