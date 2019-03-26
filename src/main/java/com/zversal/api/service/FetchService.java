package com.zversal.api.service;

import org.bson.Document;
import org.springframework.stereotype.Service;

import com.zversal.api.database.DatabaseQuery;

@Service
public class FetchService {

	private DatabaseQuery databasequery = new DatabaseQuery();

	public Document getData(String ticker) {
		return databasequery.getData(ticker);
	}

	public Document getTicker(String channel) {
		return databasequery.getTicker(channel);
	}

	public Document getEarningData(String ticker) {
		return databasequery.getEarningData(ticker);
	}

	public Document getSnapshot(String ticker) {
		return databasequery.getEarningData(ticker);
	}

	public Document getStats(String ticker) {
		return databasequery.getStats(ticker);
	}

}
