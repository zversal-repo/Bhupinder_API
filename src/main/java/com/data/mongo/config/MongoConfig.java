package com.data.mongo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:mongo.properties")
public class MongoConfig implements EnvironmentAware {
    @Autowired
    private static Environment env;

    @Override
    public void setEnvironment(final Environment environment) {
	MongoConfig.env = environment;
    }

    public String getDatabaseName() {
	String databasename = env.getProperty("mongo.database");
	if (databasename.isEmpty() == true) {
	    throw new NullPointerException("databasename is empty");
	}
	return databasename;
    }

    public String getUri() {
	return env.getProperty("mongo.uri");
    }

    public String getCollection() {
	return env.getProperty("mongo.collection");
    }

}
