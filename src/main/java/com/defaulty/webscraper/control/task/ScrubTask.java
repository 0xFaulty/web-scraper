package com.defaulty.webscraper.control.task;

/**
 * Interface based on {@code ScrubResults} used for getting
 * specific information for single task.
 */
public interface ScrubTask extends ScrubResults, Runnable {
    String getUri();
}
