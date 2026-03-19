import java.util.*;

class RoomInventory {
    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 1);
        inventory.put("Double", 1);
    }

    public synchronized boolean allocate(String type) {
        int available = inventory.getOrDefault(type, 0);
        if (available > 0) {
            inventory.put(type, available - 1);
            return true;
        }
        return false;
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

    public Reservation(String g, String t) {
        guest = g;
        roomType = t;
    }
}

class BookingQueue {
    private Queue<Reservation> queue = new LinkedList<>();

    public synchronized void add(Reservation r) {
        queue.add(r);
    }

    public synchronized Reservation get() {
        return queue.poll();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }
}

class BookingProcessor extends Thread {

    private BookingQueue queue;
    private RoomInventory inventory;

    public BookingProcessor(BookingQueue q, RoomInventory i) {
        queue = q;
        inventory = i;
    }

    public void run() {
        while (true) {
            Reservation r;
            synchronized (queue) {
                if (queue.isEmpty()) break;
                r = queue.get();
            }

            if (r != null) {
                boolean success = inventory.allocate(r.roomType);
                if (success) {
                    System.out.println(Thread.currentThread().getName() +
                            " booked " + r.roomType + " for " + r.guest);
                } else {
                    System.out.println(Thread.currentThread().getName() +
                            " failed for " + r.guest + " (" + r.roomType + ")");
                }
            }
        }
    }
}

public class BOOKmystayapp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      BOOK MY STAY APPLICATION");
        System.out.println("=======================================");
        System.out.println("Version: 11.0");
        System.out.println("---------------------------------------");

        RoomInventory inventory = new RoomInventory();

        BookingQueue queue = new BookingQueue();

        queue.add(new Reservation("Arnav", "Single"));
        queue.add(new Reservation("Rahul", "Single"));
        queue.add(new Reservation("Sneha", "Double"));
        queue.add(new Reservation("Amit", "Double"));

        BookingProcessor t1 = new BookingProcessor(queue, inventory);
        BookingProcessor t2 = new BookingProcessor(queue, inventory);

        t1.setName("Thread-1");
        t2.setName("Thread-2");

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (Exception e) {}

        inventory.display();

        System.out.println("\nApplication executed successfully.");
    }
}