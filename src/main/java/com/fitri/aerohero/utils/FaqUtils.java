package com.fitri.aerohero.utils;

import com.fitri.aerohero.models.Faq;

import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FaqUtils {
    
    // Path to the FAQ CSV file
    private static final Path FILE_PATH = Paths.get("src", "main", "resources", "data", "faqs.csv");
    
    //loads entries from CSV file and returns them as a list 
    public static List<Faq> loadFaqs() {
        List<Faq> faqs = new ArrayList<>();

        // If the file doesn't exist, return an empty list
        if (!Files.exists(FILE_PATH)) {
            return faqs;
        }

        try (BufferedReader reader = Files.newBufferedReader(FILE_PATH)) {
            String line;
            boolean isFirstLine = true;

            while ((line = reader.readLine()) != null) {
                if (isFirstLine) {
                    isFirstLine = false; // Skip header
                    continue;
                }

                String[] parts = parseCSVLine(line);
                if (parts.length == 3) {
                    faqs.add(new Faq(parts[0], parts[1], parts[2]));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return faqs;
    }

    
    //saves a list of FAQs to the CSV file 
    public static void saveFaqs(List<Faq> faqs) {
        try (BufferedWriter writer = Files.newBufferedWriter(FILE_PATH)) {
            writer.write("category,question,answer");
            writer.newLine();

            // Write each FAQ entry, properly escaped
            for (Faq f : faqs) {
                writer.write(escapeCSV(f.getCategory()) + "," +
                             escapeCSV(f.getQuestion()) + "," +
                             escapeCSV(f.getAnswer()));
                writer.newLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //escape quotes, line breaks, commas
    private static String escapeCSV(String field) {
        String escaped = field.replace("\"", "\"\"");
        return "\"" + escaped + "\"";
    }

    //parse a CSV line with quoted fields
    private static String[] parseCSVLine(String line) {
        List<String> result = new ArrayList<>();
        StringBuilder current = new StringBuilder();
        boolean inQuotes = false;

        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(i);

            if (ch == '\"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '\"') {
                    current.append('\"');
                    i++; // Skip escaped quote
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (ch == ',' && !inQuotes) {
                result.add(current.toString());
                current.setLength(0);
            } else {
                current.append(ch);
            }
        }

        result.add(current.toString());
        return result.toArray(new String[0]);
    }
}