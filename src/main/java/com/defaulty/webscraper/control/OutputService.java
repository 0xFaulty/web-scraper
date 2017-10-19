package com.defaulty.webscraper.control;

import com.defaulty.webscraper.control.task.ScrubTask;

/**
 * Interface describe transfer finish point for tasks returning.
 */
public interface OutputService {
    void returnTask(ScrubTask task);
}
