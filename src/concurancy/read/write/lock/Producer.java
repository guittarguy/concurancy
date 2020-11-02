package concurancy.read.write.lock;

import java.util.Random;
import java.util.concurrent.Callable;

public class Producer implements Callable<String> {
    private final Random random;
    private final ConcurentReadWriteMap<Integer, Long> cache;

    public Producer(ConcurentReadWriteMap<Integer, Long> cache) {
        random = new Random();
        this.cache = cache;
    }
    @Override
    public String call() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            long key = random.nextInt(10);
            cache.put(i, Long.valueOf(key));
            if (cache.get(i) == null) {
                System.out.println("Key " + key + " not found");
            }
        }
        return "Added 1000 values";
    }
}
