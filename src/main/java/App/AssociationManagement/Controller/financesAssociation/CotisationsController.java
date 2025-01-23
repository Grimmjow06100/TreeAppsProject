package App.AssociationManagement.Controller.financesAssociation;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class CotisationsController {
    Stage primaryStage;
    FinancesAssociationController financesAssociationController;

    @FXML
    Button retourButtonCotisations;

    //Constructeur
    public CotisationsController() {
        this.financesAssociationController = new FinancesAssociationController();
        this.primaryStage = new Stage();
    }

    public CotisationsController(FinancesAssociationController financesAssociationController, Stage stage) {
        this.financesAssociationController = financesAssociationController;
        this.primaryStage = stage;
    }

    @FXML
    public void initialize() {
        retourButtonCotisations.setOnAction(event -> retourFinancesAssociation());
    }

    //Retour sur la fenêtre de finances de l'association
    public void retourFinancesAssociation() {
        financesAssociationController.show();
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/financesAssociation/Cotisations.fxml"));
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
