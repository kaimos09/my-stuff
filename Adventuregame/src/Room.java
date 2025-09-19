import java.util.ArrayList;
import java.util.HashMap;

public class Room {
    private String description;
    private ArrayList<Item> items;
    private HashMap<String, Room> exits;

    public Room(String description) {
        this.description = description;
        this.items = new ArrayList<>();
        this.exits = new HashMap<>();
    }

    public void setExit(String direction, Room neighbor) {
        exits.put(direction, neighbor);
    }

    public Room getExit(String direction) {
        return exits.get(direction);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public Item removeItem(String name) {
        for (Item i : items) {
            if (i.getName().equalsIgnoreCase(name)) {
                items.remove(i);
                return i;
            }
        }
        return null;
    }

    public String getLongDescription() {
        StringBuilder sb = new StringBuilder(description);
        if (!items.isEmpty()) {
            sb.append("\nItems here: ");
            for (Item i : items) {
                sb.append(i.getName()).append(" ");
            }
        }
        if (!exits.isEmpty()) {
            sb.append("\nExits: ");
            for (String dir : exits.keySet()) {
                sb.append(dir).append(" ");
            }
        }
        return sb.toString();
    }
}
