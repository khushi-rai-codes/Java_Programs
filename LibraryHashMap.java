import java.util.HashMap;
import java.util.Scanner;
public class LibraryHashMap {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        HashMap<Integer, String> library = new HashMap<>();
        while (true) {
            System.out.println("\n===== Library Management =====");
            System.out.println("1. Add Book");
            System.out.println("2. Search Book");
            System.out.println("3. Display All Books");
            System.out.println("4. Remove Book");
            System.out.println("5. Exit");
            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter Book ID: ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    System.out.print("Enter Book Title: ");
                    String title = sc.nextLine();
                    library.put(id, title);
                    System.out.println("Book added successfully.");
                    break;
                case 2:
                    System.out.print("Enter Book ID to search: ");
                    int searchId = sc.nextInt();
                    if (library.containsKey(searchId)) {
                        System.out.println("Book Found: "
                                + library.get(searchId));
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 3:
                    if (library.isEmpty()) {
                        System.out.println("Library is empty.");
                    } else {
                        System.out.println("\n----- Books -----");
                        for (Integer bookId : library.keySet()) {
                            System.out.println(
                                    "ID: " + bookId
                                    + " | Title: " + library.get(bookId)
                            );
                        }
                    }
                    break;
                case 4:
                    System.out.print("Enter Book ID to remove: ");
                    int removeId = sc.nextInt();
                    if (library.remove(removeId) != null) {
                        System.out.println("Book removed successfully.");
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting Library Management.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
