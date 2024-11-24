package pizzatimepack;

import ViewModels.MainViewModel;
import ViewModels.QueuesViewModel;
import ViewModels.SimTimeViewModel;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NavSceneController {
    @FXML
    private VBox menuItemsBox;

    @FXML
    private Button dropDownButton, cashButton, kitchenButton, staffButton, logButton;

    @FXML
    private BorderPane viewBorderPane;

    @FXML
    private Label clockLabel;

    @FXML
    private Button speedUp0Btn, speedUp1Btn, speedUp60Btn;

    @FXML
    private ChoiceBox<String> strtegyChoiceBox;

    private MainViewModel mainViewModel;
    private SimTimeViewModel simTimeViewModel;
    private QueuesViewModel queuesViewModel;

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
        this.simTimeViewModel = mainViewModel.getSimTimeViewModel();
        this.queuesViewModel = mainViewModel.getQueuesViewModel();

        // Initialize bindings related to SimTimeViewModel
        bindClockLabel();
        bindSpeedButtons();

        bindStrategyChoiceBox();
        initializeDefaultStrategy();
    }

    private void bindClockLabel() {
        clockLabel.textProperty().bind(simTimeViewModel.simDateTimeProperty());
    }

    public void bindSpeedButtons() {
        simTimeViewModel.simTimeSpeedProperty().addListener((observable, oldValue, newValue) -> {
            updateSpeedButtonStates(newValue.intValue());
        });

        speedUp0Btn.setOnAction(event -> {
            simTimeViewModel.setSimTimeSpeed(0);
            updateSpeedButtonStates(0);
        });

        speedUp1Btn.setOnAction(event -> {
            simTimeViewModel.setSimTimeSpeed(1);
            updateSpeedButtonStates(1);
        });

        speedUp60Btn.setOnAction(event -> {
            simTimeViewModel.setSimTimeSpeed(60);
            updateSpeedButtonStates(60);
        });

        updateSpeedButtonStates(simTimeViewModel.simTimeSpeedProperty().get());
    }

    private void updateSpeedButtonStates(int speed) {
        speedUp0Btn.setDisable(speed == 0);
        speedUp1Btn.setDisable(speed == 1);
        speedUp60Btn.setDisable(speed == 60);
    }

    private void bindStrategyChoiceBox() {
        strtegyChoiceBox.setItems(queuesViewModel.getStrats());

        strtegyChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                queuesViewModel.setStrat(newValue);
            }
        });
    }

    private void initializeDefaultStrategy() {
        ObservableList<String> strategies = queuesViewModel.getStrats();
        if (!strategies.isEmpty()) {
            String defaultStrategy = strategies.get(0);
            strtegyChoiceBox.getSelectionModel().select(defaultStrategy);
            queuesViewModel.setStrat(defaultStrategy);
        }
    }

    @FXML
    private void toggleMenu() {
        boolean isVisible = menuItemsBox.isVisible();

        menuItemsBox.setVisible(!isVisible);

        double shift = menuItemsBox.getPrefHeight();
        if (!isVisible) {
            cashButton.setLayoutY(cashButton.getLayoutY() + shift);
            kitchenButton.setLayoutY(kitchenButton.getLayoutY() + shift);
            staffButton.setLayoutY(staffButton.getLayoutY() + shift);
            logButton.setLayoutY(logButton.getLayoutY() + shift);
            dropDownButton.getStyleClass().add("active-button");
        } else {
            cashButton.setLayoutY(cashButton.getLayoutY() - shift);
            kitchenButton.setLayoutY(kitchenButton.getLayoutY() - shift);
            staffButton.setLayoutY(staffButton.getLayoutY() - shift);
            logButton.setLayoutY(logButton.getLayoutY() - shift);
            dropDownButton.getStyleClass().remove("active-button");
        }
    }

    @FXML
    private void btnPizza() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/pizza-view.fxml"));
        AnchorPane view = loader.load();

        PizzaSceneController controller = loader.getController();
        controller.setMenuViewModel(mainViewModel.getMenuViewModel());

        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }

    @FXML
    private void btnDrinks() throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/Views/drinks-view.fxml"));
        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }

    @FXML
    private void btnDesserts() throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/Views/desserts-view.fxml"));
        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }

    @FXML
    private void btnCash() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/cash_view.fxml"));
        AnchorPane view = loader.load();

        CashSceneController controller = loader.getController();
        controller.setQueuesViewModel(mainViewModel.getQueuesViewModel());

        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }

    @FXML
    private void btnStaff() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/staff-view.fxml"));
        AnchorPane view = loader.load();

        StaffSceneController controller = loader.getController();
        controller.setKitchenViewModel(mainViewModel.getKitchenViewModel());
        controller.setQueuesViewModel(mainViewModel.getQueuesViewModel());

        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }

    @FXML
    private void btnKitchen() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/kitchen-view.fxml"));
        AnchorPane view = loader.load();

        KitchenSceneController controller = loader.getController();
        controller.setKitchenViewModel(mainViewModel.getKitchenViewModel());

        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }

    @FXML
    private void btnLogs() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/log-view.fxml"));
        AnchorPane view = loader.load();

        LogsSceneController controller = loader.getController();
        controller.setLogViewModel(mainViewModel.getLogViewModel());

        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }
}
