import java.util.Scanner;
public class StudentGradeCalculator {
    public static char calculateGrade(double marks) {
        if (marks >= 90)
            return 'A';
        else if (marks >= 75)
            return 'B';
        else if (marks >= 60)
            return 'C';
        else if (marks >= 40)
            return 'D';
        else
            return 'F';
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Student Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Marks: ");
        double marks = sc.nextDouble();
        char grade = calculateGrade(marks);
        System.out.println("\nStudent Name : " + name);
        System.out.println("Marks        : " + marks);
        System.out.println("Grade        : " + grade);
        sc.close();
    }
}
