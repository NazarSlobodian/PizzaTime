package Model.Generators;

import Model.KitchenStuff.Order;

public interface OrderGenerator {
    /**
     *
     * @return randomly filled order
     */
    Order generateOrder();
}
