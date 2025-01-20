package App.AssociationMember;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class LoginScreenController {
    @FXML
    private TextField identifiantLabel;

    @FXML
    private Button loginButton;

    @FXML
    private Label loginLabel;

    @FXML
    private VBox logo;

    @FXML
    private ImageView logoImageView;

    @FXML
    private PasswordField passwordLabel;


    @FXML
    void loginAction() {
        if(Member.checkAuthentification(identifiantLabel.getText(), passwordLabel.getText())){
            System.out.println("Authentification réussie");
        }

    }



}
