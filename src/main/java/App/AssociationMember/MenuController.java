package App.AssociationMember;

import com.fasterxml.jackson.databind.JsonNode;
import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import others.ResourceHandler;

import java.util.Optional;

public class MenuController {

    @FXML
    private JFXButton arbreRemarquableButton;

    @FXML
    private JFXButton cotisationButton;

    @FXML
    private JFXButton home;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton planificationButton;

    @FXML
    private JFXButton profilButton;

    private JsonNode user;

    public void setUser(JsonNode user){
        this.user=user;
    }

    @FXML
    void handleButtonClick() {
        ResourceHandler r=new ResourceHandler("src/main/resources/App/AssociationMember");
        profilButton.setOnAction((ActionEvent _) -> {
            Optional<FXMLLoader>loader = r.getFXMLLoader("ProfilPage.fxml");
            if (loader.isPresent()) {
                try {
                    StackPane pane = loader.get().load();
                    ProfilController controller = loader.get().getController();
                    controller.setUser(user);
                    profilButton.getScene().setRoot(pane);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        arbreRemarquableButton.setOnAction((ActionEvent event) -> {
            Optional<FXMLLoader>loader = r.getFXMLLoader("ArbreRemarquablePage.fxml");
            if (loader.isPresent()) {
                try {
                    StackPane pane = loader.get().load();
                    ArbreRemarquableController controller = loader.get().getController();
                    controller.setUser(user);
                    arbreRemarquableButton.getScene().setRoot(pane);
                } catch (Exception e) {
                    System.out.println(e.getMessage());
                }
            }
        });
        cotisationButton.setOnAction((ActionEvent event) -> {
            Optional<FXMLLoader>loader = r.getFXMLLoader("CotisationPage.fxml");
            if (loader.isPresent()) {
                try {
                    StackPane pane = loader.get().load();
                    CotisationPageController controller = loader.get().getController();
                    controller.setUser(user);
                    cotisationButton.getScene().setRoot(pane);
                } catch (Exception e) {
                    System.out.println(e.getMessage());                }
            }
        });
        planificationButton.setOnAction((ActionEvent event) -> {
            Optional<FXMLLoader>loader = r.getFXMLLoader("PlanificationPage.fxml");
            if (loader.isPresent()) {
                try {
                    StackPane pane = loader.get().load();
                    PlanificationController controller = loader.get().getController();
                    controller.setUser(user);
                    planificationButton.getScene().setRoot(pane);
                } catch (Exception e) {
                    System.out.println(e.getMessage());                }
            }
        });
        logoutButton.setOnAction((ActionEvent event) -> {
            System.out.println("Logout Button clicked");
            Optional<FXMLLoader>loader = r.getFXMLLoader("LoginScreen.fxml");
            if (loader.isPresent()) {
                try {
                    VBox pane = loader.get().load();
                    this.user=null;
                    logoutButton.getScene().setRoot(pane);
                } catch (Exception e) {
                    System.out.println(e.getMessage());                }
            }

        });
        home.setOnAction((ActionEvent event) -> {
            Optional<FXMLLoader>loader = r.getFXMLLoader("HomePage.fxml");
            if (loader.isPresent()) {
                try {
                    StackPane pane = loader.get().load();
                    HomePageController controller = loader.get().getController();
                    controller.setUser(user);
                    home.getScene().setRoot(pane);
                } catch (Exception e) {
                    System.out.println(e.getMessage());                }
            }
        });

    }



}
