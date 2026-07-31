import java.util.Scanner;
class BankAccount {
    private String name;
    private double balance;
    public void createAccount(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }
    public void deposit(double amount) {
        balance += amount;
    }
    public void withdraw(double amount) {
        if(amount <= balance)
            balance -= amount;
        else
            System.out.println("Insufficient Balance!");
    }
    public void display() {
        System.out.println("\nAccount Holder : " + name);
        System.out.println("Balance        : " + balance);
    }
}
public class BankManagementSystem {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Account Holder Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Initial Balance: ");
        double balance = sc.nextDouble();
        BankAccount account = new BankAccount();
        account.createAccount(name, balance);
        System.out.print("Enter Deposit Amount: ");
        account.deposit(sc.nextDouble());
        System.out.print("Enter Withdraw Amount: ");
        account.withdraw(sc.nextDouble());
        account.display();
        sc.close();
    }
}
