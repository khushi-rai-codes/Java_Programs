import java.util.Scanner;
import java.util.Stack;
public class BrowserHistory {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Stack<String> history = new Stack<>();
        while (true) {
            System.out.println("\n===== BROWSER HISTORY =====");
            System.out.println("1. Visit Website");
            System.out.println("2. Go Back");
            System.out.println("3. Current Page");
            System.out.println("4. Display History");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter website: ");
                    String website = sc.nextLine();
                    history.push(website);
                    System.out.println("Website visited.");
                    break;
                case 2:
                    if (history.size() > 1) {
                        history.pop();
                        System.out.println(
                            "Returned to: " + history.peek()
                        );
                    } else {
                        System.out.println("No previous page.");
                    }
                    break;
                case 3:
                    if (!history.isEmpty()) {
                        System.out.println(
                            "Current Page: " + history.peek()
                        );
                    } else {
                        System.out.println("No page opened.");
                    }
                    break;
                case 4:
                    if (history.isEmpty()) {
                        System.out.println("History is empty.");
                    } else {
                        System.out.println("\nBrowser History:");
                        for (String page : history) {
                            System.out.println(page);
                        }
                    }
                    break;
                case 5:
                    System.out.println("Exiting browser history.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
