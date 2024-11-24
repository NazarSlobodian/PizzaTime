package pizzatimepack;

import ViewModels.CookerViewModel;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

public class EditSkillsSceneController {
    @FXML
    private VBox skillsBox;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    private CookerViewModel cookerViewModel;
    private final Map<String, CheckBox> skillCheckBoxes = new HashMap<>();

    public void initialize() {
        // Close the window on cancel
        cancelButton.setOnAction(event -> {
            Stage stage = (Stage) cancelButton.getScene().getWindow();
            stage.close();
        });

        // Save the updated skills and close the window
        saveButton.setOnAction(event -> {
            if (cookerViewModel != null) {
                Map<String, Boolean> updatedSkills = new HashMap<>();
                for (Map.Entry<String, CheckBox> entry : skillCheckBoxes.entrySet()) {
                    updatedSkills.put(entry.getKey(), entry.getValue().isSelected());
                }
                cookerViewModel.setSkills(updatedSkills);
            }

            Platform.runLater(() -> {
                Stage stage = (Stage) saveButton.getScene().getWindow();
                stage.close();
            });
        });
    }

    public void setCookerViewModel(CookerViewModel cookerViewModel) {
        this.cookerViewModel = cookerViewModel;

        // Populate skillsBox with checkboxes
        skillsBox.getChildren().clear();
        if (cookerViewModel != null) {
            for (Map.Entry<String, Boolean> skill : cookerViewModel.getSkills().entrySet()) {
                CheckBox checkBox = new CheckBox(skill.getKey());
                checkBox.setSelected(skill.getValue());
                skillCheckBoxes.put(skill.getKey(), checkBox);
                skillsBox.getChildren().add(checkBox);
            }
        }
    }
}