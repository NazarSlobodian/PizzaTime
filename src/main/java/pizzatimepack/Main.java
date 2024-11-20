package pizzatimepack;

import Model.Pizzeria;
import ViewModels.MainViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/enter-view.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        String css = this.getClass().getResource("/Views/main.css").toExternalForm();
        scene.getStylesheets().add(css);
        stage.setScene(scene);
        stage.show();

        Pizzeria pizzeria = new Pizzeria();
        MainViewModel mainViewModel = new MainViewModel(pizzeria);

        EnterSceneController enterController = loader.getController();
        enterController.setMainViewModel(mainViewModel);

        SimulatorLauncher launcher = new SimulatorLauncher(pizzeria);
        launcher.run();
        stage.setOnCloseRequest(event -> {
            launcher.stop(); // Stop the simulator
            System.exit(0); // Exit the application
        });
    }

    public static void main(String[] args)
    {
        launch();
    }
}