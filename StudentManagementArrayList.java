import java.util.ArrayList;
import java.util.Scanner;
class Student {
    int roll;
    String name;
    Student(int roll, String name) {
        this.roll = roll;
        this.name = name;
    }
    void display() {
        System.out.println("Roll No : " + roll);
        System.out.println("Name    : " + name);
    }
}
public class StudentManagementArrayList {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();
        for (int i = 0; i < n; i++) {
            System.out.println("\nStudent " + (i + 1));
            System.out.print("Roll No: ");
            int roll = sc.nextInt();
            sc.nextLine();
            System.out.print("Name: ");
            String name = sc.nextLine();
            students.add(new Student(roll, name));
        }
        System.out.println("\n----- Student Records -----");
        for (Student s : students) {
            s.display();
            System.out.println();
        }
        sc.close();
    }
}
