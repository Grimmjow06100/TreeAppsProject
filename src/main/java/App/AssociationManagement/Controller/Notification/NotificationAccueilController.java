package App.AssociationManagement.Controller.Notification;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import java.io.IOException;

public class NotificationAccueilController {

    @FXML
    private Button retourButton; // Bouton lié à FXML avec fx:id="retourButton"

    @FXML
    protected void onRetourButtonClick(ActionEvent event) {
        try {
            // Charger la page d'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/PageAccueil/PageAccueil.fxml"));
            Parent accueilView = loader.load();

            // Obtenir le stage actuel et charger la nouvelle scène
            Stage stage = (Stage) retourButton.getScene().getWindow();
            stage.setScene(new Scene(accueilView, 600, 400));
            stage.setTitle("Page d'Accueil");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}