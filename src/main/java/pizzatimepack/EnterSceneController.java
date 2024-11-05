package pizzatimepack;

import ViewModels.MainViewModel;
import ViewModels.SimTimeViewModel;
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
    private SimTimeViewModel simTimeViewModel;

    public void setSimTimeViewModel(SimTimeViewModel simTimeViewModel) {
        this.simTimeViewModel = simTimeViewModel;
    }

    public void switchToNav(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/nav-view.fxml"));
        root = loader.load();

        NavSceneController navController = loader.getController();
        navController.setSimTimeViewModel(simTimeViewModel);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = getClass().getResource("/Views/main.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}
