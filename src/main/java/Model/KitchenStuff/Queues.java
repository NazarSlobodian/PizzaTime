package Model.KitchenStuff;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import Model.Generators.FlowGenerator;
import Model.Generators.OrderStrategyManager;
import Model.Utils.Clock;
import Model.Utils.Schedule;

/**
 * Manages the queues for order processing and integrates with OrderStrategyManager, FlowGenerator, and KitchenManager.
 */
public class Queues {
    private final Map<String, Queue<Order>> orderQueues;
    private final Queue<Order> rejectedQueue;
    private final List<Order> allOrders;
    private final KitchenManager kitchenManager;
    Schedule  schedule;

    private final FlowGenerator flowGenerator;
    private final OrderStrategyManager orderStrategyManager;
    Clock clock;

    public Queues(Schedule schedule, FlowGenerator flowGenerator, OrderStrategyManager orderStrategyManager, KitchenManager kitchenManager, Clock clock) {
        this.orderQueues = new HashMap<>();
        this.rejectedQueue = new LinkedList<>();
        this.allOrders = new ArrayList<>();
        this.schedule = schedule;
        this.flowGenerator = flowGenerator;
        this.orderStrategyManager = orderStrategyManager;
        this.kitchenManager = kitchenManager;
        this.clock = clock;
    }

    /**
     * Adds a new queue for a specific type of order.
     *
     * @param queueName The name of the queue.
     */
    public void addQueue(String queueName) {
        orderQueues.put(queueName, new LinkedList<>());
    }

    /**
     * Main logic to generate orders and manage the flow between queues and the kitchen.
     */
    public void manageOrderFlow() {
        if (flowGenerator.shouldGenerateOrder()) {
            // Generate a new order using the active strategy
            Order newOrder = orderStrategyManager.generateOrder();
            addOrderToAppropriateQueue(newOrder);
        }

        // Try to process orders from all queues into the kitchen
        processOrdersToKitchen();
    }

    /**
     * Adds an order to the appropriate queue based on custom criteria.
     *
     * @param order The order to be added.
     */
    private void addOrderToAppropriateQueue(Order order) {
        allOrders.add(order); // Save to the history of all orders

        // Example logic: decide the queue based on order size
        String queueName = (order.getItems().size() <= 3) ? "SmallOrders" : "LargeOrders";
        Queue<Order> queue = orderQueues.get(queueName);

        if (queue != null && canOrderBeCompleted(order)) {
            queue.add(order);
            System.out.println("Order added to queue [" + queueName + "]: " + order);
        } else {
            rejectOrder(order);
        }
    }

    /**
     * Processes orders from all queues and sends them to the kitchen if possible.
     */
    private void processOrdersToKitchen() {
        for (Map.Entry<String, Queue<Order>> entry : orderQueues.entrySet()) {
            Queue<Order> queue = entry.getValue();
            while (!queue.isEmpty()) {
                Order order = queue.peek();
                if (kitchenManager.canAcceptOrder(order)) {
                    queue.poll(); // Remove the order from the queue
                    Order orderCopy = new Order(order); // Create a copy before sending
                    kitchenManager.acceptOrder(orderCopy);
                    System.out.println("Order sent to kitchen from [" + entry.getKey() + "]: " + orderCopy);
                } else {
                    System.out.println("Kitchen cannot accept order yet from queue [" + entry.getKey() + "]: " + order);
                    break; // Stop processing this queue if the kitchen can't accept the current order
                }
            }
        }
    }

    /**
     * Checks if the order can be completed before the end of the day.
     *
     * @param order The order to check.
     * @return True if the order can be completed, false otherwise.
     */
    private boolean canOrderBeCompleted(Order order) {
        long estimatedCompletionTime = order.getOrderTime() + 20 * 60 * 1000; // 20 minutes
        return estimatedCompletionTime <= schedule.getCloseTimeMs(clock.getLocalDate());
    }

    /**
     * Rejects the order if it can't be completed on time.
     *
     * @param order The order to be rejected.
     */
    private void rejectOrder(Order order) {
        rejectedQueue.add(order);
        System.out.println("Order rejected: " + order);
    }

    /**
     * Retrieves the queue of rejected orders.
     *
     * @return The rejected orders queue.
     */
    public Queue<Order> getRejectedOrders() {
        return rejectedQueue;
    }

    /**
     * Retrieves the list of all orders processed throughout the day.
     *
     * @return The list of all orders.
     */
    public List<Order> getAllOrders() {
        return allOrders;
    }
}