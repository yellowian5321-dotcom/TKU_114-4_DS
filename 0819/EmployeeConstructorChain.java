
abstract class EmployeeBase {

    protected String id;
    protected String name;

    public EmployeeBase(String id, String name) {
        System.out.println("[Constructor] EmployeeBase 初始化: " + id + " - " + name);
        this.id = id;
        this.name = name;
    }

    public abstract double calculatePay();
}

class FullTimeEmployee extends EmployeeBase {

    private double monthlySalary;

    public FullTimeEmployee(String id, String name, double monthlySalary) {
        super(id, name);
        System.out.println("[Constructor] FullTimeEmployee 初始化完成");
        this.monthlySalary = Math.max(0, monthlySalary);
    }

    @Override
    public double calculatePay() {
        return monthlySalary;
    }
}

class PartTimeEmployee extends EmployeeBase {

    private double hourlyRate;
    private int hoursWorked;

    public PartTimeEmployee(String id, String name, double hourlyRate, int hoursWorked) {
        super(id, name);
        System.out.println("[Constructor] PartTimeEmployee 初始化完成");
        this.hourlyRate = Math.max(0, hourlyRate);
        this.hoursWorked = Math.max(0, hoursWorked);
    }

    @Override
    public double calculatePay() {
        return hourlyRate * hoursWorked;
    }
}

public class EmployeeConstructorChain {

    public static void main(String[] args) {
        System.out.println("--- 建立全職員工 (觀察 Constructor 鏈) ---");
        EmployeeBase ft = new FullTimeEmployee("FT01", "王小明", -50000); // 測試負數邊界

        System.out.println("\n--- 建立兼職員工 (觀察 Constructor 鏈) ---");
        EmployeeBase pt = new PartTimeEmployee("PT01", "李小美", 190, -40); // 測試負數邊界

        System.out.println("\n--- 薪資結算 ---");
        System.out.println(ft.name + " 實領薪資: $" + ft.calculatePay());
        System.out.println(pt.name + " 實領薪資: $" + pt.calculatePay());
    }
}
