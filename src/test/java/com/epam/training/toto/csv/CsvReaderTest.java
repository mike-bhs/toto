package com.epam.training.toto.csv;

import org.junit.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

public class CsvReaderTest {

    @Test
    public void readAll() {
        CsvReader csvReader = new CsvReader("src/test/java/resources/test.csv", ";");

        try {
            List<List<String>> actual = csvReader.readAll();
            List<List<String>> expected = Arrays.asList(
                    Arrays.asList("2015", "43", "2", "2015.10.26.", "99", "164 630 UAH", "23", "16 415 UAH", "1738", "865 UAH", "10187", "145 UAH", "32184", "90 UAH", "1", "1", "2", "2", "1", "1", "X", "1", "1", "2", "1", "2", "X", "+1"),
                    Arrays.asList("2015", "43", "1", "2015.10.22.", "0", "0 UAH", "1", "2 772 490 UAH", "73", "12 355 UAH", "781", "1 155 UAH", "4222", "410 UAH", "1", "2", "X", "X", "1", "1", "1", "1", "X", "1", "1", "X", "1", "+X")
            );

            assertThat(actual, is(expected));
        } catch (IOException e) {
            fail("An error was thrown: " + e.getMessage());
        }


    }
}

