
public class RecursiveDigitReport {

    public static int digitSum(int n) {
        if (n < 0) {
            n = -n;
        }
        if (n < 10) {
            return n;
        }
        return (n % 10) + digitSum(n / 10);
    }

    public static int digitCount(int n) {
        if (n < 0) {
            n = -n;
        }
        if (n < 10) {
            return 1;
        }
        return 1 + digitCount(n / 10);
    }

    public static int countDigit(int n, int target) {
        if (n < 0) {
            n = -n;
        }
        if (n < 10) {
            return (n == target) ? 1 : 0;
        }
        return (n % 10 == target ? 1 : 0) + countDigit(n / 10, target);
    }

    public static void main(String[] args) {
        int[] tests = {50205, 0, -731};
        for (int n : tests) {
            System.out.println("n = " + n + ":");
            System.out.println("  Digit Sum   = " + digitSum(n));
            System.out.println("  Digit Count = " + digitCount(n));
            System.out.println("  Count '0'   = " + countDigit(n, 0));
        }
    }
}
