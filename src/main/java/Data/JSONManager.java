package Data;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public enum JSONManager {
    INSTANCE;
    private final String BASE_URL="src/main/resources/JSONDB";
    private static final ObjectMapper objectMapper = new ObjectMapper();

    // Initialisation de l'ObjectMapper
    static {
        objectMapper.registerModule(new JavaTimeModule()); // Support pour LocalDate
    }


    // ✅ Création d'un fichier JSON s'il n'existe pas
    public synchronized void createJsonFile(String filename) {
        File file = new File(BASE_URL + "/" + filename);
        if (!file.exists()) {
            try (FileWriter writer = new FileWriter(file)) {
                writer.write("[]"); // Initialisation avec un tableau vide
                System.out.println("✅ Fichier JSON créé : " + file.getName());
            } catch (IOException e) {
                System.out.println("❌ Erreur lors de la création du fichier : " + e.getMessage());
            }
        }
    }

    // ✅ Ajout d'un ou plusieurs objets dans un fichier JSON
    public synchronized void addToJsonFile(String fileName, List<?> newData, String keyField) {
        File file = new File(BASE_URL + "/" + fileName);
        if (!file.exists()) {
            createJsonFile(fileName); // Création automatique si le fichier n'existe pas
        }

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON doit contenir un tableau.");
                return;
            }

            ArrayNode arrayNode = (ArrayNode) rootNode;
            Set<String> existingKeys = arrayNode.findValuesAsText(keyField).stream().collect(Collectors.toSet());

            for (Object obj : newData) {
                JsonNode newNode = objectMapper.valueToTree(obj);
                if (!newNode.has(keyField) || existingKeys.contains(newNode.get(keyField).asText())) {
                    System.out.println("❌ L'objet existe déjà ou n'a pas la clé `" + keyField + "`.");
                    continue;
                }
                arrayNode.add(newNode);
                existingKeys.add(newNode.get(keyField).asText());
            }

            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, arrayNode);
            System.out.println("✅ Ajout réussi dans " + fileName);
        } catch (IOException e) {
            System.out.println("❌ Erreur d'écriture JSON : " + e.getMessage());
        }
    }

    public <T> Optional<T> getAllObjectFromJson(String filenmae,Class<T>clazz){
        File file = new File(BASE_URL + "/" + filenmae);
        if (!file.exists()) {
            System.out.println("❌ Fichier non trouvé : " + filenmae);
            return Optional.empty();
        }

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON doit contenir un tableau.");
                return Optional.empty();
            }
            List<T> list = new ArrayList<>();
            for (JsonNode node : rootNode) {
                T obj = objectMapper.treeToValue(node, clazz);
                list.add(obj);
            }
            return Optional.of((T) list);
        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
        }
        return Optional.empty();
    }

    public <T> Optional<T> getObjectFromJson(String fileName, String key, String value, Class<T> clazz) {
        File file = new File(BASE_URL + "/" + fileName);
        if (!file.exists()) {
            System.out.println("❌ Fichier non trouvé : " + fileName);
            return Optional.empty();
        }

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON doit contenir un tableau.");
                return Optional.empty();
            }

            for (JsonNode node : rootNode) {
                if (node.has(key) && node.get(key).asText().equals(value)) {
                    T obj = objectMapper.treeToValue(node, clazz);
                    System.out.println("✅ Objet trouvé et converti avec succès !");
                    return Optional.of(obj);
                }
            }

            System.out.println("❌ Aucun objet trouvé avec `" + key + "` = `" + value + "`.");
        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
        }

        return Optional.empty();
    }


    // ✅ Recherche d'objets dans un fichier JSON par clé/valeur
    public List<JsonNode> searchInJson(String fileName, String key, String value) {
        File file = new File(BASE_URL + "/" + fileName);
        if (!file.exists()) {
            System.out.println("❌ Fichier non trouvé : " + fileName);
            return Collections.emptyList();
        }

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON doit contenir un tableau.");
                return Collections.emptyList();
            }

            List<JsonNode> results = new ArrayList<>();
            for (JsonNode node : rootNode) {
                if (node.has(key) && node.get(key).asText().equals(value)) {
                    results.add(node);
                }
            }

            return results.isEmpty() ? Collections.emptyList() : results;
        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
            return Collections.emptyList();
        }
    }

    public JsonNode searchInJson(String fileName, String key, String value, String key2, String value2) {
        File file = new File(BASE_URL + "/" + fileName);
        if (!file.exists()) {
            System.out.println("❌ Fichier non trouvé : " + fileName);
            return null;
        }

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON doit contenir un tableau.");
                return null;
            }

            for (JsonNode node : rootNode) {
                if (node.has(key) && node.get(key).asText().equals(value) && node.has(key2) && node.get(key2).asText().equals(value2)) {
                    return node;
                }
            }

            System.out.println("❌ Aucun objet trouvé avec `" + key + "` = `" + value + "`.");
        } catch (IOException e) {
            System.out.println("❌ Erreur de lecture JSON : " + e.getMessage());
        }

        return null;
    }

    public synchronized void modifyInJson(String fileName, String uniqueKey, String uniqueValue, JsonNode newValue){
        File file = new File(BASE_URL + "/" + fileName);
        if (!file.exists()) {
            System.out.println("❌ Fichier non trouvé : " + fileName);
            return;
        }

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON doit contenir un tableau.");
                return;
            }

            boolean updated = false;
            for (JsonNode node : rootNode) {
                if (node.has(uniqueKey) && node.get(uniqueKey).asText().equals(uniqueValue)) {
                    ((ObjectNode) node).setAll((ObjectNode) newValue);
                    updated = true;
                }
            }

            if (updated) {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, rootNode);
                System.out.println("✅ Donnée modifiée avec succès !");
            } else {
                System.out.println("❌ Aucune correspondance trouvée.");
            }

        } catch (IOException e) {
            System.out.println("❌ Erreur d'écriture JSON : " + e.getMessage());
        }
    }

    public synchronized void modifyInJson(String fileName, String uniqueKey, String uniqueValue, Object newValue) {
        File file = new File(BASE_URL + "/" + fileName);
        if (!file.exists()) {
            System.out.println("❌ Fichier non trouvé : " + fileName);
            return;
        }

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON doit contenir un tableau.");
                return;
            }

            boolean updated = false;
            JsonNode newValueNode = objectMapper.valueToTree(newValue);
            for (JsonNode node : rootNode) {
                if (node.has(uniqueKey) && node.get(uniqueKey).asText().equals(uniqueValue)) {
                    ((ObjectNode) node).setAll((ObjectNode) newValueNode);
                    updated = true;
                }
            }

            if (updated) {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, rootNode);
                System.out.println("✅ Donnée modifiée avec succès !");
            } else {
                System.out.println("❌ Aucune correspondance trouvée.");
            }

        } catch (IOException e) {
            System.out.println("❌ Erreur d'écriture JSON : " + e.getMessage());
        }
    }

    // ✅ Suppression d'un objet JSON par clé/valeur
    public synchronized void deleteFromJson(String fileName, String key, String value) {
        File file = new File(BASE_URL + "/" + fileName);
        if (!file.exists()) {
            System.out.println("❌ Fichier non trouvé : " + fileName);
            return;
        }

        try {
            JsonNode rootNode = objectMapper.readTree(file);
            if (!rootNode.isArray()) {
                System.out.println("❌ Erreur : Le fichier JSON doit contenir un tableau.");
                return;
            }

            ArrayNode arrayNode = (ArrayNode) rootNode;
            Iterator<JsonNode> iterator = arrayNode.elements();
            boolean updated = false;

            while (iterator.hasNext()) {
                JsonNode node = iterator.next();
                if (node.has(key) && node.get(key).asText().equals(value)) {
                    iterator.remove();
                    updated = true;
                }
            }

            if (updated) {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, arrayNode);
                System.out.println("✅ Suppression réussie !");
            } else {
                System.out.println("❌ Aucun objet trouvé avec `" + key + "` = `" + value + "`.");
            }

        } catch (IOException e) {
            System.out.println("❌ Erreur d'écriture JSON : " + e.getMessage());
        }
    }

    // ✅ Suppression d'un fichier JSON
    public synchronized void deleteJsonFile(String fileName) {
        File file = new File(BASE_URL + "/" + fileName);
        if (file.exists() && file.delete()) {
            System.out.println("✅ Fichier supprimé : " + fileName);
        } else {
            System.out.println("❌ Impossible de supprimer le fichier.");
        }
    }

    // ✅ Récupération du chemin de la base de données
    public String getBASE_URL() {
        return BASE_URL;
    }

    //
    public ObjectMapper getObjectMapper() {
        return objectMapper;
    }

}
