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
            JsonManager j = JsonManager.INSTANCE;
            Message.showInformation("Paiement", "Paiement accepté ✅");
            j.updateNode(user, List.of(Map.entry("cotisationPayee", true)));
            ArrayNode cotisations = (ArrayNode) user.get("cotisationsPayees");
            cotisations.add(LocalDate.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
            j.updateNode(association,List.of(Map.entry("budget",association.get("budget").asDouble()+50.00)));
            System.out.println(association.get("budget").asDouble());
            payer.setDisable(true);
            System.out.println("✅ Paiement accepté !");
        } else {
            Message.showAlert("Validation Error", "Please fill in all fields correctly.");
        }
    }


}
