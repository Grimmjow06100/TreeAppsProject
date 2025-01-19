package others;


import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;

public class Personne {


    private String nom;
    private String prenom;
    private int age;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    LocalDate dateNaissance;



    public Personne(String nom, String prenom, int age, LocalDate dateNaissance) {
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;
        this.dateNaissance = dateNaissance;

    }

    public Personne(){}

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public int getAge() {
        return age;
    }
    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

}
