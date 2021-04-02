package com.polyakov.service;

import com.mongodb.client.*;
import org.bson.Document;

import java.util.HashMap;
import java.util.stream.StreamSupport;

public class RControllerDB {

    private final MongoDatabase database;

    private final String host;

    public RControllerDB(String host) {
        MongoClient mongoClient = MongoClients.create();
        mongoClient.startSession();
        database = mongoClient.getDatabase("MongoDb");
        this.host = host;
    }

    public boolean checkHostInDBAndCreateIfNotExist() {
        MongoIterable<String> collectionNames = database.listCollectionNames();
        boolean isExist = StreamSupport.stream(collectionNames.spliterator(), false).anyMatch(name -> name.equals(host));
        if (!isExist) {
            database.createCollection(host);
        }
        return isExist;
    }

    public HashMap<String, Integer> getDataFromMap() {
        MongoCollection<Document> collection = database.getCollection(host);

        FindIterable<Document> documents = collection.find();
        HashMap<String, Integer> result = new HashMap<>();
        StreamSupport.stream(documents.spliterator(), false).forEach(document ->
                result.put(document.get("key", String.class), document.get("value", Integer.class)));
        return result;
    }

    public void writeInDb(String key, Integer value) {
        MongoCollection<Document> collection = database.getCollection(host);

        Document document = new Document();
        document.put("key", key);
        document.put("value", value);
        collection.insertOne(document);
    }
}
