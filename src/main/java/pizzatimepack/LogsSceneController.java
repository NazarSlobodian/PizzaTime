package pizzatimepack;

import ViewModels.LogViewModel;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class LogsSceneController {

    @FXML
    private TableView<String> logsTable;

    @FXML
    private TableColumn<String, String> logColumn;

    private LogViewModel logViewModel;

    public void setLogViewModel(LogViewModel logViewModel) {
        this.logViewModel = logViewModel;
        bindLogs();
    }

    private void bindLogs() {
        logsTable.setItems(logViewModel.getLogs());
        logColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()));
    }
}
