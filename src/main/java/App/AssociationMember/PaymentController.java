package App.AssociationMember;

import Data.JsonManager;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import others.Message;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Map;

public class PaymentController {

    @FXML
    private Label totalAmountLabel;

    @FXML
    private TextField nameField, cardNumberField, expiryDateField, cvvField;

    @FXML
    private Button payer;

    private JsonNode user;
    private JsonNode association;

    public void initialize() {

    }

    public void set(double amount, JsonNode user,JsonNode association) {
        this.user = user;
        this.association = association;
        totalAmountLabel.setText("€" + amount);

        payer.setOnAction(event -> handlePayment());
    }

    private boolean validateFields() {
        if (nameField.getText().isEmpty() ||
            cardNumberField.getText().isEmpty() ||
            expiryDateField.getText().isEmpty() ||
            cvvField.getText().isEmpty()) {
            return false;
        }

        // Validate card number (assuming it should be 16 digits)
        if (!cardNumberField.getText().matches("\\d{16}")) {
            return false;
        }

        // Validate expiry date (assuming format MM/YY)
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yy");
            formatter.parse(expiryDateField.getText());
        } catch (DateTimeParseException e) {
            return false;
        }

        // Validate CVV (assuming it should be 3 digits)
        if (!cvvField.getText().matches("\\d{3}")) {
            return false;
        }

        return true;
    }



    private void handlePayment() {
        if (validateFields()) {
            Message.showInformation("Paiement", "Paiement accepté ✅");

            ArrayNode cotisations = (ArrayNode) user.get("cotisationsPayees");
            System.out.println(cotisations);
            double budget = association.get("budget").asDouble();
            cotisations.add(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            payer.setDisable(true);


            //UpdateDataBase
            JsonManager.updateJsonObject("Members_JSON.json",Map.entry("identifiant",user.get("identifiant")),Map.entry("cotisationsPayees",cotisations));
            JsonManager.updateJsonObject("Members_JSON.json",Map.entry("identifiant",user.get("identifiant")),Map.entry("cotisationPayee",true));
            JsonManager.updateJsonObject("Association_JSON.json",Map.entry("nom",association.get("nom")),Map.entry("budget",budget+50.0));

        } else {
            Message.showAlert("Validation Error", "Please fill in all fields correctly.");
        }
    }


}
