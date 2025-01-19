package Demo;

import App.AssociationManagement.Tree;
import Data.JSONDatabase;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.datafaker.Faker;
import others.Personne;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import static Data.JSONDatabase.*;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void testJSONToTree() throws IOException {
        JSONDatabase db= new JSONDatabase("src/main/resources/JSONDB");
        String filename = "Arbre_JSON.json";
        File file = new File(db.getBASE_URL()+"/"+filename);
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode= objectMapper.readTree(file);

        Iterator<JsonNode>node= rootNode.elements();
        Tree tree= Tree.JSONToArbre(node.next());

        if(tree!=null){
            tree.afficherInfo();
        }

    }

    public static void testJSONClass() {



        //Création de la base de données
        JSONDatabase db = new JSONDatabase("src/main/resources/JSONDB");

        String filename = "Personne_JSON.json";


        //Création du fichier
        db.createJsonFile(filename);

        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            Personne p = new Personne( faker.name().lastName(),faker.name().firstName(),faker.number().numberBetween(15, 70));
            db.addToJsonFile(filename,p,Personne.class);

        }

        db.deleteNodeFromJSON(filename,"uniqueId","0");
        db.modifyNodeFromJSON(filename,"uniqueId","1","age","100");
    }


    public static void main(String[] args) throws IOException {
        testJSONToTree();
    }
}