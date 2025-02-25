package com.university.dao;

import org.bson.Document;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.util.ArrayList;
import java.util.List;

public class StudyMaterialDAO {
    private static final String COLLECTION_NAME = "study_materials";
    private static final MongoDatabase database = MongoDBConnection.getDatabase();
    private static final MongoCollection<Document> collection = database.getCollection(COLLECTION_NAME);

    public static void addMaterial(String title, String content) {
        Document material = new Document("title", title)
                .append("content", content);
        collection.insertOne(material);
    }

    public static List<String> getMaterials() {
    List<String> materials = new ArrayList<>();
    for (Document doc : collection.find()) {
        String title = doc.getString("title");
        String subject = doc.getString("subject");
        String uploadedBy = doc.getString("uploadedBy");
        String fileUrl = doc.getString("fileUrl");

        String materialInfo = String.format("📖 %s (%s) - Uploaded by %s\n🔗 %s",
                                             title, subject, uploadedBy, fileUrl);
        materials.add(materialInfo);
    }
    return materials;
}
}
