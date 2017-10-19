package com.defaulty.webscraper.control.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

/**
 * Utility class used for file operations.
 */
public class FileOperations {

    /**
     * Read file separated by end line into String list.
     * @param file - input file.
     * @return - read file.
     * @throws IOException - file read exception.
     */
    public static List<String> readFileIntoList(String file) throws IOException {
        return Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
    }

}