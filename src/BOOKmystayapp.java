import java.io.*;
import java.util.*;

class SystemState implements Serializable {
    HashMap<String, Integer> inventory;
    List<String> bookings;

    public SystemState(HashMap<String, Integer> inventory, List<String> bookings) {
        this.inventory = inventory;
        this.bookings = bookings;
    }
}

class PersistenceService {

    private static final String FILE_NAME = "system_state.dat";

    public void save(SystemState state) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME));
            out.writeObject(state);
            out.close();
            System.out.println("State saved.");
        } catch (Exception e) {
            System.out.println("Error saving state.");
        }
    }

    public SystemState load() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME));
            SystemState state = (SystemState) in.readObject();
            in.close();
            System.out.println("State loaded.");
            return state;
        } catch (Exception e) {
            System.out.println("No previous state found. Starting fresh.");
            return null;
        }
    }
}

public class BOOKmystayapp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      BOOK MY STAY APPLICATION");
        System.out.println("=======================================");
        System.out.println("Version: 12.0");
        System.out.println("---------------------------------------");

        PersistenceService service = new PersistenceService();

        SystemState loadedState = service.load();

        HashMap<String, Integer> inventory;
        List<String> bookings;

        if (loadedState != null) {
            inventory = loadedState.inventory;
            bookings = loadedState.bookings;
        } else {
            inventory = new HashMap<>();
            inventory.put("Single", 2);
            inventory.put("Double", 1);
            bookings = new ArrayList<>();
        }

        inventory.put("Single", inventory.getOrDefault("Single", 0) - 1);
        bookings.add("Arnav booked Single");

        System.out.println("\nCurrent Inventory:");
        for (String k : inventory.keySet()) {
            System.out.println(k + " : " + inventory.get(k));
        }

        System.out.println("\nBooking History:");
        for (String b : bookings) {
            System.out.println(b);
        }

        SystemState newState = new SystemState(inventory, bookings);
        service.save(newState);

        System.out.println("\nApplication executed successfully.");
    }
}