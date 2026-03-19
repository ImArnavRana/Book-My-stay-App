import java.util.*;

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
        inventory.put("Single", 2);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decreaseAvailability(String roomType) {
        inventory.put(roomType, getAvailability(roomType) - 1);
    }

    public void displayInventory() {
        System.out.println("\n--- Inventory ---");
        for (String type : inventory.keySet()) {
            System.out.println(type + ": " + inventory.get(type));
        }
    }
}

class Reservation {
    String guestName;
    String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
}

class BookingQueue {
    Queue<Reservation> queue = new LinkedList<>();

    public void addRequest(Reservation r) {
        queue.add(r);
    }

    public Reservation getNext() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class BookingService {

    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocatedRooms = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms.put("Single", new HashSet<>());
        allocatedRooms.put("Double", new HashSet<>());
        allocatedRooms.put("Suite", new HashSet<>());
    }

    public void processBookings(BookingQueue queue) {

        while (!queue.isEmpty()) {

            Reservation r = queue.getNext();

            if (inventory.getAvailability(r.roomType) > 0) {

                String roomId = r.roomType + "-" + (allocatedRooms.get(r.roomType).size() + 1);

                allocatedRooms.get(r.roomType).add(roomId);

                inventory.decreaseAvailability(r.roomType);

                System.out.println("Booking Confirmed: " + r.guestName + " | Room ID: " + roomId);

            } else {
                System.out.println("Booking Failed (No Availability): " + r.guestName + " | " + r.roomType);
            }
        }
    }
}

public class BOOKmystayapp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      BOOK MY STAY APPLICATION");
        System.out.println("=======================================");
        System.out.println("Version: 6.0");
        System.out.println("---------------------------------------");

        RoomInventory inventory = new RoomInventory();

        BookingQueue queue = new BookingQueue();

        queue.addRequest(new Reservation("Arnav", "Single"));
        queue.addRequest(new Reservation("Rahul", "Single"));
        queue.addRequest(new Reservation("Sneha", "Double"));
        queue.addRequest(new Reservation("Amit", "Suite"));
        queue.addRequest(new Reservation("Neha", "Suite"));

        BookingService service = new BookingService(inventory);

        service.processBookings(queue);

        inventory.displayInventory();

        System.out.println("\nApplication executed successfully.");
    }
}