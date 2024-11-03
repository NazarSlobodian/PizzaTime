package pizzatimepack;

import Model.Pizzeria;
import ViewModels.MainViewModel;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("/Views/hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Oi!");
        stage.setScene(scene);
        stage.show();

        Pizzeria pizzeria = new Pizzeria();
        MainViewModel mainViewModel = new MainViewModel(pizzeria);
        SimulatorLauncher launcher = new SimulatorLauncher(pizzeria);
        launcher.run();
        stage.setOnCloseRequest(event -> {
            launcher.stop(); // Stop the simulator
            System.exit(0); // Exit the application
        });
    }

    public static void main(String[] args) {
        launch();
    }
}