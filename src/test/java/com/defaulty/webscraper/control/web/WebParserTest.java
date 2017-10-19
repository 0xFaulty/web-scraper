package com.defaulty.webscraper.control.web;

import org.junit.Test;

import static org.junit.Assert.*;

public class WebParserTest {

    @Test
    public void extractTextFromHTML() {
        String uri = "https://www.google.ru";
        WebParser webParser = new WebParser(uri);

        String result = webParser.extractTextFromHTML();
        assertNotNull(result);
        assertNotEquals("", result);
    }

}