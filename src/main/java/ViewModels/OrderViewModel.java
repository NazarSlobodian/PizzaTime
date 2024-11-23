package ViewModels;

import Model.KitchenStuff.Order;
import javafx.application.Platform;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class OrderViewModel {
    private final StringProperty state;
    private final StringProperty beginTime;
    private final StringProperty endTime;
    private final IntegerProperty queue;

    public OrderViewModel(Order order) {
        state = new SimpleStringProperty(order.isDone()? "Done":"In progress");
        beginTime = new SimpleStringProperty(msToString(order.getOrderTime()));
        endTime = new SimpleStringProperty("N/A");

        queue = new SimpleIntegerProperty(order.getQueue());
        System.out.println(queue.getValue());


        order.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("orderReady")) {
                Platform.runLater(()-> {
                    state.set("Done");
                    endTime.set(msToString((long)evt.getNewValue()));
                    System.out.println("ATTENTIONNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN" + "Done " + endTime.getValue());
                });
            }
        });

        order.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("queueChange")) {
                Platform.runLater(()-> {
                    queue.set((int)evt.getNewValue());
                    System.out.println("QUEUE SET TO " + evt.getNewValue());
                });
            }
        });

    }

    public StringProperty stateProperty() {
        return state;
    }
    public StringProperty beginTimeProperty() {
        return beginTime;
    }
    public StringProperty endTimeProperty() {
        return endTime;
    }
    public IntegerProperty queueProperty() {
        return queue;
    }


    private String msToString(long ms) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(ms), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd,\nHH:mm:ss"));
    }
}
