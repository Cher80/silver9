package my.server.htmlpages;

import java.io.BufferedReader;

import java.io.IOException;

import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;


import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import org.json.simple.JSONObject;
import org.json.simple.JSONValue;


import my.server.CommonsServer;
import my.server.MongoPool;
import my.server.exutor.Login;
import my.server.exutor.Register;
import my.shared.User;

//import com.allen_sauer.gwt.log.client.Log;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

public class PagesHTML extends HttpServlet {

	DB dbImgs = MongoPool.getImgsDB();
	DB db = MongoPool.getMainDB();
	public static String DOMAIN = "" ;
	public static int ALBUMS_PER_PAGE = 10;

	public static final Logger LOG=Logger.getLogger(PagesHTML.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		DOMAIN = getDomain(request);
		
		String toRet = "";
		
		//request.getParameter("code").
		if (request.getParameter("page")!=null) {
			//int test = (int) Integer.parseInt(request.getParameter("test"));
			String page = (String) request.getParameter("page");
			if (page.equals("albums")) {
				int pageno = (int) Integer.parseInt(request.getParameter("pageno"));				
				AlbumsPage albumsPage = new AlbumsPage();
				toRet = toRet + albumsPage.getAlbums(pageno, 10);
			}
			
			if (page.equals("album")) {
				String albid = (String) request.getParameter("albid");
				ModelPage modelPage = new ModelPage(albid);
				toRet += modelPage.getModel();
			}
			
			
			if (page.equals("img")) {
				String albid = (String) request.getParameter("albid");
				String imgid = (String) request.getParameter("imgid");
				ImgPage imgPage = new ImgPage(imgid, albid);
				toRet += imgPage.render();
			}
			
			//LOG.info("test = " + test);
		}
		
		
		else {
			LOG.info("no test param");
		}





		PrintWriter out = response.getWriter();  
		out.println( "<html><head>" );  
		out.println( "<title>Done!</title>" );  
		out.println( "</head>" );  
		//out.println( "<body onload=\"window.close()\">" );  
		out.println( toRet );  
		out.println( "</body></html>" );  
		out.close();



	}
	
	
	public String getDomain(HttpServletRequest request) {
		String domainLoc  = request.getServerName();
		LOG.info("domainLoc = " + domainLoc);
		return domainLoc;
	}







}
