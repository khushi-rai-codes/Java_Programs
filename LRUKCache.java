import java.util.*;

public class LRUKCache<K, V> {

    private static class Entry<V> {
        V value;
        Deque<Long> history = new ArrayDeque<>();

        Entry(V value) {
            this.value = value;
        }
    }

    private final int capacity;
    private final int k;

    private long clock = 0;

    private final Map<K, Entry<V>> cache =
        new HashMap<>();

    public LRUKCache(int capacity, int k) {
        if (capacity <= 0 || k <= 0) {
            throw new IllegalArgumentException(
                "Capacity and K must be positive."
            );
        }

        this.capacity = capacity;
        this.k = k;
    }

    private void recordAccess(Entry<V> entry) {
        entry.history.addLast(++clock);

        while (entry.history.size() > k) {
            entry.history.removeFirst();
        }
    }

    public synchronized V get(K key) {
        Entry<V> entry = cache.get(key);

        if (entry == null)
            return null;

        recordAccess(entry);

        return entry.value;
    }

    public synchronized void put(K key, V value) {
        Entry<V> existing = cache.get(key);

        if (existing != null) {
            existing.value = value;
            recordAccess(existing);
            return;
        }

        if (cache.size() >= capacity) {
            evict();
        }

        Entry<V> entry = new Entry<>(value);
        recordAccess(entry);

        cache.put(key, entry);
    }

    private void evict() {
        K victim = null;
        long oldest = Long.MAX_VALUE;

        for (Map.Entry<K, Entry<V>> item :
             cache.entrySet()) {

            Deque<Long> history =
                item.getValue().history;

            long evictionTime;

            if (history.size() < k) {
                // Entries accessed fewer than K times
                // are evicted before frequently used ones.
                evictionTime = history.getFirst();
            } else {
                // The K-th most recent access.
                evictionTime = history.getFirst();
            }

            if (evictionTime < oldest) {
                oldest = evictionTime;
                victim = item.getKey();
            }
        }

        if (victim != null) {
            cache.remove(victim);
            System.out.println("Evicted: " + victim);
        }
    }

    public synchronized void display() {
        System.out.println("Current Cache:");

        for (Map.Entry<K, Entry<V>> item :
             cache.entrySet()) {

            System.out.println(
                item.getKey() + " = " +
                item.getValue().value +
                " | Access History: " +
                item.getValue().history
            );
        }
    }

    public static void main(String[] args) {

        LRUKCache<Integer, String> cache =
            new LRUKCache<>(3, 2);

        cache.put(1, "Java");
        cache.put(2, "Python");
        cache.put(3, "C++");

        cache.get(1);
        cache.get(1);

        cache.get(2);

        cache.put(4, "C");

        cache.display();
    }
}
