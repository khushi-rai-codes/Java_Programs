import java.util.ArrayList;
import java.util.Scanner;

class Room {
    private int roomNumber;
    private String type;
    private double price;
    private boolean booked;

    public Room(int roomNumber, String type, double price) {
        this.roomNumber = roomNumber;
        this.type = type;
        this.price = price;
        this.booked = false;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isBooked() {
        return booked;
    }

    public void book() {
        booked = true;
    }

    public void cancel() {
        booked = false;
    }

    public void display() {
        String status = booked ? "Booked" : "Available";

        System.out.printf(
            "%-10d %-15s %-12.2f %-12s%n",
            roomNumber, type, price, status
        );
    }
}

public class HotelReservationSystem {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Room> rooms = new ArrayList<>();

        rooms.add(new Room(101, "Single", 1500));
        rooms.add(new Room(102, "Single", 1500));
        rooms.add(new Room(201, "Double", 2500));
        rooms.add(new Room(202, "Double", 2500));
        rooms.add(new Room(301, "Deluxe", 4000));

        int choice;

        do {
            System.out.println("\n===== HOTEL RESERVATION SYSTEM =====");
            System.out.println("1. Display Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Booking");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.println("\nRoom Information");
                    System.out.printf(
                        "%-10s %-15s %-12s %-12s%n",
                        "Room", "Type", "Price", "Status"
                    );

                    for (Room room : rooms) {
                        room.display();
                    }
                    break;

                case 2:
                    System.out.print("Enter room number: ");
                    int bookNumber = scanner.nextInt();

                    boolean booked = false;

                    for (Room room : rooms) {
                        if (room.getRoomNumber() == bookNumber) {
                            if (room.isBooked()) {
                                System.out.println("Room is already booked.");
                            } else {
                                room.book();
                                System.out.println("Room booked successfully.");
                            }

                            booked = true;
                            break;
                        }
                    }

                    if (!booked) {
                        System.out.println("Room not found.");
                    }
                    break;

                case 3:
                    System.out.print("Enter room number: ");
                    int cancelNumber = scanner.nextInt();

                    boolean found = false;

                    for (Room room : rooms) {
                        if (room.getRoomNumber() == cancelNumber) {
                            found = true;

                            if (room.isBooked()) {
                                room.cancel();
                                System.out.println("Booking cancelled.");
                            } else {
                                System.out.println("Room is not booked.");
                            }

                            break;
                        }
                    }

                    if (!found) {
                        System.out.println("Room not found.");
                    }
                    break;

                case 4:
                    System.out.println("Thank you!");
                    break;

                default:
                    System.out.println("Invalid choice.");
            }

        } while (choice != 4);

        scanner.close();
    }
}
