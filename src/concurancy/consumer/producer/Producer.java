package concurancy.consumer.producer;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Producer implements Callable<String> {
    private final Lock lock;
    private final List<Integer> buffer;
    private final Condition isEmpty;
    private final Condition isFull;

    public Producer(Lock lock, List<Integer> buffer, Condition isEmpty, Condition isFull) {
        this.lock = lock;
        this.buffer = buffer;
        this.isEmpty = isEmpty;
        this.isFull = isFull;
    }

    @Override
    public String call() throws InterruptedException {
        int count = 0;
        while (count++ < 50) {
            try {
                lock.lock();
                while (buffer.size() == 49) {
                    //wait
                    this.isFull.await();
                }
                buffer.add(1);
                isEmpty.signalAll();
            } finally {
                lock.unlock();
            }
        }
        return "Produced " + (count - 1);
    }
}
