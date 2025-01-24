package App.GreenSpaceService;

public class Tree {
    private String id;
    private String nom;
    private String genre;
    private String espece;
    private String lieu;

    // Constructeur
    public Tree(String id, String nom, String genre, String espece, String lieu) {
        this.id = id;
        this.nom = nom;
        this.genre = genre;
        this.espece = espece;
        this.lieu = lieu;
    }

    // Getters et Setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getEspece() {
        return espece;
    }

    public void setEspece(String espece) {
        this.espece = espece;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }
}