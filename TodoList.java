import java.util.ArrayList;
import java.util.Scanner;
public class TodoList {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<String> tasks = new ArrayList<>();
        while (true) {
            System.out.println("\n===== TO-DO LIST =====");
            System.out.println("1. Add Task");
            System.out.println("2. View Tasks");
            System.out.println("3. Remove Task");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");
            int choice = sc.nextInt();
            sc.nextLine();
            switch (choice) {
                case 1:
                    System.out.print("Enter task: ");
                    String task = sc.nextLine();
                    tasks.add(task);
                    System.out.println("Task added successfully.");
                    break;
                case 2:
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks available.");
                    } else {
                        System.out.println("\n----- Your Tasks -----");
                        for (int i = 0; i < tasks.size(); i++) {
                            System.out.println(
                                (i + 1) + ". " + tasks.get(i)
                            );
                        }
                    }
                    break;
                case 3:
                    if (tasks.isEmpty()) {
                        System.out.println("No tasks to remove.");
                    } else {
                        System.out.print("Enter task number: ");
                        int taskNumber = sc.nextInt();
                        if (taskNumber >= 1 &&
                            taskNumber <= tasks.size()) {
                            tasks.remove(taskNumber - 1);
                            System.out.println(
                                "Task removed successfully."
                            );
                        } else {
                            System.out.println("Invalid task number.");
                        }
                    }
                    break;
                case 4:
                    System.out.println("Exiting To-Do List.");
                    sc.close();
                    return;
                default:
                    System.out.println("Invalid choice.");
            }
        }
    }
}
