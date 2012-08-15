package my.server;

import java.util.Properties;

import org.apache.log4j.Logger;



import com.mongodb.DB;
import com.mongodb.Mongo;

/**
 * Wraps {@link Mongo} instance and shares it through application.
 * Requires file 'mongo.properties' be present in classpath.
 * @author Roman Zaripov
 */
public final class MongoPool {
	private static final Logger LOG=Logger.getLogger(MongoPool.class);
	//private static final Logger LOG = LoggerFactory.getLogger(MongoPool.class);

	//private static final Mongo MONGO;
	//private static final Properties MONGO_PROPS;

	static {
		Mongo mongo = null;
//		Properties properties = new Properties();
		System.out.println("properties.load(MongoPool");
		try {
			Properties properties = new Properties();
			properties.load(MongoPool.class.getResourceAsStream("mongo.properties"));

			//properties.load(MongoPool.class.getClassLoader().getResourceAsStream("/home/cher80/_silver9_repo/silver9/Pages2NewGWT/war/WEB-INF/mongo.properties"));
			System.out.println("properties.load(MongoPool done");
			//mongo = new Mongo(properties.getProperty("host"), Integer.parseInt(properties.getProperty("port")));
			//LOG.info("Mongo pool successfully created.");	
		} catch (Exception ex) {
			LOG.error("Failed to create Mongo DB pool.", ex);
		}
		//MONGO = mongo;
		//MONGO_PROPS = properties;
	}

	private MongoPool() {} // Forbidding instance creation.

	public static void getDB() {
		/*
		if (!isValid()) {
			throw new RuntimeException("Mongo pool not initialized.");
		}
		DB db = MONGO.getDB(MONGO_PROPS.getProperty("db"));
		if (db.authenticate(MONGO_PROPS.getProperty("username"), MONGO_PROPS.getProperty("password").toCharArray())) {
			return db;
		} else {
			throw new RuntimeException("Mongo DB authentication failed");
		}*/
	}

	/*
	private static boolean isValid() {
		return MONGO != null && !MONGO_PROPS.isEmpty();
	}*/
}