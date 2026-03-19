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
    private HashMap<String, Set<String>> allocated = new HashMap<>();

    public BookingService(RoomInventory inventory) {
        this.inventory = inventory;
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
                System.out.println("Confirmed: " + r.guestName + " | " + roomId + " | ResID: " + r.reservationId);
            } else {
                System.out.println("Failed: " + r.guestName + " | " + r.roomType);
            }
        }
    }
}

class Service {
    String name;
    double cost;

    public Service(String name, double cost) {
        this.name = name;
        this.cost = cost;
    }
}

class AddOnServiceManager {

    private HashMap<String, List<Service>> serviceMap = new HashMap<>();

    public void addService(String reservationId, Service s) {
        serviceMap.putIfAbsent(reservationId, new ArrayList<>());
        serviceMap.get(reservationId).add(s);
    }

    public void displayServices(String reservationId) {
        List<Service> list = serviceMap.get(reservationId);
        double total = 0;

        if (list != null) {
            System.out.println("\nServices for " + reservationId + ":");
            for (Service s : list) {
                System.out.println(s.name + " | ₹" + s.cost);
                total += s.cost;
            }
            System.out.println("Total Add-on Cost: ₹" + total);
        }
    }
}

public class BOOKmystayapp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      BOOK MY STAY APPLICATION");
        System.out.println("=======================================");
        System.out.println("Version: 7.0");
        System.out.println("---------------------------------------");

        RoomInventory inventory = new RoomInventory();

        BookingQueue queue = new BookingQueue();

        queue.add(new Reservation("Arnav", "Single", "R1"));
        queue.add(new Reservation("Rahul", "Double", "R2"));
        queue.add(new Reservation("Sneha", "Suite", "R3"));

        BookingService booking = new BookingService(inventory);
        booking.process(queue);

        AddOnServiceManager manager = new AddOnServiceManager();

        manager.addService("R1", new Service("Breakfast", 200));
        manager.addService("R1", new Service("WiFi", 100));
        manager.addService("R2", new Service("Dinner", 300));

        manager.displayServices("R1");
        manager.displayServices("R2");

        System.out.println("\nApplication executed successfully.");
    }
}