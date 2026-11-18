import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

public class ParallelMergeSort {

    static class MergeSortTask extends RecursiveAction {

        private static final int THRESHOLD = 1000;

        private final int[] array;
        private final int left;
        private final int right;

        MergeSortTask(int[] array, int left, int right) {
            this.array = array;
            this.left = left;
            this.right = right;
        }

        @Override
        protected void compute() {
            if (right - left <= THRESHOLD) {
                Arrays.sort(array, left, right);
                return;
            }

            int middle = left + (right - left) / 2;

            MergeSortTask first =
                new MergeSortTask(array, left, middle);

            MergeSortTask second =
                new MergeSortTask(array, middle, right);

            invokeAll(first, second);

            merge(middle);
        }

        private void merge(int middle) {
            int[] temp = new int[right - left];

            int i = left;
            int j = middle;
            int k = 0;

            while (i < middle && j < right) {
                if (array[i] <= array[j]) {
                    temp[k++] = array[i++];
                } else {
                    temp[k++] = array[j++];
                }
            }

            while (i < middle)
                temp[k++] = array[i++];

            while (j < right)
                temp[k++] = array[j++];

            System.arraycopy(
                temp, 0,
                array, left,
                temp.length
            );
        }
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter array size: ");

        int n = sc.nextInt();

        if (n < 0) {
            System.out.println("Invalid array size.");
            sc.close();
            return;
        }

        int[] array = new int[n];

        System.out.println("Enter array elements:");

        for (int i = 0; i < n; i++)
            array[i] = sc.nextInt();

        ForkJoinPool pool = new ForkJoinPool();

        long start = System.nanoTime();

        try {
            pool.invoke(
                new MergeSortTask(array, 0, n)
            );
        } finally {
            pool.shutdown();
        }

        long end = System.nanoTime();

        System.out.println("\nSORTED ARRAY:");

        for (int value : array)
            System.out.print(value + " ");

        System.out.printf(
            "\n\nExecution Time: %.3f ms%n",
            (end - start) / 1_000_000.0
        );

        sc.close();
    }
}
