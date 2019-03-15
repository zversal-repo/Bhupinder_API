package com.data.mongo.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

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

//	private MongoClientURI connection = null;

	private MongoClient mongoClient = null;
	private MongoDatabase database = null;

	private MongoCollection<Document> collection = null;
	

	public MongoService() {

		try {
			this.databaseName = mongoConfig.getDatabaseName();
			this.connectionString = mongoConfig.getUri();
			this.mycollections = mongoConfig.getCollection();

		//	this.connection = new MongoClientURI(connectionString);

			this.mongoClient = MongoClients.create(connectionString);

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

	private MongoCursor<Document> find(BasicDBObject basic, String[] include) {
		
		
		FindIterable<Document> document = collection.find(basic)
				.projection(Projections.fields(Projections.include(include), Projections.excludeId()));
		MongoCursor<Document> itr = document.iterator();
		
		
		

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
		} catch (NullPointerException e) {
			System.out.println("NO DOCUMENT OR CHECK KEYS CAREFULLY");
			
		}

	}

	public Document getData(String ticker) {
		FindIterable<Document> document = collection.find(new BasicDBObject("Ticker", ticker));
		Document doc = document.first();
		return doc;
	}

	public List<String> getTicker(String channel)  {
		
		List<String> listDoc= null;
		
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

			Map<String,Object> mdoc = itr.next();
			listDoc.add( (String) mdoc.get("Ticker"));

		}
		
		return listDoc;
	}

	public Document getEarningData(String ticker)  {
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

		String[][] array = {{ "SIC Code", "Company URL", "M-Industry Industry Description", "Exchange Traded Code",
				"Unique ID", "Sector Description" }};
		String[] keys = {"CZ2"};
		removeDoc(keys,array,doc);
		/*
		 * for (int i = 0; i < array.length; i++) { ((Document)
		 * doc.get("CZ2")).remove(array[i]); } return doc;
		 */
		return doc;

	}

	public Document getStats(String ticker) {
		// TODO Auto-generated method stub
		Document doc=new Document();
		String[] includePrice= {"ZK3.Current Price","ZK3.52 week high","ZK3.Avg daily","ZK3.Beta","ZK3.52 week low"};
		String[] includeShare= {"ZK3.Market cap"};
	    String[] includeDivident= {"ZK3.Frwd Div yield","ZK3.Indicated Annual dividend","CZ1.Book value of common equity/shares outstanding for most recent completed fiscal year period",
	    		                  "DVR.Dividend - Most Recent Ex-Date","DVR.Dividend - Most Recent Pay Date"};
	    String[] includeValuation= {"CZ1.Current Price/most currt available qtrly book value per common share","CZ1.Quarter Sales growth % where Q(0)  and Q(-1) are Sales for last reported quarter and 1 quarters before the last reported quarter",
	    		                   "CZ1.Current Price/most current available annual cash flow per common share"};
	    String[] includeMGMT= {"ZK3.Return on equity(Latest QTR)","ZK3.Return on Assets(Latest QTR)","ZK3.Return on investment(latest QTR)"};  	
	    String[] includePerShare= {"ZK3.Cash Flow"};
	    String[] includeProfitability= {"ZK3.Pretax margin(12 months)","ZK3.Operating Margin(12 Months)","ZK3.Net Margin(12 months)"};
		
	    Map<String,String[]> map= new HashMap<>();
		map.put("Price And Volume",includePrice);
		map.put("Share Related Items", includeShare);
		map.put("Divident Information", includeDivident);
		map.put("Valuation Ratio", includeValuation) ;
		map.put("Per Share Data",includePerShare);
		map.put("MGMT",includeMGMT);
		map.put("Profitability",includeProfitability);
		
	  
		Iterator mapiterator = map.entrySet().iterator();
	    BasicDBObject basic = new BasicDBObject("Ticker",ticker);
	   
	    
	  while(mapiterator.hasNext()) {

		    Map.Entry<String,String[]> mapElement=(Map.Entry) mapiterator.next();
		    MongoCursor<Document> itrMongo=find(basic,((String[])mapElement.getValue()));
		   // System.out.println(itrMongo);
		    Document appendDoc=new Document();
		    appendDoc=null;
		    while(itrMongo.hasNext()) {
		    	 appendDoc=itrMongo.next();
		    	 //System.out.println(appendDoc);
		    }
		 
		    if(appendDoc!=null) {
		    doc.append(mapElement.getKey().toString(), appendDoc);}
		    
		    else {
		    	doc=null;
		    }
		    
		    
		    
	  }
	    
		/*
		 * BasicDBObject basic= new BasicDBObject("Ticker",ticker);
		 * MongoCursor<Document> itrPrice=find(basic,includePrice);
		 * MongoCursor<Document> itrShare=find(basic,includeShare);
		 * 
		 * Document docPrice= new Document(); Document docShare= new Document();
		 * 
		 * while(itrPrice.hasNext() || itrShare.hasNext()) { if(itrPrice.hasNext()) {
		 * docPrice=itrPrice.next(); } if(itrShare.hasNext()) {
		 * docShare=itrShare.next();
		 * 
		 * }
		 * 
		 * }
		 * 
		 * 
		 * 
		 * 
		 * Document completedoc= new Document(); completedoc.put("Price And Volume",
		 * docPrice); completedoc.append("Share Related Item", docShare);
		 * 
		 * return completedoc;
		 */
	  return doc;
	}
}

 