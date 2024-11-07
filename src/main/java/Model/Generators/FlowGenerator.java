package Model.Generators;

import java.util.Random;

/**
 * Interface for generation of order flow
 */
public interface FlowGenerator {
    boolean shouldGenerateOrder();
    boolean shouldGenerateOrderWithProbability(double probability);
}

/**
 * An implementation of FlowGeneratorImpl that determines when to generate a new order
 */
class FlowGeneratorImpl implements FlowGenerator {
    private final int intervalMillis;
    private long lastOrderTime;
    private final Random random;

    public FlowGeneratorImpl(int intervalMillis) {
        this.intervalMillis = intervalMillis;
        this.lastOrderTime = System.currentTimeMillis();
        this.random = new Random();
    }

    /**
     * Checks if a new order needs to be generated.
     * 
     * @return true, if the specified interval has passed, false otherwise
     */
    @Override
    public boolean shouldGenerateOrder() {
        long currentTime = System.currentTimeMillis();
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