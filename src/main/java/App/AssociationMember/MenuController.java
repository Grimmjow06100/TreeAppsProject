package App.AssociationMember;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import others.ResourceHandler;
import javafx.scene.control.Label;

import java.util.Objects;
import java.util.Optional;

public class MenuController {

    @FXML
    private JFXButton arbreListeButton;

    @FXML
    private JFXButton cotisationButton;

    @FXML
    private JFXButton votesButton;

    @FXML
    private JFXButton home;

    @FXML
    private Label userLabel;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton planificationButton;

    @FXML
    private JFXButton profilButton;

    private JsonNode user;

    public void setUser(JsonNode user){
        this.user=user;
        handleButtonClick();
    }
@FXML
public void initialize() {


}
    void handleButtonClick() {
        userLabel.setText(user.get("identifiant").asText());
        profilButton.setOnAction((ActionEvent _) -> {
            FXMLLoader loader =new FXMLLoader(getClass().getResource("/App/AssociationMember/ProfilPage.fxml"));
            try {
                StackPane pane = loader.load();
                ProfilController controller = loader.getController();
                controller.setUser(user);
                Scene scene = profilButton.getScene();
                scene.getStylesheets().clear();
                scene.getStylesheets().add(
                        Objects.requireNonNull(getClass().getResource("/App/AssociationMember/ProfilPage.css")).toExternalForm()
                );
                profilButton.getScene().setRoot(pane);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        
        });
        arbreListeButton.setOnAction((ActionEvent event) -> {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/ArbreListePage.fxml"));
            
            try {
                StackPane pane = loader.load();
                ArbreListeController controller = loader.getController();
                controller.setUser(user);
                Scene scene = profilButton.getScene();
                scene.getStylesheets().clear();
                scene.getStylesheets().add(
                        Objects.requireNonNull(getClass().getResource("/App/AssociationMember/ArbreListePage.css")).toExternalForm()
                );
                arbreListeButton.getScene().setRoot(pane);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            
        });
        cotisationButton.setOnAction((ActionEvent event) -> {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/CotisationPage.fxml"));
            
            try {
                StackPane pane = loader.load();
                CotisationPageController controller = loader.getController();
                controller.setUser(user);
                Scene scene = profilButton.getScene();
                scene.getStylesheets().clear();
                scene.getStylesheets().add(
                        Objects.requireNonNull(getClass().getResource("/App/AssociationMember/CotisationPage.css")).toExternalForm()
                );
                cotisationButton.getScene().setRoot(pane);
            } catch (Exception e) {
                System.out.println(e.getMessage());                }
            
        });
        planificationButton.setOnAction((ActionEvent event) -> {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/PlanificationPage.fxml"));
         
            try {
                JsonManager jsonManager = JsonManager.INSTANCE;
                Optional<JsonNode> rootNodeOption = jsonManager.getRootNode("Visites_JSON.json");
                StackPane pane = loader.load();
                PlanificationController controller = loader.getController();
                controller.setUser(user,rootNodeOption.get());
                Scene scene = profilButton.getScene();
                scene.getStylesheets().clear();
                scene.getStylesheets().add(
                        Objects.requireNonNull(getClass().getResource("/App/AssociationMember/PlanificationPage.css")).toExternalForm()
                );
                planificationButton.getScene().setRoot(pane);
            } catch (Exception e) {
                System.out.println(e.getMessage());                }
            
        });

        votesButton.setOnAction((ActionEvent event) -> {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/VotesPage.fxml"));

            try {
                StackPane pane = loader.load();
                VotesController controller = loader.getController();
                controller.setUser(user);
                Scene scene = profilButton.getScene();
                scene.getStylesheets().clear();
                scene.getStylesheets().add(
                        Objects.requireNonNull(getClass().getResource("/App/AssociationMember/VotesPage.css")).toExternalForm()
                );
                votesButton.getScene().setRoot(pane);
            } catch (Exception e) {
                System.out.println(e.getMessage());                }

        });
        logoutButton.setOnAction((ActionEvent event) -> {
             System.out.println("Logout Button clicked");
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/LoginScreen.fxml"));
        
            try {
                VBox pane = loader.load();
                this.user=null;
                logoutButton.getScene().setRoot(pane);
            } catch (Exception e) {
                System.out.println(e.getMessage());                }
           

        });
        home.setOnAction((ActionEvent event) -> {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/HomePage.fxml"));
           
            try {
                StackPane pane = loader.load();
                HomePageController controller = loader.getController();
                controller.setUser(user);
                home.getScene().setRoot(pane);
            } catch (Exception e) {
                System.out.println(e.getMessage());                }
            
        });

    }



}
