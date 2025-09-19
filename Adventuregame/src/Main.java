pimport java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to the Text Adventure!");

        // Setup game world
        Room startRoom = new Room("You are in a dark cave. There is a passage north.");
        Room secondRoom = new Room("You are in a forest clearing. The sun is shining.");
        startRoom.setExit("north", secondRoom);
        secondRoom.setExit("south", startRoom);

        // Create player in the start room
        Player player = new Player(startRoom);

        // Game loop (very basic for now)
        Scanner in = new Scanner(System.in);
        boolean playing = true;

        while (playing) {
            System.out.println(player.getCurrentRoom().getLongDescription());
            System.out.print("> ");
            String command = in.nextLine().trim().toLowerCase();

            if (command.equals("quit")) {
                playing = false;
            } else if (command.startsWith("go ")) {
                String direction = command.substring(3);
                Room next = player.getCurrentRoom().getExit(direction);
                if (next != null) {
                    player.setCurrentRoom(next);
                } else {
                    System.out.println("You can't go that way.");
                }
            } else {
                System.out.println("I don't understand that command.");
            }
        }

        System.out.println("Goodbye!");
        in.close();
    }
}


