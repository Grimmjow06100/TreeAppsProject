

package App.AssociationMember;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class TreeListViewController {

    @FXML
    private TextField searchBar;

    @FXML
    private ListView<String> treeListView;

    private ObservableList<String> treeList;

    private JsonNode user;

    public void setUser(JsonNode user){
        this.user=user;
        updateChoice();
    }

    public void updateChoice() {
    treeListView.setOnMouseClicked((event) -> {
        if (event.getClickCount() == 2) {
            String selected = treeListView.getSelectionModel().getSelectedItem();
            String id = selected.split("idBase : ")[1].split("Nom: ")[0];
            int idInt = Integer.parseInt(id.trim());
            JsonManager jsonManager = JsonManager.INSTANCE;
            Optional<JsonNode> arbreOption = jsonManager.getNode("Arbres_JSON.json", List.of(Map.entry("idBase", idInt)));
            int votes = user.get("nominations").size();
            if (arbreOption.isPresent()) {
                JsonNode arbre = arbreOption.get();
                // Show confirmation dialog
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation de vote");
                alert.setHeaderText(null);
                alert.setContentText("Voulez-vous vraiment voter pour cet arbre?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.isPresent() && result.get() == ButtonType.OK) {
                    JsonNode nomination = arbre.deepCopy();
                    ((ObjectNode) nomination).retain("idBase", "genre", "espece", "libelle_france", "lieu");

                    // Check if there are already votes
                    if (votes >= 5) {
                        // Remove the oldest vote
                        ((ObjectNode) user).withArray("nominations").remove(0);
                    }

                    // Add the new vote
                    ((ObjectNode) user).withArray("nominations").add(nomination);
                }
            }
        }
    });
}

    @FXML
    public void initialize() {
        JsonManager jsonManager = JsonManager.INSTANCE;
        treeList = FXCollections.observableArrayList();
        List<JsonNode> arbreList = jsonManager.getNodeList("Arbres_JSON.json", List.of(Map.entry("remarquable", "NON")));
        arbreList.forEach((JsonNode node) -> {
            String id = node.get("idBase").asText();
            String nom = node.get("libelle_france").asText();
            String genre = node.get("genre").asText();
            String espece = node.get("espece").asText();
            String lieu = node.get("lieu").asText();
            treeList.add("idBase : "+id+" Nom: " + nom + " Genre: " + genre + " Espece: " + espece + " Lieu: " + lieu);
        });

        FilteredList<String> filteredList = new FilteredList<>(treeList, _ -> true);
        searchBar.textProperty().addListener((_, _, newValue) -> {
            filteredList.setPredicate(tree -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return tree.toLowerCase().contains(lowerCaseFilter);
            });
        });

        treeListView.setItems(filteredList);
    }
}