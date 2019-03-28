package com.zversal.api.database;

import org.bson.Document;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.zversal.api.configuration.ConfigProperties;

public class DatabaseConnection {

	private ConfigProperties configproperties = new ConfigProperties();
	private MongoCollection<Document> collection = null;

	private void connection() {
		System.out.println("Connection");
		String databaseName = configproperties.getDatabaseName();
		String connectionString = configproperties.getUri();
		String collectionName = configproperties.getCollection();

		try {
			MongoClient mongoClient = MongoClients.create(connectionString);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			checkDatabase(mongoClient, databaseName);
			collection = database.getCollection(collectionName);
			checkCollection(database, collectionName);
		} catch (IllegalArgumentException iae) {
			System.out.println("Database name is invalid");
			iae.printStackTrace();
			System.exit(1);
		}
	}

	public MongoCollection<Document> getCollections() {
		connection();
		return collection;
	}

	private void checkCollection(MongoDatabase database, String collectionName) {
		Boolean checkcol = false;
		MongoIterable<String> collections = database.listCollectionNames();
		for (String collectionname : collections) {
			if (collectionname.equals(collectionName)) {
				checkcol = true;
			}
		}
		if (checkcol == false) {
			System.out.println("Collection Not Found");
			System.exit(1);
		}
	}

	private void checkDatabase(MongoClient mongoClient, String databaseName) {
		Boolean checkDB = false;
		MongoIterable<String> databases = mongoClient.listDatabaseNames();
		for (String databasename : databases) {
			if (databasename.equals(databaseName)) {
				checkDB = true;
			}
		}
		if (checkDB == false) {
			System.out.println("Database Not Found");
			System.exit(1);
		}
	}
}
