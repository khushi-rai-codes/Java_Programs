import java.util.ArrayList;
import java.util.Scanner;

class Product {
    private int id;
    private String name;
    private int quantity;
    private double price;

    public Product(int id, String name, int quantity, double price) {
        this.id = id;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void addStock(int amount) {
        if (amount > 0) {
            quantity += amount;
        }
    }

    public boolean sell(int amount) {
        if (amount <= 0 || amount > quantity) {
            return false;
        }

        quantity -= amount;
        return true;
    }

    public void display() {
        System.out.printf(
            "%-8d %-20s %-10d %.2f%n",
            id, name, quantity, price
        );
    }
}

public class InventoryManagementSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Product> products = new ArrayList<>();

        int choice;

        do {
            System.out.println("\n===== INVENTORY MANAGEMENT =====");
            System.out.println("1. Add Product");
            System.out.println("2. Add Stock");
            System.out.println("3. Sell Product");
            System.out.println("4. Display Inventory");
            System.out.println("5. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter product ID: ");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter product name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter quantity: ");
                    int quantity = scanner.nextInt();

                    System.out.print("Enter price: ");
                    double price = scanner.nextDouble();

                    products.add(
                        new Product(id, name, quantity, price)
                    );

                    System.out.println("Product added successfully.");
                    break;

                case 2:
                    System.out.print("Enter product ID: ");
                    int stockId = scanner.nextInt();

                    System.out.print("Enter quantity to add: ");
                    int stockAmount = scanner.nextInt();

                    boolean stockFound = false;

                    for (Product product : products) {
                        if (product.getId() == stockId) {
                            product.addStock(stockAmount);
                            stockFound = true;
                            System.out.println("Stock updated.");
                            break;
                        }
                    }

                    if (!stockFound) {
                        System.out.println("Product not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter product ID: ");
                    int sellId = scanner.nextInt();

                    System.out.print("Enter quantity to sell: ");
                    int sellAmount = scanner.nextInt();

                    boolean sellFound = false;

                    for (Product product : products) {
                        if (product.getId() == sellId) {
                            sellFound = true;

                            if (product.sell(sellAmount)) {
                                System.out.println("Sale completed.");
                            } else {
                                System.out.println(
                                    "Invalid quantity or insufficient stock."
                                );
                            }

                            break;
                        }
                    }

                    if (!sellFound) {
                        System.out.println("Product not found.");
                    }
                    break;

                case 4:
                    System.out.println("\n===== INVENTORY =====");
                    System.out.printf(
                        "%-8s %-20s %-10s %s%n",
                        "ID", "Name", "Quantity", "Price"
                    );

                    for (Product product : products) {
                        product.display();
                    }
                    break;

                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 5);

        scanner.close();
    }
}
