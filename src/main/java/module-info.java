module Demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.opencsv;
    requires java.sql;


    opens Demo to javafx.fxml;
    exports Demo;
}