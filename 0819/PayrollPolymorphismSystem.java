
abstract class Employee {

    protected String name;

    public Employee(String name) {
        this.name = name;
    }

    public abstract double calculatePay();

    public String getName() {
        return name;
    }
}

class SalariedEmployee extends Employee {

    private double monthlySalary;

    public SalariedEmployee(String name, double monthlySalary) {
        super(name);
        this.monthlySalary = Math.max(0, monthlySalary);
    }

    @Override
    public double calculatePay() {
        return monthlySalary;
    }
}

class HourlyEmployee extends Employee {

    private double wage;
    private double hours;

    public HourlyEmployee(String name, double wage, double hours) {
        super(name);
        this.wage = Math.max(0, wage);
        this.hours = Math.max(0, hours);
    }

    @Override
    public double calculatePay() {
        return wage * hours;
    }
}

class CommissionEmployee extends Employee {

    private double baseSalary;
    private double salesAmount;
    private double commissionRate;

    public CommissionEmployee(String name, double baseSalary, double salesAmount, double commissionRate) {
        super(name);
        this.baseSalary = Math.max(0, baseSalary);
        this.salesAmount = Math.max(0, salesAmount);
        this.commissionRate = Math.max(0, commissionRate);
    }

    @Override
    public double calculatePay() {
        return baseSalary + (salesAmount * commissionRate);
    }
}

public class PayrollPolymorphismSystem {

    public static void main(String[] args) {
        Employee[] employees = new Employee[]{
            new SalariedEmployee("張經理", 65000),
            new HourlyEmployee("林工讀生", 185, 120),
            new CommissionEmployee("陳業務", 30000, 500000, 0.08)
        };

        double totalPay = 0;
        Employee highestPaid = employees[0];

        for (Employee emp : employees) {
            double pay = emp.calculatePay();
            totalPay += pay;
            System.out.printf("員工: %-8s | 本期薪資: $%.2f%n", emp.getName(), pay);
            if (pay > highestPaid.calculatePay()) {
                highestPaid = emp;
            }
        }

        System.out.println("========================================");
        System.out.printf("總發放薪資: $%.2f%n", totalPay);
        System.out.printf("最高薪資者: %s ($%.2f)%n", highestPaid.getName(), highestPaid.calculatePay());
    }
}
