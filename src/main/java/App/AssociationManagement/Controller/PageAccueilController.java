package App.AssociationManagement.Controller;

import App.AssociationManagement.Controller.classificationArbres.ClassificationArbresRemarquablesController;
import App.AssociationManagement.Controller.financesAssociation.FinancesAssociationController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PageAccueilController {
    Stage primaryStage;
    FinancesAssociationController financesAssociationController;
    ClassificationArbresRemarquablesController classificationArbresRemarquablesController;
    VisiteArbreController visiteArbreController;

    @FXML
    Button financesButton;

    @FXML
    Button votesArbresButton;

    @FXML
    Button visitesArbresButton;

    //Constructeur
    public PageAccueilController() {
        this.primaryStage = new Stage();
        this.financesAssociationController = new FinancesAssociationController(this, primaryStage);
        this.classificationArbresRemarquablesController = new ClassificationArbresRemarquablesController(this, primaryStage);
        this.visiteArbreController = new VisiteArbreController(this, primaryStage);
    }

    public PageAccueilController(Stage stage) {
        this.primaryStage = stage;
        this.financesAssociationController = new FinancesAssociationController(this, primaryStage);
        this.classificationArbresRemarquablesController = new ClassificationArbresRemarquablesController(this, primaryStage);
        this.visiteArbreController = new VisiteArbreController(this, primaryStage);
    }

    public void financesAssociation() {
        financesAssociationController.show();
    }

    public void classementArbres() {
        classificationArbresRemarquablesController.show();
    }

    public void visiteArbres() {
        visiteArbreController.show();
    }

    @FXML
    public void initialize() {
        financesButton.setOnAction(event -> financesAssociation());
        votesArbresButton.setOnAction(event -> classementArbres());
        visitesArbresButton.setOnAction(event -> visiteArbres());
    }

    public void show(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/PageAccueil.fxml"));
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            //primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

