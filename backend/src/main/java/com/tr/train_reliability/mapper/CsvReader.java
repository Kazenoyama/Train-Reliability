package com.tr.train_reliability.mapper;

import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class CsvReader {

    public List<Map<String, String>> read(String path) {
        List<Map<String, String>> rows = new ArrayList<>();
        String delimiter = ";";

        try (BufferedReader br = new BufferedReader(new FileReader(path, StandardCharsets.UTF_8))) {
            String headerLine = br.readLine();
            if (headerLine == null) return rows;

            headerLine = headerLine.replace("\uFEFF", "");
            String[] headers = headerLine.split(delimiter);

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(delimiter, -1);
                Map<String, String> row = new HashMap<>();

                for (int i = 0; i < headers.length; i++) {
                    String value = "";
                    if (i < values.length) {
                        value = values[i].trim();
                    }

                    row.put(headers[i], value.isEmpty() ? null : value);
                }
                rows.add(row);
            }
        } catch (IOException e) {
            throw new RuntimeException("Erreur lors de la lecture du CSV : " + e.getMessage());
        }

        return rows;
    }

}
