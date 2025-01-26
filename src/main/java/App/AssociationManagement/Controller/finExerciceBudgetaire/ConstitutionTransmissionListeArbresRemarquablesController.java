package App.AssociationManagement.Controller.finExerciceBudgetaire;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConstitutionTransmissionListeArbresRemarquablesController {

    @FXML
    private ListView<String> listeArbresContainter;

    @FXML
    private Button GreenServiceSubmit;

    private static final String FILENAME = "Arbres_JSON.json";
    private static final String MEMBERS_FILENAME = "Members_JSON.json";
    private static final String REMARKABLE_JSON = "ArbreRemarquableChoisi.json";

    @FXML
    protected void initialize() {
        loadClassementProvisoire();
        GreenServiceSubmit.setOnAction(this::onGreenServiceSubmit);
    }

    @FXML
    protected void onButtonRetourClick(ActionEvent event) {
        try {
            // Charger la vue précédente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/finExerciceBudgetaire/AccueilFinExerciceBudgetaire.fxml"));
            Parent membreView = loader.load();

            // Obtenir la scène actuelle et appliquer la nouvelle
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(membreView, 600, 400));
            stage.setTitle("Exercice Budgétaire");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadClassementProvisoire() {
        List<JsonNode> membres = JsonManager.getNodeWithoutFilter(MEMBERS_FILENAME);
        Map<String, Integer> arbreVotes = new HashMap<>();

        for (JsonNode membre : membres) {
            if (membre.has("nominations")) {
                for (JsonNode nomination : membre.get("nominations")) {
                    if (nomination.has("idBase")) {
                        String idArbre = nomination.get("idBase").asText();
                        arbreVotes.put(idArbre, arbreVotes.getOrDefault(idArbre, 0) + 1);
                    }
                }
            }
        }

        List<JsonNode> arbres = JsonManager.getNodeWithoutFilter(FILENAME);
        List<JsonNode> arbresClassement = arbres.stream()
                .filter(arbre -> arbre.has("idBase") && arbreVotes.containsKey(arbre.get("idBase").asText()))
                .sorted((a1, a2) -> {
                    int votesDiff = arbreVotes.getOrDefault(a2.get("idBase").asText(), 0) - arbreVotes.getOrDefault(a1.get("idBase").asText(), 0);
                    if (votesDiff == 0) {
                        int circonferenceDiff = a2.has("circonference") && a1.has("circonference") ? a2.get("circonference").asInt() - a1.get("circonference").asInt() : 0;
                        if (circonferenceDiff == 0) {
                            return a2.has("hauteur") && a1.has("hauteur") ? a2.get("hauteur").asInt() - a1.get("hauteur").asInt() : 0;
                        }
                        return circonferenceDiff;
                    }
                    return votesDiff;
                })
                .limit(5)
                .collect(Collectors.toList());

        listeArbresContainter.getItems().clear();
        for (JsonNode arbre : arbresClassement) {
            String idBase = arbre.has("idBase") ? arbre.get("idBase").asText() : "Inconnu";
            String genre = arbre.has("genre") ? arbre.get("genre").asText() : "Inconnu";
            String libelleFrance = arbre.has("libelle_france") ? arbre.get("libelle_france").asText() : "Inconnu";
            String espece = arbre.has("espece") ? arbre.get("espece").asText() : "Inconnu";
            listeArbresContainter.getItems().add(idBase + " - " + genre + " - " + libelleFrance + " - " + espece);
        }
    }
    private void onGreenServiceSubmit(ActionEvent event) {
        String selectedItem = listeArbresContainter.getSelectionModel().getSelectedItem();

        if (selectedItem == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING, "Veuillez sélectionner un arbre avant de soumettre.", javafx.scene.control.ButtonType.OK);
            alert.show();
            return;
        }

        String[] parts = selectedItem.split(" - ");
        if (parts.length == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Impossible de soumettre la demande. Format d'arbre invalide.", javafx.scene.control.ButtonType.OK);
            alert.show();
            return;
        }

        String idBase = parts[0];
        List<JsonNode> arbres = JsonManager.getNodeWithoutFilter(FILENAME);

        JsonNode selectedTree = arbres.stream()
                .filter(arbre -> arbre.has("idBase") && arbre.get("idBase").asText().equals(idBase))
                .findFirst()
                .orElse(null);

        if (selectedTree == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Arbre introuvable dans le fichier JSON.", javafx.scene.control.ButtonType.OK);
            alert.show();
            return;
        }

        Map<String, Object> changement = new HashMap<>();
        changement.put("TypeChangement", "Proposition arbre remarquable");
        changement.put("Arbre", selectedTree);

        // Ajouter idBase à la racine pour s'assurer de l'unicité
        if (selectedTree.has("idBase")) {
            changement.put("idBase", selectedTree.get("idBase").asText());
        } else {
            System.out.println("❌ L'arbre sélectionné ne possède pas d'idBase.");
            return;
        }

        // Log pour vérifier l'objet avant insertion
        System.out.println("Objet envoyé à insertInJson : " + changement);

        // Insérer dans le JSON
        JsonManager.insertInJson("ArbreRemarquableChoisi.json", List.of(changement), "idBase");

        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Demande soumise avec succès au service des espaces verts !", javafx.scene.control.ButtonType.OK);
        alert.show();
    }
}