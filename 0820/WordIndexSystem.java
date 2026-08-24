
import java.util.*;

public class WordIndexSystem {

    public static void main(String[] args) {
        String[] sentences = {
            "Java is great, and Java is versatile.",
            "Object-oriented programming with Java makes code robust, safe, and modular.",
            "Collections framework in Java is powerful and convenient."
        };

        Map<String, Integer> wordCountMap = new HashMap<>();
        Set<String> uniqueWords = new HashSet<>();

        for (String sentence : sentences) {
            String cleaned = sentence.replaceAll("[.,]", " ").toLowerCase();
            String[] tokens = cleaned.split("\\s+");

            for (String token : tokens) {
                if (!token.isEmpty()) {
                    uniqueWords.add(token);
                    wordCountMap.put(token, wordCountMap.getOrDefault(token, 0) + 1);
                }
            }
        }

        System.out.println("總不重複單字數: " + uniqueWords.size());
        System.out.println("\n--- 出現至少 2 次的單字統計 ---");
        wordCountMap.entrySet().stream()
                .filter(entry -> entry.getValue() >= 2)
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .forEach(e -> System.out.printf("%-12s : %d 次\n", e.getKey(), e.getValue()));
    }
}
