package com.zversal.api.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource("classpath:mongo.properties")
public class ConfigProperties implements EnvironmentAware {
	@Autowired
	private static Environment env;

	@Override
	public void setEnvironment(final Environment environment) {
		ConfigProperties.env = environment;
	}

	public String getDatabaseName() {
		String databasename = env.getProperty("mongo.database");
		return databasename;
	}

	public String getUri() {
		String uri = env.getProperty("mongo.uri");
		return uri;
	}

	public String getCollection() {
		String collectionName = env.getProperty("mongo.collection");
		return collectionName;
	}

}
