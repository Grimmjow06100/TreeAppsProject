module Demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires net.datafaker;

    opens Demo to javafx.fxml;
    exports Demo;

    exports others; // Rendre accessible le package "others" à tous les modules
    opens others to com.fasterxml.jackson.databind; // Autorise Jackson à accéder aux champs privés
}