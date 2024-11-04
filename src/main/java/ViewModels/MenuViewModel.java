package ViewModels;

import Model.FoodAndStuff.Menu;
import Model.FoodAndStuff.Pizza;
import Model.Pizzeria;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class MenuViewModel {

    private final Menu menu;
    private final ObservableList<SingularPizzaViewModel> pizzasInMenu;

    public MenuViewModel(Menu menu) {
        this.menu = menu;

        this.pizzasInMenu = FXCollections.observableArrayList();

        for (Pizza pizza : this.menu.getPizzas()) {
            pizzasInMenu.add(new SingularPizzaViewModel(pizza));
        }
        System.out.println("MENU:"); // <---don't forget to delete this
        for (SingularPizzaViewModel pizza : pizzasInMenu) {
            System.out.println("Name: " + pizza.nameProperty().getValue() + ", minTime: " + pizza.readinessProperty().getValue());
        }

        this.menu.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("menuPizzaDeleted")) {
                Platform.runLater(() -> {
                    System.out.println("Pizza " + pizzasInMenu.get((int)evt.getNewValue()).nameProperty().getValue() + " removed");
                    pizzasInMenu.remove((int)evt.getNewValue());
                });
            }
        });
        this.menu.addPropertyChangeListener(evt -> {
            if (evt.getPropertyName().equals("menuPizzaAdded")) {
                Platform.runLater(() -> {
                    pizzasInMenu.add(new SingularPizzaViewModel((Pizza)evt.getNewValue()));
                    System.out.println(((Pizza) evt.getNewValue()).getName() + " added");
                });
            }
        });
    }

    public ObservableList<SingularPizzaViewModel> getPizzasInMenu() {
        return pizzasInMenu;
    }

    public void obliteratePizzaFromMenu(int index) {
        menu.removePizza(index);
    }

}
