import java.util.BitSet;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class BloomFilter {

    private final BitSet bits;
    private final int size;
    private final int hashCount;
    private int insertedCount;

    public BloomFilter(int expectedElements, double falsePositiveRate) {

        if (expectedElements <= 0) {
            throw new IllegalArgumentException(
                "Expected elements must be positive."
            );
        }

        if (!(falsePositiveRate > 0.0 &&
              falsePositiveRate < 1.0)) {
            throw new IllegalArgumentException(
                "False positive rate must be between 0 and 1."
            );
        }

        double calculatedSize = -(
            expectedElements * Math.log(falsePositiveRate)
        ) / (Math.log(2) * Math.log(2));

        if (calculatedSize > Integer.MAX_VALUE) {
            throw new IllegalArgumentException(
                "Requested filter is too large."
            );
        }

        this.size = Math.max(1, (int) Math.ceil(calculatedSize));

        this.hashCount = Math.max(
            1,
            (int) Math.round(
                (double) size / expectedElements * Math.log(2)
            )
        );

        this.bits = new BitSet(size);
    }

    private long[] hash(String value) {
        try {
            MessageDigest digest =
                MessageDigest.getInstance("SHA-256");

            byte[] hash = digest.digest(
                value.getBytes(StandardCharsets.UTF_8)
            );

            long h1 = 0;
            long h2 = 0;

            for (int i = 0; i < 8; i++) {
                h1 = (h1 << 8) | (hash[i] & 0xffL);
                h2 = (h2 << 8) | (hash[i + 8] & 0xffL);
            }

            return new long[] {
                h1,
                h2 | 1L
            };

        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException(
                "SHA-256 is unavailable.", e
            );
        }
    }

    private int getIndex(long h1, long h2, int i) {
        long combined = h1 + i * h2;

        return (int) Long.remainderUnsigned(
            combined, size
        );
    }

    public synchronized void add(String value) {
        if (value == null)
            throw new IllegalArgumentException("Null is not allowed.");

        long[] hashes = hash(value);

        for (int i = 0; i < hashCount; i++) {
            bits.set(
                getIndex(hashes[0], hashes[1], i)
            );
        }

        insertedCount++;
    }

    public synchronized boolean mightContain(String value) {
        if (value == null)
            throw new IllegalArgumentException("Null is not allowed.");

        long[] hashes = hash(value);

        for (int i = 0; i < hashCount; i++) {
            if (!bits.get(
                getIndex(hashes[0], hashes[1], i)
            )) {
                return false;
            }
        }

        return true;
    }

    public int getBitArraySize() {
        return size;
    }

    public int getHashCount() {
        return hashCount;
    }

    public int getInsertedCount() {
        return insertedCount;
    }

    public static void main(String[] args) {

        BloomFilter filter = new BloomFilter(1000, 0.01);

        filter.add("Java");
        filter.add("Python");
        filter.add("C++");
        filter.add("JavaScript");

        System.out.println("BLOOM FILTER");
        System.out.println("-----------------------");

        String[] tests = {
            "Java",
            "Python",
            "C++",
            "Ruby",
            "Go"
        };

        for (String item : tests) {
            System.out.printf(
                "%-12s : %s%n",
                item,
                filter.mightContain(item)
                    ? "Possibly present"
                    : "Definitely absent"
            );
        }

        System.out.println("\nBit array size: "
            + filter.getBitArraySize());

        System.out.println("Hash functions: "
            + filter.getHashCount());

        System.out.println("Inserted items: "
            + filter.getInsertedCount());
    }
}
