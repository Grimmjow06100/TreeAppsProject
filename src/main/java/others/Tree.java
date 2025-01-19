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
    private final String nomCommun;
    private final int circonference; // en cm
    private final double hauteur; // en mètres
    private final String stadeDeveloppement;
    private final String adresse;
    private final double latitude;
    private final double longitude;
    private final boolean estRemarquable;
    private final Optional<LocalDate> dateClassification; // Peut être vide si inconnu

    // ✅ Constructeur
    public Tree(String genre, String espece, String nomCommun, int circonference, double hauteur,
                String stadeDeveloppement, String adresse, double latitude, double longitude,
                boolean estRemarquable) {
        this.genre = genre;
        this.espece = espece;
        this.nomCommun = nomCommun;
        this.circonference = circonference;
        this.hauteur = hauteur;
        this.stadeDeveloppement = stadeDeveloppement;
        this.adresse = adresse;
        this.latitude = latitude;
        this.longitude = longitude;
        this.estRemarquable = estRemarquable;
        this.dateClassification = Optional.empty();
    }

    public static Tree JSONToArbre(JsonNode node) {
        try {
            // Vérification de la présence des clés pour éviter NullPointerException
            String genre =  node.get("genre").asText() ;
            String espece =  node.get("espece").asText() ;
            String nomCommun =  node.get("libelle_france").asText() ;
            int circonference = node.get("circonference").asInt();
            double hauteur = node.get("hauteur").asDouble();
            String stadeDeveloppement =node.get("stade_de_developpement").asText() ;
            String adresse =  node.get("lieu").asText();
            double latitude = node.get("latitude").asDouble() ;
            double longitude =  node.get("longitude").asDouble();
            boolean estRemarquable = !Objects.equals(node.get("remarquable").asText(), "NON");

            return new Tree(genre, espece, nomCommun, circonference, hauteur, stadeDeveloppement, adresse, latitude, longitude, estRemarquable);
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la conversion du JSON en arbre : " + e.getMessage());
            return null;
        }
    }

    public static List<Tree> JSONToArbreList(List<JsonNode> list) {
        List<Tree> trees = new ArrayList<>();
        for (JsonNode treeNode : list) {
            Tree arbre = JSONToArbre(treeNode);
            if (arbre != null) {  // Éviter d'ajouter un arbre mal formé
                trees.add(arbre);
            }
        }
        return trees;
    }

    // ✅ Getters
    public String getGenre() { return genre; }
    public String getEspece() { return espece; }
    public String getNomCommun() { return nomCommun; }
    public int getCirconference() { return circonference; }
    public double getHauteur() { return hauteur; }
    public String getStadeDeveloppement() { return stadeDeveloppement; }
    public String getAdresse() { return adresse; }
    public double getLatitude() { return latitude; }
    public double getLongitude() { return longitude; }
    public boolean isRemarquable() { return estRemarquable; }
    public Optional<LocalDate> getDateClassification() { return dateClassification; }

    // ✅ Méthode pour afficher les infos
    public void afficherInfo() {
        System.out.println("🌳 Arbre : " + nomCommun + " (" + genre + " " + espece + ")");
        System.out.println("📏 Circonférence : " + circonference + " cm, Hauteur : " + hauteur + " m");
        System.out.println("🌱 Stade de développement : " + stadeDeveloppement);
        System.out.println("📍 Adresse : " + adresse + " (GPS : " + latitude + ", " + longitude + ")");
        if (estRemarquable) {
            System.out.println("🏅 Arbre remarquable depuis : " +
                    dateClassification.map(LocalDate::toString).orElse("Date inconnue"));
        } else {
            System.out.println("🏅 Pas encore classé remarquable.");
        }
        System.out.println("-------------------------------------------------");
    }
}
