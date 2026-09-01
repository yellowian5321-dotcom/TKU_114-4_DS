
import java.util.*;

public class InterestSetComparison {

    public static <T> Set<T> union(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<>(a);
        result.addAll(b);
        return result;
    }

    public static <T> Set<T> intersection(Set<T> a, Set<T> b) {
        Set<T> result = new HashSet<>(a);
        result.retainAll(b);
        return result;
    }

    public static <T> Set<T> firstOnly(Set<T> first, Set<T> secondary) {
        Set<T> result = new HashSet<>(first);
        result.removeAll(secondary);
        return result;
    }

    public static <T> Set<T> secondaryOnly(Set<T> first, Set<T> secondary) {
        return firstOnly(secondary, first);
    }

    public static void main(String[] args) {
        Set<String> userA = Set.of("Programming", "Gaming", "Music", "Reading");
        Set<String> userB = Set.of("Gaming", "Cooking", "Reading", "Travel");

        System.out.println("A 的興趣: " + userA);
        System.out.println("B 的興趣: " + userB);
        System.out.println("並集 (Union): " + union(userA, userB));
        System.out.println("交集 (Intersection): " + intersection(userA, userB));
        System.out.println("僅 A 擁有 (First-Only): " + firstOnly(userA, userB));
        System.out.println("僅 B 擁有 (Secondary-Only): " + secondaryOnly(userA, userB));
    }
}
