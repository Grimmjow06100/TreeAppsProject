package Demo;

import Data.JSONDatabase;
import com.fasterxml.jackson.databind.JsonNode;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import net.datafaker.Faker;
import others.Personne;

import java.io.IOException;

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

    public static void testJSONClass() {

        //reintialisation de la base de données
        deleteFile("src/main/resources/JSONDB/Personne_JSON.json");


        //Création de la base de données


        JSONDatabase db = new JSONDatabase("src/main/resources/JSONDB");

        String filename = "Personne_JSON.json";


        db.createJsonFile("Personne_JSON");

        Faker faker = new Faker();
        for (int i = 0; i < 10; i++) {
            Personne p = new Personne( faker.name().lastName(),faker.name().firstName(),faker.number().numberBetween(15, 70));
            db.addToJsonFile("Personne_JSON",p,Personne.class);

        }

        deleteObject(db.fullPath("Personne_JSON.json"),"uniqueId","0");
        modifyJSON(db.fullPath("Personne_JSON.json"),"uniqueId","1","age","100");
    }


    public static void main(String[] args) {
        testJSONClass();

    }
}