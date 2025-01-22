package App.GreenSpaceService;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class NotifController {
    public void OnButtonReturnClick3(ActionEvent actionEvent) {
        try {
            // Chemin fixe pour le fichier FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/GreenServiceSpace/accueil-view.fxml"));
            Parent secondView = loader.load();

            // Obtenir le Stage actuel
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene().getWindow();

            // Remplacer la scène actuelle par celle de la deuxième vue
            stage.setScene(new Scene(secondView, 800, 600));
            stage.getScene().getStylesheets().add(
                    Objects.requireNonNull(getClass().getResource("/App/GreenServiceSpace/styles.css")).toExternalForm()
            );
            stage.setTitle("Gestion des espaces verts");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace(); // Log de l'exception pour diagnostiquer les erreurs
        }
    }
}
