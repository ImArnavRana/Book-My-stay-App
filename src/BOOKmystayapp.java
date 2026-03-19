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
        System.out.println("Single Room | ₹" + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, "Medium", 2000);
    }

    public void displayRoomDetails() {
        System.out.println("Double Room | ₹" + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, "Large", 5000);
    }

    public void displayRoomDetails() {
        System.out.println("Suite Room | ₹" + price);
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

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, 0);
    }

    public void decrease(String type) {
        inventory.put(type, getAvailability(type) - 1);
    }
}

class Reservation {
    String guestName;
    String roomType;
    String reservationId;
    String roomId;

    public Reservation(String guestName, String roomType, String reservationId) {
        this.guestName = guestName;
        this.roomType = roomType;
        this.reservationId = reservationId;
    }
}

class BookingQueue {
    Queue<Reservation> queue = new LinkedList<>();

    public void add(Reservation r) {
        queue.add(r);
    }

    public Reservation next() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

class BookingHistory {
    private List<Reservation> history = new ArrayList<>();

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return history;
    }
}

class BookingService {

    private RoomInventory inventory;
    private HashMap<String, Set<String>> allocated = new HashMap<>();
    private BookingHistory history;

    public BookingService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        allocated.put("Single", new HashSet<>());
        allocated.put("Double", new HashSet<>());
        allocated.put("Suite", new HashSet<>());
    }

    public void process(BookingQueue q) {
        while (!q.isEmpty()) {
            Reservation r = q.next();

            if (inventory.getAvailability(r.roomType) > 0) {

                String roomId = r.roomType + "-" + (allocated.get(r.roomType).size() + 1);
                allocated.get(r.roomType).add(roomId);

                inventory.decrease(r.roomType);

                r.roomId = roomId;

                history.add(r);

                System.out.println("Confirmed: " + r.guestName + " | " + roomId + " | " + r.reservationId);

            } else {
                System.out.println("Failed: " + r.guestName + " | " + r.roomType);
            }
        }
    }
}

class ReportService {

    public void generateReport(List<Reservation> history) {

        System.out.println("\n--- Booking History Report ---");

        for (Reservation r : history) {
            System.out.println("Guest: " + r.guestName +
                    " | RoomType: " + r.roomType +
                    " | RoomID: " + r.roomId +
                    " | ResID: " + r.reservationId);
        }

        System.out.println("Total Bookings: " + history.size());
    }
}

public class BOOKmystayapp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      BOOK MY STAY APPLICATION");
        System.out.println("=======================================");
        System.out.println("Version: 8.0");
        System.out.println("---------------------------------------");

        RoomInventory inventory = new RoomInventory();

        BookingQueue queue = new BookingQueue();

        queue.add(new Reservation("Arnav", "Single", "R1"));
        queue.add(new Reservation("Rahul", "Double", "R2"));
        queue.add(new Reservation("Sneha", "Suite", "R3"));
        queue.add(new Reservation("Amit", "Suite", "R4"));

        BookingHistory history = new BookingHistory();

        BookingService booking = new BookingService(inventory, history);
        booking.process(queue);

        ReportService report = new ReportService();
        report.generateReport(history.getAll());

        System.out.println("\nApplication executed successfully.");
    }
}