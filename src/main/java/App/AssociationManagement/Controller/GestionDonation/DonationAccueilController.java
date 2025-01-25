package App.AssociationManagement.Controller.GestionDonation;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class DonationAccueilController {
    @FXML
    private TextField rechercheTextField;

    @FXML
    private ListView<String> donateursListView;

    private ObservableList<String> donateursList;

    @FXML
    protected void onButtonRetourClick(ActionEvent event) {
        try {
            // Charger la page d'accueil
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/PageAccueil/PageAccueil.fxml"));
            Parent accueilView = loader.load();

            // Obtenir la scène actuelle
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(new Scene(accueilView, 600, 400));
            stage.setTitle("Menu Principal");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        donateursList = FXCollections.observableArrayList();

        Optional<JsonNode> donationsRoot = JsonManager.getRootNode("Donations_JSON.json");
        if (donationsRoot.isPresent()) {
            ArrayNode donationsArray = (ArrayNode) donationsRoot.get();
            donationsArray.forEach((JsonNode node) -> {
                int id = node.get("id").asInt();
                String nature = node.get("nature").asText();
                int montant = node.get("montant").asInt();
                String date = node.get("date_don").asText();

                donateursList.add("ID: " + id + " | Nature: " + nature + " | Montant: " + montant + "€ | Date: " + date);
            });
        }

        FilteredList<String> filteredList = new FilteredList<>(donateursList, _ -> true);
        rechercheTextField.textProperty().addListener((_, _, newValue) -> {
            filteredList.setPredicate(donateur -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return donateur.toLowerCase().contains(lowerCaseFilter);
            });
        });

        donateursListView.setItems(filteredList);
    }
}
