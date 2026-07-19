import java.util.Scanner;
class Book {
    int id;
    String title;
    boolean issued;
    void addBook() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Book ID: ");
        id = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter Book Title: ");
        title = sc.nextLine();
        issued = false;
    }
    void issueBook() {
        issued = true;
    }
    void display() {
        System.out.println("\nBook Details");
        System.out.println("ID : " + id);
        System.out.println("Title : " + title);
        System.out.println("Status : " + (issued ? "Issued" : "Available"));
    }
}
public class LibraryManagement {
    public static void main(String[] args) {
        Book b = new Book();
        b.addBook();
        b.issueBook();
        b.display();
    }
}
