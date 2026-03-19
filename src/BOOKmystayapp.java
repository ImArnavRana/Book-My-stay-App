/**
 * BOOKmystayapp
 *
 * Use Case 2: Basic Room Types & Static Availability
 * Demonstrates abstraction, inheritance, and polymorphism
 * in a Hotel Booking System.
 *
 * @author Arnav Rana
 * @version 2.0
 */

// Abstract Class
abstract class Room {
    protected int numberOfBeds;
    protected String size;
    protected double price;

    public Room(int numberOfBeds, String size, double price) {
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }

    public abstract void displayRoomDetails();
}

// Single Room
class SingleRoom extends Room {
    public SingleRoom() {
        super(1, "Small", 1000);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: Single Room");
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size);
        System.out.println("Price: ₹" + price);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, "Medium", 2000);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: Double Room");
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size);
        System.out.println("Price: ₹" + price);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, "Large", 5000);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: Suite Room");
        System.out.println("Beds: " + numberOfBeds);
        System.out.println("Size: " + size);
        System.out.println("Price: ₹" + price);
    }
}

/**
 * Main Class
 */
public class BOOKmystayapp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      BOOK MY STAY APPLICATION");
        System.out.println("=======================================");
        System.out.println("Version: 2.0");
        System.out.println("---------------------------------------");

        // Polymorphism
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Static Availability
        int singleAvailable = 5;
        int doubleAvailable = 3;
        int suiteAvailable = 2;

        System.out.println("\n--- Room Details ---\n");

        single.displayRoomDetails();
        System.out.println("Available: " + singleAvailable);
        System.out.println("--------------------------------");

        doubleRoom.displayRoomDetails();
        System.out.println("Available: " + doubleAvailable);
        System.out.println("--------------------------------");

        suite.displayRoomDetails();
        System.out.println("Available: " + suiteAvailable);
        System.out.println("--------------------------------");

        System.out.println("Application executed successfully.");
    }
}