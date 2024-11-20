package pizzatimepack;

import ViewModels.MainViewModel;
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

    private MainViewModel mainViewModel;

    public void setMainViewModel(MainViewModel mainViewModel) {
        this.mainViewModel = mainViewModel;
    }

    public void switchToNav(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/nav-view.fxml"));
        root = loader.load();

        NavSceneController navController = loader.getController();
        navController.setMainViewModel(mainViewModel);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        String css = getClass().getResource("/Views/main.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();
    }
}
