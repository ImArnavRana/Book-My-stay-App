import java.util.HashMap;

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
        System.out.println("Room Type: Single Room | Beds: " + numberOfBeds + " | Size: " + size + " | Price: ₹" + price);
    }
}

class DoubleRoom extends Room {
    public DoubleRoom() {
        super(2, "Medium", 2000);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: Double Room | Beds: " + numberOfBeds + " | Size: " + size + " | Price: ₹" + price);
    }
}

class SuiteRoom extends Room {
    public SuiteRoom() {
        super(3, "Large", 5000);
    }

    public void displayRoomDetails() {
        System.out.println("Room Type: Suite Room | Beds: " + numberOfBeds + " | Size: " + size + " | Price: ₹" + price);
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single", 5);
        inventory.put("Double", 0);
        inventory.put("Suite", 2);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}

class RoomSearch {

    private RoomInventory inventory;

    public RoomSearch(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchRooms(Room single, Room doubleRoom, Room suite) {

        System.out.println("\n--- Available Rooms ---\n");

        if (inventory.getAvailability("Single") > 0) {
            single.displayRoomDetails();
            System.out.println("Available: " + inventory.getAvailability("Single"));
            System.out.println("--------------------------------");
        }

        if (inventory.getAvailability("Double") > 0) {
            doubleRoom.displayRoomDetails();
            System.out.println("Available: " + inventory.getAvailability("Double"));
            System.out.println("--------------------------------");
        }

        if (inventory.getAvailability("Suite") > 0) {
            suite.displayRoomDetails();
            System.out.println("Available: " + inventory.getAvailability("Suite"));
            System.out.println("--------------------------------");
        }
    }
}

public class BOOKmystayapp {

    public static void main(String[] args) {

        System.out.println("=======================================");
        System.out.println("      BOOK MY STAY APPLICATION");
        System.out.println("=======================================");
        System.out.println("Version: 4.0");
        System.out.println("---------------------------------------");

        Room single = new SingleRoom();
        Room doubleRoom = new DoubleRoom();
        Room suite = new SuiteRoom();

        RoomInventory inventory = new RoomInventory();

        RoomSearch search = new RoomSearch(inventory);

        search.searchRooms(single, doubleRoom, suite);

        System.out.println("\nApplication executed successfully.");
    }
}