package pizzatimepack;

import ViewModels.SimTimeViewModel;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class NavSceneController {
    @FXML
    private VBox menuItemsBox;

    @FXML
    private Button dropDownButton;

    @FXML
    private BorderPane viewBorderPane;

    @FXML
    private Button cashButton;

    @FXML
    private Button kitchenButton;

    @FXML
    private Button staffButton;

    @FXML
    private Label clockLabel;

    private SimTimeViewModel simTimeViewModel;

    public void setSimTimeViewModel(SimTimeViewModel simTimeViewModel) {
        this.simTimeViewModel = simTimeViewModel;
        bindClockLabel();
    }

    private void bindClockLabel() {
        clockLabel.textProperty().bind(simTimeViewModel.simDateTimeProperty());
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
            dropDownButton.getStyleClass().add("active-button");
        } else {
            cashButton.setLayoutY(cashButton.getLayoutY() - shift);
            kitchenButton.setLayoutY(kitchenButton.getLayoutY() - shift);
            staffButton.setLayoutY(staffButton.getLayoutY() - shift);
            dropDownButton.getStyleClass().remove("active-button");
        }
    }

    @FXML
    private void btnPizza() throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/Views/pizza-view.fxml"));
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
        AnchorPane view = FXMLLoader.load(getClass().getResource("/Views/cash_view.fxml"));
        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }

    @FXML
    private void btnKitchen() throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("/Views/kitchen-view.fxml"));
        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }

}
