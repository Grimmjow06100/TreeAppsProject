package App.AssociationManagement.Controller.GestionDonation;

import Data.JsonManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.stage.Stage;
import App.AssociationManagement.Controller.GestionDonation.DemandeSubventionController;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;

public class AjouterDonateurController {

    @FXML
    private ComboBox<String> natureComboBox;
    @FXML
    private DatePicker dateDonPicker;

    private static final String JSON_FILE = "Donations.json";

    @FXML
    public void initialize() {
        natureComboBox.getItems().addAll("Services Municipaux", "Entreprise", "Association", "Individu");
    }

    @FXML
    protected void onButtonSuivantClick(ActionEvent event) {
        String nature = natureComboBox.getValue();
        LocalDate date = dateDonPicker.getValue();

        if (nature == null || date == null) {
            System.out.println("Tous les champs doivent être remplis !");
            return;
        }

        try {
            // Génération d'un nouvel ID basé sur le dernier ID existant
            int newId = JsonManager.getLastId(JSON_FILE) + 1;

            // Création du donateur sans montant
            ObjectMapper objectMapper = new ObjectMapper();
            ObjectNode donateurNode = objectMapper.createObjectNode();
            donateurNode.put("id", newId);
            donateurNode.put("nature", nature);
            donateurNode.put("montant", 0); // Montant non défini
            donateurNode.put("date_don", date.toString());

            // Insérer dans le fichier JSON
            JsonManager.insertInJson(JSON_FILE, Collections.singletonList(donateurNode));

            // Passer à la page suivante
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/Donation/DemandeSubvention.fxml"));
            Parent subventionView = loader.load();

            // Passer l'ID au contrôleur suivant
            DemandeSubventionController controller = loader.getController();
            controller.setDonateurId(newId);

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(subventionView, 600, 400));
            stage.setTitle("Demande de Subvention");
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    protected void onButtonRetourClick(ActionEvent event) {
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
}
