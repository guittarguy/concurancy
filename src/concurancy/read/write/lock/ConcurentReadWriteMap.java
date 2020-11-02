package concurancy.read.write.lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;

public class ConcurentReadWriteMap<K, V> {

    private final ReadWriteLock lock;
    private final Map<K, V> cache;
    private final Lock readLock;
    private final Lock writeLock;

    public ConcurentReadWriteMap(ReadWriteLock lock) {
        this.lock = lock;
        this.cache = new HashMap<>();
        this.readLock = lock.readLock();
        this.writeLock = lock.writeLock();
    }

    public V put(K key, V value) {
        this.writeLock.lock();
        try {
            return this.cache.put(key, value);
        } finally {
            this.writeLock.unlock();
        }
    }

    public V get(K key) {
        this.readLock.lock();
        try {
            return this.cache.get(key);
        } finally {
            this.readLock.unlock();
        }
    }
}
