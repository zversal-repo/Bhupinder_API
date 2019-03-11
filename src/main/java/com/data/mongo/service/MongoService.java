package com.data.mongo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.data.mongo.config.MongoConfig;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;

import com.mongodb.MongoClientURI;
import com.mongodb.MongoException;
import com.mongodb.MongoSocketOpenException;
import com.mongodb.MongoTimeoutException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

@Service
public class MongoService {

	private MongoConfig mongoConfig = new MongoConfig();
	private String databaseName = null;
	private String connectionString = null;
	private String mycollections = null;

	private MongoClientURI connection = null;

	private MongoClient mongoClient = null;
	private MongoDatabase database = null;

	private MongoCollection<Document> collection = null;
	// private static MongoCollection<Document> newcollection
	// =database.getCollection("new");

	public MongoService() {

		try {
			this.databaseName = mongoConfig.getDatabaseName();
			this.connectionString = mongoConfig.getUri();
			this.mycollections = mongoConfig.getCollection();

			this.connection = new MongoClientURI(connectionString);

			this.mongoClient = MongoClients.create();

			this.database = mongoClient.getDatabase(databaseName);

			this.collection = database.getCollection(mycollections);
		}
		/*
		 * catch(MongoException m) { System.out.println("MongoException");
		 * 
		 * }
		 */
		  catch (IllegalArgumentException iae) {
			System.out.println("Illegal Argument Exception");	

		} 
		/*
		 * catch (MongoTimeoutException mte) {
		 * System.out.println("Mongo Timeout Exception"); }
		 */

	}

	private MongoCursor<Document> find(BasicDBObject basic, String[] include) throws NullPointerException {
		MongoCursor<Document> itr= null;
		
		FindIterable<Document> document = collection.find(basic)
				.projection(Projections.fields(Projections.include(include), Projections.excludeId()));
		itr = document.iterator();
		
		

		return itr;
	}

	private void removeDoc(String[] keys, String[][] array, Document doc) {
		try {
			for (int i = 0; i < keys.length; i++) {
				int j = 0;
				while (j != -1) {
					if (j == array[i].length) {
						j = -1;
					} else {
						((Document) doc.get(keys[i])).remove(array[i][j]);
						j++;
					}

				}
			}
		} catch (Exception e) {
			System.out.println("NO DOCUMENT OR CHECK KEYS CAREFULLY");
			System.out.println(0);
		}

	}

	public Document getData(String ticker) {
		FindIterable<Document> document = collection.find(new BasicDBObject("Ticker", ticker));
		Document doc = document.first();
		return doc;
	}

	public List<Object> getTicker(String channel) throws NullPointerException {
		
		List<Object> listDoc= null;
		
		BasicDBObject basic = new BasicDBObject("Channel", channel);
		String[] include = { "Ticker" };
		/*
		 * FindIterable<Document> document = collection.find(new
		 * BasicDBObject("Channel", channel))
		 * .projection(Projections.fields(Projections.include("Ticker"),
		 * Projections.excludeId()));
		 * 
		 * MongoCursor<Document> itr = document.iterator();
		 */
		MongoCursor<Document> itr = find(basic, include);
		
		listDoc = new ArrayList<>();

		while (itr.hasNext()) {

			Map<String, Object> mdoc = itr.next();
			listDoc.add(mdoc.get("Ticker"));

		}
		
	/*
	 * catch(NullPointerException npe) {
	 * System.out.println("Null Pointer Exception"); }
	 */

		return listDoc;
	}

	public Object getEarningData(String ticker) throws NullPointerException {
		// TODO Auto-generated method stub

		String[] keys = { "ZN3", "ZN1", "Z2B" };

		String[][] array = {
				{ "Four QTR prior end Date", "Four QTR prior EPS", "Four QTR prior EPS", "Actual EPS for Four QTR",
						"Four QTR prior differnce", "Four QTR prior Surprise%" },
				{ "Three QTRs Actual EPS", "Company", "Long Term Growth", "Current QTR %Growth", "Next QTR %Growth",
						"Long Term Growth High", "Next FY %Growth", "No. of LTG", "Current FY %Growth",
						"Two QTRs ACtual EPS", "Last FY Actual EPS", "Long Term Growth Low" },
				{ "KEY 25", "KEY 26", "KEY 27", "KEY 28", "KEY 29", "KEY 30", "KEY31", "KEY32", "KEY33", "Company" } };
		System.out.println(array[0].length);
		System.out.println(array[1].length);
		System.out.println(array[2].length);

		BasicDBObject basic = new BasicDBObject("Ticker", ticker);
		/*
		 * FindIterable<Document> document=collection.find(new BasicDBObject("Ticker",
		 * ticker)) .projection(Projections.fields(Projections.include("ZN3","ZN1",
		 * "Z2B","Z6C.KEY 1"), Projections.excludeId())); //
		 * .projection(Projections.fields(Projections.exclude(lis),Projections.excludeId
		 * ())); MongoCursor<Document> itr = document.iterator();
		 */
		MongoCursor<Document> itr = find(basic, keys);
		Document doc = null;
		while (itr.hasNext()) {
			doc = itr.next();
		}
		removeDoc(keys, array, doc);

		return doc;
	}

	public Document getSnapshot(String ticker) {
		
		String[] include={"CZ2","CZ3","ZK3.Market cap","CZ1.No of Employees"}; 
		BasicDBObject basic= new BasicDBObject("Ticker",ticker);
		/*
		 * FindIterable<Document> document=collection.find(new BasicDBObject("Ticker",
		 * ticker)) .projection(Projections.fields(Projections.include("CZ2",
		 * "CZ3","ZK3.Market cap","CZ1.No of Employees"), Projections.excludeId()));
		 */
		  
	    MongoCursor<Document> itr = find(basic,include);
		  
	
		Document doc = null;
		while (itr.hasNext()) {
			doc = itr.next();
		}

		String[] array = { "SIC Code", "Company URL", "M-Industry Industry Description", "Exchange Traded Code",
				"Unique ID", "Sector Description" };
		for (int i = 0; i < array.length; i++) {
			((Document) doc.get("CZ2")).remove(array[i]);
		}
		return doc;

	}
}
