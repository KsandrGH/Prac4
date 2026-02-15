package com.example.phone;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Finds phone-like substrings and replaces them with a unified format:
 *   +<cc> (AAA) BBB-CC-DD
 *
 * The matcher is intentionally tolerant to "errors" (spaces, dashes, parentheses, dots).
 *
 * Important: we only rewrite matches that clearly look like a phone number:
 * - 10 digits: local number
 * - 11 digits with leading 7 or 8: treat as RU-style and take last 10 digits
 * Otherwise: keep original match unchanged to avoid corrupting non-phone numbers.
 */
public final class PhoneNormalizer {

    private final String countryCode;

    /**
     * Candidate pattern (tolerant):
     * - optional leading '+'
     * - digits mixed with separators (space, dash, dot, parentheses)
     * - total length ensures at least ~10 digits are present
     *
     * We will validate by counting digits after match.
     */
    private static final Pattern CANDIDATE = Pattern.compile(
            "(?<!\\d)(\\+?\\d[\\d\\s().-]{8,}\\d)(?!\\d)"
    );

    public PhoneNormalizer(String countryCodeDigits) {
        this.countryCode = countryCodeDigits;
    }

    public String normalizeInText(String input) {
        if (input == null || input.isEmpty()) return input;

        Matcher m = CANDIDATE.matcher(input);
        StringBuffer sb = new StringBuffer();

        while (m.find()) {
            String original = m.group(1);
            String replacement = normalizeOne(original);
            // Quote to keep literal replacement (avoid $ backrefs)
            m.appendReplacement(sb, Matcher.quoteReplacement(replacement));
        }
        m.appendTail(sb);
        return sb.toString();
    }

    private String normalizeOne(String phoneLike) {
        String digits = phoneLike.replaceAll("\\D+", ""); // keep digits only

        String tenDigits = null;

        if (digits.length() == 10) {
            tenDigits = digits;
        } else if (digits.length() == 11 && (digits.charAt(0) == '7' || digits.charAt(0) == '8')) {
            tenDigits = digits.substring(1);
        } else {
            // Not confident it's a phone number (could be an ID, year range, etc.)
            return phoneLike;
        }

        String aaa = tenDigits.substring(0, 3);
        String bbb = tenDigits.substring(3, 6);
        String cc = tenDigits.substring(6, 8);
        String dd = tenDigits.substring(8, 10);

        return "+" + countryCode + " (" + aaa + ") " + bbb + "-" + cc + "-" + dd;
    }
}
