import java.util.Random;

public class KellyPokerSimulator {
    public record Config(
            double startingBankroll,
            int hands,
            double winProbability,
            double payoutOdds,
            double kellyFractionMultiplier,
            double maxBetFraction,
            long seed
    ) {
    }

    public record Result(
            double finalBankroll,
            int handsPlayed,
            int wins,
            int losses,
            double averageBet,
            double maxDrawdown,
            double kellyFractionUsed
    ) {
    }

    private final Config config;

    public KellyPokerSimulator(Config config) {
        validate(config);
        this.config = config;
    }

    public Result run() {
        Random random = new Random(config.seed());
        double bankroll = config.startingBankroll();
        double peak = bankroll;
        double maxDrawdown = 0.0;
        double totalBet = 0.0;
        int wins = 0;
        int losses = 0;

        double kellyFraction = calculateKellyFraction(
                config.winProbability(),
                config.payoutOdds()
        ) * config.kellyFractionMultiplier();

        kellyFraction = Math.max(0.0, Math.min(kellyFraction, config.maxBetFraction()));

        for (int i = 0; i < config.hands(); i++) {
            if (bankroll <= 0.0) {
                break;
            }
            double bet = bankroll * kellyFraction;
            totalBet += bet;

            boolean win = random.nextDouble() < config.winProbability();
            if (win) {
                bankroll += bet * config.payoutOdds();
                wins++;
            } else {
                bankroll -= bet;
                losses++;
            }

            if (bankroll > peak) {
                peak = bankroll;
            } else {
                double drawdown = peak - bankroll;
                if (drawdown > maxDrawdown) {
                    maxDrawdown = drawdown;
                }
            }
        }

        int handsPlayed = wins + losses;
        double averageBet = handsPlayed == 0 ? 0.0 : totalBet / handsPlayed;

        return new Result(
                bankroll,
                handsPlayed,
                wins,
                losses,
                averageBet,
                maxDrawdown,
                kellyFraction
        );
    }

    private static double calculateKellyFraction(double winProbability, double payoutOdds) {
        double lossProbability = 1.0 - winProbability;
        return (payoutOdds * winProbability - lossProbability) / payoutOdds;
    }

    private static void validate(Config config) {
        if (config.startingBankroll() <= 0) {
            throw new IllegalArgumentException("Starting bankroll must be positive.");
        }
        if (config.hands() <= 0) {
            throw new IllegalArgumentException("Hands must be positive.");
        }
        if (config.winProbability() < 0.0 || config.winProbability() > 1.0) {
            throw new IllegalArgumentException("Win probability must be between 0 and 1.");
        }
        if (config.payoutOdds() <= 0.0) {
            throw new IllegalArgumentException("Payout odds must be positive.");
        }
        if (config.kellyFractionMultiplier() < 0.0) {
            throw new IllegalArgumentException("Kelly fraction multiplier must be non-negative.");
        }
        if (config.maxBetFraction() <= 0.0 || config.maxBetFraction() > 1.0) {
            throw new IllegalArgumentException("Max bet fraction must be between 0 and 1.");
        }
    }
}
