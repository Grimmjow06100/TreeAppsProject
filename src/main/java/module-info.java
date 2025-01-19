module TreeApp {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.sql;
    requires com.fasterxml.jackson.databind;
    requires net.datafaker;
    requires com.fasterxml.jackson.datatype.jsr310;

    opens Demo to javafx.fxml;
    exports Demo;

    exports others; // Rendre accessible le package "others" à tous les modules
    opens others to com.fasterxml.jackson.databind; // Autorise Jackson à accéder aux champs privés

    exports App.AssociationMember ; // Rendre accessible le package "App.AssociationMember" à tous les modules
    opens App.AssociationMember to com.fasterxml.jackson.databind;

    exports App.AssociationManagement; // Rendre accessible
    opens App.AssociationManagement to com.fasterxml.jackson.databind;
}