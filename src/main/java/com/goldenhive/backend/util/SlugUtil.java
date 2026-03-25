package com.goldenhive.backend.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public class SlugUtil {
    
    private static final Pattern NONLATIN = Pattern.compile("[^\\w-\\s]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s_]");
    
    /**
     * Generate SEO-friendly slug from string
     * Example: "Taj Mahal Tours" -> "taj-mahal-tours"
     */
    public static String generateSlug(String input) {
        if (input == null || input.trim().isEmpty()) {
            return "";
        }
        
        String noWhitespace = WHITESPACE.matcher(input).replaceAll("-");
        String normalized = Normalizer.normalize(noWhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        
        return slug.toLowerCase(Locale.ENGLISH)
                .replaceAll("-+", "-")
                .replaceAll("^-|-$", "");
    }
}
