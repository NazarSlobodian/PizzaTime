package pizzatimepack;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddPizzaController {

    @FXML
    private TextField nameField;

    @FXML
    private TextArea ingredientsArea;

    @FXML
    private TextField timeField;

    private String name;
    private String ingredients;
    private int time;

    private boolean isConfirmed = false;

    public String getName() {
        return name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public int getTime() {
        return time;
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    @FXML
    private void onOk() {
        String enteredName = nameField.getText();
        String enteredIngredients = ingredientsArea.getText();
        String enteredTime = timeField.getText();

        // Перевірка обов'язкових полів
        if (enteredName.isEmpty() || enteredTime.isEmpty()) {
            showAlert("Помилка", "Назва та час приготування є обов'язковими полями!", Alert.AlertType.WARNING);
            return;
        }

        try {
            time = Integer.parseInt(enteredTime);
            if (time <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            showAlert("Помилка", "Час приготування повинен бути додатним числом!", Alert.AlertType.WARNING);
            return;
        }

        name = enteredName;
        ingredients = enteredIngredients.isEmpty() ? "Не вказано" : enteredIngredients;
        isConfirmed = true;

        // Закриваємо вікно
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancel() {
        // Закриваємо вікно без підтвердження
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null); // Без заголовку
        alert.setContentText(message);
        alert.showAndWait();
    }
}
