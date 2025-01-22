package App.AssociationMember;

import com.fasterxml.jackson.databind.JsonNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import others.ResourceHandler;

import java.util.List;
import java.util.Map;
import java.util.Optional;


public class LoginScreenController {


    @FXML
    private TextField identifiantLabel;

    @FXML
    private PasswordField passwordLabel;


    @FXML
    void handleLoginAction() {
        ResourceHandler resourceHandler = new ResourceHandler("src/main/resources/App/AssociationMember");
        Optional<JsonNode> user=Member.login("johnDoe","password123");
        if(true){
            SessionManager s=SessionManager.INSTANCE;
            s.setUserData(user.get());
            System.out.println(s.getUserData().toString());
            Optional<FXMLLoader> loader = resourceHandler.getFXMLLoader("HomePage.fxml");
            if(loader.isPresent()){
                try {
                    FXMLLoader fxmlLoader = loader.get();
                    StackPane pane = fxmlLoader.load();
                    HomePageController controller = fxmlLoader.getController();
                    controller.setUser(user.get());
                    identifiantLabel.getScene().setRoot(pane);

                } catch (Exception e) {
                    System.out.println("Error : " + e.getMessage());
                }
            }
        }

    }



}
