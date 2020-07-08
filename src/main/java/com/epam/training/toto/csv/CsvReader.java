package com.epam.training.toto.csv;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvReader {
    private final String fileName;
    private final String separator;

    public CsvReader(String fileName, String separator) {
        this.fileName = fileName;
        this.separator = separator;
    }

    public List<List<String>> readAll() throws IOException {
        List<List<String>> records = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = br.readLine()) != null) {
            String[] values = line.split(separator);
            records.add(Arrays.asList(values));
        }

        return records;
    }
}
