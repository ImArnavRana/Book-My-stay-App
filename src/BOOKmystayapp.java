import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String message) {
        super(message);
    }
}

abstract class Room {
    protected int numberOfBeds;
    protected String size;
    protected double price;

    public Room(int numberOfBeds, String size, double price) {
        this.numberOfBeds = numberOfBeds;
        this.size = size;
        this.price = price;
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 0);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void decrease(String type) throws InvalidBookingException {
        int current = getAvailability(type);
        if (current <= 0) {
            throw new InvalidBookingException("No availability for " + type);
        }
        inventory.put(type, current - 1);
    }

    public boolean isValidRoomType(String type) {
        return inventory.containsKey(type);
    }
}

class Reservation {
    String guestName;
    String roomType;
    String reservationId;

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

class BookingService {

    private RoomInventory inventory;
    private Set<String> allocatedIds = new HashSet<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void process(BookingQueue q) {

        while (!q.isEmpty()) {

            Reservation r = q.next();

            try {

                if (!inventory.isValidRoomType(r.roomType)) {
                    throw new InvalidBookingException("Invalid room type: " + r.roomType);
                }

                String roomId = r.roomType + "-" + (allocatedIds.size() + 1);

                if (allocatedIds.contains(roomId)) {
                    throw new InvalidBookingException("Duplicate Room ID");
                }

                inventory.decrease(r.roomType);

                allocatedIds.add(roomId);

                System.out.println("Confirmed: " + r.guestName + " | " + roomId);

            } catch (InvalidBookingException e) {
                System.out.println("Error: " + e.getMessage() + " | Guest: " + r.guestName);
            }
        }
    }
}

public class BOOKmystayapp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      BOOK MY STAY APPLICATION");
        System.out.println("=======================================");
        System.out.println("Version: 9.0");
        System.out.println("---------------------------------------");

        RoomInventory inventory = new RoomInventory();

        BookingQueue queue = new BookingQueue();

        queue.add(new Reservation("Arnav", "Single", "R1"));
        queue.add(new Reservation("Rahul", "Suite", "R2"));
        queue.add(new Reservation("Sneha", "Deluxe", "R3"));
        queue.add(new Reservation("Amit", "Double", "R4"));

        BookingService service = new BookingService(inventory);

        service.process(queue);

        System.out.println("\nApplication executed successfully.");
    }
}