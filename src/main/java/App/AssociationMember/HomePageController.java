package App.AssociationMember;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class HomePageController {

    @FXML
    private JFXDrawer JFXDrawer;

    @FXML
    private JFXHamburger JFXHamburger;

    @FXML
    private VBox vboxMenu;

    @FXML
    private ImageView logo;

    @FXML
    private ListView<String> listViewVisites;

    @FXML
    private ListView<String> listViewNotif;


    JsonNode user;

    public void setUser(JsonNode user){
        this.user=user;
        updateListView();
        updateMenu();
    }

    public void updateMenu(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/Menu.fxml"));
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
            System.out.println("Error de l'initialisation: " + e.getMessage());
        }


    }

    public void loadNotifications() {
        String fileName = "GreenSpaceNotif.json"; // Chemin du fichier JSON
        List<String> notificationList = new ArrayList<>();

        try {
            // Lire le contenu du fichier JSON
            List<JsonNode> notifList = JsonManager.getNodeWithoutFilter(fileName);

            // Parcourir la liste à l'envers pour afficher les notifications les plus récentes en premier
            for (int i = notifList.size() - 1; i >= 0; i--) {
                JsonNode notif = notifList.get(i);

                // Extraire les informations nécessaires
                String typeChangement = notif.has("TypeChangement") ? notif.get("TypeChangement").asText() : "Type inconnu";
                JsonNode arbre = notif.get("Arbre");
                String idArbre = arbre.has("id") ? arbre.get("id").asText() : "ID inconnu";
                String nomArbre = arbre.has("nom") ? arbre.get("nom").asText() : "Nom inconnu";

                // Construire la chaîne de caractère pour chaque notification
                String notifText = String.format("Type: %s | ID: %s | Nom: %s", typeChangement, idArbre, nomArbre);

                // Ajouter la notification à la liste
                notificationList.add(notifText);
            }

            // Ajouter les notifications à la ListView
            listViewNotif.getItems().setAll(notificationList);

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la lecture des notifications : " + e.getMessage());
        }
    }


    public void updateListView(){
        JsonNode node=user.get("visites");

        for(JsonNode n:node){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            String date = n.get("date").asText();
            LocalDate visiteDate = LocalDate.parse(date, formatter);
            if (visiteDate.isBefore(LocalDate.now())) {
                return;
            }
            String nom=n.get("tree").get("libelle_france").asText();
            String lieu=n.get("tree").get("lieu").asText();
            listViewVisites.getItems().add("visite le "+ date+" de "+nom+" à "+lieu);
        }


    }




    @FXML
    public void  initialize() {
        logo.setImage(new Image("file:src/main/resources/App/AssociationMember/logo.png"));
        loadNotifications();
    }
}
