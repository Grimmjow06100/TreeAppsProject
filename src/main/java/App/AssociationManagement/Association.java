package App.AssociationManagement;

import java.util.ArrayList;
import java.util.List;
import App.AssociationMember.Member;

public class Association {
    private final String nom;
    private final String adresse;
    private List<Member> members;
    private List<Tree> arbresRemarquables;
    private List<Visit> visits;
    private Budget budget;

    // Constructeur
    public Association(String nom, String adresse) {
        this.nom = nom;
        this.adresse = adresse;
        this.members = new ArrayList<>();
        this.arbresRemarquables = new ArrayList<>();
        this.visits = new ArrayList<>();
        this.budget = new Budget();
    }

    // ✅ Ajouter un Member
    public void ajouterMember(Member member) {
        members.add(member);
        System.out.println("✅ Member ajouté : " + member.getNom());
    }

    // ✅ Ajouter un arbre remarquable
    public void ajouterArbre(Tree Tree) {
        arbresRemarquables.add(Tree);
        System.out.println("🌳 getArbre ajouté : " + Tree.getNomCommun());
    }

    // ✅ Organiser une visite et enregistrer le compte-rendu
    public void organiserVisite(Visit visit) {
        visits.add(visit);
        budget.enregistrerDepense(visit.getCout());
        System.out.println("📅 Visite de l'getArbre "+ visit.getArbre().getNomCommun() +" organisée : ");
    }

    // ✅ Ajouter un don, une subvention ou une cotisation
    public void enregistrerEntree(double montant, String description) {
        budget.ajouterEntree(montant, description);
    }

    // ✅ Consulter le budget
    public double getSoldeBudget() {
        return budget.getSolde();
    }

    // ✅ Afficher les Members
    public void afficherMembers() {
        System.out.println("📜 Liste des Members :");
        for (Member m : members) {
            System.out.println("- " + m.getNom());
        }
    }

    // ✅ Afficher les Arbres remarquables
    public void afficherArbres() {
        System.out.println("🌿 Arbres remarquables :");
        for (Tree a : arbresRemarquables) {
            System.out.println("- " + a.getNomCommun());
        }
    }

    // Getters et Setters
    public String getNom() { return nom; }
    public String getAdresse() { return adresse; }
    public List<Member> getMembers() { return members; }
    public List<Tree> getArbresRemarquables() { return arbresRemarquables; }
    public List<Visit> getVisites() { return visits; }
    public Budget getBudget() { return budget; }
}
