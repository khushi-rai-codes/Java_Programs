import java.util.Arrays;
import java.util.concurrent.RecursiveAction;
import java.util.concurrent.ForkJoinPool;

public class ParallelQuickSort {

    static class QuickSortTask extends RecursiveAction {

        private final int[] array;
        private final int low;
        private final int high;

        private static final int THRESHOLD = 1000;

        QuickSortTask(int[] array, int low, int high) {
            this.array = array;
            this.low = low;
            this.high = high;
        }

        @Override
        protected void compute() {

            if (low >= high)
                return;

            if (high - low < THRESHOLD) {
                Arrays.sort(array, low, high + 1);
                return;
            }

            int pivot = array[low + (high - low) / 2];

            int less = low;
            int current = low;
            int greater = high;

            // Three-way partitioning.
            while (current <= greater) {

                if (array[current] < pivot) {
                    swap(current, less);
                    current++;
                    less++;
                }

                else if (array[current] > pivot) {
                    swap(current, greater);
                    greater--;
                }

                else {
                    current++;
                }
            }

            QuickSortTask left =
                new QuickSortTask(array, low, less - 1);

            QuickSortTask right =
                new QuickSortTask(array, greater + 1, high);

            left.fork();

            right.compute();

            left.join();
        }

        private void swap(int i, int j) {
            int temp = array[i];
            array[i] = array[j];
            array[j] = temp;
        }
    }

    public static void main(String[] args) {

        int[] numbers = {
            45, 12, 89, 3, 67, 23,
            90, 11, 56, 34, 78, 2,
            99, 1, 45, 67, 12
        };

        System.out.println(
            "Original Array: " + Arrays.toString(numbers)
        );

        ForkJoinPool pool = new ForkJoinPool();

        try {
            pool.invoke(
                new QuickSortTask(
                    numbers, 0, numbers.length - 1
                )
            );
        } finally {
            pool.shutdown();
        }

        System.out.println(
            "Sorted Array:   " + Arrays.toString(numbers)
        );

        System.out.println(
            "Sorting Completed Successfully!"
        );
    }
}
