package concurancy.bariers;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        class Friend implements Callable<String> {

            private final CyclicBarrier cyclicBarrier;

            public Friend(CyclicBarrier cyclicBarrier) {
                this.cyclicBarrier = cyclicBarrier;
            }

            @Override
            public String call() throws Exception {
                Random r = new Random();
                Thread.sleep((r.nextInt(20)*100+100));
                System.out.println("I just arrived, waiting for others...");
                cyclicBarrier.await();
                System.out.println("Let's go to cinema!");
                return "ok";
            }
        }

        ExecutorService executorService = Executors.newFixedThreadPool(4);
        CyclicBarrier cyclicBarrier = new CyclicBarrier(4, () -> System.out.println("barier oppening"));
        List<Future<String>> futures = new ArrayList<>();
        try {
            for (int i = 0; i < 4; i++) {
                Friend friend = new Friend(cyclicBarrier);
                futures.add(executorService.submit(friend));
            }
            futures.forEach(stringFuture -> {
                try {
                    stringFuture.get();
                } catch (InterruptedException | ExecutionException ex) {
                    System.out.println(ex.getMessage());
                }
            });
        } finally {
            executorService.shutdown();
        }
    }
}
