package concurancy;

import java.util.Calendar;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class RunnableExample {

    public static void main(String[] args) {
        start();
        pool();
        fixedPool();
    }

    private static void start() {
        Runnable runnable = () -> System.out.println("Hello world");
        Thread thread = new Thread(runnable);
        thread.start();
    }

    private static void pool() {
        ExecutorService singleThreadExecutor = Executors.newSingleThreadExecutor();
        execute(singleThreadExecutor, "Single thread pool executed after ");
    }

    private static void fixedPool() {

        ExecutorService fixedPoolThreadExecutor = Executors.newFixedThreadPool(4);
        execute(fixedPoolThreadExecutor, String.format("Fixed thread pool with %d threads executed after ", 4));

    }

    public static void execute(ExecutorService executorService, String message) {
        long startTimestamp = Calendar.getInstance().getTimeInMillis();
        Runnable r1 = () -> testExecution("Execution 1");
        Runnable r2 = () -> testExecution("Execution 1");
        Runnable r3 = () -> testExecution("Execution 1");
        Runnable r4 = () -> testExecution("Execution 1");
        executorService.execute(r1);
        executorService.execute(r2);
        executorService.execute(r3);
        executorService.execute(r4);
        long duration = Calendar.getInstance().getTimeInMillis() - startTimestamp;
        System.out.println(message + duration);
    }

    private static void testExecution(String message) {
        try {
            System.out.println(Thread.currentThread().getName() + ": " + message);
            TimeUnit.SECONDS.sleep(5l);
        } catch (InterruptedException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
