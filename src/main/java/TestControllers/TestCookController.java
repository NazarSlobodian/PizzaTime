package TestControllers;
import TestControllers.TestClassCook.TestCook;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class TestCookController {



    @FXML
    private TableView<TestCook> tableView;

    @FXML
    private TableColumn<TestCook, String> cookIdColumn;

    @FXML
    private TableColumn<TestCook, String> cookStateColumn;

    @FXML
    private TableColumn<TestCook, Void> actionColumn;

    @FXML
    private Button initializeButton;

    public void initialize() {
        // Ініціалізація колонок
        cookIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        cookStateColumn.setCellValueFactory(new PropertyValueFactory<>("state"));

        // Додати кастомну колонку з кнопками
        actionColumn.setCellFactory(new Callback<TableColumn<TestCook, Void>, TableCell<TestCook, Void>>() {
            @Override
            public TableCell<TestCook, Void> call(final TableColumn<TestCook, Void> param) {
                return new TableCell<TestCook, Void>() {
                    private final Button btn = new Button();

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || getTableRow() == null) {
                            setGraphic(null);
                        } else {
                            TestCook cook = getTableRow().getItem();
                            if (cook != null) {
                                btn.setText(cook.isActive() ? "Зупинити" : "Продовжити");
                                btn.setOnAction(event -> {
                                    cook.toggleActive();
                                    btn.setText(cook.isActive() ? "Зупинити" : "Продовжити");
                                });
                                setGraphic(btn);
                            }
                        }
                    }
                };
            }
        });

        // Додати обробник подій для кнопки ініціалізації
        initializeButton.setOnAction(event -> initializeTable());
    }

    private void initializeTable() {
        System.out.println("Кнопка ініціалізації натиснута!");
        ObservableList<TestCook> cooks = FXCollections.observableArrayList();

        for (int i = 1; i <= 10; i++) {
            String id = "Кухар " + i;
            String state = (Math.random() < 0.5) ? "Активний" : "Неактивний";
            cooks.add(new TestCook(id, state, "Активний".equals(state)));
        }

        // Виводимо дані для перевірки
        System.out.println("Дані для таблиці:");
        for (TestCook cook : cooks) {
            System.out.println("ID: " + cook.getId() + ", Стан: " + cook.getState());
        }

        try {
            tableView.setItems(cooks); // Встановлюємо дані в таблицю
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}