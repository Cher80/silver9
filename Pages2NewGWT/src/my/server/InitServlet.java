package my.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.bson.types.ObjectId;

import com.allen_sauer.gwt.log.client.Log;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class InitServlet extends HttpServlet { 
	
	Logger LOG=Logger.getLogger(InitServlet.class);
	private DB db;
	
	 public void init(ServletConfig config) throws ServletException {
		 
		
		 
		 LOG.info(" System.currentTimeMillis()/1000); " +  (long)System.currentTimeMillis()/1000); 
		 
		    //System.out.println("Log4JInitServlet init() starting.");
		    String log4jfile = "WEB-INF/classes/my/server/log4j.xml";
		    //String log4jfile = config.getInitParameter("log4j-properties-location");
		    //System.out.println("log4jfile: "+ log4jfile);
		    //String log4jfile = getInitParameter("log4j-properties-location");
		    ServletContext sc = config.getServletContext();
		    ////System.out.println("log4jfile: "+ log4jfile);
		    if (log4jfile != null) {
		    	
		      String propertiesFilename = sc.getRealPath(log4jfile);
		      //System.out.println("propertiesFilename: "+ propertiesFilename);
		      DOMConfigurator.configure(propertiesFilename);
		      //LOG.info("logger configured.");
		    }else{
		      //System.out.println("Error setting up logger.");
		    }
		  //System.out.println("Log4JInitServlet init() done.");
		  
		  LOG.info("InitServlet LOG4J!"); 
		  
		  
		  MongoPool.getGrabberDB();
		  MongoPool.getMainDB();
		  db = MongoPool.getMainDB();
		  ensureIndexes();
		  super.init(config);

	}
	 
	 
	 public void ensureIndexes() {
		 DBCollection albums = db.getCollection("albums");
		 BasicDBObject obj = new BasicDBObject();
		 obj.put("tags2.LIKE.tagiswinner", 1);
		 obj.put("timestamp", -1);
		 obj.put("tags2.LIKE.tagtotalpluses", -1);
		 albums.ensureIndex(obj);
		 
		 
		 obj = new BasicDBObject();
		 obj.put("status", 1);
		 obj.put("timestamp", -1);
		 albums.ensureIndex(obj);
		 
		 DBCollection users = db.getCollection("users");
		 obj = new BasicDBObject();
		 obj.put("email", 1);
		 users.ensureIndex(obj);
		 
		 
		 DBCollection images = db.getCollection("images");
		 obj = new BasicDBObject();
		 obj.put("album", 1);
		 obj.put("status", 1);
		 obj.put("timestamp", -1);
		 images.ensureIndex(obj);
		 
		 
		 DBCollection activities = db.getCollection("activity");
		 obj = new BasicDBObject();
		 obj.put("timestamp", -1);
		 activities.ensureIndex(obj);
		 
		 DBCollection comments = db.getCollection("comments");
		 obj = new BasicDBObject();
		 obj.put("albumid", 1);
		 obj.put("timestamp", -1);
		 comments.ensureIndex(obj);
		 
		 LOG.debug("Indexes intited");

	 }
}
