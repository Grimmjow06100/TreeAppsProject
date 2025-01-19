package others;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fasterxml.jackson.databind.JsonNode;

public class Tree {
    private final String genre;
    private final String espece;
    private final String libelle_france;
    private final int circonference; // en cm
    private final double hauteur; // en mètres
    private final String stade_de_developpement;
    private final String lieu;
    private final double latitude;
    private final double longitude;
    private  String remarquable;
    private final LocalDate dateClassification; // Peut être vide si inconnu

    // ✅ Constructeur
    public Tree(String genre, String espece, String nomCommun, int circonference, double hauteur,
                String stadeDeveloppement, String adresse, double latitude, double longitude,
                String remarquable) {
        this.genre = genre;
        this.espece = espece;
        this.libelle_france = nomCommun;
        this.circonference = circonference;
        this.hauteur = hauteur;
        this.stade_de_developpement = stadeDeveloppement;
        this.lieu= adresse;
        this.latitude = latitude;
        this.longitude = longitude;
        this.remarquable = remarquable;
        this.dateClassification = null;
    }

    public Tree(){
        this.genre = "";
        this.espece = "";
        this.libelle_france = "";
        this.circonference = 0;
        this.hauteur = 0;
        this.stade_de_developpement = "";
        this.lieu = "";
        this.latitude = 0;
        this.longitude = 0;
        this.remarquable = "NON";
        this.dateClassification = null;
    }


    // ✅ Getters
    public String getGenre() { return genre; }
    public String getEspece() { return espece; }
    public String getLibelle_france() { return libelle_france; }
    public int getCirconference() { return circonference; }
    public double getHauteur() { return hauteur; }
    public String getStade_de_developpement() { return stade_de_developpement; }
    public String getLieu() { return lieu; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public String getRemarquable() { return remarquable; }
    public LocalDate getDateClassification() { return dateClassification; }

    // ✅ Méthode pour afficher les infos
    public void afficherInfo() {
        Optional<String> dateClassification = Optional.ofNullable(this.dateClassification)
                .map(LocalDate::toString);
        System.out.println("🌳 Arbre : " + libelle_france + " (" + genre + " " + espece + ")");
        System.out.println("📏 Circonférence : " + circonference + " cm, Hauteur : " + hauteur + " m");
        System.out.println("🌱 Stade de développement : " + stade_de_developpement);
        System.out.println("📍 Adresse : " + lieu + " (GPS : " + latitude + ", " + longitude + ")");
        if (remarquable=="OUI") {
            System.out.println("🏅 Arbre remarquable depuis : " +
                    dateClassification.orElse("Date inconnue"));
        } else {
            System.out.println("🏅 Pas encore classé remarquable.");
        }
        System.out.println("-------------------------------------------------");
    }

    public void setRemarquable(String remarquable) {
        this.remarquable = remarquable;
    }
}
