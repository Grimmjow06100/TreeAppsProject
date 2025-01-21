package App.AssociationMember;

import com.fasterxml.jackson.databind.JsonNode;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import others.ResourceHandler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

public class HomePageController {

    @FXML
    private JFXDrawer JFXDrawer;

    @FXML
    private JFXHamburger JFXHamburger;

    @FXML
    private VBox vboxMenu;

    @FXML
    private HBox topHbox;

    @FXML
    private ListView<String> listViewVisites;

    JsonNode user;

    public void setUser(JsonNode user){
        this.user=user;
        updateListView();
    }


    public void updateListView(){
        JsonNode node=user.get("visites");
        String date;
        String nom;
        String lieu;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        for(JsonNode visite:node){
            date = visite.get("date").asText();
            LocalDate visiteDate = LocalDate.parse(date, formatter);
            if (visiteDate.isBefore(LocalDate.now())) {
                System.out.println("Visite après la date d'aujourd'hui");
                continue;
            }

            date=visite.get("date").asText();
            nom=visite.get("tree").get("libelle_france").asText();
            lieu=visite.get("tree").get("lieu").asText();
            listViewVisites.getItems().add("visite le "+ date+" de "+nom+" à "+lieu);

        }

    }



    @FXML
    public void  initialize() {
        topHbox.setStyle("-fx-background-color: lightgray;");
        if(user!=null){
            System.out.println(user.get("nom").asText());
        }
        else{
            System.out.println("User is null");
        }
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
                System.out.println("Error de l'initialisation: " + e.getMessage());
            }
        }
    }
}
