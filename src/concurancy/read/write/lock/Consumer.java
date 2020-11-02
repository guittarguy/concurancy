package concurancy.read.write.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

public class Consumer implements Callable<List<String>> {

    private final ConcurentReadWriteMap<Integer, Long> cache;

    public Consumer(ConcurentReadWriteMap<Integer, Long> cache) {
        this.cache = cache;
    }

    @Override
    public List<String> call() throws InterruptedException {
        List<String> a = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            a.add(String.valueOf(this.cache.get(i)));
        }
        return a;
    }
}
