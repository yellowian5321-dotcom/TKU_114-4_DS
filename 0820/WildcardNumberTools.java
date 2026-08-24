
import java.util.*;

public class WildcardNumberTools {

    public static double average(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return 0.0;
        }
        double sum = 0.0;
        for (Number num : values) {
            if (num != null) {
                sum += num.doubleValue();
            }
        }
        return sum / values.size();
    }

    public static double maximum(List<? extends Number> values) {
        if (values == null || values.isEmpty()) {
            return Double.NaN;
        }
        double max = Double.NEGATIVE_INFINITY;
        boolean hasValue = false;
        for (Number num : values) {
            if (num != null) {
                max = Math.max(max, num.doubleValue());
                hasValue = true;
            }
        }
        return hasValue ? max : Double.NaN;
    }

    public static void addRange(List<? super Integer> target, int start, int end) {
        if (target == null || start > end) {
            return;
        }
        for (int i = start; i <= end; i++) {
            target.add(i);
        }
    }

    public static void main(String[] args) {
        List<Integer> intList = Arrays.asList(10, 20, 30);
        List<Double> dblList = Arrays.asList(1.5, 4.5, 9.0);

        System.out.println("Int List Avg: " + average(intList) + ", Max: " + maximum(intList));
        System.out.println("Dbl List Avg: " + average(dblList) + ", Max: " + maximum(dblList));
        System.out.println("Empty List Avg: " + average(Collections.emptyList()) + ", Max: " + maximum(Collections.emptyList()));

        List<Number> numList = new ArrayList<>();
        addRange(numList, 5, 8);
        System.out.println("AddRange Result: " + numList);
    }
}
