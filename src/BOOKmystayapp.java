/**
 * BOOKmystayapp
 *
 * Use Case 3: Centralized Room Inventory Management
 * Demonstrates use of HashMap for managing room availability.
 *
 * @author Arnav Rana
 * @version 3.0
 */

import java.util.HashMap;

// Abstract Room Class
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
        System.out.println("Room Type: Single Room | Beds: " + numberOfBeds + " | Size: " + size + " | Price: ₹" + price);
    }
}

// Double Room
class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, "Medium", 2000);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: Double Room | Beds: " + numberOfBeds + " | Size: " + size + " | Price: ₹" + price);
    }
}

// Suite Room
class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, "Large", 5000);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: Suite Room | Beds: " + numberOfBeds + " | Size: " + size + " | Price: ₹" + price);
    }
}

// Inventory Class (Centralized Management)
class RoomInventory {

    private HashMap<String, Integer> inventory;

    // Constructor initializes inventory
    public RoomInventory() {
        inventory = new HashMap<>();

        // Initial room availability
        inventory.put("Single", 5);
        inventory.put("Double", 3);
        inventory.put("Suite", 2);
    }

    // Get availability
    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    // Update availability
    public void updateAvailability(String roomType, int count) {
        inventory.put(roomType, count);
    }

    // Display all inventory
    public void displayInventory() {
        System.out.println("\n--- Current Room Inventory ---");
        for (String type : inventory.keySet()) {
            System.out.println(type + " Rooms Available: " + inventory.get(type));
        }
    }
}

// Main Class
public class BOOKmystayapp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      BOOK MY STAY APPLICATION");
        System.out.println("=======================================");
        System.out.println("Version: 3.0");
        System.out.println("---------------------------------------");

        // Create room objects
        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        // Display room details
        System.out.println("\n--- Room Details ---");
        single.displayRoomDetails();
        doubleRoom.displayRoomDetails();
        suite.displayRoomDetails();

        // Initialize Inventory
        RoomInventory inventory = new RoomInventory();

        // Display inventory
        inventory.displayInventory();

        // Example update
        System.out.println("\nUpdating availability...");
        inventory.updateAvailability("Single", 4);

        // Display updated inventory
        inventory.displayInventory();

        System.out.println("\nApplication executed successfully.");
    }
}