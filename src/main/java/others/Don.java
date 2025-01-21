package others;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicInteger;

public class Don {
    private static final AtomicInteger compteur = new AtomicInteger(1);

    @JsonProperty("id")
    private final int id;

    @JsonProperty("montant")
    private double montant;

    @JsonProperty("donateur")
    private String donateur;

    @JsonProperty("dateDon")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private LocalDate dateDon;

    // ✅ Constructeur
    public Don(double montant, String donateur) {
        this.id = compteur.getAndIncrement();
        this.montant = montant;
        this.donateur = donateur;
        this.dateDon = LocalDate.now();
    }

    // ✅ Getter et Setter
    public int getId() { return id; }
    public double getMontant() { return montant; }
    public void setMontant(double montant) { this.montant = montant; }

    public String getDonateur() { return donateur; }
    public void setDonateur(String donateur) { this.donateur = donateur; }

    public LocalDate getDateDon() { return dateDon; }
    public void setDateDon(LocalDate dateDon) { this.dateDon = dateDon; }

    @Override
    public String toString() {
        return "Don{id=" + id + ", montant=" + montant + ", donateur='" + donateur + '\'' +
                ", dateDon=" + dateDon + '}';
    }
}
