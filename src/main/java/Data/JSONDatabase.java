package Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import net.datafaker.Faker;
import others.Personne;


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
        File file = new File(BASE_URL + "/" + filename + ".json");
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
            File file = new File(BASE_URL + "/" + fileName + ".json");

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
            objectMapper.writeValue(file, arrayNode);
            System.out.println("✅ Donnée ajoutée avec succès !");

        } catch (IOException e) {
            System.out.println("❌ Erreur lors de l'écriture dans le fichier JSON : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println("❌ Erreur : " + e.getMessage());
        }
    }

    // Méthode pour vérifier si un JSON correspond à une classe donnée
    private static boolean isValidObject(JsonNode node, Class<?> clazz) {
        try {
            objectMapper.treeToValue(node, clazz);
            return true;
        } catch (Exception e) {
            System.out.println("❌ Erreur lors de la conversion JSON → " + clazz.getSimpleName() + " : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean isValidLogin(String pathToData, String username, String password) {
        try {
            File file = new File(pathToData);
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

    public static <T> Optional<T> getObject(String pathToData, String key, String value, Class<T> clazz) {
        try {
            File file = new File(pathToData);
            if (!file.exists()) {
                System.out.println("❌ Fichier non trouvé : " + pathToData);
                return Optional.empty();
            }

            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON ne contient pas un tableau.");
                return Optional.empty();
            }

            for (JsonNode node : rootNode) {
                if (node.has(key) && node.get(key).asText().equalsIgnoreCase(value)) { // Recherche insensible à la casse
                    return Optional.of(objectMapper.treeToValue(node, clazz));
                }
            }

            System.out.println("❌ Aucune correspondance trouvée pour `" + key + "` = `" + value + "`");
        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
        }
        return Optional.empty();
    }

    // Récupérer une liste d'objets qui correspondent au critère (key = value)
    public static <T> List<T> getObjectsList(String pathToData, Class<T> clazz, String key, String value) {
        List<T> resultList = new ArrayList<>();
        try {
            File file = new File(pathToData);
            if (!file.exists()) {
                System.out.println("❌ Fichier non trouvé : " + pathToData);
                return resultList; // Retourne une liste vide si le fichier n'existe pas
            }

            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON ne contient pas un tableau.");
                return resultList;
            }

            for (JsonNode node : rootNode) {
                if (node.has(key) && node.get(key).asText().equalsIgnoreCase(value)) { // Recherche insensible à la casse
                    resultList.add(objectMapper.treeToValue(node, clazz));
                }
            }

            if (resultList.isEmpty()) {
                System.out.println("❌ Aucune correspondance trouvée pour `" + key + "` = `" + value + "`");
            } else {
                System.out.println("✅ " + resultList.size() + " correspondance(s) trouvée(s) !");
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
        }
        return resultList;
    }

    public static <T> List<T> getObjectsList(String pathToData, Class<T> clazz) {
        List<T> resultList = new ArrayList<>();
        try {
            File file = new File(pathToData);
            if (!file.exists()) {
                System.out.println("❌ Fichier non trouvé : " + pathToData);
                return resultList; // Retourne une liste vide si le fichier n'existe pas
            }

            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON ne contient pas un tableau.");
                return resultList;
            }

            for (JsonNode node : rootNode) {
                resultList.add(objectMapper.treeToValue(node, clazz));
            }

            if (resultList.isEmpty()) {
                System.out.println("❌ Aucune correspondance trouvée !");
            } else {
                System.out.println("✅ " + resultList.size() + " correspondance(s) trouvée(s) !");
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
        }
        return resultList;
    }

    public static void modifyJSON(String pathToData, String uniqueKey, String uniqueValue, String keyToUpdate, String newValue) {
        try {
            File file = new File(pathToData);
            if (!file.exists()) {
                System.out.println("❌ Fichier non trouvé : " + pathToData);
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
                objectMapper.writeValue(file, arrayNode);
                System.out.println("✅ Donnée modifiée avec succès !");
            } else {
                System.out.println("❌ Aucune correspondance trouvée pour `" + uniqueKey + "` = `" + uniqueValue + "`");
            }

        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
        }
    }

    public static void deleteObject(String pathToData, String key, String value) {
        try {
            File file = new File(pathToData);
            if (!file.exists()) {
                System.out.println("❌ Fichier non trouvé : " + pathToData);
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
                objectMapper.writeValue(file, arrayNode);
                System.out.println("✅ Donnée modifiée avec succès !");
            } else {
                System.out.println("❌ Aucune correspondance trouvée pour `" + key + "` = `" + value + "`");
            }
        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
        }

    }

    public static void deleteFile(String pathToData) {
        File file = new File(pathToData);
        if (file.delete()) {
            System.out.println("✅ Fichier supprimé avec succès : " + pathToData);
        } else {
            System.out.println("❌ Impossible de supprimer le fichier : " + pathToData);
        }
    }

    public String fullPath(String filename){
        return BASE_URL + "/" + filename;
    }

    public String getBASE_URL() {
        return BASE_URL;
    }
}

