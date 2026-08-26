
public class RecursiveTextTools {

    public static String reverse(String s) {
        if (s == null || s.length() <= 1) {
            return s;
        }
        return reverse(s.substring(1)) + s.charAt(0);
    }

    public static boolean isPalindrome(String s) {
        if (s == null) {
            return false;
        }
        String cleaned = cleanString(s, 0);
        return checkPalindrome(cleaned, 0, cleaned.length() - 1);
    }

    private static String cleanString(String s, int idx) {
        if (idx == s.length()) {
            return "";
        }
        char c = s.charAt(idx);
        if (Character.isLetterOrDigit(c)) {
            return Character.toLowerCase(c) + cleanString(s, idx + 1);
        }
        return cleanString(s, idx + 1);
    }

    private static boolean checkPalindrome(String s, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (s.charAt(left) != s.charAt(right)) {
            return false;
        }
        return checkPalindrome(s, left + 1, right - 1);
    }

    public static int countCharacter(String s, char target) {
        if (s == null || s.isEmpty()) {
            return 0;
        }
        int match = (Character.toLowerCase(s.charAt(0)) == Character.toLowerCase(target)) ? 1 : 0;
        return match + countCharacter(s.substring(1), target);
    }

    public static void main(String[] args) {
        String[] tests = {"", "a", "Level", "A man a plan a canal Panama", "Hello World"};
        for (String t : tests) {
            System.out.println("Original: \"" + t + "\"");
            System.out.println("  Reversed: \"" + reverse(t) + "\"");
            System.out.println("  Is Palindrome: " + isPalindrome(t));
            System.out.println("  Count 'a'/'A': " + countCharacter(t, 'a'));
        }
    }
}
