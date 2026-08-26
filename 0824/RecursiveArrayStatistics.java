
public class RecursiveArrayStatistics {

    public static int maximum(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("陣列不可為空");
        }
        return maxHelper(arr, 0);
    }

    private static int maxHelper(int[] arr, int idx) {
        if (idx == arr.length - 1) {
            return arr[idx];
        }
        return Math.max(arr[idx], maxHelper(arr, idx + 1));
    }

    public static int minimum(int[] arr) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("陣列不可為空");
        }
        return minHelper(arr, 0);
    }

    private static int minHelper(int[] arr, int idx) {
        if (idx == arr.length - 1) {
            return arr[idx];
        }
        return Math.min(arr[idx], minHelper(arr, idx + 1));
    }

    public static int countAbove(int[] arr, int threshold) {
        if (arr == null || arr.length == 0) {
            throw new IllegalArgumentException("陣列不可為空");
        }
        return countAboveHelper(arr, threshold, 0);
    }

    private static int countAboveHelper(int[] arr, int threshold, int idx) {
        if (idx == arr.length) {
            return 0;
        }
        int current = arr[idx] > threshold ? 1 : 0;
        return current + countAboveHelper(arr, threshold, idx + 1);
    }

    public static void main(String[] args) {
        int[] arr = {12, 45, 7, 89, 23, 56};
        System.out.println("Max: " + maximum(arr));
        System.out.println("Min: " + minimum(arr));
        System.out.println("Count Above 30: " + countAbove(arr, 30));
    }
}
