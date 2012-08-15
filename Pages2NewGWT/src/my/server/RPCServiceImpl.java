package my.server;

import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.apache.log4j.xml.DOMConfigurator;

import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceExeption;
import my.server.exutor.Register;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.Mongo;
import com.mongodb.MongoException;

public class RPCServiceImpl extends RemoteServiceServlet implements RPCService {

	Logger LOG=Logger.getLogger(RPCServiceImpl.class);
	private static final long serialVersionUID = 1L;
	/*
	  public void init(ServletConfig config) throws ServletException {
		    System.out.println("Log4JInitServlet init() starting.");
		    String log4jfile = config.getInitParameter("log4j-properties-location");
		    System.out.println("log4jfile: "+ log4jfile);
		    //String log4jfile = getInitParameter("log4j-properties-location");
		    ServletContext sc = config.getServletContext();
		    //System.out.println("log4jfile: "+ log4jfile);
		    if (log4jfile != null) {
		    	
		      String propertiesFilename = sc.getRealPath(log4jfile);
		      System.out.println("propertiesFilename: "+ propertiesFilename);
		      DOMConfigurator.configure(propertiesFilename);
		      //LOG.info("logger configured.");
		    }else{
		      System.out.println("Error setting up logger.");
		    }
		  System.out.println("Log4JInitServlet init() done.");
		      
		  super.init(config);

	}
	  */
	/**
	 * 
	 */
	

	@Override
	public int doRegister(int uid) throws RPCServiceExeption {
		//Logger LOG=Logger.getLogger(RPCServiceImpl.class);
		LOG.info("doRegister LOG4J!");
		/*
		Mongo m = null;
		try {
			m = new Mongo();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MongoException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		DB db = m.getDB( "leforum" );
		DBCollection coll = db.getCollection("forums");
		DBObject myDoc = coll.findOne();
		ServerLogger.getInstance().getLogger().log(Level.INFO, myDoc.toString());
		*/
		
		/*
		DB db = DBConnector.getInstance().getMainDB();
		//DBConnector.getInstance();
		
		// TODO Auto-generated method stub
		
		DBCollection coll = db.getCollection("forums");
		//DBCollection coll = db.getCollection("forums");

		DBObject myDoc = coll.findOne();
		Map mapDoc = myDoc.toMap();
		
		//mapDoc.get(arg0)
		//ServerLogger.getInstance().getLogger().log(Level.INFO, myDoc.toString());
		
		LinkedHashMap lhmDoc = (LinkedHashMap)myDoc;
		
		
		System.out.println(myDoc);
		System.out.println(lhmDoc);
		System.out.println(mapDoc);
		System.out.println(mapDoc.get("fid"));
		
		ArrayList groups = (ArrayList) mapDoc.get("groups"); 
		System.out.println(groups);
		
		Map group1 = (Map)groups.get(1);
		System.out.println(group1);
		
		ArrayList fids = (ArrayList) group1.get("fids"); 
		System.out.println(fids);

		Map fid1 = (Map)fids.get(1);
		System.out.println(fid1);

		Object curfid = fid1.get("fid");
		System.out.println(curfid);
		*/
		//if (true) 
			//throw new RPCServiceExeption("Eto strashnaya oshibka");
		
		Register register =  new Register();
		int result = register.executeRegister();
		return result*2;
	}

}
