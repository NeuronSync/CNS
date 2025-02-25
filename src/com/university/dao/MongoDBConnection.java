package com.university.dao;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {
    private static final String DB_URI = "mongodb://localhost:27017";
    private static final String DB_NAME = "university";
    private static MongoDatabase database;

    public static MongoDatabase getDatabase() {
        if (database == null) {
            MongoClient mongoClient = MongoClients.create(DB_URI);
            database = mongoClient.getDatabase(DB_NAME);
        }
        return database;
    }
}
