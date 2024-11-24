package pizzatimepack;

import Model.FoodAndStuff.Pizza;
import ViewModels.MenuPizzaViewModel;
import ViewModels.MenuViewModel;
import javafx.collections.ListChangeListener;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

public class PizzaSceneController {
    @FXML
    private TilePane pizzasTilePane;

    private MenuViewModel menuViewModel;

    public void setMenuViewModel(MenuViewModel menuViewModel) {
        this.menuViewModel = menuViewModel;

        // Слухач для змін у списку піц
        menuViewModel.getPizzasInMenu().addListener((ListChangeListener<MenuPizzaViewModel>) change -> {
            while (change.next()) {
                refreshPizzasUI();
            }
        });
        refreshPizzasUI();
    }

    private void refreshPizzasUI() {
        pizzasTilePane.getChildren().clear();

        pizzasTilePane.setPrefColumns(2);
        pizzasTilePane.setHgap(20);
        pizzasTilePane.setVgap(20);

        for (MenuPizzaViewModel pizza : menuViewModel.getPizzasInMenu()) {
            pizzasTilePane.getChildren().add(createPizzaCard(pizza));
        }
    }

    private HBox createPizzaCard(MenuPizzaViewModel pizza) {
        HBox pizzaCard = new HBox();
        pizzaCard.setSpacing(10);
        pizzaCard.setStyle("-fx-padding: 10; -fx-border-color: lightgray; -fx-border-width: 1; -fx-border-radius: 5; -fx-background-color: white;");

        // Замінник зображення піци
        Rectangle pizzaPlaceholder = new Rectangle(100, 100);
        pizzaPlaceholder.setFill(Color.LIGHTGRAY);
        pizzaPlaceholder.setStroke(Color.BLACK);

        VBox textBox = new VBox();
        textBox.setSpacing(5);
        textBox.setStyle("-fx-padding: 5;");

        Label nameLabel = new Label(pizza.nameProperty().getValue());
        nameLabel.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");
        nameLabel.setWrapText(true);

        Label timeLabel = new Label("Time: " + pizza.minTimeProperty().getValue());
        timeLabel.setWrapText(true);

        textBox.getChildren().addAll(nameLabel, timeLabel);

        Button deleteButton = new Button();
        deleteButton.setMnemonicParsing(false);
        deleteButton.getStyleClass().add("pizzasButtons");

        ImageView minusIcon = new ImageView();
        URL imageUrl = getClass().getResource("/Views/images/minus.png");
        if (imageUrl != null) {
            minusIcon.setImage(new Image(imageUrl.toExternalForm()));
        }
        minusIcon.setFitHeight(20);
        minusIcon.setFitWidth(20);
        minusIcon.setPreserveRatio(true);

        deleteButton.setGraphic(minusIcon);
        deleteButton.setOnAction(event -> menuViewModel.obliteratePizzaFromMenu(menuViewModel.getPizzasInMenu().indexOf(pizza)));

        pizzaCard.getChildren().addAll(pizzaPlaceholder, textBox, deleteButton);

        return pizzaCard;
    }

    @FXML
    private void BtnAddPizza() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Views/AddItemWindow.fxml"));
            Parent root = loader.load();

            AddPizzaController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle("Додати нову піцу");
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(new Scene(root));
            stage.showAndWait();

            if (controller.isConfirmed()) {
                String name = controller.getName();
                int time = controller.getTime();

                menuViewModel.addPizza(name, time * 60 * 1000);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
