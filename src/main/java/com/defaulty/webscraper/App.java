package com.defaulty.webscraper;

import com.defaulty.webscraper.control.OutputService;
import com.defaulty.webscraper.control.file.FileOperations;
import com.defaulty.webscraper.control.task.*;

import java.nio.file.NoSuchFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 0xFaulty
 * @version 0.1
 */
public class App implements OutputService {

    private final static ExecutorService service = Executors.newFixedThreadPool(10);
    private ScrubTotal scrubTotal;
    private final String[] args;

    private List<ScrubTask> finishTaskList = new ArrayList<>();
    private int TaskCount;
    private int flags;

    /**
     * Application class constructor.
     * @param args - runtime arguments that must contain:
     *             [0] - url address or file path
     *             [1] - words for search separated by commas.
     *             Also, possible flags:
     *             	-v --Count data get and data process time in ms.
     *             	-w --Count number of provided word(s) occurrence on webpage(s).
     *             	-c --Count number of characters of each web page.
     *             	-e --Printing sentences’ which contain given words.
     */
    public App(String[] args) {
        this.args = args;
    }

    /**
     * Parse received arguments and send it to run in ExecutorService threads pool.
     */
    private void process() {
        if (args.length > 1) {
            try {
                List<String> uriList = new ArrayList<>();

                if (args[0].startsWith("http:") || args[0].startsWith("https:"))
                    uriList.add(args[0]);
                else
                    uriList = FileOperations.readFileIntoList(args[0]);

                List<String> wordList = Arrays.asList(args[1].split(","));

                for (int i = 2; i < args.length; i++) {
                    if (args[i].equals("-v")) flags |= TaskFlags.V.getValue();
                    if (args[i].equals("-w")) flags |= TaskFlags.W.getValue();
                    if (args[i].equals("-c")) flags |= TaskFlags.C.getValue();
                    if (args[i].equals("-e")) flags |= TaskFlags.E.getValue();
                }

                scrubTotal = new ScrubTotalImpl(uriList, wordList, flags);

                for (String uri : uriList) {
                    ScrubTask task = new ScrubTaskImpl(uri, wordList, this, flags);
                    service.execute(task);
                    TaskCount++;
                }

                service.shutdown();

            } catch (NoSuchFileException e) {
                System.out.println("Error: " + args[0] + " not found.");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else
            System.out.println("Not enough runtime arguments.");

    }

    /**
     * Point for return result from tasks after processing.
     * Printing result information for each task and in total.
     * @param task - scrub task returned after processing.
     */
    @Override
    public synchronized void returnTask(ScrubTask task) {
        finishTaskList.add(task);
        System.out.println(scrubTotal.TaskToString(task));
        scrubTotal.addToTotal(task);
        if (finishTaskList.size() == TaskCount)
            System.out.println(scrubTotal.getTotal());
    }

    /**
     * Application start point.
     * @param args - runtime arguments, described in {@code App} constructor.
     */
    public static void main(String[] args) {
        App app = new App(args);
        app.process();
    }

}
