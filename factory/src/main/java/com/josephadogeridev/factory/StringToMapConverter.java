package com.josephadogeridev.factory;

import java.util.HashMap;
import java.util.Map;

public class StringToMapConverter {
    public  HashMap<String, String> convert(String args) {
        // The input string with the non-standard format
        String input = args;

        // The expected map
        HashMap<String, String> outputMap = new HashMap<>();

        try {
            // 1. Isolate the key-value string: "{message=...}"
            // Find the index of the first '{' and the last '}'
            int startIndex = input.indexOf('{');
            int endIndex = input.lastIndexOf('}');

            // Check that both characters were found
            if (startIndex != -1 && endIndex != -1) {
                String keyValueString = input.substring(startIndex + 1, endIndex);

                // 2. Split the key and value using the '=' separator
                String[] parts = keyValueString.split("=", 2); // Limit to 2 parts to handle '=' in the value

                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    // 3. Add the key and value to the map
                    outputMap.put(key, value);

                    return  outputMap;
                }
            }

        } catch (Exception e) {
            System.err.println("Failed to parse string: " + e.getMessage());
        }

        // Print the result
        System.out.println("Input String: " + input);
        System.out.println("Converted HashMap: " + outputMap);
        return null;

    }
}