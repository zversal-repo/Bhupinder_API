package com.data.mongo.controller;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.data.mongo.config.MongoConfig;

import com.data.mongo.service.MongoService;
import com.mongodb.MongoTimeoutException;

@RestController
public class MongoController {
	@Autowired
	private MongoService mongoservice;
	
	
	
	
	@RequestMapping(value="/data/{Ticker}",method=RequestMethod.GET)
	public Document getData(@PathVariable ("Ticker") String ticker) {
		Document doc = mongoservice.getData(ticker );
		return doc;
		}
	
	@RequestMapping(value="/ticker/{Channel}",method=RequestMethod.GET)
	public List<Object> getTicker(@PathVariable ("Channel") String channel) {
		List<Object> doc = null;
		try {
		 doc = mongoservice.getTicker(channel);
	    
		}
		catch(NullPointerException npe) {
			System.out.println("Null Pointer Exception");
		}
		return doc;
	}
	@RequestMapping(value="/earning/{Ticker}",method=RequestMethod.GET)
	public Object getEarningData(@PathVariable("Ticker") String ticker) {
		
		
	   Object  doc = mongoservice.getEarningData(ticker);
		
		
		
		return doc;
	
	}
	
	@RequestMapping(value="/snapshot/{Ticker}",method=RequestMethod.GET)
	public Document getsnapshot(@PathVariable("Ticker") String ticker) {
		Document doc = mongoservice.getSnapshot(ticker);
		return doc;
	}
	}
	

