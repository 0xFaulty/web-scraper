package com.defaulty.webscraper.control.task;

import java.util.HashMap;
import java.util.List;

/**
 * Interface describe container with scrub task results.
 */
public interface ScrubResults {

    HashMap<String, Integer> getWordCountHash();

    List<String> getWordSentence();

    int getCharCount();

    long getScrubElapsedTimeMillis();

    long getProcessElapsedTimeMillis();

}
