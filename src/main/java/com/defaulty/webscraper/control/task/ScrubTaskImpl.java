package com.defaulty.webscraper.control.task;

import com.defaulty.webscraper.control.web.GetHTMLText;
import com.defaulty.webscraper.control.OutputService;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ScrubTaskImpl implements ScrubTask {

    private final List<String> wordList;
    private final String uri;
    private final OutputService outputService;

    private int flags = 0b0000;

    HashMap<String, Integer> wordCountHash = new HashMap<>();
    List<String> wordSentence = new ArrayList<>();
    int charCount = 0;
    long scrubElapsedTimeMillis = 0;
    long processElapsedTimeMillis = 0;

    public ScrubTaskImpl(String uri, List<String> wordList, OutputService outputService) throws IllegalArgumentException{
        this.uri = uri;
        this.wordList = wordList;
        this.outputService = outputService;
    }

    @Override
    public void run() {
        long startTimeMillis = System.currentTimeMillis();
        String htmlText = formatText(new GetHTMLText(uri).extractTextFromHTML());

        scrubElapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;
        startTimeMillis = System.currentTimeMillis();

        charCount = htmlText.length();

        for (String sentence : htmlText.split("\\. ")) {
            for (String word : wordList) {
                int count = countOccurrences(sentence, word);
                if (count > 0 && !wordSentence.contains(sentence)) wordSentence.add(sentence);
                Integer savedCount = wordCountHash.get(word);
                if (savedCount == null)
                    wordCountHash.put(word, count);
                else
                    wordCountHash.put(word, savedCount + count);
            }
        }

        processElapsedTimeMillis = System.currentTimeMillis() - startTimeMillis;

        if (outputService != null) outputService.returnTask(this);
    }

    private String formatText(String text) {
        return text.replaceAll("\\r?\\n\\s*", ". ").trim();
    }

    private int countOccurrences(String sentence, String word) {
        int res = 0;
        Pattern p = Pattern.compile(" " + word + " ");
        Matcher m = p.matcher(sentence);
        while (m.find())
            res++;
        return res;
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

    public void setFlags(int flags) {
        this.flags = flags;
    }

    @Override
    public String toString() {
        return "TASK RESULT:\n" +
                "\turi: " + uri + "\n" +
                "\tsearch: " + wordList + "\n" +
                "\n" +
                getFlagsResults();
    }

    String getFlagsResults() {
        StringBuilder builder = new StringBuilder();
        if (TaskFlags.V.containsIn(flags)) {
            builder.append("\tScrub Time: ").append(scrubElapsedTimeMillis).append(" ms \n");
            builder.append("\tProcess Time: ").append(processElapsedTimeMillis).append(" ms \n");
            builder.append("\n");
        }
        if (TaskFlags.W.containsIn(flags)) {
            for (Map.Entry<String, Integer> entry : wordCountHash.entrySet())
                builder.append("\t").append(entry.getKey()).append(" - ").append(entry.getValue()).append(" time(s) \n");
            builder.append("\n");
        }
        if (TaskFlags.C.containsIn(flags)) {
            builder.append("\tCharacter count: ").append(charCount).append("\n");
            builder.append("\n");
        }
        if (TaskFlags.E.containsIn(flags)) {
            builder.append("\tSentences: [").append(wordSentence.size()).append("]\n");
            for (String s : wordSentence)
                builder.append("\t").append(s).append("\n");
        }

        return builder.toString();
    }

}
