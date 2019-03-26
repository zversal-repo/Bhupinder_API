package com.zversal.api.database;

import java.util.Set;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.zversal.api.configuration.ConfigProperties;

public class DatabaseConnection {
	private ConfigProperties configproperties = new ConfigProperties();
	private MongoCollection<Document> collection = null;
	private MongoDatabase database = null;

	private void connection() {
		try {
			System.out.println("Connection");
			String databaseName = configproperties.getDatabaseName();
			if (databaseName.isEmpty()) {
				System.out.println("Database Name is Empty");
				System.exit(1);
			}
			String connectionString = configproperties.getUri();
			if (connectionString.isEmpty()) {
				System.out.println("Uri String is Empty");
				System.exit(1);
			}
			String collectionName = configproperties.getCollection();

			if (collectionName.isEmpty()) {
				System.out.println("Collection Name is Empty");
				System.exit(1);
			}

			MongoClient mongoClient = MongoClients.create(connectionString);
			MongoDatabase database = mongoClient.getDatabase(databaseName);

			collection = database.getCollection(collectionName);
			checkCollection(database, collectionName);

		} catch (IllegalArgumentException | NullPointerException e) {
			// System.out.println("ERROR");
			e.printStackTrace();
			System.exit(1);

		}
	}

	public MongoCollection<Document> getCollections() {
		connection();
		return collection;
	}

	private void checkCollection(MongoDatabase database, String collectionName) {
		MongoIterable<String> collections = database.listCollectionNames();
		for (String collectionname : collections) {

			if (collectionname.equals(collectionName) == false) {
				System.out.println("Collection not found");
				System.exit(1);
			}

		}

	}

}
