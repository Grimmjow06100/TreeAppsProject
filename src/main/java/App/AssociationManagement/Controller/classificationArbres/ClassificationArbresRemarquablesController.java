package App.AssociationManagement.Controller.classificationArbres;

import App.AssociationManagement.Controller.PageAccueilController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.stage.Stage;

import java.io.IOException;

public class ClassificationArbresRemarquablesController {
    Stage primaryStage;
    PageAccueilController pageAccueilController;

    @FXML
    MenuBar menuBar;

    @FXML
    MenuItem menuItem1;

    @FXML
    MenuItem menuItem2;

    @FXML
    Button retourButtonClassement;

    @FXML
    Button listeButton;

    @FXML
    Button classementButton;

    //Constructeur
    public ClassificationArbresRemarquablesController() {
        this.primaryStage = new Stage();
        this.pageAccueilController = new PageAccueilController(primaryStage);
    }

    public ClassificationArbresRemarquablesController(PageAccueilController pageAccueilController, Stage stage) {
        this.pageAccueilController = pageAccueilController;
        this.primaryStage = stage;
    }

    @FXML
    public void initialize(){
        //actions à effectuer lors de l'initialisation de la vue
        /*
        arbresClassement = FXCollections.observableArrayList(
                new Arbre("Chêne", "Quercus robur"),
                new Arbre("Pin", "Pinus sylvestris")
        );

        arbreListViewClassement.setItems(arbresClassement.stream()
                .map(a -> a.getNomFrancais() + " (" + a.getEspece() + ")")
                .collect(Collectors.toCollection(FXCollections::observableArrayList)));

        arbreListViewClassement.setOnMouseClicked(event -> ouvrirDetails(arbreListViewClassement.getSelectionModel().getSelectedItem()));
        */

        retourButtonClassement.setOnAction(event -> retourPageAccueil());
        //TODO listeButton.setOnAction(event -> classificationArbresRemarquablesController.listeArbres());
        //TODO classementButton.setOnAction(event -> classificationArbresRemarquablesController.classementArbres());
    }

    //Retour sur le menu principal
    public void retourPageAccueil() {
        pageAccueilController.show();
    }

    public void show(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationManagement/classificationArbres/ClassificationArbresRemarquables.fxml"));
            loader.setController(this);
            Parent root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //Méthode pour afficher la liste des arbres remarquables données par la municipalité

    //Méthode pour afficher le classement des arbres remarquables votés par les membres

    /* TODO
    private void ouvrirDetails(int idArbre) {
        // Charger la fenêtre des détails
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/details_arbre.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Fermer la fenêtre en cliquant ailleurs
            root.setOnMouseClicked(e -> stage.close());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}
