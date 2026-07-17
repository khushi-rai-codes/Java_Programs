import java.util.Scanner;
class BankAccount {
    private String accountHolder;
    private double balance;
    public void createAccount() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Account Holder Name: ");
        accountHolder = sc.nextLine();
        System.out.print("Enter Initial Balance: ");
        balance = sc.nextDouble();
    }
    public void deposit(double amount) {
        balance += amount;
    }
    public void display() {
        System.out.println("\n----- Account Details -----");
        System.out.println("Account Holder : " + accountHolder);
        System.out.println("Balance : " + balance);
    }
    public static void main(String[] args) {
        BankAccount account = new BankAccount();
        account.createAccount();
        account.deposit(1000);
        account.display();
    }
}
