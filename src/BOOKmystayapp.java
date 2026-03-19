import java.util.*;

class InvalidBookingException extends Exception {
    public InvalidBookingException(String msg) {
        super(msg);
    }
}

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 1);
        inventory.put("Double", 1);
        inventory.put("Suite", 1);
    }

    public int getAvailability(String type) {
        return inventory.getOrDefault(type, -1);
    }

    public void decrease(String type) throws InvalidBookingException {
        int val = getAvailability(type);
        if (val <= 0) throw new InvalidBookingException("No availability for " + type);
        inventory.put(type, val - 1);
    }

    public void increase(String type) {
        inventory.put(type, getAvailability(type) + 1);
    }

    public void display() {
        System.out.println("\nInventory:");
        for (String k : inventory.keySet()) {
            System.out.println(k + " : " + inventory.get(k));
        }
    }
}

class Reservation {
    String guest;
    String roomType;
    String resId;
    String roomId;
    boolean active = true;

    public Reservation(String g, String t, String r) {
        guest = g;
        roomType = t;
        resId = r;
    }
}

class BookingService {

    private RoomInventory inventory;
    private HashMap<String, Reservation> confirmed = new HashMap<>();
    private Stack<String> rollbackStack = new Stack<>();

    public BookingService(RoomInventory inv) {
        this.inventory = inv;
    }

    public void book(Reservation r) {
        try {
            inventory.decrease(r.roomType);
            r.roomId = r.roomType + "-" + (confirmed.size() + 1);
            confirmed.put(r.resId, r);
            System.out.println("Booked: " + r.guest + " | " + r.roomId);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void cancel(String resId) {
        try {
            if (!confirmed.containsKey(resId)) {
                throw new InvalidBookingException("Reservation not found");
            }

            Reservation r = confirmed.get(resId);

            if (!r.active) {
                throw new InvalidBookingException("Already cancelled");
            }

            rollbackStack.push(r.roomId);

            inventory.increase(r.roomType);

            r.active = false;

            System.out.println("Cancelled: " + r.guest + " | " + r.roomId);

        } catch (InvalidBookingException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void showRollbackStack() {
        System.out.println("\nRollback Stack:");
        for (String id : rollbackStack) {
            System.out.println(id);
        }
    }
}

public class BOOKmystayapp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      BOOK MY STAY APPLICATION");
        System.out.println("=======================================");
        System.out.println("Version: 10.0");
        System.out.println("---------------------------------------");

        RoomInventory inventory = new RoomInventory();

        BookingService service = new BookingService(inventory);

        Reservation r1 = new Reservation("Arnav", "Single", "R1");
        Reservation r2 = new Reservation("Rahul", "Double", "R2");

        service.book(r1);
        service.book(r2);

        inventory.display();

        service.cancel("R1");
        service.cancel("R3");
        service.cancel("R1");

        inventory.display();

        service.showRollbackStack();

        System.out.println("\nApplication executed successfully.");
    }
}