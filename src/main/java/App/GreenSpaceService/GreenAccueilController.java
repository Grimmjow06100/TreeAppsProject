package App.GreenSpaceService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;


public class GreenAccueilController {


    @FXML
    protected void onButtonAssoClick(ActionEvent event){
        try {
            // Charger la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App.GreenSpace/assoc-list-view.fxml"));
            Parent arbreView = loader.load();

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(new Scene(arbreView,800,600));
            stage.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/App.GreenSpace/styles.css")).toExternalForm());
            stage.setTitle("Gestion des associations");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onButtonArbreClick(ActionEvent event) {
        try {
            // Charger la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App.GreenSpace/three-list-view.fxml"));
            Parent arbreView = loader.load();

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(new Scene(arbreView,800,600));
            stage.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/App.GreenSpace/styles.css")).toExternalForm());
            stage.setTitle("Gestion des associations");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    protected void onButtonNotifClick(ActionEvent event){
        try {
            // Charger la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App.GreenSpace/notif-view.fxml"));
            Parent arbreView = loader.load();

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(new Scene(arbreView,800,600));
            stage.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/App.GreenSpace/styles.css")).toExternalForm());
            stage.setTitle("Gestion des associations");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}