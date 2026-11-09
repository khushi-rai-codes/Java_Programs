import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;

public class LFUCache<K, V> {

    private static class Node<V> {
        V value;
        int frequency;

        Node(V value) {
            this.value = value;
            this.frequency = 1;
        }
    }

    private final int capacity;

    private int minFrequency = 0;

    private final Map<K, Node<V>> cache = new HashMap<>();

    private final Map<Integer, LinkedHashSet<K>> frequencyMap =
        new HashMap<>();

    public LFUCache(int capacity) {
        if (capacity < 0)
            throw new IllegalArgumentException(
                "Capacity cannot be negative."
            );

        this.capacity = capacity;
    }

    public synchronized V get(K key) {
        Node<V> node = cache.get(key);

        if (node == null)
            return null;

        updateFrequency(key, node);

        return node.value;
    }

    private void updateFrequency(K key, Node<V> node) {
        int oldFrequency = node.frequency;

        LinkedHashSet<K> oldSet =
            frequencyMap.get(oldFrequency);

        oldSet.remove(key);

        if (oldSet.isEmpty()) {
            frequencyMap.remove(oldFrequency);

            if (minFrequency == oldFrequency)
                minFrequency++;
        }

        node.frequency++;

        frequencyMap
            .computeIfAbsent(
                node.frequency,
                ignored -> new LinkedHashSet<>()
            )
            .add(key);
    }

    public synchronized void put(K key, V value) {
        if (capacity == 0)
            return;

        if (cache.containsKey(key)) {
            Node<V> node = cache.get(key);
            node.value = value;

            updateFrequency(key, node);
            return;
        }

        if (cache.size() >= capacity) {
            LinkedHashSet<K> leastUsed =
                frequencyMap.get(minFrequency);

            K evictedKey =
                leastUsed.iterator().next();

            leastUsed.remove(evictedKey);

            if (leastUsed.isEmpty())
                frequencyMap.remove(minFrequency);

            cache.remove(evictedKey);

            System.out.println("Evicted: " + evictedKey);
        }

        Node<V> node = new Node<>(value);

        cache.put(key, node);

        frequencyMap
            .computeIfAbsent(
                1,
                ignored -> new LinkedHashSet<>()
            )
            .add(key);

        minFrequency = 1;
    }

    public synchronized void display() {
        System.out.println("\nCurrent Cache:");

        for (Map.Entry<K, Node<V>> entry : cache.entrySet()) {
            System.out.println(
                "Key: " + entry.getKey()
                + ", Value: " + entry.getValue().value
                + ", Frequency: " + entry.getValue().frequency
            );
        }
    }

    public static void main(String[] args) {
        LFUCache<Integer, String> cache =
            new LFUCache<>(3);

        cache.put(1, "Java");
        cache.put(2, "Python");
        cache.put(3, "C++");

        cache.get(1);
        cache.get(1);
        cache.get(2);

        cache.display();

        System.out.println("\nInserting a new element:");

        cache.put(4, "JavaScript");

        cache.display();

        System.out.println("\nAccessing key 1:");

        System.out.println("Value: " + cache.get(1));
    }
}
