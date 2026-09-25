import java.util.ArrayList;
import java.util.Scanner;

class Student {
    private int rollNumber;
    private String name;
    private int presentDays;
    private int totalDays;

    public Student(int rollNumber, String name) {
        this.rollNumber = rollNumber;
        this.name = name;
        this.presentDays = 0;
        this.totalDays = 0;
    }

    public void markAttendance(boolean present) {
        totalDays++;

        if (present) {
            presentDays++;
        }
    }

    public void display() {
        double percentage = totalDays == 0
                ? 0
                : (presentDays * 100.0) / totalDays;

        System.out.println(
            rollNumber + "\t" +
            name + "\t" +
            presentDays + "/" + totalDays + "\t" +
            String.format("%.2f", percentage) + "%"
        );
    }
}

public class StudentAttendanceSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        System.out.print("Enter number of students: ");
        int n = scanner.nextInt();
        scanner.nextLine();

        for (int i = 0; i < n; i++) {
            System.out.println("\nStudent " + (i + 1));

            System.out.print("Enter roll number: ");
            int rollNumber = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter name: ");
            String name = scanner.nextLine();

            students.add(new Student(rollNumber, name));
        }

        System.out.print("\nEnter number of attendance days: ");
        int days = scanner.nextInt();

        for (int day = 1; day <= days; day++) {
            System.out.println("\nDay " + day);

            for (Student student : students) {
                System.out.print("Enter 1 for Present, 0 for Absent: ");
                int status = scanner.nextInt();

                student.markAttendance(status == 1);
            }
        }

        System.out.println("\n===== ATTENDANCE REPORT =====");
        System.out.println("Roll\tName\tAttendance\tPercentage");

        for (Student student : students) {
            student.display();
        }

        scanner.close();
    }
}
