package App.AssociationMember;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.animation.PauseTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ArbreListeController {

    @FXML
    private JFXDrawer JFXDrawer;

    @FXML
    private JFXHamburger JFXHamburger;

    @FXML
    private ComboBox<?> filtre;

    @FXML
    private ListView<String> treeListView;

    @FXML
    private ImageView logo;

    @FXML
    private TextField recherche;

    @FXML
    private HBox topHbox;

    @FXML
    private VBox vboxMenu;

    private JsonNode user;
    private ObservableList<String> treeList;

    public void setUser(JsonNode user){
        this.user=user;
        updateMenu();
        handleCellClick();
    }

    @FXML
    public void initialize() {
        logo.setImage(new Image("file:src/main/resources/App/AssociationMember/logo.png"));
        treeList = FXCollections.observableArrayList();
        List<JsonNode> arbreList = JsonManager.getNodeWithoutFilter("Arbres_JSON.json");
        arbreList.forEach((JsonNode node) -> {
            String id = node.get("idBase").asText();
            String nom = node.get("libelle_france").asText();
            treeList.add(String.format(
                    "🌲 ID %s - Nom %s ",
                    id, nom));

        });

        FilteredList<String> filteredList = new FilteredList<>(treeList, _ -> true);
        recherche.textProperty().addListener((_, _, newValue) -> {
            filteredList.setPredicate(tree -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return tree.toLowerCase().contains(lowerCaseFilter);
            });
        });

        treeListView.setItems(filteredList);
    }

    public void updateMenu(){
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/Menu.fxml"));
        try {
            VBox box = loader.load();
            MenuController controller = loader.getController();
            controller.setUser(user);
            JFXDrawer.setSidePane(box);

            HamburgerBackArrowBasicTransition burgerTask2 = new HamburgerBackArrowBasicTransition(JFXHamburger);
            burgerTask2.setRate(-1);
            JFXHamburger.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, (_) -> {
                System.out.println("Hamburger clicked");
                burgerTask2.setRate(burgerTask2.getRate() * -1);
                burgerTask2.play();
                if (JFXDrawer.isOpened()) {
                    JFXDrawer.close();
                    // ✅ Attendre 300ms avant de masquer vboxMenu
                    PauseTransition pause = new PauseTransition(Duration.millis(300));
                    pause.setOnFinished(event -> vboxMenu.setVisible(false));
                    pause.play();


                } else {
                    JFXDrawer.open();
                    vboxMenu.setVisible(true);
                }
            });
        } catch (Exception e) {
            System.out.println("Error de l'initialisation: " + e.getMessage());
        }


    }

    public void handleCellClick(){
        treeListView.setOnMouseClicked((event) -> {
            if(event.getClickCount() == 2 && !treeListView.getSelectionModel().isEmpty()){
                String selected = treeListView.getSelectionModel().getSelectedItem();
                String id = selected.split("ID ")[1].split("- Nom ")[0];
                int idInt = Integer.parseInt(id.trim());
                Optional<JsonNode> arbreOption=JsonManager.getNode("Arbres_JSON.json", Map.entry("idBase", idInt));
                if(arbreOption.isPresent()){
                    JsonNode arbre = arbreOption.get();
                    Stage stage = new Stage();
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/App/AssociationMember/DetailsPage.fxml"));
                    try {
                        VBox detailsPage = loader.load();
                        DetailsController controller = loader.getController();
                        controller.setUser(user, arbre);
                        stage.setTitle("Details");
                        stage.setScene(new Scene(detailsPage));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

            }
        });
    }


}
