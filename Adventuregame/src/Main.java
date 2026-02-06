import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Kelly Criterion Poker Simulator");
        System.out.println("--------------------------------");

        Scanner scanner = new Scanner(System.in);

        double bankroll = promptForDouble(scanner, "Starting bankroll", 1000.0);
        int hands = (int) promptForDouble(scanner, "Number of hands to simulate", 1000);
        double winProbability = promptForDouble(scanner, "Win probability (0-1)", 0.55);
        double payoutOdds = promptForDouble(scanner, "Payout odds (net, e.g. 1.0 for even money)", 1.0);
        double kellyFraction = promptForDouble(scanner, "Kelly fraction multiplier (e.g. 1.0 full, 0.5 half)", 1.0);
        double maxBetFraction = promptForDouble(scanner, "Max bet fraction of bankroll (0-1)", 1.0);
        long seed = (long) promptForDouble(scanner, "Random seed (integer)", System.currentTimeMillis());

        KellyPokerSimulator.Config config = new KellyPokerSimulator.Config(
                bankroll,
                hands,
                winProbability,
                payoutOdds,
                kellyFraction,
                maxBetFraction,
                seed
        );

        KellyPokerSimulator simulator = new KellyPokerSimulator(config);
        KellyPokerSimulator.Result result = simulator.run();

        System.out.println();
        System.out.println("Simulation complete.");
        System.out.printf("Final bankroll: %.2f%n", result.finalBankroll());
        System.out.printf("Hands played: %d%n", result.handsPlayed());
        System.out.printf("Wins: %d | Losses: %d%n", result.wins(), result.losses());
        System.out.printf("Average bet: %.2f%n", result.averageBet());
        System.out.printf("Max drawdown: %.2f%n", result.maxDrawdown());
        System.out.printf("Kelly fraction used: %.4f%n", result.kellyFractionUsed());

        scanner.close();
    }

    private static double promptForDouble(Scanner scanner, String prompt, double defaultValue) {
        while (true) {
            System.out.printf("%s [%s]: ", prompt, formatDefault(defaultValue));
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                return defaultValue;
            }
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException ex) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static String formatDefault(double value) {
        if (Math.floor(value) == value) {
            return String.format("%.0f", value);
        }
        return String.format("%.4f", value);
    }
}
