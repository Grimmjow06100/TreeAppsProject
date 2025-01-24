package App.AssociationManagement.Controller.gestionMembres;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
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
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class VoirListeMembresController {

    @FXML
    protected void onButtonRetourClick(ActionEvent event) {
        try {
            // Charger la nouvelle vue
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/gestionMembres/GestionMembres.fxml"));
            Parent membreView = loader.load();

            // Obtenir la scène actuelle à partir de l'événement
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();

            // Définir la nouvelle scène
            stage.setScene(new Scene(membreView, 600, 400));
            //stage.getScene().getStylesheets().add(Objects.requireNonNull(getClass().getResource("/App/AssociationManagement/styles.css")).toExternalForm());
            stage.setTitle("Gestion des membres de l'association");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private TextField nomMembreTextField;

    @FXML
    private ListView<String> listeMembresListView;

    private ObservableList<String> memberList;

    private JsonNode user;

    public void setUser(JsonNode user) {
        this.user = user;
        updateChoice();
    }

    public void updateChoice() {
        listeMembresListView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                String selected = listeMembresListView.getSelectionModel().getSelectedItem();
                String identifiant = selected.split("Identifiant: ")[1].split(" Nom: ")[0].trim();
                Optional<JsonNode> membreOption = JsonManager.getNode("Members_JSON.json", Map.entry("identifiant", identifiant));

                if (membreOption.isPresent()) {
                    JsonNode membre = membreOption.get();
                    showMemberDetails(membre);
                }
            }
        });
    }

    private void showMemberDetails(JsonNode membre) {
        String details = "Nom: " + membre.get("nom").asText() +
                "\nPrénom: " + membre.get("prenom").asText() +
                "\nÂge: " + membre.get("age").asText() +
                "\nAdresse: " + membre.get("adresse").asText() +
                "\nDate de naissance: " + membre.get("dateNaissance").asText() +
                "\nCotisation payée: " + (membre.get("cotisationPayee").asBoolean() ? "Oui" : "Non");

        Message.showInformation("Détails du membre", details);
    }

    @FXML
    public void initialize() {
        JsonManager jsonManager = JsonManager.INSTANCE;
        memberList = FXCollections.observableArrayList();

        List<JsonNode> membreList = jsonManager.getNodeList("Members_JSON.json", List.of());
        membreList.forEach((JsonNode node) -> {
            String identifiant = node.get("identifiant").asText();
            String nom = node.get("nom").asText();
            String prenom = node.get("prenom").asText();
            String age = node.get("age").asText();

            memberList.add("Identifiant: " + identifiant + " Nom: " + prenom + " " + nom + " Âge: " + age);
        });

        FilteredList<String> filteredList = new FilteredList<>(memberList, _ -> true);
        nomMembreTextField.textProperty().addListener((_, _, newValue) -> {
            filteredList.setPredicate(member -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return member.toLowerCase().contains(lowerCaseFilter);
            });
        });

        listeMembresListView.setItems(filteredList);
    }
}
