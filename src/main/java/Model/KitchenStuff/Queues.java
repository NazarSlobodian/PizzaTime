package Model.KitchenStuff;

import java.util.LinkedList;
import java.util.Queue;

import Model.Generators.FlowGenerator;
import Model.Generators.OrderStrategyManager;

/**
 * Manages the queues for order processing and integrates with OrderStrategyManager, FlowGenerator, and KitchenManager.
 */
public class Queues {
    private final Queue<Order> generalQueue;
    private final Queue<Order> rejectedQueue;
    private final KitchenManager kitchenManager;
    private final long endOfDayTime;

    private final FlowGenerator flowGenerator;
    private final OrderStrategyManager orderStrategyManager;

    public Queues(long endOfDayTime, FlowGenerator flowGenerator, OrderStrategyManager orderStrategyManager, KitchenManager kitchenManager) {
        this.generalQueue = new LinkedList<>();
        this.rejectedQueue = new LinkedList<>();
        this.endOfDayTime = endOfDayTime;
        this.flowGenerator = flowGenerator;
        this.orderStrategyManager = orderStrategyManager;
        this.kitchenManager = kitchenManager;
    }

    /**
     * Main logic to generate orders and manage the flow between queues and the kitchen.
     */
    public void manageOrderFlow() {
        if (flowGenerator.shouldGenerateOrder()) {
            // Generate a new order using the active strategy
            Order newOrder = orderStrategyManager.generateOrder();
            addOrderToGeneralQueue(newOrder);
        }

        // Try to process orders from the general queue into the kitchen
        processOrdersToKitchen();
    }

    /**
     * Adds an order to the general queue for processing.
     * @param order The order to be added.
     */
    private void addOrderToGeneralQueue(Order order) {
        if (canOrderBeCompleted(order)) {
            generalQueue.add(order);
            System.out.println("Order added to general queue: " + order);
        } else {
            rejectOrder(order);
        }
    }

    /**
     * Processes orders from the general queue and sends them to the kitchen if possible.
     */
    private void processOrdersToKitchen() {
        while (!generalQueue.isEmpty()) {
            Order order = generalQueue.peek();
            if (kitchenManager.canAcceptOrder(order)) {
                generalQueue.poll(); // Remove the order from the general queue
                kitchenManager.acceptOrder(order); // Pass the order to the kitchen
                System.out.println("Order sent to kitchen: " + order);
            } else {
                System.out.println("Kitchen cannot accept order yet: " + order);
                break; // Stop processing if the kitchen can't accept the current order
            }
        }
    }

    /**
     * Checks if the order can be completed before the end of the day.
     * @param order The order to check.
     * @return True if the order can be completed, false otherwise.
     */
    private boolean canOrderBeCompleted(Order order) {
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