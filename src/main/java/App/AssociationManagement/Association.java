package App.AssociationManagement;
import java.io.IOException;
import java.util.*;
import App.AssociationMember.Member;
import Data.JSONManager;
import com.fasterxml.jackson.databind.JsonNode;
import others.Tree;

public class Association {
    private final String nom;
    private final Budget budget;
    private JSONManager jsonManager = JSONManager.INSTANCE;

    private Map<Tree, Integer> votes = new HashMap<>();
    private static final int MAX_TREES_TO_SUBMIT = 5;

    // Constructeur
    public Association(String nom,Budget budget) {
        this.nom = nom;;
        this.budget = budget;
    }

    // ✅ Vérifier les membres qui n'ont pas payé leur cotisation
    public List<Member> MembresNonPayants()  {

        List<Member> members = new ArrayList<>();
        System.out.println("📋 Membres n'ayant pas encore payé leur cotisation :");
        boolean found = false;
        List<JsonNode>node= jsonManager.getNodeList("members.json", List.of(Map.entry("cotisationPayee", "false")));
        node.forEach(n->{
            try {
                members.add(jsonManager.objectMapper.treeToValue(n, Member.class));
            }catch (IOException e){
                System.out.println("❌ Erreur lors de la lecture du fichier JSON : " + e.getMessage());
            }
        });
        if (!found) {
            System.out.println("✅ Tous les membres ont payé leur cotisation !");
        }

        return members;
    }

    public Budget getBudget() { return budget; }
    public String getNom() { return nom; }

    //Toute cette partie est à modifier


    // ✅ Collecte toutes les nominations et les compte

    /*
    public void collecterNominations() {
        votes.clear();
        for (Member membre : membres) {
            for (Tree tree : membre.getNominations()) {
                votes.put(tree, votes.getOrDefault(tree, 0) + 1);
            }
        }
    }
    // ✅ Comparateur pour trier les arbres par priorité (circonférence, hauteur)
    public static final Comparator<Tree> TREE_COMPARATOR = Comparator
            .comparingInt((Tree t) -> -t.getCirconference()) // Trier d'abord par circonférence (descendant)
            .thenComparingDouble(t -> -t.getHauteur()); // Puis par hauteur (descendant)
    // ✅ Génère la liste finale en fonction des règles de priorité
    public List<Tree> genererListeSoumise() {
        collecterNominations();

        // Trier les arbres par nombre de votes, puis par priorité (circonférence, hauteur)
        List<Map.Entry<Tree, Integer>> sortedEntries = new ArrayList<>(votes.entrySet());
        sortedEntries.sort(Map.Entry.<Tree, Integer>comparingByValue(Comparator.reverseOrder())
                .thenComparing(entry -> entry.getKey(),TREE_COMPARATOR));

        // Récupérer les 5 premiers arbres
        List<Tree> finalSelection = new ArrayList<>();
        for (int i = 0; i < Math.min(MAX_TREES_TO_SUBMIT, sortedEntries.size()); i++) {
            finalSelection.add(sortedEntries.get(i).getKey());
        }

        return finalSelection;
    }

    // ✅ Marquer un arbre comme "remarquable" s'il est accepté par la municipalité
    public void marquerRemarquable(Tree arbre) {
        arbre.setRemarquable("OUI");
        System.out.println("🌟 L’arbre " + arbre.getGenre() + " (" + arbre.getLieu() + ") a été classé remarquable !");
    }

    // ✅ Ajouter un Member
    public void ajouterMember(Member member) {
        members.add(member);
        System.out.println("✅ Member ajouté : " + member.getNom());
    }

    // ✅ Ajouter un arbre remarquable
    public void ajouterArbre(Tree Tree) {
        arbresRemarquables.add(Tree);
        System.out.println("🌳 getArbre ajouté : " + Tree.getLibelle_france());
    }

    // ✅ Organiser une visite et enregistrer le compte-rendu
    public void organiserVisite(Visit visit) {
        visits.add(visit);
        budget.enregistrerDepense(visit.getCout());
        System.out.println("📅 Visite de l'getArbre "+ visit.getTree().getLibelle_france() +" organisée : ");
    }

    // ✅ Ajouter un don, une subvention ou une cotisation
    public void enregistrerEntree(double montant, String description) {
        budget.ajouterEntree(montant, description);
    }






    // Getters et Setters
    public String getNom() { return nom; }
    public List<Member> getMembers() { return members; }
    public List<Tree> getArbresRemarquables() { return arbresRemarquables; }
    public List<Visit> getVisites() { return visits; }

*/
}

