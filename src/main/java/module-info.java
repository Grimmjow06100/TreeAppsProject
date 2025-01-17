module Demo {
    requires javafx.controls;
    requires javafx.fxml;


    opens Demo to javafx.fxml;
    exports Demo;
}