package com.example.phone;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Usage:
 *   java -jar phone-variant6-1.0.0.jar <inputFile> [outputFile] [--cc=1]
 *
 * Examples:
 *   java -jar target/phone-variant6-1.0.0.jar data/input.txt
 *   java -jar target/phone-variant6-1.0.0.jar data/input.txt data/output.txt
 *   java -jar target/phone-variant6-1.0.0.jar data/input.txt data/output.txt --cc=44
 */
public class Main {


    public static void main(String[] args) {
        if (args.length < 1) {
            System.err.println("Usage: java -jar phone-variant6-1.0.0.jar <inputFile> [outputFile] [--cc=1]");
            System.exit(2);
        }

        Path input = Paths.get(args[0]);
        Path output = null;
        if (args.length >= 2 && !args[1].startsWith("--")) {
            output = Paths.get(args[1]);
        }

        String countryCode = "1";
        for (String arg : args) {
            if (arg != null && arg.startsWith("--cc=")) {
                countryCode = arg.substring("--cc=".length()).trim();
                if (countryCode.startsWith("+")) {
                    countryCode = countryCode.substring(1);
                }
                if (countryCode.isEmpty() || !countryCode.matches("\\d{1,4}")) {
                    System.err.println("Invalid country code. Use digits only, e.g. --cc=1 or --cc=44");
                    System.exit(2);
                }
            }
        }

        try {
            String text = new String(Files.readAllBytes(input), StandardCharsets.UTF_8);
            PhoneNormalizer normalizer = new PhoneNormalizer(countryCode);
            String result = normalizer.normalizeInText(text);

            if (output == null) {
                System.out.print(result);
            } else {
                Files.write(output, result.getBytes(StandardCharsets.UTF_8));
                System.out.println("Written: " + output.toAbsolutePath());
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            System.exit(1);
        }
    }
}
