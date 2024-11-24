package pizzatimepack;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

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
    private String imagePath; // Path to the selected image

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

    public String getImagePath() {
        return imagePath; // Return the image path
    }

    public boolean isConfirmed() {
        return isConfirmed;
    }

    @FXML
    private void onOk() {
        String enteredName = nameField.getText();
        String enteredIngredients = ingredientsArea.getText();
        String enteredTime = timeField.getText();

        // Validate required fields
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

        // Close the window
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onCancel() {
        // Close the window without confirmation
        Stage stage = (Stage) nameField.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void onUploadImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Виберіть фото для піци");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            imagePath = selectedFile.toURI().toString(); // Save the image path as a URI
            showAlert("Фото додано", "Шлях до фото: " + selectedFile.getName(), Alert.AlertType.INFORMATION);
        }
    }

    private void showAlert(String title, String message, Alert.AlertType alertType) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
