
public class Q08_RecursiveAudit {

    public static int sumValid(int[] data, int index) {
        if (data == null) {
            return 0;
        }
        int idx = Math.max(0, index);
        if (idx >= data.length) {
            return 0;
        }
        int current = (data[idx] >= 0 && data[idx] <= 100) ? data[idx] : 0;
        return current + sumValid(data, idx + 1);
    }

    public static int countOccurrences(int[] data, int index, int target) {
        if (data == null) {
            return 0;
        }
        int idx = Math.max(0, index);
        if (idx >= data.length) {
            return 0;
        }
        int match = (data[idx] == target) ? 1 : 0;
        return match + countOccurrences(data, idx + 1, target);
    }

    public static boolean isPalindrome(String text, int left, int right) {
        if (text == null) {
            return false;
        }
        if (left >= right) {
            return true;
        }
        char c1 = Character.toLowerCase(text.charAt(left));
        char c2 = Character.toLowerCase(text.charAt(right));
        if (c1 != c2) {
            return false;
        }
        return isPalindrome(text, left + 1, right - 1);
    }
}
