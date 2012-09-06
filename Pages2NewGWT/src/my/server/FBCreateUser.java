package my.server;

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

public class FBCreateUser extends HttpServlet {

	DB dbImgs = MongoPool.getImgsDB();
	DB db = MongoPool.getMainDB();

	public static final Logger LOG=Logger.getLogger(FBCreateUser.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		
		
		//request.getParameter("code").
		String code = request.getParameter("code").trim();
		
		
		LOG.info("code! photoId= " + code);
		
		if (code!=null) {
			
			LOG.info("code!=null ");
			    
			   
			   
			   
			
			
			String getToken = "https://graph.facebook.com/oauth/access_token?" +
					"client_id=" + MongoPool.getFB_client_id() +
					"&redirect_uri=" + MongoPool.getFB_redirect_uri() +
					"&client_secret=" + MongoPool.getFB_client_secret() +
					"&code=" + code;
			//String tokenResponse = urlReader(getToken);
			 URL oracle = new URL(getToken);
			String tokenResponse = null;
		
			try {
				tokenResponse = readPage(oracle);
			} catch (Exception e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			} 
	
			
			LOG.info("tokenResponse= " + tokenResponse);
			
			
			
			String token = getParams(tokenResponse,"access_token");
			int token_expires = Integer.parseInt( getParams(tokenResponse,"expires"));
			
			LOG.info("token= " + token);
			
			String getUser = "https://graph.facebook.com/me?" +
					"access_token=" + token;

			String userResponse = null;
			try {
				userResponse = readPage( new URL(getUser));
			} catch (Exception e) { 
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			LOG.info("userResponse= " + userResponse);
			//https://graph.facebook.com/me?access_token=
			//email
			JSONObject obj=(JSONObject)JSONValue.parse(userResponse);
			String email = (String)obj.get("email");
			String uname = (String)obj.get("name");
			LOG.info("user email uname " + email + uname);
			//SilverCookie silverCookie = new SilverCookie(response,)
			Register register = new Register();
			boolean isEmailExist = register.checkIfEmailExist(email);
			
			if (!isEmailExist) {
				User user = register.executeRegister(uname, email, "av9av9av", "av9av9av", true, 1, response, request);
				register.setFBAccesToken(user, token,token_expires);
			}
			else {
				Login login = new Login();
				User user = login.executeLogin(email, "av9av9av", true, response, request);
				register.setFBAccesToken(user, token,token_expires);
			}
			
			PrintWriter out = response.getWriter();  
            out.println( "<html><head>" );  
            out.println( "<title>Done!</title>" );  
            out.println( "</head>" );  
            out.println( "<body onload=\"window.close()\">" );  
            out.println( "<h1>Done!</h1>" );  
            out.println( "</body></html>" );  
            out.close();
		}
		
		
	}

	

	protected String getParams(String paramsLine, String vartoget) {
		String value = null;
		String delimiter = "&";
		/* given string will be split by the argument delimiter provided. */
		String[] paramsArray;
		
		paramsArray = paramsLine.split(delimiter);
		for (int i=0;i<paramsArray.length; i++) {
			
			
			String delimiter2 = "=";
			String[] paramsArray2;
			paramsArray2 = paramsArray[i].split(delimiter2);
			if (paramsArray2[0].trim().equals(vartoget)) {
				
				 value = paramsArray2[1];
				 return value;
			}
		}
		return null;
	}
	
	
	public String urlReader(String urlString) throws IOException {
		    URL oracle = new URL(urlString);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));

	        String inputLine;
	        while ((inputLine = in.readLine()) != null)
	            System.out.println(inputLine);
	        in.close();
	        
	        return inputLine;
	}

	
	private String readPage(URL url) throws Exception {

        DefaultHttpClient client = new DefaultHttpClient();
        LOG.info("url.toURI()= " + url.toURI());
        HttpGet request = new HttpGet(url.toURI());
        HttpResponse response = client.execute(request);

        Reader reader = null;
        try {
            reader = new InputStreamReader(response.getEntity().getContent());

            StringBuffer sb = new StringBuffer();
            {
                int read;
                char[] cbuf = new char[1024];
                while ((read = reader.read(cbuf)) != -1)
                    sb.append(cbuf, 0, read);
            }

            return sb.toString();

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }



}
