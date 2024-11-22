package Model.Generators;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import Model.FoodAndStuff.Menu;
import Model.KitchenStuff.Order;
import Model.Utils.Clock;

/**
 * Manages different order generation strategies
 */
public class OrderStrategyManager {
    private final Map<String, OrderGenerator> strategies = new HashMap<>();
    private final Map<String, FlowGenerator> flowGenerators = new HashMap<>();
    private OrderGenerator activeStrategy;
    private FlowGenerator activeFlowGenerator;
    private Menu menu;

    public OrderStrategyManager(Menu menu, Clock clock) {
        // Add strategies here
        strategies.put("IntervalSteady", new OrderGeneratorImpl(menu, clock));
        flowGenerators.put("IntervalSteady", new FlowGeneratorImpl(1000 * 30 * 60, clock));

        strategies.put("RandomPeak", new OrderGeneratorImpl(menu, clock));
        flowGenerators.put("RandomPeak", new FlowGeneratorImpl(1000 * 30 * 60, clock));
        // Set a default strategy
        setActiveStrategy("IntervalSteady");
        this.menu = menu;
    }

    /**
     * Set the active strategy by name
     *
     * @param strategyName The name of the strategy
     */
    public void setActiveStrategy(String strategyName) {
        if (strategies.containsKey(strategyName)) {
            this.activeStrategy = strategies.get(strategyName);
            this.activeFlowGenerator = flowGenerators.get(strategyName);
        } else {
            throw new IllegalArgumentException("Strategy not found: " + strategyName);
        }
    }

    /**
     * Get the names of all available strategies
     *
     * @return List of strategy names
     */
    public List<String> getAvailableStrategies() {
        return strategies.keySet().stream().collect(Collectors.toList());
    }

    /**
     * Generate an order using the active strategy
     *
     * @return Generated order
     */
    public Order generateOrder() {
        if (activeStrategy != null) {
            return activeStrategy.generateRandomOrder(); // Example, can call specific methods
        }
        throw new IllegalStateException("No active strategy set");
    }
    public boolean shouldGenerate() {
        if (menu.getPizzas().isEmpty()) {
            return false;
        }
        return activeFlowGenerator.shouldGenerateOrder();
    }
}