package App.AssociationMember;

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
    private JFXButton homeButton;

    @FXML
    private JFXButton logoutButton;

    @FXML
    private JFXButton planificationButton;

    @FXML
    private JFXButton profilButton;

    @FXML
    void handleButtonClick() {
        ResourceHandler r=new ResourceHandler("src/main/resources/App/AssociationMember");
        profilButton.setOnAction((ActionEvent _) -> {
            Optional<FXMLLoader>loader = r.getFXMLLoader("ProfilPage.fxml");
            if (loader.isPresent()) {
                try {
                    StackPane pane = loader.get().load();
                    profilButton.getScene().setRoot(pane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        arbreRemarquableButton.setOnAction((ActionEvent event) -> {
            Optional<FXMLLoader>loader = r.getFXMLLoader("ArbreRemarquablePage.fxml");
            if (loader.isPresent()) {
                try {
                    StackPane pane = loader.get().load();
                    arbreRemarquableButton.getScene().setRoot(pane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        cotisationButton.setOnAction((ActionEvent event) -> {
            Optional<FXMLLoader>loader = r.getFXMLLoader("Cotisation.fxml");
            if (loader.isPresent()) {
                try {
                    StackPane pane = loader.get().load();
                    cotisationButton.getScene().setRoot(pane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        planificationButton.setOnAction((ActionEvent event) -> {
            Optional<FXMLLoader>loader = r.getFXMLLoader("PlanificationPage.fxml");
            if (loader.isPresent()) {
                try {
                    StackPane pane = loader.get().load();
                    planificationButton.getScene().setRoot(pane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        logoutButton.setOnAction((ActionEvent event) -> {
            Optional<FXMLLoader>loader = r.getFXMLLoader("LoginScreen.fxml");
            if (loader.isPresent()) {
                try {
                    VBox pane = loader.get().load();
                    logoutButton.getScene().setRoot(pane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        });
        homeButton.setOnAction((ActionEvent event) -> {
            Optional<FXMLLoader>loader = r.getFXMLLoader("HomePage.fxml");
            if (loader.isPresent()) {
                try {
                    StackPane pane = loader.get().load();
                    homeButton.getScene().setRoot(pane);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }



}
