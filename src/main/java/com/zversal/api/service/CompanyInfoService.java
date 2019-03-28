package com.zversal.api.service;

import org.bson.Document;
import org.springframework.stereotype.Service;
import com.zversal.api.database.DatabaseOperation;

@Service
public class CompanyInfoService {

	private DatabaseOperation databaseOperation = new DatabaseOperation();

	public Document getData(String ticker) {
		return databaseOperation.getData(ticker);
	}

	public Document getTicker(String channel) {
		return databaseOperation.getTicker(channel);
	}

	public Document getEarningData(String ticker) {
		return databaseOperation.getEarningData(ticker);
	}

	public Document getSnapshot(String ticker) {
		return databaseOperation.getSnapshot(ticker);
	}

	public Document getStats(String ticker) {
		return databaseOperation.getStats(ticker);
	}

}
