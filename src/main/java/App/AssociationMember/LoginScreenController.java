package App.AssociationMember;


import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;


public class LoginScreenController {
    @FXML
    private TextField identifiantLabel;

    @FXML
    private PasswordField passwordLabel;


    @FXML
    void loginAction() {
        if(Member.checkAuthentification(identifiantLabel.getText(), passwordLabel.getText())){
            System.out.println("Authentification réussie");
        }

    }



}
