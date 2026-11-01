import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.Arrays;

public class ConcurrentWebCrawler {

    private final BlockingQueue<String> queue =
        new LinkedBlockingQueue<>();

    private final Set<String> visited =
        ConcurrentHashMap.newKeySet();

    private final AtomicInteger pending =
        new AtomicInteger(0);

    private final ExecutorService executor;

    public ConcurrentWebCrawler(int workers) {
        if (workers <= 0) {
            throw new IllegalArgumentException(
                "Worker count must be positive."
            );
        }

        executor = Executors.newFixedThreadPool(workers);
    }

    public void addUrl(String url) {
        if (url != null && visited.add(url)) {
            pending.incrementAndGet();
            queue.offer(url);
        }
    }

    private List<String> getLinks(String url) {
        // Simulated links for demonstration.
        switch (url) {
            case "https://example.com":
                return Arrays.asList(
                    "https://example.com/about",
                    "https://example.com/contact"
                );

            case "https://example.com/about":
                return Arrays.asList(
                    "https://example.com/team"
                );

            case "https://example.com/contact":
                return Arrays.asList(
                    "https://example.com/about"
                );

            default:
                return List.of();
        }
    }

    private void worker() {
        while (true) {
            String url;

            try {
                url = queue.poll(
                    200, TimeUnit.MILLISECONDS
                );

                if (url == null) {
                    if (pending.get() == 0)
                        return;

                    continue;
                }

                System.out.println(
                    Thread.currentThread().getName()
                    + " crawling: " + url
                );

                List<String> links = getLinks(url);

                for (String link : links)
                    addUrl(link);

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                return;
            } finally {
                // A dequeued URL is marked complete.
                // Only decrement when a URL was actually obtained.
                if (Thread.currentThread().isInterrupted())
                    return;
            }
        }
    }

    public void start(String startUrl) {
        addUrl(startUrl);

        for (int i = 0; i < 4; i++) {
            executor.submit(this::worker);
        }

        executor.shutdown();

        try {
            if (!executor.awaitTermination(
                    30, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }

        System.out.println("\nVisited URLs:");

        for (String url : visited)
            System.out.println(url);
    }

    public static void main(String[] args) {
        ConcurrentWebCrawler crawler =
            new ConcurrentWebCrawler(4);

        crawler.start("https://example.com");
    }
}
