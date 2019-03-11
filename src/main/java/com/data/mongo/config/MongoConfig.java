package com.data.mongo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.expression.EnvironmentAccessor;
import org.springframework.core.env.Environment;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

@Configuration
@PropertySource("classpath:mongo.properties")
public class MongoConfig implements EnvironmentAware {
	
	@Autowired
	 private static Environment env ;

	 @Override
	    public void setEnvironment(final Environment environment) {
	        MongoConfig.env = environment;
	    }
	 public String getDatabaseName() {
		 
	 return env.getProperty("mongo.database");
	 }
	 public String getUri() {
		return env.getProperty("mongo.uri");
	 }
    public String getCollection() {
    	return env.getProperty("mongo.collection");
    }
	 
   }

	

	 
/*	@Autowired
	 private Environment env ;

	 @Override
	 protected String getDatabaseName() {
	 return env.getProperty("mongo.database");
	 }


	 
	
	@Override
	public MongoClient mongoClient() {
		// TODO Auto-generated method stub
		return new MongoClient(env.getProperty("mongo.host"), Integer.parseInt(env.getProperty("mongo.port")));
	}

		// ...

		
		


} */
