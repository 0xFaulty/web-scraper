package com.defaulty.webscraper.control.task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class implements {@code} ScrubTotal} and {@code ScrubResults}.
 * Connects the results of tasks and print it.
 * Also can printing total result information.
 */
public class ScrubTotalImpl implements ScrubTotal, ScrubResults {

    private final List<String> uriList;
    private final List<String> wordList;
    private final int flags;

    private HashMap<String, Integer> wordCountHash = new HashMap<>();
    private List<String> wordSentence = new ArrayList<>();
    private int charCount = 0;
    private long scrubElapsedTimeMillis = 0;
    private long processElapsedTimeMillis = 0;

    public ScrubTotalImpl(List<String> uriList, List<String> wordList, int flags) {
        this.uriList = uriList;
        this.wordList = wordList;
        this.flags = flags;
    }

    /**
     * Connects task results information.
     * @param task - finished scrub task.
     */
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

    /**
     * Creates string that contain total information about all task
     * that added.
     * @return - total information.
     */
    @Override
    public String getTotal() {
        return "TOTAL RESULT:\n" +
                "\turi: " + uriList + "\n" +
                "\tsearch: " + wordList + "\n" +
                "\n" +
                getFlagsResults(this);
    }

    /**
     * Creates string that contain information about received task.
     * @param task - task for get result.
     * @return - task information.
     */
    @Override
    public String TaskToString(ScrubTask task) {
        return "TASK RESULT:\n" +
                "\turi: " + task.getUri() + "\n" +
                "\tsearch: " + wordList + "\n" +
                "\n" +
                getFlagsResults(task);
    }

    /**
     * Creates string with additional information dependent of enabled flags.
     * Used {@code ScrubResults} for uniform processing single and total information.
     * @param service - information container.
     * @return - additional information.
     */
    private String getFlagsResults(ScrubResults service) {
        StringBuilder builder = new StringBuilder();
        if (TaskFlags.V.containsIn(flags)) {
            builder.append("\tScrub Time: ").append(service.getScrubElapsedTimeMillis()).append(" ms \n");
            builder.append("\tProcess Time: ").append(service.getProcessElapsedTimeMillis()).append(" ms \n");
            builder.append("\n");
        }
        if (TaskFlags.W.containsIn(flags)) {
            for (Map.Entry<String, Integer> entry : service.getWordCountHash().entrySet())
                builder.append("\t").append(entry.getKey()).append(" - ").append(entry.getValue()).append(" time(s) \n");
            builder.append("\n");
        }
        if (TaskFlags.C.containsIn(flags)) {
            builder.append("\tCharacter count: ").append(service.getCharCount()).append("\n");
            builder.append("\n");
        }
        if (TaskFlags.E.containsIn(flags)) {
            builder.append("\tSentences: [").append(service.getWordSentence().size()).append("]\n");
            for (String s : service.getWordSentence())
                builder.append("\t").append(s).append("\n");
        }

        return builder.toString();
    }

    @Override
    public HashMap<String, Integer> getWordCountHash() {
        return wordCountHash;
    }

    @Override
    public List<String> getWordSentence() {
        return wordSentence;
    }

    @Override
    public int getCharCount() {
        return charCount;
    }

    @Override
    public long getScrubElapsedTimeMillis() {
        return scrubElapsedTimeMillis;
    }

    @Override
    public long getProcessElapsedTimeMillis() {
        return processElapsedTimeMillis;
    }
}
