package Model.KitchenStuff;


import java.util.LinkedList;
import java.util.Queue;

import Model.Generators.FlowGenerator;
import Model.Generators.OrderGenerator;


/**
 * Manages the queues for order processing and integrates with FlowGenerator and OrderGenerator.
 */
public class Queues {
    private final Queue<Order> cashierQueue;
    private final Queue<Order> kitchenQueue;
    private final Queue<Order> rejectedQueue;
    private final long endOfDayTime;

    private final FlowGenerator flowGenerator;
    private final OrderGenerator orderGenerator;

    public Queues(long endOfDayTime, FlowGenerator flowGenerator, OrderGenerator orderGenerator) {
        this.cashierQueue = new LinkedList<>();
        this.kitchenQueue = new LinkedList<>();
        this.rejectedQueue = new LinkedList<>();
        this.endOfDayTime = endOfDayTime;
        this.flowGenerator = flowGenerator;
        this.orderGenerator = orderGenerator;
    }

    /**
     * Main logic to check and generate orders.
     */
    public void manageOrderFlow() {
        if (flowGenerator.shouldGenerateOrder()) {
            // Generate a new order and add it to the cashier queue
            Order newOrder = orderGenerator.generateRandomOrder();
            addOrderToCashierQueue(newOrder);
        }
    }

    /**
     * Adds an order to the cashier queue for processing.
     * @param order The order to be added.
     */
    private void addOrderToCashierQueue(Order order) {
        if (canOrderBeCompleted(order)) {
            cashierQueue.add(order);
            System.out.println("Order added to cashier queue: " + order);
        } else {
            rejectOrder(order);
        }
    }

    /**
     * Moves an order from the cashier queue to the kitchen queue.
     */
    public void processOrderAtCashier() {
        if (!cashierQueue.isEmpty()) {
            Order order = cashierQueue.poll();
            if (order != null) {
                kitchenQueue.add(order);
                System.out.println("Order moved to kitchen queue: " + order);
            }
        }
    }

    /**
     * Processes orders in the kitchen queue.
     */
    public void processOrderInKitchen() {
        if (!kitchenQueue.isEmpty()) {
            Order order = kitchenQueue.poll();
            // Logic to handle order preparation (can be added later)
            System.out.println("Order prepared in kitchen: " + order);
        }
    }

    /**
     * Checks if the order can be completed before the end of the day.
     * @param order The order to check.
     * @return True if the order can be completed, false otherwise.
     */
    private boolean canOrderBeCompleted(Order order) {
        // Assume each order has an average preparation time (can be adjusted)
        long estimatedCompletionTime = order.getOrderTime() + 20 * 60 * 1000; // 20 minutes
        return estimatedCompletionTime <= endOfDayTime;
    }

    /**
     * Rejects the order if it can't be completed on time.
     * @param order The order to be rejected.
     */
    private void rejectOrder(Order order) {
        rejectedQueue.add(order);
        System.out.println("Order rejected: " + order);
    }

    /**
     * Retrieves the queue of rejected orders.
     * @return The rejected orders queue.
     */
    public Queue<Order> getRejectedOrders() {
        return rejectedQueue;
    }
}