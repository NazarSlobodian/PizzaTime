package Model.Generators;

import java.util.List;

import Model.KitchenStuff.Order;

/**
 * An interface for generating orders based on different strategies
 */
public interface OrderGenerator {
    Order generateRandomOrder();
    Order generateFixedOrder(int itemCount);
    Order generatePromoOrder(List<String> promoPizzaTypes);
}

