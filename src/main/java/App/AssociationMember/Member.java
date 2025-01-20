package App.AssociationMember;
import App.AssociationManagement.Association;
import Data.JSONHandler;
import others.Personne;
import com.fasterxml.jackson.annotation.JsonFormat;
import others.Tree;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class Member extends Personne{
    private String identifiant;

    private String password;

    @JsonFormat(shape = JsonFormat.Shape.ARRAY) // Stocke la liste comme un tableau JSON
    private List<LocalDate>cotisationsPayees= new ArrayList<>();

    private List<Tree> nominations = new LinkedList<>();


    private static final int MONTANT_COTISATION = 50; // Montant fixe de la cotisation

    private boolean cotisationPayee;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd") // Format JSON propre
    private final LocalDate dateInscription = LocalDate.now();



    private static final int MAX_NOMINATIONS = 5;

    public Member(Personne p,String identifiant, String password) {
        super(p.getNom(), p.getPrenom(), p.getAge(),p.getDateNaissance());
        this.identifiant = identifiant;
        this.password = password;
        this.cotisationPayee = false;
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

    public static boolean checkAuthentification(String Username,String password){
        JSONHandler json=new JSONHandler("src/main/resources/JSONDB");
        Optional<Member> member=json.getObjectFromJson("Members_JSON.json","identifiant","Username",Member.class);
        if(member.isPresent()){
            if(member.get().password.equals(password)){
                System.out.println("✅ Authentification réussie");
                return true;
            }
        }
        System.out.println("❌ l'identifiant ou le mot de passe est incorrect");
        return false;
    }

    // ✅ Vérifier si la cotisation est payée
    public boolean isCotisationPayee() {
        return cotisationPayee;
    }


    public List<Tree> getNominations() { return nominations; }


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
