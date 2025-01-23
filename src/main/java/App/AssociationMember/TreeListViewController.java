

package App.AssociationMember;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import others.Message;

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
            System.out.println(idInt);
            Optional<JsonNode> arbreOption = JsonManager.getNode("Arbres_JSON.json", Map.entry("idBase", idInt));
            int votes = user.get("nominations").size();
            if (arbreOption.isPresent()) {
                JsonNode arbre = arbreOption.get();
                JsonNode nomination = arbre.deepCopy();
                ((ObjectNode) nomination).retain("idBase", "genre", "espece", "libelle_france", "lieu");
                ArrayNode votesArray = (ArrayNode) user.get("nominations");
                boolean canVote = true;
                for(JsonNode vote : votesArray){
                    if(vote.get("idBase").asInt() == idInt){
                        Message.showInformation("Erreur", "Vous avez déjà voté pour cet arbre");
                        canVote = false;
                        break;
                    }
                }
                if(canVote) {
                    Optional<ButtonType> result = Message.showConfirmation("Confirmation", "Voulez-vous vraiment voter pour cet arbre?").showAndWait();
                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        // Check if there are already votes
                        if (votes >= 5) {
                            // Remove the oldest vote
                            votesArray.remove(0);
                        }
                        votesArray.add(nomination);
                    }
                    JsonManager.updateJsonObject("Members_JSON.json", Map.entry("identifiant", user.get("identifiant").asText()), Map.entry("nominations", votesArray));
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
            treeList.add(String.format(
                    "🌲 ID: %s - 📛 Nom: %s - 🌿 Genre: %s - 🌳 Espèce: %s - 📍 Lieu: %s",
                    id, nom, genre, espece, lieu));

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