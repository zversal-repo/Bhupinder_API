package com.data.mongo.controller;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.data.mongo.service.MongoService;

@RestController
public class MongoController {
    @Autowired
    private MongoService mongoservice;

    @RequestMapping(value = "/data/{Ticker}", method = RequestMethod.GET)
    public Document getData(@PathVariable("Ticker") String ticker) {
	Document doc = mongoservice.getData(ticker);
	return doc;
    }

    @RequestMapping(value = "/ticker/{Channel}", method = RequestMethod.GET)
    public Document getTicker(@PathVariable("Channel") String channel) {
	Document doc = mongoservice.getTicker(channel);
	return doc;
    }

    @RequestMapping(value = "/earning/{Ticker}", method = RequestMethod.GET)
    public Document getEarningData(@PathVariable("Ticker") String ticker) {
	Document doc = mongoservice.getEarningData(ticker);
	return doc;
    }

    @RequestMapping(value = "/snapshot/{Ticker}", method = RequestMethod.GET)
    public Document getsnapshot(@PathVariable("Ticker") String ticker) {
	Document doc = mongoservice.getSnapshot(ticker);
	return doc;
    }

    @RequestMapping(value = "/keystatsandfinancial/{Ticker}", method = RequestMethod.GET)
    public Document getsStats(@PathVariable("Ticker") String ticker) {
	Document doc = mongoservice.getStats(ticker);
	return doc;
    }

}
