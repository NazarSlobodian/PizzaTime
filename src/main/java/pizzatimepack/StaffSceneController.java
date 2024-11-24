package pizzatimepack;

import ViewModels.CookerViewModel;
import ViewModels.KitchenViewModel;
import ViewModels.QueuesViewModel;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;

public class StaffSceneController {

    @FXML
    private TableView<CookerViewModel> tableView;

    @FXML
    private TableColumn<CookerViewModel, String> cookIdColumn;

    @FXML
    private TableColumn<CookerViewModel, String> cookStateColumn;

    @FXML
    private TableColumn<CookerViewModel, CookerViewModel> actionColumn;

    @FXML
    private TableColumn<CookerViewModel, String> skillsColumn;

    @FXML
    private Label totalOrdersLabel, activeOrdersLabel, completedOrdersLabel;

    private KitchenViewModel kitchenViewModel;

    private QueuesViewModel queuesViewModel;

    public void setKitchenViewModel(KitchenViewModel kitchenViewModel) {
        this.kitchenViewModel = kitchenViewModel;
        bindTableView();
        bindKitchenViewModel();
    }

    public void setQueuesViewModel(QueuesViewModel queuesViewModel) {
        this.queuesViewModel = queuesViewModel;
        bindQueueViewModel();
    }

    private void bindKitchenViewModel() {
        activeOrdersLabel.textProperty().bind(
                kitchenViewModel.getOrdersInKitchen().asString()
        );

        completedOrdersLabel.textProperty().bind(
                kitchenViewModel.getOrdersDone().asString()
        );
    }

    private void bindQueueViewModel() {
        if (queuesViewModel != null) {
            totalOrdersLabel.textProperty().bind(
                    queuesViewModel.totalOrdersGenerated().asString()
            );
        }
    }

    private void bindTableView() {
        tableView.setItems(kitchenViewModel.getCooks());

        cookIdColumn.setCellValueFactory(data ->
                Bindings.createStringBinding(
                        () -> String.valueOf(kitchenViewModel.getCooks().indexOf(data.getValue()) + 1),
                        kitchenViewModel.getCooks()
                )
        );

        cookStateColumn.setCellValueFactory(data ->
                Bindings.createStringBinding(
                        () -> data.getValue().getIsPresent().get() ? "Присутній" : "Відсутній",
                        data.getValue().getIsPresent()
                )
        );

        // Колонка навичок
        skillsColumn.setCellValueFactory(data ->
                Bindings.createStringBinding(
                        () -> data.getValue().getSkills().entrySet()
                                .stream()
                                .map(entry -> entry.getKey() + ": " + (entry.getValue() ? "Так" : "Ні"))
                                .reduce((s1, s2) -> s1 + ", " + s2)
                                .orElse("Немає навичок"),
                        data.getValue().getSkills()
                )
        );
        skillsColumn.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                    setGraphic(null);
                } else {
                    setText(item);
                    setWrapText(true);
                    setTooltip(new Tooltip(item));
                }
            }
        });

        actionColumn.setCellValueFactory(data -> Bindings.createObjectBinding(() -> data.getValue()));
        actionColumn.setCellFactory(createActionCellFactory());

        kitchenViewModel.getCooks().addListener((ListChangeListener<CookerViewModel>) change -> {
            while (change.next()) {
                if (change.wasAdded()) {
                    for (CookerViewModel newCooker : change.getAddedSubList()) {
                        newCooker.getIsPresent().addListener((obs, oldVal, newVal) -> Platform.runLater(tableView::refresh));
                    }
                }
            }
            Platform.runLater(() -> tableView.refresh());
        });

        for (CookerViewModel cooker : kitchenViewModel.getCooks()) {
            cooker.getIsPresent().addListener((obs, oldVal, newVal) -> Platform.runLater(tableView::refresh));
        }
    }

    private Callback<TableColumn<CookerViewModel, CookerViewModel>, TableCell<CookerViewModel, CookerViewModel>> createActionCellFactory() {
        return column -> new TableCell<>() {
            private final Button actionButton = new Button();
            private final Button deleteButton = new Button();
            private final Button editButton = new Button();

            {
                ImageView stopIcon = new ImageView(new Image(getClass().getResourceAsStream("/Views/images/icons/stop.png")));
                stopIcon.setFitWidth(16);
                stopIcon.setFitHeight(16);

                ImageView startIcon = new ImageView(new Image(getClass().getResourceAsStream("/Views/images/icons/start.png")));
                startIcon.setFitWidth(16);
                startIcon.setFitHeight(16);

                ImageView deleteIcon = new ImageView(new Image(getClass().getResourceAsStream("/Views/images/icons/delete.png")));
                deleteIcon.setFitWidth(16);
                deleteIcon.setFitHeight(16);

                ImageView editIcon = new ImageView(new Image(getClass().getResourceAsStream("/Views/images/icons/edit.png")));
                editIcon.setFitWidth(16);
                editIcon.setFitHeight(16);

                deleteButton.setGraphic(deleteIcon);
                editButton.setGraphic(editIcon);

                deleteButton.setOnAction(event -> {
                    int index = getIndex();
                    if (index >= 0 && index < kitchenViewModel.getCooks().size()) {
                        kitchenViewModel.deleteCook(index);
                        Platform.runLater(() -> tableView.refresh());
                    }
                });

                editButton.setOnAction(event -> {
                    CookerViewModel cooker = kitchenViewModel.getCooks().get(getIndex());
                    if (cooker != null) {
                        openEditSkillsWindow(cooker);
                    }
                });

                actionButton.setOnAction(event -> {
                    CookerViewModel cooker = kitchenViewModel.getCooks().get(getIndex());
                    if (cooker.getIsPresent().get()) {
                        kitchenViewModel.stopCook(getIndex());
                    } else {
                        kitchenViewModel.startCook(getIndex());
                    }
                });
            }

            @Override
            protected void updateItem(CookerViewModel cooker, boolean empty) {
                super.updateItem(cooker, empty);

                if (empty || cooker == null || getIndex() < 0 || getIndex() >= kitchenViewModel.getCooks().size()) {
                    setGraphic(null);
                    return;
                }

                // Оновлення графіки кнопок
                ImageView currentIcon = cooker.getIsPresent().get()
                        ? new ImageView(new Image(getClass().getResourceAsStream("/Views/images/icons/stop.png")))
                        : new ImageView(new Image(getClass().getResourceAsStream("/Views/images/icons/start.png")));
                currentIcon.setFitWidth(16);
                currentIcon.setFitHeight(16);
                actionButton.setGraphic(currentIcon);

                HBox actionButtons = new HBox(5, actionButton, editButton, deleteButton);
                actionButtons.setAlignment(Pos.CENTER);
                setGraphic(actionButtons);
            }

        };
    }

    private void openEditSkillsWindow(CookerViewModel cooker) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/EditSkills-view.fxml"));
            Parent root = loader.load();

            // Get the controller and pass the CookerViewModel to it
            EditSkillsSceneController controller = loader.getController();
            controller.setCookerViewModel(cooker);

            // Create and display the stage
            Stage stage = new Stage();
            stage.setTitle("Редагування навичок");
            stage.initModality(Modality.APPLICATION_MODAL); // Block interaction with other windows
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Wait until the window is closed

            Platform.runLater(() -> tableView.refresh());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void AddCookBtn() {
        kitchenViewModel.addCook();
        Platform.runLater(() -> tableView.refresh());
    }
}