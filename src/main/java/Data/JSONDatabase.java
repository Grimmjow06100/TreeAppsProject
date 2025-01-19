package Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;



public class JSONDatabase implements JSONCheckLoginInfo {
    private final String BASE_URL;
    private static final ObjectMapper objectMapper = new ObjectMapper();


    public JSONDatabase(String base_url) {
        this.BASE_URL = base_url;

        // Créer un répertoire si celui-ci n'existe pas
        File directory = new File(BASE_URL);
        if (!directory.exists()) {
            if (directory.mkdirs()) {
                System.out.println("✅ Répertoire créé avec succès : " + BASE_URL);
            } else {
                System.out.println("❌ Impossible de créer le répertoire : " + BASE_URL);
            }
        }
    }

    public void createJsonFile(String filename) {
        File file = new File(BASE_URL + "/" + filename);
        try {
            if (file.createNewFile()) {
                System.out.println("✅ Fichier créé avec succès : " + file.getName());
                // Écrire un tableau JSON vide "[]"
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write("[]"); // Initialisation du fichier avec un tableau vide
                }
            } else {
                System.out.println("❌ Impossible de créer le fichier ou fichier déjà existant : " + file.getName());
            }
        } catch (Exception e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }

    // Méthode pour ajouter un objet validé dans le fichier JSON
    public void addToJsonFile(String fileName, Object newData, Class<?> clazz) {
        try {
            File file = new File(BASE_URL + "/" + fileName);

            // Vérifier si le fichier JSON existe
            if (!file.exists()) {
                System.out.println("❌ Erreur : Le fichier JSON n'existe pas !");
                return;
            }

            // Charger le fichier JSON existant
            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON ne contient pas un tableau !");
                return;
            }

            ArrayNode arrayNode = (ArrayNode) rootNode;

            // Vérifier si newData correspond bien à la classe spécifiée
            JsonNode newNode = objectMapper.valueToTree(newData);
            if (!isValidObject(newNode, clazz)) {
                System.out.println("❌ Erreur : Les données ne correspondent pas à la classe " + clazz.getSimpleName());
                return;
            }

            for (JsonNode node : arrayNode) {
                if (node.has("uniqueId") && node.get("uniqueId").asInt() == newNode.get("uniqueId").asInt()) {
                    System.out.println("❌ Erreur : L'objet existe déjà");
                    return; // L'objet existe déjà
                }
            }

            arrayNode.add(newNode);
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, arrayNode);
            System.out.println("✅ Donnée ajoutée avec succès !");

        } catch (IOException e) {
            System.out.println("❌ Erreur lors de l'écriture dans le fichier JSON : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }

    public List<JsonNode> jsonFilter(String fileName, String key, String value) {
        try {
            File file = new File(BASE_URL + "/" + fileName);
            if (!file.exists()) {
                System.out.println("❌ Fichier non trouvé : " + fileName);
                return new ArrayList<>();
            }

            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON ne contient pas un tableau.");
                return new ArrayList<>();
            }

            ArrayNode arrayNode = (ArrayNode) rootNode;

            // ✅ Trouver les objets qui correspondent à la clé/valeur
            List<JsonNode> filteredNodes = arrayNode.findParents(key).stream()
                    .filter(node -> node.get(key).asText().equals(value))
                    .toList();

            // ✅ Afficher les résultats
            if (filteredNodes.isEmpty()) {
                System.out.println("❌ Aucun résultat trouvé pour `" + key + "` = `" + value + "`");
                return new ArrayList<>();
            } else {
                System.out.println("✅ Résultats trouvés :");
                filteredNodes.forEach(System.out::println);
                return filteredNodes;
            }



        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
        }
        return new ArrayList<>();
    }

    // Méthode pour vérifier si un JSON correspond à une classe donnée
    private boolean isValidObject(JsonNode node, Class<?> clazz) {
        try {
            objectMapper.treeToValue(node, clazz);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la conversion JSON → " + clazz.getSimpleName() + " : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isValidLogin(String fileName, String username, String password) {
        try {
            File file = new File(BASE_URL + "/" + fileName);
            if (!file.exists()) return false;

            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) return false;

            for (JsonNode node : rootNode) {
                if (node.has("username") && node.has("password") &&
                        node.get("username").asText().equals(username) &&
                        node.get("password").asText().equals(password)) {
                    return true; // Nom d'utilisateur et mot de passe correspondent
                }
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
        return false;
    }
    public void modifyNodeFromJSON(String fileName, String uniqueKey, String uniqueValue, String keyToUpdate, String newValue) {
        try {
            File file = new File(BASE_URL + "/" + fileName);
            if (!file.exists()) {
                System.out.println("❌ Fichier non trouvé : " + fileName);
                return;
            }

            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON ne contient pas un tableau.");
                return;
            }

            ArrayNode arrayNode = (ArrayNode) rootNode;
            boolean updated = false;

            // Parcourir les objets du tableau JSON
            for (JsonNode node : arrayNode) {
                if (node.has(uniqueKey) && node.get(uniqueKey).asText().equals(uniqueValue)) {
                    if (node.isObject()) { // Vérifier que c'est bien un ObjectNode
                        ((ObjectNode) node).put(keyToUpdate, newValue);
                        updated = true;
                    }
                }
            }

            if (updated) {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, arrayNode);
                System.out.println("✅ Donnée modifiée avec succès !");
            } else {
                System.out.println("❌ Aucune correspondance trouvée pour `" + uniqueKey + "` = `" + uniqueValue + "`");
            }

        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
        }
    }

    public void deleteNodeFromJSON(String fileName, String key, String value) {
        try {
            File file = new File(BASE_URL + "/" + fileName);
            if (!file.exists()) {
                System.out.println("❌ Fichier non trouvé : " + fileName);
                return;
            }
            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON ne contient pas un tableau.");
                return;
            }
            ArrayNode arrayNode = (ArrayNode) rootNode;
            Iterator<JsonNode> iterator = arrayNode.elements();
            boolean updated = false;
            while (iterator.hasNext()) {
                JsonNode node = iterator.next();
                if (node.has(key) && node.get(key).asText().equals(value)) {
                    iterator.remove(); // ✅ Supprime l'élément en toute sécurité
                    updated = true;
                }
            }

            if (updated) {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, arrayNode);
                System.out.println("✅ Donnée modifiée avec succès !");
            } else {
                System.out.println("❌ Aucune correspondance trouvée pour `" + key + "` = `" + value + "`");
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
        }

    }

    public void deleteFile(String fileName) {
        File file = new File(BASE_URL + "/" + fileName);
        if (file.delete()) {
            System.out.println("✅ Fichier supprimé avec succès : " + fileName);
        } else {
            System.out.println("❌ Impossible de supprimer le fichier : " + fileName);
        }
    }
    public String getBASE_URL() {
        return BASE_URL;
    }
}

