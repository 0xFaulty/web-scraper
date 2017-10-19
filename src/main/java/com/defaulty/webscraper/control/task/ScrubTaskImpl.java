package com.defaulty.webscraper.control.task;

import com.defaulty.webscraper.control.OutputService;
import com.defaulty.webscraper.control.web.WebParser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScrubTaskImpl implements ScrubTask {

    private final List<String> wordList;
    private final String uri;
    private final OutputService outputService;
    private int flags;

    private HashMap<String, Integer> wordCountHash = new HashMap<>();
    private List<String> wordSentence = new ArrayList<>();
    private int charCount = 0;
    private long scrubElapsedTimeMillis = 0;
    private long processElapsedTimeMillis = 0;

    /**
     * @param uri - input link.
     * @param wordList - list with words for search.
     * @param outputService - result back point.
     * @param flags - flags contain data collect type information .
     */
    public ScrubTaskImpl(String uri, List<String> wordList, OutputService outputService, int flags) {
        this.uri = Objects.requireNonNull(uri, "URI must not be null");
        this.wordList = Objects.requireNonNull(wordList, "Word list must not be null");
        this.outputService = Objects.requireNonNull(outputService, "Output service must not be null");
        this.flags = flags;
    }

    /**
     * Task process running. Getting web parser data, process it and return to back point.
     */
    @Override
    public void run() {
        long startTimeMillis = System.currentTimeMillis();
        String htmlText = formatText(new WebParser(uri).extractTextFromHTML());

        scrubElapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
        startTimeMillis = System.currentTimeMillis();

        if (TaskFlags.C.containsIn(flags))
            charCount = htmlText.length();

        if (TaskFlags.W.containsIn(flags) || TaskFlags.E.containsIn(flags)) {
            for (String sentence : htmlText.split("\\. ")) {
                for (String word : wordList) {
                    int count = countOccurrences(sentence, word);
                    if (TaskFlags.E.containsIn(flags))
                        if (count > 0 && !wordSentence.contains(sentence)) wordSentence.add(sentence);
                    Integer savedCount = wordCountHash.get(word);
                    if (savedCount == null)
                        wordCountHash.put(word, count);
                    else
                        wordCountHash.put(word, savedCount + count);
                }
            }
        }

        processElapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;

        outputService.returnTask(this);
    }

    /**
     * Cut service characters from text.
     * @param text - input text.
     * @return - text without service characters.
     */
    private String formatText(String text) {
        return text.replaceAll("\\r?\\n\\s*", ". ").trim();
    }

    /**
     * Count word occurrences in received text.
     * @param sentence - input text.
     * @param word - search word.
     * @return - number of occurrences.
     */
    private int countOccurrences(String sentence, String word) {
        int res = 0;
        Pattern p = Pattern.compile(" " + word + " ");
        Matcher m = p.matcher(sentence);
        while (m.find())
            res++;
        return res;
    }

    public String getUri() {
        return uri;
    }

    public HashMap<String, Integer> getWordCountHash() {
        return wordCountHash;
    }

    public List<String> getWordSentence() {
        return wordSentence;
    }

    public int getCharCount() {
        return charCount;
    }

    public long getScrubElapsedTimeMillis() {
        return scrubElapsedTimeMillis;
    }

    public long getProcessElapsedTimeMillis() {
        return processElapsedTimeMillis;
    }

}
