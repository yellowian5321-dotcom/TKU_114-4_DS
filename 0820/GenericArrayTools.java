
import java.util.Arrays;
import java.util.Objects;

public class GenericArrayTools {

    public static <T> int countMatches(T[] data, T target) {
        if (data == null || data.length == 0) {
            return 0;
        }
        int count = 0;
        for (T item : data) {
            if (Objects.equals(item, target)) {
                count++;
            }
        }
        return count;
    }

    public static <T> T last(T[] data) {
        if (data == null || data.length == 0) {
            return null;
        }
        return data[data.length - 1];
    }

    public static <T> void swap(T[] data, int first, int second) {
        if (data == null || first < 0 || second < 0 || first >= data.length || second >= data.length) {
            return;
        }
        T temp = data[first];
        data[first] = data[second];
        data[second] = temp;
    }

    public static void main(String[] args) {
        String[] arr = {"A", "B", "A", "C"};
        System.out.println("Matches 'A': " + countMatches(arr, "A"));
        System.out.println("Last: " + last(arr));

        swap(arr, 0, 3);
        System.out.println("Swapped (0, 3): " + Arrays.toString(arr));

        swap(arr, -1, 10); // 不合法索引測試
        System.out.println("無效交換後: " + Arrays.toString(arr));
    }
}
