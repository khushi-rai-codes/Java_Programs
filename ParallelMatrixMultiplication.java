import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMatrixMultiplication {

    static class MatrixTask extends RecursiveAction {
        private static final int THRESHOLD = 16;

        private final long[][] A;
        private final long[][] B;
        private final long[][] C;

        private final int startRow;
        private final int endRow;
        private final int n;

        MatrixTask(long[][] A, long[][] B, long[][] C,
                   int startRow, int endRow, int n) {
            this.A = A;
            this.B = B;
            this.C = C;

            this.startRow = startRow;
            this.endRow = endRow;
            this.n = n;
        }

        @Override
        protected void compute() {
            if (endRow - startRow <= THRESHOLD) {
                multiplyRows();
                return;
            }

            int mid = (startRow + endRow) / 2;

            MatrixTask left = new MatrixTask(
                A, B, C, startRow, mid, n
            );

            MatrixTask right = new MatrixTask(
                A, B, C, mid, endRow, n
            );

            invokeAll(left, right);
        }

        private void multiplyRows() {
            for (int i = startRow; i < endRow; i++) {
                for (int j = 0; j < n; j++) {
                    long sum = 0;

                    for (int k = 0; k < n; k++) {
                        sum += A[i][k] * B[k][j];
                    }

                    C[i][j] = sum;
                }
            }
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter matrix dimension: ");

        int n = sc.nextInt();

        if (n <= 0) {
            System.out.println("Invalid matrix dimension.");
            sc.close();
            return;
        }

        long[][] A = new long[n][n];
        long[][] B = new long[n][n];
        long[][] C = new long[n][n];

        System.out.println("Enter first matrix:");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                A[i][j] = sc.nextLong();
            }
        }

        System.out.println("Enter second matrix:");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                B[i][j] = sc.nextLong();
            }
        }

        ForkJoinPool pool = new ForkJoinPool();

        long startTime = System.nanoTime();

        try {
            pool.invoke(
                new MatrixTask(A, B, C, 0, n, n)
            );
        } finally {
            pool.shutdown();
        }

        long endTime = System.nanoTime();

        System.out.println("\nResult Matrix:");

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                System.out.print(C[i][j] + "\t");
            }

            System.out.println();
        }

        double executionTime =
            (endTime - startTime) / 1_000_000.0;

        System.out.printf(
            "\nExecution time: %.3f ms%n",
            executionTime
        );

        sc.close();
    }
}
