package App.AssociationManagement.Controller.gestionMembres;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import others.Message;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class VoirListeMembresController {

    @FXML
    private TextField nomTextField;
    @FXML
    private ListView<String> nomListView;
    @FXML
    private VBox detailsContainer;

    private ObservableList<String> memberList;
    private JsonNode selectedMember;
    private static final String JSON_FILE_NAME = "Members_JSON.json";
    private static final String JSON_ARCHIVE_FILE = "Archived_Members_JSON.json";

    @FXML
    public void initialize() {
        memberList = FXCollections.observableArrayList();

        Optional<JsonNode> memberRoot = JsonManager.getRootNode(JSON_FILE_NAME);
        memberRoot.ifPresent(members -> members.forEach(node -> {
            String identifiant = node.get("identifiant").asText();
            String nom = node.get("nom").asText();
            String prenom = node.get("prenom").asText();
            memberList.add("Identifiant: " + identifiant + " Nom: " + prenom + " " + nom);
        }));

        FilteredList<String> filteredList = new FilteredList<>(memberList, _ -> true);
        nomTextField.textProperty().addListener((_, _, newValue) -> {
            filteredList.setPredicate(member -> newValue == null || newValue.isEmpty() || member.toLowerCase().contains(newValue.toLowerCase()));
        });

        nomListView.setItems(filteredList);
        nomListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                afficherDetailsMembre();
            }
        });
    }

    private void afficherDetailsMembre() {
        detailsContainer.getChildren().clear();
        String selected = nomListView.getSelectionModel().getSelectedItem();
        if (selected == null) return;
        String identifiant = selected.split(" ")[1];
        Optional<JsonNode> membreOption = JsonManager.getNode(JSON_FILE_NAME, Map.entry("identifiant", identifiant));

        membreOption.ifPresent(membre -> {
            selectedMember = membre;
            detailsContainer.getChildren().add(new Label("Nom: " + membre.get("nom").asText()));
            detailsContainer.getChildren().add(new Label("Prénom: " + membre.get("prenom").asText()));
            detailsContainer.getChildren().add(new Label("Âge: " + membre.get("age").asText()));
            detailsContainer.getChildren().add(new Label("Adresse: " + membre.get("adresse").asText()));
            detailsContainer.getChildren().add(new Label("Date de naissance: " + membre.get("dateNaissance").asText()));
            detailsContainer.getChildren().add(new Label("Identifiant: " + membre.get("identifiant").asText()));
            detailsContainer.getChildren().add(new Label("Cotisation payée: " + (membre.get("cotisationPayee").asBoolean() ? "Oui" : "Non")));
        });
    }


    @FXML
    protected void onButtonRetourClick(ActionEvent event) {
        retournerPagePrecedente(event);
    }

    private void retournerPagePrecedente(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/gestionMembres/GestionMembres.fxml"));
            Parent membreView = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(membreView, 600, 400));
            stage.setTitle("Gestion des membres de l'association");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
