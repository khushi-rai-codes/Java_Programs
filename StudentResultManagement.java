import java.util.ArrayList;
import java.util.Scanner;
class Student {
    int roll;
    String name;
    double marks;
    Student(int roll, String name, double marks) {
        this.roll = roll;
        this.name = name;
        this.marks = marks;
    }
    void display() {
        String result = (marks >= 40) ? "Pass" : "Fail";
        System.out.println("\nRoll No : " + roll);
        System.out.println("Name    : " + name);
        System.out.println("Marks   : " + marks);
        System.out.println("Result  : " + result);
    }
}
public class StudentResultManagement {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();
        System.out.print("Enter number of students: ");
        int n = sc.nextInt();
        sc.nextLine();
        for(int i = 0; i < n; i++) {
            System.out.println("\nStudent " + (i + 1));
            System.out.print("Roll No: ");
            int roll = sc.nextInt();
            sc.nextLine();
            System.out.print("Name: ");
            String name = sc.nextLine();
            System.out.print("Marks: ");
            double marks = sc.nextDouble();
            sc.nextLine();
            students.add(new Student(roll, name, marks));
        }
        System.out.println("\n===== Student Records =====");
        for(Student s : students)
            s.display();
        sc.close();
    }
}
