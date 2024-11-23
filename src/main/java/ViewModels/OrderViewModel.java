package ViewModels;

import Model.KitchenStuff.Order;
import javafx.application.Platform;
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
    public OrderViewModel(Order order) {
        state = new SimpleStringProperty(order.isDone()? "Done":"In progress");
        beginTime = new SimpleStringProperty(msToString(order.getOrderTime()));
        endTime = new SimpleStringProperty("N/A");

        order.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("orderReady")) {
                Platform.runLater(()-> {
                    state.set("Done");
                    endTime.set(msToString((long)evt.getNewValue()));
                    System.out.println("ATTENTIONNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNNN" + "Done " + endTime.getValue());
                });
            }
        });
    }
    private String msToString(long ms) {
        return LocalDateTime.ofInstant(Instant.ofEpochMilli(ms), ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd,\nHH:mm:ss"));
    }
}
