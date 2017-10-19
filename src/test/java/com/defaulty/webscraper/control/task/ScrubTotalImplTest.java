package com.defaulty.webscraper.control.task;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ScrubTotalImplTest {

    @Test
    public void isURIPath() throws Exception {
        List<String> uriList = new ArrayList<>();
        uriList.add("test1");
        List<String> wordList = new ArrayList<>();
        wordList.add("word1");
        ScrubTotal scrubTotal = new ScrubTotalImpl(uriList, wordList);
        //ScrubTask task = new ScrubTaskImpl("test2", wordList, null);
    }

}