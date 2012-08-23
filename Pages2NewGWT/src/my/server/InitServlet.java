package my.server;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class InitServlet extends HttpServlet { 
	
	Logger LOG=Logger.getLogger(InitServlet.class);
	
	 public void init(ServletConfig config) throws ServletException {
		    System.out.println("Log4JInitServlet init() starting.");
		    String log4jfile = "WEB-INF/classes/my/server/log4j.xml";
		    //String log4jfile = config.getInitParameter("log4j-properties-location");
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
		  LOG.info("InitServlet LOG4J!"); 
		  
		  
		  MongoPool.getGrabberDB();
		  MongoPool.getMainDB();
		  super.init(config);

	}
}
