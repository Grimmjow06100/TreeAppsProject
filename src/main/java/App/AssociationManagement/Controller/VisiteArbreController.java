package App.AssociationManagement.Controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class VisiteArbreController {
    Stage primaryStage;
    PageAccueilController pageAccueilController;

    @FXML
    Button retourButtonVisite;

    //Constructeur
    public VisiteArbreController() {
        this.primaryStage = new Stage();
        this.pageAccueilController = new PageAccueilController(primaryStage);
    }

    public VisiteArbreController(PageAccueilController pageAccueilController, Stage stage) {
        this.pageAccueilController = pageAccueilController;
        this.primaryStage = stage;
    }

    @FXML
    private void initialize() {
        retourButtonVisite.setOnAction(event -> retourPageAccueil());
    }


    //Retour sur le menu principal
    public void retourPageAccueil() {
        pageAccueilController.show();
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/VisiteArbre.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

