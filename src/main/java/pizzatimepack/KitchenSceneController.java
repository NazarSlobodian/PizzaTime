package pizzatimepack;

import ViewModels.KitchenPizzaViewModel;
import ViewModels.KitchenViewModel;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class KitchenSceneController {

    @FXML
    private TableView<KitchenPizzaViewModel> kitchenTable;

    @FXML
    private TableColumn<KitchenPizzaViewModel, String> orderNumberColumn;

    @FXML
    private TableColumn<KitchenPizzaViewModel, String> statusColumn;

    @FXML
    private TableColumn<KitchenPizzaViewModel, String> readinessColumn;

    private KitchenViewModel kitchenViewModel;

    public void setKitchenViewModel(KitchenViewModel kitchenViewModel) {
        this.kitchenViewModel = kitchenViewModel;
        kitchenTable.setItems(kitchenViewModel.getPizzasInKitchen());
    }

    @FXML
    public void initialize() {
        orderNumberColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        statusColumn.setCellValueFactory(cellData -> cellData.getValue().stateProperty());
        readinessColumn.setCellValueFactory(cellData -> cellData.getValue().readinessProperty());
    }
}
