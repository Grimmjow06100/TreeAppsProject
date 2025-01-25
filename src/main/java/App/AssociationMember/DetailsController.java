package App.AssociationMember;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import others.Message;
import others.Tree;

import java.util.Map;
import java.util.Optional;

public class DetailsController {

    @FXML
    private TextFlow description;

    @FXML
    private JFXButton votesButton;

    private JsonNode user;
    private JsonNode arbre;

    public void setUser(JsonNode user, JsonNode arbre){
        this.user=user;
        this.arbre=arbre;
        update();
    }


    public void update(){
        Text text=new Text(Tree.arbreNodeToString(arbre));
        description.getChildren().add(text);
        votesButton.setOnAction((event) -> {
            JsonNode nomination = arbre.deepCopy();
            int idInt = arbre.get("idBase").asInt();
            int votes=user.get("nominations").size();
            ((ObjectNode) nomination).retain("idBase","libelle_france");
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
        });

    }


}
