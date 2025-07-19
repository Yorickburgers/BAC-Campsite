package nl.novi.baccampsite.utils;

public class StringUtils {
    public static String slugify(String input) {
        if (input == null) return null;

        input = input.replaceAll("(?i)^way of ", "")
                .replaceAll("(?i)^path of ", "")
                .replaceAll("(?i)^oath of ", "")
                .replaceAll("(?i)^circle of ", "")
                .replaceAll("(?i)^school of ", "")
                .replaceAll("(?i)^college of ", "")
                .replaceAll("(?i)^ domain", "")
                .replaceAll("(?i)^oath of ", "")
                .replaceAll("(?i)^the ", "");

        return input
                .toLowerCase()
                .replaceAll("[^a-z0-9\\s-]", "")
                .replaceAll("\\s+", "-")
                .replaceAll("-{2,}", "-")
                .replaceAll("^-|-$", "");
    }
}
