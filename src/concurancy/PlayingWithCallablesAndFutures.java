package concurancy;

import java.util.concurrent.*;

public class PlayingWithCallablesAndFutures {

    public static void main(String[] args) throws InterruptedException, ExecutionException, TimeoutException {
        Callable<String> task = () -> {
            throw new IllegalStateException("Test Exception");
//            Thread.sleep(3000);
//            return "I am in thread " + Thread.currentThread().getName();
        };
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        try {
            for (int i = 0; i < 10; i++) {
                Future<String> future = executorService.submit(task);
                System.out.println("I get: " + future.get());
            }
        } finally {

            executorService.shutdown();
        }
    }
}
