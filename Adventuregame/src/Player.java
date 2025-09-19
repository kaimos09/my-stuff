import java.util.ArrayList;

public class Player {
    private Room currentRoom;
    private ArrayList<Item> inventory;

    public Player(Room startRoom) {
        this.currentRoom = startRoom;
        this.inventory = new ArrayList<>();
    }

    public Room getCurrentRoom() {
        return currentRoom;
    }

    public void setCurrentRoom(Room room) {
        this.currentRoom = room;
    }

    public void addItem(Item item) {
        inventory.add(item);
    }

    public Item dropItem(String name) {
        for (Item i : inventory) {
            if (i.getName().equalsIgnoreCase(name)) {
                inventory.remove(i);
                return i;
            }
        }
        return null;
    }

    public String inventoryList() {
        if (inventory.isEmpty()) {
            return "You are not carrying anything.";
        }
        StringBuilder sb = new StringBuilder("You have: ");
        for (Item i : inventory) {
            sb.append(i.getName()).append(" ");
        }
        return sb.toString();
    }
}
