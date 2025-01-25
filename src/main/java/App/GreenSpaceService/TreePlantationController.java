package App.GreenSpaceService;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class TreePlantationController {


    public void onSaveButtonClick(ActionEvent actionEvent) {
    }

    public void onCancelButtonClick(ActionEvent actionEvent) {
    }

    @FXML
    public void onReturnButtonClick(ActionEvent actionEvent) {
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
}
