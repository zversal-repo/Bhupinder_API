package com.zversal.api.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.zversal.api.service.CompanyInfoService;

@RestController
public class CompanyInfoController {
	@Autowired
	private CompanyInfoService fetchservice;

	@RequestMapping(value = "/data/{Ticker}", method = RequestMethod.GET)
	public Document getData(@PathVariable("Ticker") String ticker) {
		Document doc = fetchservice.getData(ticker);
		return doc;
	}

	@RequestMapping(value = "/ticker/{Channel}", method = RequestMethod.GET)
	public Document getTicker(@PathVariable("Channel") String channel) {
		Document doc = fetchservice.getTicker(channel);
		return doc;
	}

	@RequestMapping(value = "/earning/{Ticker}", method = RequestMethod.GET)
	public Document getEarningData(@PathVariable("Ticker") String ticker) {
		Document doc = fetchservice.getEarningData(ticker);
		return doc;
	}

	@RequestMapping(value = "/snapshot/{Ticker}", method = RequestMethod.GET)
	public Document getsnapshot(@PathVariable("Ticker") String ticker) {
		Document doc = fetchservice.getSnapshot(ticker);
		return doc;
	}

	@RequestMapping(value = "/keystatsandfinancial/{Ticker}", method = RequestMethod.GET)
	public Document getsStats(@PathVariable("Ticker") String ticker) {
		Document doc = fetchservice.getStats(ticker);
		return doc;
	}

}
