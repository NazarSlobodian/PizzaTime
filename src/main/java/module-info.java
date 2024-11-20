module pizzatimepack {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.desktop;
    requires org.reflections;
    requires java.naming;

    opens pizzatimepack to javafx.fxml;
    exports pizzatimepack;
}