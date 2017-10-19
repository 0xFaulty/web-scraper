package com.defaulty.webscraper.control.task;

import java.util.HashMap;
import java.util.List;

public interface ScrubTask extends Runnable {

    HashMap<String, Integer> getWordCountHash();

    List<String> getWordSentence();

    int getCharCount();

    long getScrubElapsedTimeMillis();

    long getProcessElapsedTimeMillis();

    void setFlags(int flags);

}
