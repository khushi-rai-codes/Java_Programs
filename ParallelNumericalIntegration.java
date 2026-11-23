import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ParallelNumericalIntegration {

    static final double A = 0.0;
    static final double B = 1.0;

    static final long INTERVALS = 10_000_000;

    static final int THRESHOLD = 100_000;

    static class IntegrationTask
            extends RecursiveTask<Double> {

        private final long start;
        private final long end;
        private final double h;

        IntegrationTask(
            long start,
            long end,
            double h
        ) {
            this.start = start;
            this.end = end;
            this.h = h;
        }

        private double function(double x) {
            return 4.0 / (1.0 + x * x);
        }

        @Override
        protected Double compute() {

            long length = end - start;

            if (length <= THRESHOLD) {
                double sum = 0.0;

                for (long i = start; i < end; i++) {
                    double x1 = A + i * h;
                    double x2 = A + (i + 1) * h;

                    sum += (function(x1) +
                            function(x2)) * h / 2.0;
                }

                return sum;
            }

            long middle = start + length / 2;

            IntegrationTask left =
                new IntegrationTask(start, middle, h);

            IntegrationTask right =
                new IntegrationTask(middle, end, h);

            left.fork();

            double rightResult = right.compute();
            double leftResult = left.join();

            return leftResult + rightResult;
        }
    }

    public static void main(String[] args) {

        double h = (B - A) / INTERVALS;

        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.nanoTime();

        double result;

        try {
            result = pool.invoke(
                new IntegrationTask(0, INTERVALS, h)
            );
        } finally {
            pool.shutdown();
        }

        long endTime = System.nanoTime();

        double actual = Math.PI;

        System.out.println(
            "Parallel Numerical Integration"
        );

        System.out.println("------------------------------");

        System.out.printf(
            "Calculated Value: %.12f%n", result
        );

        System.out.printf(
            "Actual Value:     %.12f%n", actual
        );

        System.out.printf(
            "Absolute Error:   %.12f%n",
            Math.abs(actual - result)
        );

        System.out.printf(
            "Execution Time:   %.3f ms%n",
            (endTime - startTime) / 1_000_000.0
        );
    }
}
