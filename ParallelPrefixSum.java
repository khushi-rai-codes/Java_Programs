import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelPrefixSum {

    static class SumTask extends RecursiveTask<Long> {

        private final int[] array;
        private final int start;
        private final int end;

        private static final int THRESHOLD = 1000;

        SumTask(int[] array, int start, int end) {
            this.array = array;
            this.start = start;
            this.end = end;
        }

        @Override
        protected Long compute() {

            if (end - start <= THRESHOLD) {
                long sum = 0;

                for (int i = start; i < end; i++) {
                    sum += array[i];
                }

                return sum;
            }

            int middle = (start + end) / 2;

            SumTask left = new SumTask(array, start, middle);
            SumTask right = new SumTask(array, middle, end);

            left.fork();

            long rightResult = right.compute();
            long leftResult = left.join();

            return leftResult + rightResult;
        }
    }

    static void parallelPrefixSum(int[] array) {

        int n = array.length;

        if (n == 0)
            return;

        ForkJoinPool pool = new ForkJoinPool();

        try {
            long total = pool.invoke(
                new SumTask(array, 0, n)
            );

            long[] prefix = new long[n];

            int chunks = Math.min(
                pool.getParallelism(), n
            );

            int chunkSize = (n + chunks - 1) / chunks;

            SumTask[] tasks = new SumTask[chunks];
            long[] sums = new long[chunks];

            for (int i = 0; i < chunks; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize, n);

                if (start < end) {
                    tasks[i] = new SumTask(array, start, end);
                    sums[i] = pool.invoke(tasks[i]);
                }
            }

            long offset = 0;

            for (int i = 0; i < chunks; i++) {
                int start = i * chunkSize;
                int end = Math.min(start + chunkSize, n);

                if (start >= end)
                    continue;

                long running = offset;

                for (int j = start; j < end; j++) {
                    running += array[j];
                    prefix[j] = running;
                }

                offset += sums[i];
            }

            System.out.println("Prefix Sum: "
                    + Arrays.toString(prefix));

            System.out.println("Total Sum: " + total);

        } finally {
            pool.shutdown();
        }
    }

    public static void main(String[] args) {

        int[] numbers = {
            2, 4, 6, 8, 10, 12, 14, 16
        };

        System.out.println("Original Array: "
                + Arrays.toString(numbers));

        parallelPrefixSum(numbers);
    }
}
