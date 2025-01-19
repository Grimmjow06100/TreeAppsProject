package App.AssociationMember;
import App.AssociationManagement.Association;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import others.Personne;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class Member extends Personne{
    private String identifiant;

    private String password;


    private static final int MONTANT_COTISATION = 50; // Montant fixe de la cotisation

    private boolean cotisationPayee;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Format JSON propre
    private final LocalDate dateInscription = LocalDate.now();

    @JsonFormat(shape = JsonFormat.Shape.ARRAY) // Stocke la liste comme un tableau JSON
    private List<LocalDate>cotisationsPayees;

    public Member(Personne p,String identifiant, String password) {
        super(p.getNom(), p.getPrenom(), p.getAge(),p.getDateNaissance());
        this.identifiant = identifiant;
        this.password = password;
        this.cotisationPayee = false;
        List<LocalDate>cotisationsPayees;
    }

    Member(){
        super();
    }

    // ✅ Méthode pour payer la cotisation
    public void payerCotisation(Association association) {
        if (!cotisationPayee) {
            cotisationPayee = true;
            System.out.println("💰 Cotisation annuelle de " + MONTANT_COTISATION + "€ payée par " + getNom());
            association.getBudget().ajouterEntree(MONTANT_COTISATION, "Cotisation de " + getNom());
        } else {
            System.out.println("ℹ️ Cotisation déjà payée pour cette année.");
        }
    }

    // ✅ Vérifier si la cotisation est payée
    public boolean isCotisationPayee() {
        return cotisationPayee;
    }
    // ✅ Afficher l’état du membre
    public void afficherStatut() {
        System.out.println("👤 Membre : " + getNom() + " - Identifiant : " + identifiant);
        System.out.println("💰 Cotisation : " + (cotisationPayee ? "✅ Payée" : "❌ Non payée"));
    }


    public String getIdentifiant() {
        return identifiant;
    }
    public String getPassword() {
        return password;
    }
    public LocalDate getDateInscription() {
        return dateInscription;
    }
    public static double getMontantCotisation() { return MONTANT_COTISATION; }
}
