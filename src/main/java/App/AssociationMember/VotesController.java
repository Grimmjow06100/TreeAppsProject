package App.AssociationMember;

import com.fasterxml.jackson.databind.JsonNode;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class VotesController {

    @FXML
    private JFXDrawer JFXDrawer;

    @FXML
    private JFXHamburger JFXHamburger;

    @FXML
    private ListView<String> listView;

    @FXML
    private ImageView logo;

    @FXML
    private VBox vboxMenu;

    private JsonNode user;

    public void setUser(JsonNode user){
        this.user=user;
        updateMenu();
        updateListView();
    }

    @FXML
    public void initialize() {
        logo.setImage(new Image("file:src/main/resources/App/AssociationMember/logo.png"));

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

    public void updateListView(){
        JsonNode voteList =user.get("nominations");
        voteList.forEach((JsonNode node)->{
            String id = node.get("idBase").asText();
            String nom = node.get("libelle_france").asText();
            listView.getItems().add( String.format("🌳 ID %s - %s",id, nom));
        });
    }

}
