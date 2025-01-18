module Demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.sql;
    requires com.fasterxml.jackson.databind;


    opens Demo to javafx.fxml;
    exports Demo;
}