package Demo;

import App.AssociationManagement.Visit;
import App.AssociationMember.Member;
import others.Nomination;
import others.ResourceHandler;
import others.Tree;
import Data.JSONManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import others.Personne;
import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.nio.file.Path;
import java.nio.file.Paths;


public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        //affichage samy
        ResourceHandler rh= new ResourceHandler("src/main/resources/App/AssociationMember");
        Optional<FXMLLoader> loader= rh.getFXMLLoader("LoginScreen.fxml");
        if(loader.isPresent()){
            FXMLLoader fxmlLoader = loader.get();
            Scene scene = new Scene(fxmlLoader.load());
            stage.setTitle("Hello!");
            stage.setScene(scene);
            stage.show();

        }

        //affichage yoan
        Stage secondStage = new Stage(); // Nouveau Stage
        Path accueilPath = Paths.get("src/main/resources/App.GreenSpace/accueil-view.fxml");
        FXMLLoader accueilLoader = new FXMLLoader(accueilPath.toUri().toURL());
        Scene scene1 = new Scene(accueilLoader.load(), 800, 600);
        scene1.getStylesheets().add(
                Objects.requireNonNull(getClass().getResource("/App.GreenSpace/styles.css")).toExternalForm()
        );

        secondStage.setTitle("Gestion des espaces verts");
        secondStage.setScene(scene1);
        secondStage.show();

    }
    public static void MakeJsonFiles() {
        JSONManager db=  JSONManager.INSTANCE;
        db.deleteJsonFile("Members_JSON.json");
        db.deleteJsonFile("Visits_JSON.json");


        db.createJsonFile("Members_JSON.json");
        db.createJsonFile("Visits_JSON.json");

        String filename = "Arbres_JSON.json";
        File file = new File(db.getBASE_URL()+"/"+filename);

        Personne p1= new Personne("Doe", "John", 25, LocalDate.of(1996, 5, 15));
        Personne p2= new Personne("Doe", "Jane", 22, LocalDate.of(1999, 8, 25));
        Personne p3= new Personne("Doe", "Alice", 30, LocalDate.of(1991, 2, 10));
        Personne p4= new Personne("Doe", "Bob", 28, LocalDate.of(1993, 12, 5));
        Personne p5= new Personne("Doe", "Eve", 35, LocalDate.of(1986, 10, 30));
        Member m1= new Member(p1, "johnDoe", "password123",LocalDate.of(2021, 5, 15));
        Member m2= new Member(p2, "JaneDoe", "password456",LocalDate.of(2021, 5, 15));
        Member m3= new Member(p3, "AliceDoe", "password789",LocalDate.of(2021, 5, 15));
        Member m4= new Member(p4, "BobDoe", "password101112",LocalDate.of(2021, 5, 15));
        Member m5= new Member(p5, "DoeEve", "password131415",LocalDate.of(2021, 5, 15));

        System.out.println(m1);
        db.addToJsonFile("Members_JSON.json", List.of(m1, m2, m3, m4, m5), "identifiant");



        Optional<Tree> tree1=db.getObjectFromJson(filename,"genre","Tetradium", Tree.class);
        Optional<Tree> tree2=db.getObjectFromJson(filename,"genre","Platanus", Tree.class);
        Optional<Tree> tree3=db.getObjectFromJson(filename,"genre","Styphnolobium", Tree.class);




        Visit v1= new Visit(50,LocalDate.of(2025, 3, 16), tree1.get());
        Visit v2= new Visit(150,LocalDate.of(2021, 5, 16), tree2.get());
        Visit v3= new Visit(100,LocalDate.of(2021, 5, 17), tree3.get());


        db.addToJsonFile("Visits_JSON.json",List.of(v1, v2, v3), "date");

        Nomination n1= new Nomination(tree1.get(), 3);
        Nomination n2= new Nomination(tree2.get(), 2);

        db.createJsonFile("Nominations_JSON.json");
        db.addToJsonFile("Nominations_JSON.json", List.of(n1, n2), "idBase");

        m1.addVisit(v1);
        m1.addVisit(v2);
        m1.addVisit(v3);

        m2.addCotisationPayee(LocalDate.of(2021, 5, 15));
        m2.addCotisationPayee(LocalDate.of(2021, 5, 16));

        m3.addNominations(tree1.get());
        m3.addNominations(tree2.get());
        m3.addNominations(tree3.get());

    }


    public static void main(String[] args) throws IOException {
        MakeJsonFiles();
        launch();

    }
}