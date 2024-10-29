module pizzatimepack {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;

    opens pizzatimepack to javafx.fxml;
    exports pizzatimepack;
}