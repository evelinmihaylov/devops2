package com.napier.sem;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoCollection;
import org.bson.Document;

public class Main {
    public static void main(String[] args) {
        // Use try-with-resources so MongoClient is closed automatically

        try (MongoClient mongoClient = new MongoClient("mongo-dbserver", 27017)) {

            // Get a database - will create when we use it


            MongoDatabase database = mongoClient.getDatabase("mydb");
            // Get a collection from the database
            MongoCollection<Document> collection = database.getCollection("test");

            // Create a document to store
            Document doc = new Document("name", "Kevin Sim")
                    .append("class", "DevOps")
                    .append("year", "2024")
                    .append("result", new Document("CW", 95).append("EX", 85));

            // Add document to collection
            collection.insertOne(doc);

            // Check document in collection
            Document myDoc = collection.find().first();
            if (myDoc != null) {
                System.out.println(myDoc.toJson());
            } else {
                System.out.println("No document found!");
            }
        }
    }
}