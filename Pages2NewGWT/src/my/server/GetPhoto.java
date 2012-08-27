package my.server;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import my.client.rpcs.RPCServiceExeption;
import my.shared.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSDBFile;

public class GetPhoto extends HttpServlet {

	DB dbImgs = MongoPool.getImgsDB();
	DB db = MongoPool.getMainDB();

	public static final Logger LOG=Logger.getLogger(GetPhoto.class);
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {
		
		
		
		String photoId = request.getParameter("photoid").trim();
		
		
		LOG.info("GetPhoto! photoId= " + photoId);
		
		if (!photoId.equals("null")&&photoId!=null) {
			LOG.info("photoId!=null");
		/*
		File file = new File("/imgs/1.jpg");

		int ch;
		StringBuffer strContent = new StringBuffer("");
		FileInputStream fin = null;
		try {
			fin = new FileInputStream(file);
			while ((ch = fin.read()) != -1)
				strContent.append((char) ch);
			fin.close();
		} catch (Exception e) {
			System.out.println(e);
		}
		//System.out.println(strContent.toString());
		LOG.info(strContent.toString());
		 */
		
		byte[] ba = read(new File("/imgs/1.jpg"));

		GridFS gfs = new GridFS(dbImgs, "images");
		//5039ce5e22d2dd8db32a1333
		BasicDBObject query = new BasicDBObject();
		query.append("_id", new ObjectId(photoId));
		
		GridFSDBFile image = gfs.findOne(query);
		image.getFilename();
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();

		image.writeTo(buffer);
		byte[] ba2 = buffer.toByteArray();
		
		/*
		PrintWriter out = response.getWriter();  
		out.println( image.getFilename() );  
		out.close();  
		*/
		
		
		response.setContentType("image/jpeg");
		response.setContentLength(ba2.length);

		response.getOutputStream().write(ba2);
		}
		else {
			//response.setContentType("image/jpeg");
			//response.setContentLength(ba2.length);
			response.setContentType( "text/html" );  
			PrintWriter out = response.getWriter();  
			out.println("No photo");  
			out.close();  
			
			//response.getOutputStream().write("No photo".toCharArray());
		}
	}


	public byte[] read(File file) throws IOException {

		byte []buffer = new byte[4096];
		ByteArrayOutputStream ous = new ByteArrayOutputStream();
		InputStream ios = new FileInputStream(file);
		int read = 0;
		while ( (read = ios.read(buffer)) != -1 ) {
			ous.write(buffer, 0, read);
		}
		return ous.toByteArray();
	}



}
