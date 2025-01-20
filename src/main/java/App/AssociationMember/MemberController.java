package App.AssociationMember;

import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MemberController {
    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;
    @FXML private Button loginButton;
    @FXML private Label errorLabel;

    @FXML
    public void initialize() {
        loginButton.setOnAction(event -> handleLogin());
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();
        Member.checkAuthentification(username, password);
    }
}
