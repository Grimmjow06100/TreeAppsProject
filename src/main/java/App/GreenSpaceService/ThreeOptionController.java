package App.GreenSpaceService;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ThreeOptionController {

    @FXML
    private Label idLabel;
    @FXML
    private Label nomLabel;
    @FXML
    private Label genreLabel;
    @FXML
    private Label especeLabel;
    @FXML
    private Label lieuLabel;
    @FXML
    private Label remarquableLabel;
    @FXML
    private Label latitudeLabel;
    @FXML
    private Label longitudeLabel;
    @FXML
    private Label hauteurLabel;
    @FXML
    private Label circonferenceLabel;
    @FXML
    private Label developpementStageLabel;

    // Méthode pour définir les informations d'un arbre
    public void setTreeInfo(Tree tree) {
        idLabel.setText("ID : " + tree.getId());
        nomLabel.setText("Nom : " + tree.getNom());
        genreLabel.setText("Genre : " + tree.getGenre());
        especeLabel.setText("Espèce : " + tree.getEspece());
        lieuLabel.setText("Lieu : " + tree.getLieu());
        remarquableLabel.setText("Remarquable : " + tree.getRemarquable());
        latitudeLabel.setText("Latitude : " + tree.getLatitude());
        longitudeLabel.setText("Longitude : " + tree.getLongitude());
        hauteurLabel.setText("Hauteur : " + tree.getHauteur());
        circonferenceLabel.setText("Circonférence : " + tree.getCirconference());
        developpementStageLabel.setText("Stade de développement : " + tree.getDeveloppementStage());
    }
}
