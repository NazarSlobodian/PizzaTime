package com.example.javafxsample;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
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
    private void toggleMenu() {
        boolean isVisible = menuItemsBox.isVisible();

        menuItemsBox.setVisible(!isVisible);

        if (isVisible) {
            dropDownButton.getStyleClass().remove("active-button");
        } else {
            dropDownButton.getStyleClass().add("active-button");
        }
    }

    @FXML
    private void btnPizza() throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("pizza-view.fxml"));
        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }

    @FXML
    private void btnDrinks() throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("drinks-view.fxml"));
        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }

    @FXML
    private void btnDesserts() throws IOException {
        AnchorPane view = FXMLLoader.load(getClass().getResource("desserts-view.fxml"));
        viewBorderPane.setVisible(true);
        viewBorderPane.setCenter(view);
    }


}
