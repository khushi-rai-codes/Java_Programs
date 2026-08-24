import java.util.Scanner;
class Employee {
    int id;
    String name;
    double basicSalary;
    Employee(int id, String name, double basicSalary) {
        this.id = id;
        this.name = name;
        this.basicSalary = basicSalary;
    }
    double calculateHRA() {
        return basicSalary * 0.20;
    }
    double calculateDA() {
        return basicSalary * 0.10;
    }
    double calculateGrossSalary() {
        return basicSalary + calculateHRA() + calculateDA();
    }
    double calculateTax() {
        return calculateGrossSalary() * 0.05;
    }
    double calculateNetSalary() {
        return calculateGrossSalary() - calculateTax();
    }
    void displaySalaryDetails() {
        System.out.println("\n===== EMPLOYEE PAYSLIP =====");
        System.out.println("Employee ID     : " + id);
        System.out.println("Employee Name   : " + name);
        System.out.printf("Basic Salary    : %.2f%n", basicSalary);
        System.out.printf("HRA             : %.2f%n", calculateHRA());
        System.out.printf("DA              : %.2f%n", calculateDA());
        System.out.printf("Gross Salary    : %.2f%n", calculateGrossSalary());
        System.out.printf("Tax             : %.2f%n", calculateTax());
        System.out.printf("Net Salary      : %.2f%n", calculateNetSalary());
    }
}
public class EmployeePayroll {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Employee ID: ");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Employee Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Basic Salary: ");
        double salary = sc.nextDouble();
        Employee employee =
                new Employee(id, name, salary);
        employee.displaySalaryDetails();
        sc.close();
    }
}
