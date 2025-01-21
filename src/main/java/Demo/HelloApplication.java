package Demo;

import App.AssociationManagement.Visit;
import App.AssociationMember.LoginScreenController;
import App.AssociationMember.Member;
import javafx.scene.chart.ScatterChart;
import others.Nomination;
import others.ResourceHandler;
import others.Tree;
import Data.JSONHandler;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import others.Personne;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        ResourceHandler rh= new ResourceHandler("src/main/resources/App/AssociationMember");
        Optional<FXMLLoader> loader= rh.getFXMLLoader("PlanificationPage.fxml");
        if(loader.isPresent()){
            FXMLLoader fxmlLoader = loader.get();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

        }


    }


    public static void MakeJsonFiles() throws IOException {
        JSONHandler db= new JSONHandler("src/main/resources/JSONDB");
        db.createJsonFile("Members_JSON.json");
        db.createJsonFile("Visits_JSON.json");

        String filename = "Arbres_JSON.json";
        File file = new File(db.getBASE_URL()+"/"+filename);

        Personne p1= new Personne("Doe", "John", 25, LocalDate.of(1996, 5, 15));
        Personne p2= new Personne("Doe", "Jane", 22, LocalDate.of(1999, 8, 25));
        Personne p3= new Personne("Doe", "Alice", 30, LocalDate.of(1991, 2, 10));
        Personne p4= new Personne("Doe", "Bob", 28, LocalDate.of(1993, 12, 5));
        Personne p5= new Personne("Doe", "Eve", 35, LocalDate.of(1986, 10, 30));
        Member m1= new Member(p1, "johnDoe", "password123");
        Member m2= new Member(p2, "JaneDoe", "password456");
        Member m3= new Member(p3, "AliceDoe", "password789");
        Member m4= new Member(p4, "BobDoe", "password101112");
        Member m5= new Member(p5, "DoeEve", "password131415");

        System.out.println(m1);
        db.addToJsonFile("Members_JSON.json", List.of(m1, m2, m3, m4, m5), "identifiant");

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode rootNode= objectMapper.readTree(file);

        Iterator<JsonNode>node= rootNode.elements();
        Optional<Tree> tree1=db.getObjectFromJson(filename,"genre","Tetradium", Tree.class);
        Optional<Tree> tree2=db.getObjectFromJson(filename,"genre","Platanus", Tree.class);
        Optional<Tree> tree3=db.getObjectFromJson(filename,"genre","Styphnolobium", Tree.class);




        Visit v1= new Visit(50,LocalDate.of(2021, 5, 15), tree1.get(), List.of(m1, m2, m3),"Hey Hey Hey !!! La mala est gang");
        Visit v2= new Visit(150,LocalDate.of(2021, 5, 16), tree2.get(), null,"null");
        Visit v3= new Visit(100,LocalDate.of(2021, 5, 17), tree3.get(), List.of(m1, m4, m5),"typeShit");


        db.addToJsonFile("Visits_JSON.json",List.of(v1, v2, v3), "date");

        Nomination n1= new Nomination(tree1.get(), 3);
        Nomination n2= new Nomination(tree2.get(), 2);

        db.createJsonFile("Nominations_JSON.json");
        db.addToJsonFile("Nominations_JSON.json", List.of(n1, n2), "idBase");

    }


    public static void main(String[] args) throws IOException {

        launch();

    }
}