package pizzatimepack;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DessertsSceneController {
    @FXML
    private void BtnAddDesserts() {
        try {
            // Завантажуємо FXML для діалогового вікна
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AddItemWindow.fxml"));
            Parent root = loader.load();

            // Створюємо нове вікно
            Stage stage = new Stage();
            stage.setTitle("Додати десерт");
            stage.initModality(Modality.APPLICATION_MODAL); // Блокує основне вікно, поки це відкрите
            stage.setScene(new Scene(root));
            stage.showAndWait(); // Очікує закриття діалогового вікна
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
