package App.AssociationMember;
import App.AssociationManagement.Visit;
import Data.JSONManager;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.JsonNode;
import others.Personne;
import com.fasterxml.jackson.annotation.JsonFormat;
import others.Tree;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class Member extends Personne{

    private static final int MONTANT_COTISATION = 50; // Montant fixe de la cotisation
    private static final int MAX_NOMINATIONS = 5;

    private String identifiant;
    private String password;

    private boolean cotisationPayee;


    //Liste
    @JsonFormat(shape = JsonFormat.Shape.ARRAY) // Stocke la liste comme un tableau JSON
    private List<LocalDate>cotisationsPayees;


    @JsonIgnoreProperties(value = { "genre", "espece", "circonference", "libelle_france", "hauteur", "stade_de_developpement", "lieu", "latitude", "longitude", "remarquable","dateClassification" })
    private List<Tree>nominations;


    @JsonIgnoreProperties(value = {"compteRendu" ,"cout"})
    private List<Visit>visites;


    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy") // Format JSON propre
    private LocalDate dateInscription;



    public Member(Personne p,String identifiant, String password,LocalDate dateInscription){
        super(p.getNom(), p.getPrenom(), p.getAge(),p.getDateNaissance());
        this.identifiant = identifiant;
        this.password = password;
        this.cotisationPayee = false;
        this.dateInscription=dateInscription;
        this.nominations = new LinkedList<>();
        this.visites = new ArrayList<>();
        this.cotisationsPayees = new ArrayList<>();

    }

    public Member (){
        super();
        this.nominations = new LinkedList<>();
        this.visites = new ArrayList<>();
        this.cotisationsPayees = new ArrayList<>();
    }
    public static JsonNode login(String Username, String password){
        JSONManager json= JSONManager.INSTANCE;
        JsonNode user=json.searchInJson("Members_JSON.json","identifiant",Username,"password",password);
        if(user!=null){
            return user;

        }
        System.out.println("❌ l'identifiant ou le mot de passe est incorrect");
        return null;
    }

   public void addNominations(Tree tree) {
    updateMemberData(member -> {
        if (member.getNominations().size() < MAX_NOMINATIONS) {
            member.getNominations().add(tree);
        } else {
            member.getNominations().removeFirst();
            member.getNominations().add(tree);
        }
    });
}

public void addCotisationPayee(LocalDate date) {
    updateMemberData(member -> member.getCotisationsPayees().add(date));
}

public void addVisit(Visit visit) {
    updateMemberData(member -> member.getVisites().add(visit));
}

private void updateMemberData(Consumer<Member> updateAction) {
    JSONManager json = JSONManager.INSTANCE;
    Member member = json.getObjectFromJson("Members_JSON.json", "identifiant", identifiant, Member.class).get();
    updateAction.accept(member);
    json.modifyInJson("Members_JSON.json", "identifiant", identifiant, member);
}

    // ✅ Vérifier si la cotisation est payée
    public boolean isCotisationPayee() {
        return cotisationPayee;
    }


    public List<Tree> getNominations() { return nominations; }
    public List<LocalDate> getCotisationsPayees() { return cotisationsPayees; }
    public List<Visit> getVisites() { return visites; }


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
