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
		String databasename = null;
		try {
			databasename = env.getProperty("mongo.database");
			if (databasename.isEmpty()) {
				System.out.println("DatabaseName is Empty ");
				System.exit(1);
			}
		} catch (NullPointerException n) {
			System.out.println("mongo.database property is not present in property file");
			n.printStackTrace();
			System.exit(1);
		}
		return databasename;
	}

	public String getUri() {
		String uri = null;
		try {
			uri = env.getProperty("mongo.uri");
			if (uri.isEmpty()) {
				System.out.println("Uri is Empty");
				System.exit(1);
			}
		} catch (NullPointerException n) {
			System.out.println("mongo.uri property is not present in property file");
			n.printStackTrace();
			System.exit(1);
		}
		return uri;
	}

	public String getCollection() {
		String collectionName = null;
		try {
			collectionName = env.getProperty("mongo.collection");
			if (collectionName.isEmpty()) {
				System.out.println("collectionName is Empty");
				System.exit(1);
			}
		} catch (NullPointerException n) {
			System.out.println("mongo.collection property is not present in property file");
			n.printStackTrace();
			System.exit(1);
		}
		return collectionName;
	}

}
