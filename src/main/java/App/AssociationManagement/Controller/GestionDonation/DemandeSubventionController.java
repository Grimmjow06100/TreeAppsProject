package App.AssociationManagement.Controller.GestionDonation;


import Data.JsonManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.Map;

public class DemandeSubventionController {

    @FXML
    private TextField montantTextField;

    private int donateurId;

    public void setDonateurId(int id) {
        this.donateurId = id;
    }

    @FXML
    private void onButtonValiderClick(ActionEvent event) {
        String montantStr = montantTextField.getText(); // Récupérer la valeur du champ texte
        if (montantStr.isEmpty()) {
            System.out.println("Veuillez entrer un montant !");
            return;
        }

        int montant = Integer.parseInt(montantStr);
        System.out.println("Montant saisi : " + montant);

        // Vérifie que l'ID du donateur est bien défini
        if (donateurId == -1) {
            System.out.println("⚠️ ID du donateur non défini !");
            return;
        }

        // Mise à jour du montant dans le fichier JSON
        JsonManager.updateJsonField("Donations.json", "id",String.valueOf(donateurId) , "montant", String.valueOf(montant));
        System.out.println("Montant mis à jour pour le donateur ID " + donateurId);

        // Retourner à la page d'accueil des donations
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/Donation/DonationAccueil.fxml"));
            Parent accueilView = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(accueilView, 600, 400));
            stage.setTitle("Gestion des Dons");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void onButtonRetourClick(ActionEvent event) {
        try {
            // Charger la page d'accueil des dons
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/Donation/DonationAccueil.fxml"));
            Parent accueilView = loader.load();

            // Obtenir la scène actuelle
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(new Scene(accueilView, 600, 400));
            stage.setTitle("Gestion des Dons");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}