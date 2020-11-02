package concurancy.read.write.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
        ExecutorService executorService = Executors.newFixedThreadPool(6);
        ConcurentReadWriteMap<Integer, Long> cache = new ConcurentReadWriteMap<>(readWriteLock);

        List<Callable<String>> producers = new ArrayList<>();
        List<Callable<List<String>>> consumers = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            producers.add(new Producer(cache));
        }

        for (int i = 0; i < 2; i++) {
            consumers.add(new Consumer(cache));
        }

        List<Future<String>> futures = executorService.invokeAll(producers);

        List<Future<List<String>>> con = executorService.invokeAll(consumers);

        try {
            futures.forEach(stringFuture -> {
                try {
                    System.out.println(stringFuture.get());
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }
            });
        } finally {
            executorService.shutdown();
        }

        try {
            con.forEach(listFuture -> {
                try {
                    System.out.println(listFuture.get());
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println("Exception: " + ex.getMessage());
                }
            });
        } finally {
            executorService.shutdown();
        }
    }
}
