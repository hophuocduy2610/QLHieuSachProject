module com.example.nhom10_qlhs {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires fontawesomefx;

    opens com.example.nhom10_qlhs to javafx.fxml;
    opens com.example.nhom10_qlhs.entities to javafx.fxml;
    exports com.example.nhom10_qlhs;
    exports com.example.nhom10_qlhs.entities;
    exports com.example.nhom10_qlhs.controller;
    opens com.example.nhom10_qlhs.controller to javafx.fxml;
}
