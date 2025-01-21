package App.AssociationMember;


import Data.JSONHandler;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import others.ResourceHandler;

import java.util.Optional;
import java.util.Stack;


public class LoginScreenController {
    @FXML
    private TextField identifiantLabel;

    @FXML
    private PasswordField passwordLabel;


    @FXML
    void handleLoginAction() {
        ResourceHandler resourceHandler = new ResourceHandler("src/main/resources/App/AssociationMember");
        JsonNode user=Member.login(identifiantLabel.getText(), passwordLabel.getText());
        if(user!=null){
            SessionManager s=SessionManager.INSTANCE;
            s.setUserData(user);
            System.out.println(s.getUserData().toString());
            Optional<FXMLLoader> loader = resourceHandler.getFXMLLoader("HomePage.fxml");
            if(loader.isPresent()){
                try {
                    StackPane pane  = loader.get().load();
                    identifiantLabel.getScene().setRoot(pane);

                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            }
        }

    }



}
