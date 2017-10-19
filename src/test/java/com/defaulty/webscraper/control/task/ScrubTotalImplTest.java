package com.defaulty.webscraper.control.task;

import com.defaulty.webscraper.App;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class ScrubTotalImplTest {

    @Test
    public void ScrubTotalTest() throws Exception {
        List<String> uriList = new ArrayList<>();
        uriList.add("test1");
        List<String> wordList = new ArrayList<>();
        wordList.add("word1");

        int flags = TaskFlags.V.getValue();
        flags |= TaskFlags.W.getValue();
        flags |= TaskFlags.C.getValue();
        flags |= TaskFlags.E.getValue();

        ScrubTotal scrubTotal = new ScrubTotalImpl(uriList, wordList,flags);

        String[] arg = {""};
        ScrubTask task = new ScrubTaskImpl(uriList.get(0), wordList, new App(arg), flags);
        ScrubTask task2 = new ScrubTaskImpl(uriList.get(0), wordList, new App(arg), flags);

        scrubTotal.addToTotal(task);
        scrubTotal.addToTotal(task2);

        scrubTotal.TaskToString(task);
    }

}