package concurancy;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PlayingWithExecutorsAndRunnables {

    public static void main(String[] args) {
        Runnable task = () -> System.out.println("I am in thread " + Thread.currentThread().getName());
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
        ExecutorService executorService = Executors.newFixedThreadPool(4);
        for (int i=0; i< 10; i++){
            executorService.execute(task);
//            new Thread(task).start();
        }
        executorService.shutdown();
    }

}
