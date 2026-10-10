import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class MultithreadedFileSearch {

    static class FileSearchTask implements Runnable {
        private final Path file;
        private final String keyword;

        FileSearchTask(Path file, String keyword) {
            this.file = file;
            this.keyword = keyword.toLowerCase();
        }

        @Override
        public void run() {
            int lineNumber = 0;
            int matches = 0;

            try (BufferedReader reader = Files.newBufferedReader(file)) {
                String line;

                while ((line = reader.readLine()) != null) {
                    lineNumber++;

                    if (line.toLowerCase().contains(keyword)) {
                        System.out.printf(
                            "[%s] Line %d: %s%n",
                            file.getFileName(),
                            lineNumber,
                            line.trim()
                        );

                        matches++;
                    }
                }

                System.out.printf(
                    "[%s] Total matches: %d%n",
                    file.getFileName(),
                    matches
                );

            } catch (IOException e) {
                System.err.println(
                    "Could not read " + file + ": " + e.getMessage()
                );
            }
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter directory path: ");
        Path directory = Paths.get(scanner.nextLine().trim());

        System.out.print("Enter keyword to search: ");
        String keyword = scanner.nextLine().trim();

        if (keyword.isEmpty()) {
            System.out.println("Keyword cannot be empty.");
            scanner.close();
            return;
        }

        if (!Files.isDirectory(directory)) {
            System.out.println("Invalid directory.");
            scanner.close();
            return;
        }

        List<Path> files = new ArrayList<>();

        try (var paths = Files.list(directory)) {
            paths.filter(Files::isRegularFile)
                 .filter(path ->
                     path.getFileName().toString()
                         .toLowerCase().endsWith(".txt")
                 )
                 .forEach(files::add);

        } catch (IOException e) {
            System.out.println(
                "Unable to list files: " + e.getMessage()
            );
            scanner.close();
            return;
        }

        if (files.isEmpty()) {
            System.out.println("No text files found.");
            scanner.close();
            return;
        }

        int threadCount = Math.min(
            files.size(),
            Runtime.getRuntime().availableProcessors()
        );

        ExecutorService executor =
            Executors.newFixedThreadPool(Math.max(1, threadCount));

        for (Path file : files) {
            executor.submit(new FileSearchTask(file, keyword));
        }

        executor.shutdown();

        try {
            if (!executor.awaitTermination(1, TimeUnit.HOURS)) {
                executor.shutdownNow();
                System.out.println("Search timed out.");
            } else {
                System.out.println("Search completed.");
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
            System.out.println("Search interrupted.");
        }

        scanner.close();
    }
}
