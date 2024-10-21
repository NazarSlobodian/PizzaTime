module pizzatime.grouppackage.pizzatime {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;

    opens pizzatimepack to javafx.fxml;
    exports pizzatimepack;
}