package pizzatimepack;

import ViewModels.QueuesViewModel;
import ViewModels.OrderViewModel;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.HashMap;
import java.util.Map;

public class CashSceneController {

    @FXML
    private TableView<OrderViewModel> tableView;

    @FXML
    private TableColumn<OrderViewModel, Number> cashColumn;

    @FXML
    private TableColumn<OrderViewModel, Number> orderNumberColumn;

    @FXML
    private TableColumn<OrderViewModel, String> statusColumn;

    @FXML
    private TableColumn<OrderViewModel, String> startTimeColumn;

    @FXML
    private TableColumn<OrderViewModel, String> endTimeColumn;

    @FXML
    private Button addCashButton;

    @FXML
    private Button removeCashButton;

    @FXML
    private Label cashCountLabel;

    private QueuesViewModel queuesViewModel;

    private final Map<OrderViewModel, Integer> orderNumbers = new HashMap<>();
    private int nextOrderNumber = 1;

    public void setQueuesViewModel(QueuesViewModel queuesViewModel) {
        this.queuesViewModel = queuesViewModel;
        bindTableView();
        bindButtons();
        bindCashCountLabel();
    }

    private void bindTableView() {
        tableView.setItems(queuesViewModel.getAllOrders());

        cashColumn.setCellValueFactory(data -> data.getValue().queueProperty());
        orderNumberColumn.setCellValueFactory(this::generateOrderNumber);
        statusColumn.setCellValueFactory(data -> data.getValue().stateProperty());
        startTimeColumn.setCellValueFactory(data -> data.getValue().beginTimeProperty());
        endTimeColumn.setCellValueFactory(data -> data.getValue().endTimeProperty());
    }

    private ObservableValue<Number> generateOrderNumber(TableColumn.CellDataFeatures<OrderViewModel, Number> data) {
        OrderViewModel order = data.getValue();
        if (orderNumbers.containsKey(order)) {
            return new SimpleIntegerProperty(orderNumbers.get(order));
        }
        int generatedNumber = nextOrderNumber++;
        orderNumbers.put(order, generatedNumber);
        return new SimpleIntegerProperty(generatedNumber);
    }

    private void bindButtons() {
        addCashButton.setOnAction(event -> queuesViewModel.addQueue());
        removeCashButton.setOnAction(event -> queuesViewModel.deleteQueue());

        // Додамо логіку для блокування кнопок
        removeCashButton.disableProperty().bind(
                Bindings.createBooleanBinding(
                        () -> queuesViewModel.queuesCount().get() <= 2,
                        queuesViewModel.queuesCount()
                )
        );
    }

    private void bindCashCountLabel() {
        cashCountLabel.textProperty().bind(
                Bindings.concat("Кількість кас: ", queuesViewModel.queuesCount())
        );
    }
}
