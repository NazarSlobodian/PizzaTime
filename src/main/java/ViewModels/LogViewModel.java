package ViewModels;

import Model.Utils.Logger;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class LogViewModel {
    private final ObservableList<String> logs;

    public LogViewModel(Logger logger) {
        this.logs = FXCollections.observableArrayList();
        logger.addPropertyChangeListener(evt-> {
            if (evt.getPropertyName().equals("logAdded")) {
                Platform.runLater(()-> {
                    logs.add((String)evt.getNewValue());
                    System.out.println("NEW LOG" + (String)evt.getNewValue());
                });
            }
        });
    }
}
