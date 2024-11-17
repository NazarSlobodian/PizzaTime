package Model.Generators;

import Model.KitchenStuff.Order;

/**
 * An interface for generating orders based on different strategies
 */
public interface OrderGenerator {
    Order generateRandomOrder();
    Order generateOrderAfterInterval();
}

