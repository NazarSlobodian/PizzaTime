package Model.Generators;

/**
 * Interface for generation of order flow
 */
public interface FlowGenerator {
    boolean shouldGenerateOrder();
    boolean shouldGenerateOrderWithProbability(double probability);
}