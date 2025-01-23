package App.AssociationManagement.Controller.financesAssociation;

import App.AssociationManagement.Controller.PageAccueilController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class FinancesAssociationController {
    Stage primaryStage;
    PageAccueilController pageAccueilController;
    CotisationsController cotisationsController;
    DepensesController depensesController;
    DonsSubventionsController donsSubventionsController;

    @FXML
    Button retourButtonFinances;

    @FXML
    Button donsSubventionsButton;

    @FXML
    Button cotisationsButton;

    @FXML
    Button depensesButton;

    //Constructeur
    public FinancesAssociationController() {
        this.primaryStage = new Stage();
        this.pageAccueilController = new PageAccueilController(primaryStage);
        this.cotisationsController = new CotisationsController(this, primaryStage);
        this.depensesController = new DepensesController(this, primaryStage);
        this.donsSubventionsController = new DonsSubventionsController(this, primaryStage);
    }

    public FinancesAssociationController(PageAccueilController pageAccueilController, Stage stage) {
        this.primaryStage = stage;
        this.pageAccueilController = pageAccueilController;
        this.cotisationsController = new CotisationsController(this, primaryStage);
        this.depensesController = new DepensesController(this, primaryStage);
        this.donsSubventionsController = new DonsSubventionsController(this, primaryStage);
    }

    @FXML
    private void initialize() {
        retourButtonFinances.setOnAction(event -> retourPageAccueil());
        donsSubventionsButton.setOnAction(event -> ouvrirDonsSubventions());
        cotisationsButton.setOnAction(event -> ouvrirCotisations());
        depensesButton.setOnAction(event -> ouvrirDepenses());
    }

    public void show() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/financesAssociation/FinancesAssociation.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void retourPageAccueil() {
        pageAccueilController.show();
    }

    public void ouvrirCotisations() {
        cotisationsController.show();
    }

    public void ouvrirDepenses() {
        depensesController.show();
    }

    public void ouvrirDonsSubventions() {
        donsSubventionsController.show();
    }
}
