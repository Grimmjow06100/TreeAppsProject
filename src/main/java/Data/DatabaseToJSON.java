package Data;
import java.sql.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.File;
import java.io.IOException;

public class DatabaseToJSON {
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/treeappsproject?useSSL=false"; // Remplace par ton URL
    private static final String USER = "root";  // Remplace par ton utilisateur
    private static final String PASSWORD = "Mysqlsamytch5!";  // Remplace par ton mot de passe
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static void exportTableToJson(String jsonFilePath) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(
                     "SELECT genre, espece, circonference, libelle_france, hauteur, stade_de_developpement, lieu, " +
                             "ST_X(geo_point_2d) AS latitude, ST_Y(geo_point_2d) AS longitude, remarquable " +
                             "FROM arbre WHERE stade_de_developpement IS NOT NULL AND remarquable IS NOT NULL LIMIT 2000;"
             )) {

            // ✅ Création du tableau JSON depuis la BDD
            ArrayNode newArrayNode = objectMapper.createArrayNode();
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                ObjectNode rowObject = objectMapper.createObjectNode();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = metaData.getColumnName(i);
                    rowObject.put(columnName, rs.getString(i)); // Stocker tout en String
                }
                newArrayNode.add(rowObject);
            }

            // ✅ Vérifier si le fichier existe déjà
            File file = new File(jsonFilePath);
            if (file.exists()) {
                // 🔹 Charger les données actuelles du fichier JSON
                JsonNode existingRootNode = objectMapper.readTree(file);
                if (existingRootNode.isArray()) {
                    ArrayNode existingArrayNode = (ArrayNode) existingRootNode;

                    // 🔹 Vérifier si les nouvelles données sont déjà enregistrées
                    if (existingArrayNode.equals(newArrayNode)) {
                        System.out.println("ℹ️ Les données ont déjà été exportées. Aucune mise à jour nécessaire.");
                        return;
                    }
                }
            }

            // ✅ Écrire dans le fichier JSON (si les données sont nouvelles)
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, newArrayNode);
            System.out.println("✅ Export réussi : " + jsonFilePath);

        } catch (SQLException e) {
            System.out.println("❌ Erreur SQL : " + e.getMessage());
        } catch (IOException e) {
            System.out.println("❌ Erreur d'écriture JSON : " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        exportTableToJson("C:\\Users\\samy0\\OneDrive\\Bureau\\Polytech\\Info\\Java\\ET4\\demo\\src\\main\\resources\\JSONDB\\arbres.json"); // Remplace "arbres" par le nom de ta table
    }
}
