import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

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

class SingleRoom extends Room {
    public SingleRoom() {
        super(1, "Small", 1000);
    }

    public void displayRoomDetails() {
        System.out.println("Single Room | Beds: " + numberOfBeds + " | Size: " + size + " | Price: ₹" + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, "Medium", 2000);
    }

    public void displayRoomDetails() {
        System.out.println("Double Room | Beds: " + numberOfBeds + " | Size: " + size + " | Price: ₹" + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, "Large", 5000);
    }

    public void displayRoomDetails() {
        System.out.println("Suite Room | Beds: " + numberOfBeds + " | Size: " + size + " | Price: ₹" + price);
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 2);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String toString() {
        return "Guest: " + guestName + " | Room Type: " + roomType;
    }
}

class BookingQueue {

    private Queue<Reservation> queue;

    public BookingQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {
        queue.add(reservation);
    }

    public void displayQueue() {
        System.out.println("\n--- Booking Requests (FIFO Order) ---");
        for (Reservation r : queue) {
            System.out.println(r);
        }
    }
}

public class BOOKmystayapp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      BOOK MY STAY APPLICATION");
        System.out.println("=======================================");
        System.out.println("Version: 5.0");
        System.out.println("---------------------------------------");

        RoomInventory inventory = new RoomInventory();

        System.out.println("\n--- Available Rooms ---");
        System.out.println("Single: " + inventory.getAvailability("Single"));
        System.out.println("Double: " + inventory.getAvailability("Double"));
        System.out.println("Suite: " + inventory.getAvailability("Suite"));

        BookingQueue bookingQueue = new BookingQueue();

        bookingQueue.addRequest(new Reservation("Arnav", "Single"));
        bookingQueue.addRequest(new Reservation("Rahul", "Double"));
        bookingQueue.addRequest(new Reservation("Sneha", "Suite"));

        bookingQueue.displayQueue();

        System.out.println("\nApplication executed successfully.");
    }
}