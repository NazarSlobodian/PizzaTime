package com.example.javafxsample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class EnterSceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void switchToNav(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("nav-view.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = getClass().getResource("/com/example/javafxsample/main.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}
