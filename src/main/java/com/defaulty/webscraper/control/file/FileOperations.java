package com.defaulty.webscraper.control.file;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class FileOperations {

    public static boolean isURIPath(String uri) {
        return (uri.startsWith("http:") || uri.startsWith("https:"));
    }

    public static List<String> readFileIntoList(String file) throws IOException {
        return Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
    }

}