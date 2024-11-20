package Model.Generators;

import Model.Utils.Clock;

import java.util.Random;

/**
 * An implementation of FlowGeneratorImpl that determines when to generate a new order
 */
public class FlowGeneratorImpl implements FlowGenerator {
    private final int intervalMillis;
    private long lastOrderTime;
    private final Random random;
    private final Clock clock;

    public FlowGeneratorImpl(int intervalMillis, Clock clock) {
        this.intervalMillis = intervalMillis;
        this.lastOrderTime = clock.getCurrentTime()-intervalMillis;
        this.random = new Random();
        this.clock = clock;
    }

    /**
     * Checks if a new order needs to be generated.
     *
     * @return true, if the specified interval has passed, false otherwise
     */
    @Override
    public boolean shouldGenerateOrder() {
        long currentTime = clock.getCurrentTime();
        if (currentTime - lastOrderTime >= intervalMillis) {
            lastOrderTime = currentTime;
            return true;
        }
        return false;
    }


    /**
     * Additional opportunity with probability for order generation.
     *
     * @param probability probability (from 0.0 to 1.0)
     * @return true if an order is to be generated, false otherwise
     */
    @Override
    public boolean shouldGenerateOrderWithProbability(double probability) {
        if (shouldGenerateOrder()) {
            return random.nextDouble() < probability;
        }
        return false;
    }
}
