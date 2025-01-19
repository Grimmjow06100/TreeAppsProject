package others;


import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDate;

public class Personne {
    private static int idCounter=0;

    @JsonProperty("uniqueId")
    private final int uniqueId;
    private String nom;
    private String prenom;
    private int age;
    LocalDate dateNaissance;



    public Personne(String nom, String prenom, int age, LocalDate dateNaissance) {
        this.uniqueId = idCounter++;
        this.nom = nom;
        this.prenom = prenom;
        this.age = age;

    }

    public Personne(){
        this.uniqueId = idCounter;
    }

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

    public void setAge(int age) {
        this.age = age;
    }

    public int getUniqueId() {
        return uniqueId;
    }
}
