package com.defaulty.webscraper.control.task;

/**
 * The interface describes a service that can connect single scrub task results and print it.
 * Also can printing total result information.
 */
public interface ScrubTotal {

    void addToTotal(ScrubTask task);

    String TaskToString(ScrubTask task);

    String getTotal();

}
