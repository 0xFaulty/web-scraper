package com.defaulty.webscraper.control.task;

import java.util.List;
import java.util.Map;

public class ScrubTotalImpl extends ScrubTaskImpl implements ScrubTotal {

    private final List<String> uriList;
    private final List<String> wordList;

    public ScrubTotalImpl(List<String> uriList, List<String> wordList) {
        super(null, null, null);
        this.uriList = uriList;
        this.wordList = wordList;
    }

    public void addToTotal(ScrubTask task) {
        scrubElapsedTimeMillis += task.getScrubElapsedTimeMillis();
        processElapsedTimeMillis += task.getProcessElapsedTimeMillis();

        for (Map.Entry<String, Integer> entry : task.getWordCountHash().entrySet()) {
            Integer value = wordCountHash.get(entry.getKey());
            if (value == null)
                wordCountHash.put(entry.getKey(), entry.getValue());
            else
                wordCountHash.put(entry.getKey(), value + entry.getValue());
        }

        charCount += task.getCharCount();
        wordSentence.addAll(task.getWordSentence());
    }

    @Override
    public void run() {
        System.out.println("Total task just contain result sum, nothing runnable.");
    }

    @Override
    public String toString() {
        return "TOTAL RESULT:\n" +
                "\turi: " + uriList +
                "\tsearch: " + wordList + "\n" +
                "\n" +
                getFlagsResults();
    }
}
