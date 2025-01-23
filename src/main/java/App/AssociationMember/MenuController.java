package App.AssociationMember;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import others.ResourceHandler;
import javafx.scene.control.Label;
import java.util.Optional;

public class MenuController {

    @FXML
    private JFXButton arbreRemarquableButton;

    @FXML
    private JFXButton cotisationButton;

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
    
    void handleButtonClick() {
        userLabel.setText(user.get("identifiant").asText());
        profilButton.setOnAction((ActionEvent _) -> {
            FXMLLoader loader =new FXMLLoader(getClass().getResource("/App/AssociationMember/ProfilPage.fxml"));
            try {
                StackPane pane = loader.load();
                ProfilController controller = loader.getController();
                controller.setUser(user);
                profilButton.getScene().setRoot(pane);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        
        });
        arbreRemarquableButton.setOnAction((ActionEvent event) -> {
             FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/ArbreRemarquablePage.fxml"));
            
            try {
                StackPane pane = loader.load();
                ArbreRemarquableController controller = loader.getController();
                controller.setUser(user);
                arbreRemarquableButton.getScene().setRoot(pane);
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
                planificationButton.getScene().setRoot(pane);
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
