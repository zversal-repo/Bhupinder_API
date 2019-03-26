package com.zversal.api.database;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

public class DatabaseOperation {
	private DatabaseConnection connection = new DatabaseConnection();
	
	private MongoCollection<Document> collection = connection.getCollections();
	

	private FindIterable<Document> findDoc(Document basic, String[] include) {
		
		FindIterable<Document> document = null;
		try {
			document = collection.find(basic)
					.projection(Projections.fields(Projections.include(include), Projections.excludeId()));
			// MongoCursor<Document> itr = document.iterator();
		} catch (NullPointerException n) {
			System.out.println("NullPointerException");
		}
		return document;
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
		FindIterable<Document> document = collection.find(new Document("Ticker", ticker));
		Document doc = document.first();
		return doc;
	}

	public Document getTicker(String channel) {
		final String[] include = { "Ticker" };
		MongoCursor<Document> itr = findDoc(new Document("Channel", channel), include).iterator();
		List<Object> listDoc = new ArrayList<>();
		Document doc = new Document();
		while (itr.hasNext()) {
			Document mdoc = itr.next();
			listDoc.add(mdoc.get("Ticker"));
		}
		doc.put("Ticker", listDoc);
		return doc;
	}

	public Document getEarningData(String ticker) {
		final String[] keys = { "ZN3", "ZN1", "Z2B" };
		final String[][] array = {
				{ "Four QTR prior end Date", "Four QTR prior EPS", "Four QTR prior EPS", "Actual EPS for Four QTR",
						"Four QTR prior differnce", "Four QTR prior Surprise%" },
				{ "Three QTRs Actual EPS", "Company", "Long Term Growth", "Current QTR %Growth", "Next QTR %Growth",
						"Long Term Growth High", "Next FY %Growth", "No. of LTG", "Current FY %Growth",
						"Two QTRs ACtual EPS", "Last FY Actual EPS", "Long Term Growth Low" },
				{ "KEY 25", "KEY 26", "KEY 27", "KEY 28", "KEY 29", "KEY 30", "KEY31", "KEY32", "KEY33", "Company" } };
		// Document basic = new Document("Ticker", ticker);
		Document doc = findDoc(new Document("Ticker", ticker), keys).first();
		removeDoc(keys, array, doc);
		return doc;
	}

	public Document getSnapshot(String ticker) {
		final String[] include = { "CZ2", "CZ3", "ZK3.Market cap", "CZ1.No of Employees" };
		// Document basic= new Document("Ticker",ticker);
		final Document doc = findDoc(new Document("Ticker", ticker), include).first();
		final String[][] array = { { "SIC Code", "Company URL", "M-Industry Industry Description",
				"Exchange Traded Code", "Unique ID", "Sector Description" } };
		final String[] keys = { "CZ2" };
		removeDoc(keys, array, doc);
		return doc;
	}

	public Document getStats(String ticker) {
		Document doc = new Document();
		final String[] includePrice = { "ZK3.Current Price", "ZK3.52 week high", "ZK3.Avg daily", "ZK3.Beta",
				"ZK3.52 week low" };
		final String[] includeShare = { "ZK3.Market cap" };
		final String[] includeDivident = { "ZK3.Frwd Div yield", "ZK3.Indicated Annual dividend",
				"CZ1.Book value of common equity/shares outstanding for most recent completed fiscal year period",
				"DVR.Dividend - Most Recent Ex-Date", "DVR.Dividend - Most Recent Pay Date" };
		final String[] includeValuation = { "CZ1.Current Price/most currt available qtrly book value per common share",
				"CZ1.Quarter Sales growth % where Q(0)  and Q(-1) are Sales for last reported quarter and 1 quarters before the last reported quarter",
				"CZ1.Current Price/most current available annual cash flow per common share" };
		final String[] includeMGMT = { "ZK3.Return on equity(Latest QTR)", "ZK3.Return on Assets(Latest QTR)",
				"ZK3.Return on investment(latest QTR)" };
		final String[] includePerShare = { "ZK3.Cash Flow" };
		final String[] includeProfitability = { "ZK3.Pretax margin(12 months)", "ZK3.Operating Margin(12 Months)",
				"ZK3.Net Margin(12 months)" };

		Map<String, String[]> map = new HashMap<>();
		map.put("Price And Volume", includePrice);
		map.put("Share Related Items", includeShare);
		map.put("Divident Information", includeDivident);
		map.put("Valuation Ratio", includeValuation);
		map.put("Per Share Data", includePerShare);
		map.put("MGMT", includeMGMT);
		map.put("Profitability", includeProfitability);

		Iterator<Map.Entry<String, String[]>> mapiterator = map.entrySet().iterator();
		while (mapiterator.hasNext()) {
			Map.Entry<String, String[]> mapElement = mapiterator.next();
			Document appendDoc = findDoc(new Document("Ticker", ticker), (mapElement.getValue())).first();
			if (appendDoc != null) {
				doc.append(mapElement.getKey(), appendDoc);
			} else {
				doc = null;
			}
		}
		return doc;
	}

}
