import java.util.concurrent.TimeUnit;

public class TokenBucketRateLimiter {

    private final long capacity;
    private final double refillRate;

    private double tokens;
    private long lastRefillTime;

    public TokenBucketRateLimiter(long capacity, double refillRate) {
        if (capacity <= 0) {
            throw new IllegalArgumentException(
                "Capacity must be positive."
            );
        }

        if (!Double.isFinite(refillRate) || refillRate <= 0) {
            throw new IllegalArgumentException(
                "Refill rate must be finite and positive."
            );
        }

        this.capacity = capacity;
        this.refillRate = refillRate;
        this.tokens = capacity;
        this.lastRefillTime = System.nanoTime();
    }

    private void refill() {
        long now = System.nanoTime();
        long elapsed = now - lastRefillTime;

        if (elapsed <= 0) {
            return;
        }

        double seconds = elapsed / 1_000_000_000.0;

        tokens = Math.min(
            capacity,
            tokens + seconds * refillRate
        );

        lastRefillTime = now;
    }

    public synchronized boolean tryAcquire() {
        refill();

        if (tokens >= 1.0) {
            tokens -= 1.0;
            return true;
        }

        return false;
    }

    public synchronized double availableTokens() {
        refill();
        return tokens;
    }

    public synchronized long estimatedWaitMillis() {
        refill();

        if (tokens >= 1.0) {
            return 0;
        }

        double secondsUntilToken = (1.0 - tokens) / refillRate;

        return (long) Math.ceil(secondsUntilToken * 1000.0);
    }

    public static void main(String[] args)
            throws InterruptedException {

        TokenBucketRateLimiter limiter =
            new TokenBucketRateLimiter(5, 2.0);

        System.out.println("Token Bucket Rate Limiter");
        System.out.println("Capacity: 5 tokens");
        System.out.println("Refill rate: 2 tokens/second\n");

        for (int i = 1; i <= 10; i++) {
            boolean allowed = limiter.tryAcquire();

            System.out.printf(
                "Request %2d: %-10s | Tokens remaining: %.2f%n",
                i,
                allowed ? "ACCEPTED" : "REJECTED",
                limiter.availableTokens()
            );

            if (i == 5) {
                System.out.println("\nWaiting for tokens to refill...\n");
                TimeUnit.SECONDS.sleep(2);
            }
        }

        System.out.println(
            "\nEstimated wait for next token: "
                + limiter.estimatedWaitMillis()
                + " ms"
        );
    }
}
