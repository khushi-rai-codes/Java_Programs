import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

public class ConsistentHashing {

    private final TreeMap<Long, String> ring =
        new TreeMap<>();

    private final int virtualNodes;

    public ConsistentHashing(int virtualNodes) {
        if (virtualNodes <= 0) {
            throw new IllegalArgumentException(
                "Virtual node count must be positive."
            );
        }

        this.virtualNodes = virtualNodes;
    }

    private long hash(String value) {
        try {
            MessageDigest digest =
                MessageDigest.getInstance("SHA-256");

            byte[] bytes = digest.digest(
                value.getBytes(StandardCharsets.UTF_8)
            );

            long result = 0;

            for (int i = 0; i < 8; i++) {
                result = (result << 8) |
                    (bytes[i] & 0xffL);
            }

            return result;

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                "SHA-256 is unavailable.", e
            );
        }
    }

    public void addServer(String server) {
        if (server == null || server.isEmpty()) {
            throw new IllegalArgumentException(
                "Server name cannot be empty."
            );
        }

        for (int i = 0; i < virtualNodes; i++) {
            String virtualNode = server + "#" + i;
            ring.put(hash(virtualNode), server);
        }
    }

    public void removeServer(String server) {
        for (int i = 0; i < virtualNodes; i++) {
            String virtualNode = server + "#" + i;
            ring.remove(hash(virtualNode));
        }
    }

    public String getServer(String key) {
        if (key == null || key.isEmpty()) {
            throw new IllegalArgumentException(
                "Key cannot be empty."
            );
        }

        if (ring.isEmpty())
            return null;

        long hashValue = hash(key);

        Map.Entry<Long, String> entry =
            ring.ceilingEntry(hashValue);

        if (entry == null)
            entry = ring.firstEntry();

        return entry.getValue();
    }

    public static void main(String[] args) {
        ConsistentHashing hashing =
            new ConsistentHashing(100);

        hashing.addServer("Server-A");
        hashing.addServer("Server-B");
        hashing.addServer("Server-C");

        List<String> keys = new ArrayList<>();

        for (int i = 1; i <= 15; i++)
            keys.add("User-" + i);

        System.out.println("Initial Distribution:");

        for (String key : keys) {
            System.out.println(
                key + " -> " + hashing.getServer(key)
            );
        }

        System.out.println("\nAdding Server-D...");

        hashing.addServer("Server-D");

        System.out.println("\nUpdated Distribution:");

        for (String key : keys) {
            System.out.println(
                key + " -> " + hashing.getServer(key)
            );
        }

        System.out.println("\nRemoving Server-B...");

        hashing.removeServer("Server-B");

        System.out.println("\nFinal Distribution:");

        for (String key : keys) {
            System.out.println(
                key + " -> " + hashing.getServer(key)
            );
        }
    }
}
