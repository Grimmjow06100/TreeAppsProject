package App.AssociationMember;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;


import java.io.IOException;
import java.util.List;
import java.util.Map;


public class ArbreRemarquableController {

    @FXML
    private JFXDrawer JFXDrawer;

    @FXML
    private JFXHamburger JFXHamburger;

    @FXML
    private ListView<String> listViewArbre;

    @FXML
    private ListView<String> listViewVotes;


    @FXML
    private VBox vboxMenu;

    @FXML
    private JFXButton voteButton;

    @FXML
    private ImageView logo;

    @FXML
    private HBox topHbox;


    private JsonNode user;

    public void setUser(JsonNode user){
        this.user=user;
        updateMenu();
        updateListeView();
        buttonAction();
    }

    public void updateListeView(){

        JsonManager jsonManager = JsonManager.INSTANCE;
        List<JsonNode> arbreList =jsonManager.getNodeList("Arbres_JSON.json",List.of(Map.entry("remarquable","OUI")));
        arbreList.forEach((JsonNode node)->{
            String nom = node.get("libelle_france").asText();
            String genre = node.get("genre").asText();
            String espece = node.get("espece").asText();
            String lieu = node.get("lieu").asText();
            listViewArbre.getItems().add("Nom: "+nom+" Genre: "+genre+" Espece: "+espece+" Lieu: "+lieu);
        });

        JsonNode voteList =user.get("nominations");
        voteList.forEach((JsonNode node)->{
            String nom = node.get("libelle_france").asText();
            String genre = node.get("genre").asText();
            String espece = node.get("espece").asText();
            String lieu = node.get("lieu").asText();
            listViewVotes.getItems().add("Nom: "+nom+" Genre: "+genre+" Espece: "+espece+" Lieu: "+lieu);
        });
    }


    void buttonAction() {
        voteButton.setOnAction((ActionEvent _) -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/TreeListView.fxml"));
                VBox treeListView = loader.load();
                TreeListViewController controller = loader.getController();
                controller.setUser(user);
                Stage stage = new Stage();
                stage.setTitle("Tree List");
                stage.setScene(new Scene(treeListView,700,400));
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    public void updateMenu(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/Menu.fxml"));
        logo.setImage(new Image("file:src/main/resources/App/AssociationMember/logo.png"));

        try {
            VBox box = loader.load();
            MenuController controller = loader.getController();
            controller.setUser(user);
            JFXDrawer.setSidePane(box);

            HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(JFXHamburger);
            burgerTask2.setRate(-1);
            JFXHamburger.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, (_) -> {
                System.out.println("Hamburger clicked");
                burgerTask2.setRate(burgerTask2.getRate() * -1);
                burgerTask2.play();
                if (JFXDrawer.isOpened()) {
                    JFXDrawer.close();
                    // ✅ Attendre 300ms avant de masquer vboxMenu
                    PauseTransition pause = new PauseTransition(Duration.millis(300));
                    pause.setOnFinished(event -> vboxMenu.setVisible(false));
                    pause.play();


                } else {
                    JFXDrawer.open();
                    vboxMenu.setVisible(true);
                }
            });
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }

    }

    @FXML
    public void  initialize() {
        System.out.println("HomePageController initialized");
        topHbox.setStyle("-fx-background-color: lightgray;");
    }

}
