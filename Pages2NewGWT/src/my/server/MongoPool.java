package my.server;

import java.net.UnknownHostException;
import java.util.Properties;

import org.apache.log4j.Logger;



import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

/**
 * Wraps {@link Mongo} instance and shares it through application.
 * Requires file 'mongo.properties' be present in classpath.
 * @author Roman Zaripov
 */
public final class MongoPool {
	private static Logger LOG=Logger.getLogger(MongoPool.class);
	private static Mongo grabberMongo;
	private static Mongo mainMongo;
	private static Mongo imgsMongo;
	private static String secretKey;
	
	private final static Properties MONGO_PROPS;
	static {
		//mongo = null;
		Properties properties = new Properties();
		//System.out.println("properties.load(MongoPool");
		try {
	//		Properties properties = new Properties();
			properties.load(MongoPool.class.getResourceAsStream("mongo.properties"));

			//properties.load(MongoPool.class.getClassLoader().getResourceAsStream("/home/cher80/_silver9_repo/silver9/Pages2NewGWT/war/WEB-INF/mongo.properties"));
			//System.out.println("properties.load(MongoPool done");
			//LOG.info("Mongo pool successfully created.");	
		} catch (Exception ex) {
			//LOG.error("Failed to create Mongo DB pool.", ex);
		}
//		MONGO = mongo;
		MONGO_PROPS = properties;
	}
	
	private static boolean isGrabberDBInited = false;
	private static boolean isMainDBInited = false;
	private static boolean isImgsDBInited = false;

	public static String getFB_client_id() {
		return MONGO_PROPS.getProperty("fb_client_id");
	}
	
	public static String getFB_redirect_uri() {
		return MONGO_PROPS.getProperty("fb_redirect_uri");
	}
	
	public static String getFB_client_secret() {
		return MONGO_PROPS.getProperty("fb_client_secret");
	}
	
	
	
	public static String getSecretKey() {
		return MONGO_PROPS.getProperty("secretkey");
	}
	
	public static DB getImgsDB() {
		if (!isImgsDBInited) {
			// = new Mongo();
			//boolean auth = db.authenticate(MONGO_PROPS.getProperty("grabberusername"), MONGO_PROPS.getProperty("grabberpassword").toCharArray());

			imgsMongo = doDBInit(
					MONGO_PROPS.getProperty("imgshost"),
					Integer.parseInt(MONGO_PROPS.getProperty("imgsport")),
					MONGO_PROPS.getProperty("imgsusername"), 
					MONGO_PROPS.getProperty("imgspassword").toCharArray()
					);
			LOG.info("ImgsDBInited inited");
			isImgsDBInited = true;
		}
		DB imgsDB = grabberMongo.getDB( "imgsdb" );
		return imgsDB;
	}
	
	public static DB getGrabberDB() {
		if (!isGrabberDBInited) {
			// = new Mongo();
			//boolean auth = db.authenticate(MONGO_PROPS.getProperty("grabberusername"), MONGO_PROPS.getProperty("grabberpassword").toCharArray());

			grabberMongo = doDBInit(
					MONGO_PROPS.getProperty("grabberhost"),
					Integer.parseInt(MONGO_PROPS.getProperty("grabberport")),
					MONGO_PROPS.getProperty("grabberusername"), 
					MONGO_PROPS.getProperty("grabberpassword").toCharArray()
					);
			LOG.info("GrabberDB inited");
			isGrabberDBInited = true;
		}
		DB grabberDB = grabberMongo.getDB( "grabberdb" );
		
		return grabberDB;
	}

	
	public static DB getMainDB() {
		if (!isMainDBInited) {
			// = new Mongo();
			//boolean auth = db.authenticate(MONGO_PROPS.getProperty("grabberusername"), MONGO_PROPS.getProperty("grabberpassword").toCharArray());

			mainMongo = doDBInit(
					MONGO_PROPS.getProperty("mainhost"),
					Integer.parseInt(MONGO_PROPS.getProperty("mainport")),
					MONGO_PROPS.getProperty("mainusername"), 
					MONGO_PROPS.getProperty("mainpassword").toCharArray()
					);
			LOG.info("MainDB inited");
			isMainDBInited = true;
		}
		DB mainDB = mainMongo.getDB("maindb");
		return mainDB;
	}

	
	private static Mongo doDBInit(String host, int port, String name, Object pass) {
		
		Mongo mongo = null;
		try {
			mongo = new Mongo(host, port);
		} catch (NumberFormatException e) {
			  LOG.error("NumberFormatException" + e);
			e.printStackTrace();
		} catch (UnknownHostException e) {
			 LOG.error("UnknownHostException" + e);
			e.printStackTrace();
		} catch (MongoException e) {
			LOG.error("MongoException" + e);
			e.printStackTrace();
		}

		DB db = mongo.getDB( "admin" );
	    //use admin
		boolean auth = db.authenticate(name, (char[]) pass);
		if (auth) {
			LOG.info("Mongo DB authentication complete!");
			
		} else {
			LOG.error("Mongo DB authentication failed");
			throw new RuntimeException("Mongo DB authentication failed");
			
		}
		return mongo;

		/*
		boolean isGrabberDBExist = false;
		
		
		DB db2 = grabberMongo.getDB( "mydb" );
		DBCollection coll = db2.getCollection("testCollection");
		
		BasicDBObject doc = new BasicDBObject();

        doc.put("name", "MongoDB");
        doc.put("type", "database");
        doc.put("count", 1);
        coll.insert(doc);
		*/
		/*
		for (String s : grabberMongo.getDatabaseNames()) {
			LOG.info("Database in grabberMongo " + s);
			if (s.equals("grabberdb")) {
				isGrabberDBExist = true;
			}
		}*/
		
	}

}