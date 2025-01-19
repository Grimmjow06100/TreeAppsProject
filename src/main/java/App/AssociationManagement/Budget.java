package App.AssociationManagement;

public class Budget {
    private double solde;

    public Budget() {
        this.solde = 0;
    }

    public void ajouterEntree(double montant, String description) {
        solde += montant;
        System.out.println("💰 Entrée : " + description + " (+ " + montant + "€)");
    }

    public void enregistrerDepense(double montant) {
        solde -= montant;
        System.out.println("💸 Dépense enregistrée : " + montant + "€");
    }

    public double getSolde() {
        return solde;
    }
}
