package App.AssociationManagement.Controller.gestionVisiteArbre;

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
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import others.Message;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

public class VoirListeVisitesController {

    @FXML
    private TextField nomArbreTextField;

    @FXML
    private ListView<String> listeVisitesListView;

    private ObservableList<String> visiteList;

    @FXML
    protected void onButtonRetourClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/PageAccueil/PageAccueil.fxml"));
            Parent visiteView = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(visiteView, 600, 400));
            stage.setTitle("Gestion des visites d’arbres");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        visiteList = FXCollections.observableArrayList();

        Optional<JsonNode> visiteRoot = JsonManager.getRootNode("Visites_JSON.json");
        if (visiteRoot.isPresent()) {
            ArrayNode visiteArray = (ArrayNode) visiteRoot.get();
            visiteArray.forEach((JsonNode node) -> {
                String id = node.has("id") ? node.get("id").asText() : "N/A";
                String date = node.has("date") ? node.get("date").asText() : "Non spécifiée";
                String cout = node.has("cout") ? node.get("cout").asText() : "0";

                // Vérifie si "tree" existe et n'est pas null
                String arbre = "Inconnu";
                String lieu = "Inconnu";
                if (node.has("tree") && !node.get("tree").isNull()) {
                    JsonNode treeNode = node.get("tree");
                    arbre = treeNode.has("libelle_france") ? treeNode.get("libelle_france").asText() : "Inconnu";
                    lieu = treeNode.has("lieu") ? treeNode.get("lieu").asText() : "Inconnu";
                } else {
                    System.err.println("Erreur : Une visite sans 'tree' a été détectée dans le JSON.");
                }

                visiteList.add("ID: " + id + " | Arbre: " + arbre + " | Lieu: " + lieu + " | Date: " + date + " | Coût: " + cout + "€");
            });
        }

        FilteredList<String> filteredList = new FilteredList<>(visiteList, _ -> true);
        nomArbreTextField.textProperty().addListener((_, _, newValue) -> {
            filteredList.setPredicate(visite -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                return visite.toLowerCase().contains(newValue.toLowerCase());
            });
        });

        listeVisitesListView.setItems(filteredList);

        listeVisitesListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selected = listeVisitesListView.getSelectionModel().getSelectedItem();
                String id = selected.split("ID: ")[1].split(" \\|")[0].trim();
                Optional<JsonNode> visiteOption = JsonManager.getNode("Visites_JSON.json", Map.entry("id", id));

                if (visiteOption.isPresent()) {
                    JsonNode visite = visiteOption.get();
                    showVisiteDetails(visite);
                }
            }
        });
    }

    private void showVisiteDetails(JsonNode visite) {
        String details = "Arbre: " + visite.get("tree").get("libelle_france").asText() +
                "\nLieu: " + visite.get("tree").get("lieu").asText() +
                "\nDate: " + visite.get("date").asText() +
                "\nCoût: " + visite.get("cout").asText() + "€" +
                "\nCompte rendu: " + (visite.get("compte_rendu").asText().isEmpty() ? "Aucun" : visite.get("compte_rendu").asText());

        Message.showInformation("Détails de la visite", details);
    }

    @FXML
    protected void onButtonAjouterVisiteClick(ActionEvent event) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/gestionVisiteArbre/AjouterVisite.fxml"));
            Parent ajouterVisiteView = loader.load();

            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(ajouterVisiteView, 600, 400));
            stage.setTitle("Ajouter une visite d'arbre");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}