package com.defaulty.webscraper;

import com.defaulty.webscraper.control.file.FileOperations;
import com.defaulty.webscraper.control.OutputService;
import com.defaulty.webscraper.control.task.*;

import java.nio.file.NoSuchFileException;
import java.util.*;
import java.util.concurrent.*;

/**
 * Hello world!
 */
public class App implements OutputService {

    private final static ExecutorService service = Executors.newFixedThreadPool(10);
    private ScrubTotal scrubTotal;
    private final String[] args;

    private List<ScrubTask> finishTaskList = new ArrayList<>();
    private int TaskCount;
    private int flags;

    private App(String[] args) {
        this.args = args;
    }

    private void process() {
        if (args.length > 1) {
            try {
                List<String> uriList = new ArrayList<>();
                if (FileOperations.isURIPath(args[0]))
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

                for (String uri : uriList) {
                    ScrubTask task = new ScrubTaskImpl(uri, wordList, this);
                    task.setFlags(flags);
                    service.execute(task);
                    TaskCount++;
                }

                service.shutdown();

                scrubTotal = new ScrubTotalImpl(wordList, uriList);
                scrubTotal.setFlags(flags);
            } catch (NoSuchFileException e){
                System.out.println("Error: " + args[0] + " not found.");
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else
            System.out.println("Not enough runtime arguments.");

    }

    @Override
    public synchronized void returnTask(ScrubTask task) {
        finishTaskList.add(task);
        System.out.println(task.toString());
        scrubTotal.addToTotal(task);
        if (finishTaskList.size() == TaskCount)
            System.out.println(scrubTotal.toString());
    }

    public static void main(String[] args) {
        App app = new App(args);
        app.process();
    }

}
