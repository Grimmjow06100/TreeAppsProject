package Demo;

import App.AssociationManagement.Association;
import App.AssociationManagement.Controller.PageAccueilController;
import App.AssociationManagement.Visit;
import App.AssociationMember.Member;
import javafx.scene.Parent;
import others.*;
import Data.JsonManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Objects;


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
        ResourceHandler rh2 = new ResourceHandler("src/main/resources/App/GreenServiceSpace");
        Optional<FXMLLoader> accueilLoader = rh2.getFXMLLoader("accueil-view.fxml");

        if(accueilLoader.isPresent()){
            Scene scene1 = new Scene(accueilLoader.get().load(), 800, 600);
            scene1.getStylesheets().add(
                    Objects.requireNonNull(getClass().getResource("/App/GreenServiceSpace/styles.css")).toExternalForm()
            );

            secondStage.setTitle("Gestion des espaces verts");
            secondStage.setScene(scene1);
            secondStage.show();
        };

        //affichage ny
        Stage accueilStage = new Stage();  // Nouveau Stage pour la page d'accueil
        ResourceHandler rh3 = new ResourceHandler("src/main/resources/App/AssociationManagement");
        Optional<FXMLLoader> accueilFXMLLoader = rh3.getFXMLLoader("PageAccueil.fxml");

        if (accueilFXMLLoader.isPresent()) {
            FXMLLoader loader2 = accueilFXMLLoader.get();
            Scene accueilScene = new Scene(loader2.load(), 800, 600);

            accueilStage.setTitle("Gestion de l'association");
            accueilStage.setScene(accueilScene);
            accueilStage.show();

        }


    }
    public static void MakeJsonFiles() {
        JsonManager db=  JsonManager.INSTANCE;
        db.deleteJsonFile("Members_JSON.json");
        db.deleteJsonFile("Visits_JSON.json");
        db.deleteJsonFile("Association_JSON.json");


        db.createJsonFile("Members_JSON.json");
        db.createJsonFile("Visits_JSON.json");
        db.createJsonFile("Association_JSON.json");

        String filename = "Arbres_JSON.json";
        File file = new File(db.BASE_URL+"/"+filename);

        Personne p1= new Personne("Doe", "John", 25, LocalDate.of(1996, 5, 15),"balec");
        Personne p2= new Personne("Doe", "Jane", 22, LocalDate.of(1999, 8, 25),"balec");
        Personne p3= new Personne("Doe", "Alice", 30, LocalDate.of(1991, 2, 10),"balec");
        Personne p4= new Personne("Doe", "Bob", 28, LocalDate.of(1993, 12, 5),"balec");
        Personne p5= new Personne("Doe", "Eve", 35, LocalDate.of(1986, 10, 30),"balec");
        Member m1= new Member(p1, "johnDoe", "password123",LocalDate.of(2021, 5, 15));
        Member m2= new Member(p2, "JaneDoe", "password456",LocalDate.of(2021, 5, 15));
        Member m3= new Member(p3, "AliceDoe", "password789",LocalDate.of(2021, 5, 15));
        Member m4= new Member(p4, "BobDoe", "password101112",LocalDate.of(2021, 5, 15));
        Member m5= new Member(p5, "DoeEve", "password131415",LocalDate.of(2021, 5, 15));

        System.out.println(m1);
        db.insertInJson("Members_JSON.json", List.of(m1, m2, m3, m4, m5), "identifiant");

        Don d= new Don(100, "kaka");
        Facture f= new Facture(100, "kaka",false);


        Association a= new Association("Arre Assoc",500,List.of(d),List.of(f));

        db.insertInJson("Association_JSON.json", List.of(a), "nom");

        Optional<Tree> tree1=db.getObjectFromJson(filename,"genre","Tetradium", Tree.class);
        Optional<Tree> tree2=db.getObjectFromJson(filename,"genre","Platanus", Tree.class);
        Optional<Tree> tree3=db.getObjectFromJson(filename,"genre","Styphnolobium", Tree.class);




        Visit v1= new Visit(50,LocalDate.of(2025, 3, 16), tree1.get());
        Visit v2= new Visit(150,LocalDate.of(2021, 5, 16), tree2.get());
        Visit v3= new Visit(100,LocalDate.of(2021, 5, 17), tree3.get());


        db.insertInJson("Visits_JSON.json",List.of(v1, v2, v3), "date");

        Nomination n1= new Nomination(tree1.get(), 3);
        Nomination n2= new Nomination(tree2.get(), 2);

        db.createJsonFile("Nominations_JSON.json");
        db.insertInJson("Nominations_JSON.json", List.of(n1, n2), "idBase");

        m1.addVisit(v1);
        m1.addVisit(v2);
        m1.addVisit(v3);
        m1.addNominations(tree1.get());
        m1.addNominations(tree2.get());
        m1.addNominations(tree3.get());
        m1.addCotisationPayee(LocalDate.of(2021, 5, 15));
        m1.addCotisationPayee(LocalDate.of(2021, 5, 16));
        m1.addCotisationPayee(LocalDate.of(2021, 5, 17));


    }


    public static void main(String[] args) throws IOException {

        launch();

    }
}