package concurancy.consumer.producer;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class Consumer implements Callable<String> {

    private final Lock lock;
    private final List<Integer> buffer;
    private final Condition isEmpty;
    private final Condition isFull;

    public Consumer(Lock lock, List<Integer> buffer, Condition isEmpty, Condition isFull) {
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
                while (buffer.isEmpty()) {
                    //wait
                    this.isEmpty.await();
                }
                buffer.remove(buffer.size() - 1);
                this.isFull.signalAll();
            } finally {
                lock.unlock();
            }
        }
        return "Consumed " + (count - 1);
    }
}
