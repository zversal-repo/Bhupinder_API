package com.zversal.api.database;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.zversal.api.configuration.ConfigProperties;

public class DatabaseConnection {
	private ConfigProperties configproperties = new ConfigProperties();
	private MongoCollection<Document> collection = null;

	private void connection() {
		try {
			String databaseName = configproperties.getDatabaseName();
			String connectionString = configproperties.getUri();
			String mycollections = configproperties.getCollection();

			if (databaseName.isEmpty() || connectionString.isEmpty() || mycollections.isEmpty()) {
				throw new NullPointerException();
			}

			MongoClient mongoClient = MongoClients.create(connectionString);
			MongoDatabase database = mongoClient.getDatabase(databaseName);
			collection = database.getCollection(mycollections);
		} catch (IllegalArgumentException iae) {
			throw new IllegalArgumentException();
		}
	}

	public MongoCollection<Document> getCollections() {
		connection();
		return collection;

	}

}
