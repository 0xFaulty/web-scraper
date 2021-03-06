package com.defaulty.webscraper.control.file;

import org.junit.Ignore;
import org.junit.Test;

import java.nio.file.NoSuchFileException;
import java.util.List;

import static org.junit.Assert.*;

public class FileOperationsTest {

    @Ignore
    public void readFileIntoList() throws Exception {
        List<String> list = FileOperations.readFileIntoList("data\\test1.txt");
        assertNotNull(list);
        list = FileOperations.readFileIntoList("data/test1.txt");
        assertNotNull(list);
        list = FileOperations.readFileIntoList(
                "C:\\Users\\Valentin3d\\YandexDisk\\Sourses\\Java\\web-scraper\\data\\test1.txt");
        assertNotNull(list);

    }

    @Test(expected = NoSuchFileException.class)
    public void readFileIntoListNoSuch() throws Exception {
        List<String> list = FileOperations.readFileIntoList("wrong way");
        assertNull(list);
    }

}