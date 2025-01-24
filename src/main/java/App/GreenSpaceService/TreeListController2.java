package App.GreenSpaceService;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class TreeListController2 {

    @FXML
    private TableView<Tree> treeTableView;

    @FXML
    private TableColumn<Tree, String> colId;

    @FXML
    private TableColumn<Tree, Void> colActions;

    @FXML
    private TableColumn<Tree, String> colNom;

    @FXML
    private TableColumn<Tree, String> colGenre;

    @FXML
    private TableColumn<Tree, String> colEspece;

    @FXML
    private TableColumn<Tree, String> colLieu;

    private ObservableList<Tree> treeList = FXCollections.observableArrayList(); // Liste observable pour le TableView

    @FXML
    public void OnActionButtonClicked5(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource
                    ("/App/GreenServiceSpace/three-manager-view.fxml"));
            Parent secondView = loader.load();
            Stage stage = (Stage) ((javafx.scene.Node) actionEvent.getSource()).getScene()
                    .getWindow();
            stage.setScene(new Scene(secondView, 800, 600));
            stage.getScene().getStylesheets().add(
                    Objects.requireNonNull(getClass().getResource
                            ("/App/GreenServiceSpace/styles.css")).toExternalForm());
            stage.setTitle("Gestion des espaces verts");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void initialize() {
        // Configurer les colonnes pour mapper les propriétés de Tree
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        colGenre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        colEspece.setCellValueFactory(new PropertyValueFactory<>("espece"));
        colLieu.setCellValueFactory(new PropertyValueFactory<>("lieu"));

        addButtonToTable();
        // Charger les données JSON et remplir le tableau
        loadTreeData();
        treeTableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

    }

    public void loadTreeData() {
        JsonManager jsonManager = JsonManager.INSTANCE;

        // Lecture des données JSON
        List<JsonNode> arbreList = jsonManager.getNodeWithoutFilter("Arbres_JSON.json");

        // Convertir les données JSON en objets Tree et les ajouter à la liste
        arbreList.forEach((JsonNode node) -> {
            String id = node.get("idBase").asText();
            String nom = node.get("libelle_france").asText();
            String genre = node.get("genre").asText();
            String espece = node.get("espece").asText();
            String lieu = node.get("lieu").asText();
            String remarquable = node.get("remarquable").asText();

            treeList.add(new Tree(id, nom, genre, espece, lieu,remarquable));
        });

        // Ajouter les données au TableView
        treeTableView.setItems(treeList);
    }



    private void addButtonToTable() {
        // Ajout de la colonne pour les boutons
        Callback<TableColumn<Tree, Void>, TableCell<Tree, Void>> cellFactory = new Callback<>() {
            @Override
            public TableCell<Tree, Void> call(final TableColumn<Tree, Void> param) {
                return new TableCell<>() {

                    private final Button btn = new Button("infos");


                    {
                        btn.setOnAction(event -> {
                            // Obtenir l'arbre correspondant à cette ligne
                            Tree tree = getTableView().getItems().get(getIndex());
                            if(Objects.equals(tree.getRemarquable(), "NON")) { //filtre pour empecher la suppression
                                // Supprimer l'arbre de la liste
                                treeList.remove(tree);
                            }
                        });

                        // Ajout de style CSS au bouton
                        btn.setStyle("-fx-background-color: rgba(46, 139, 87,0.5); -fx-text-fill: white; -fx-border-radius: 5; -fx-background-radius: 5;");
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
            }
        };

        colActions.setCellFactory(cellFactory);
    }
}
