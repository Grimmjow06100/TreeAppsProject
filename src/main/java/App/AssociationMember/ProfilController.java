package App.AssociationMember;

import com.fasterxml.jackson.databind.JsonNode;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import others.ResourceHandler;

import java.util.Optional;

public class ProfilController {

    @FXML
    private JFXDrawer JFXDrawer;

    @FXML
    private JFXHamburger JFXHamburger;

    @FXML
    private Label adresse;

    @FXML
    private Label dateDeNaissance;

    @FXML
    private Label dateInscription;

    @FXML
    private Label nom;

    @FXML
    private Label prenom;

    @FXML
    private VBox vboxMenu;

    @FXML
    private HBox topHbox;

    JsonNode user;

    
    public void setUser(JsonNode user){
        this.user=user;
        updateProfil();
    }

    public void updateProfil(){
        nom.setText(user.get("nom").asText());
        prenom.setText(user.get("prenom").asText());
        adresse.setText(user.get("adresse").asText());
        dateDeNaissance.setText(user.get("date_naissance").asText());
        dateInscription.setText(user.get("date_inscription").asText());
    }

    @FXML
    public void  initialize() {
        System.out.println("HomePageController initialized");
        topHbox.setStyle("-fx-background-color: lightgray;");
        ResourceHandler resourceHandler = new ResourceHandler("src/main/resources/App/AssociationMember");
        Optional<FXMLLoader> loader = resourceHandler.getFXMLLoader("Menu.fxml");
        if(loader.isPresent()){
            try {
                VBox box = loader.get().load();
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

    }

}
