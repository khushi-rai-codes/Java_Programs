import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ConcurrentHashMap;

public class SlidingWindowRateLimiter {

    private static class ClientWindow {
        private final Deque<Long> timestamps =
            new ArrayDeque<>();
    }

    private final int maxRequests;
    private final long windowMillis;

    private final ConcurrentHashMap<String, ClientWindow>
        clients = new ConcurrentHashMap<>();

    public SlidingWindowRateLimiter(
            int maxRequests, long windowMillis) {

        if (maxRequests <= 0 || windowMillis <= 0) {
            throw new IllegalArgumentException(
                "Limits must be positive."
            );
        }

        this.maxRequests = maxRequests;
        this.windowMillis = windowMillis;
    }

    public boolean allowRequest(String clientId) {
        long now = System.currentTimeMillis();

        ClientWindow window = clients.computeIfAbsent(
            clientId,
            key -> new ClientWindow()
        );

        synchronized (window) {

            long earliestAllowed = now - windowMillis;

            while (!window.timestamps.isEmpty() &&
                   window.timestamps.peekFirst()
                       <= earliestAllowed) {

                window.timestamps.pollFirst();
            }

            if (window.timestamps.size() >= maxRequests) {
                return false;
            }

            window.timestamps.addLast(now);

            return true;
        }
    }

    public int getRemainingRequests(String clientId) {
        ClientWindow window = clients.get(clientId);

        if (window == null)
            return maxRequests;

        long now = System.currentTimeMillis();

        synchronized (window) {
            long earliestAllowed = now - windowMillis;

            while (!window.timestamps.isEmpty() &&
                   window.timestamps.peekFirst()
                       <= earliestAllowed) {

                window.timestamps.pollFirst();
            }

            return maxRequests - window.timestamps.size();
        }
    }

    public static void main(String[] args)
            throws InterruptedException {

        SlidingWindowRateLimiter limiter =
            new SlidingWindowRateLimiter(5, 10000);

        String client = "CLIENT_101";

        System.out.println("SLIDING WINDOW RATE LIMITER");
        System.out.println("---------------------------");

        for (int i = 1; i <= 8; i++) {

            boolean allowed =
                limiter.allowRequest(client);

            System.out.println(
                "Request " + i + ": " +
                (allowed ? "ACCEPTED" : "REJECTED")
            );
        }

        System.out.println(
            "\nRemaining requests: " +
            limiter.getRemainingRequests(client)
        );

        System.out.println("\nWaiting for window expiration...");
        Thread.sleep(10500);

        boolean allowed = limiter.allowRequest(client);

        System.out.println(
            "New request after expiration: " +
            (allowed ? "ACCEPTED" : "REJECTED")
        );
    }
}
