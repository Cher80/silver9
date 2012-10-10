package my.server.grabber;

import java.io.IOException;
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
import my.server.CommonsServer;
import my.server.MongoPool;
import my.server.exutor.Albums;
import my.server.exutor.Register;
import my.server.exutor.UserCookie;
import my.shared.CookieObj;
import my.shared.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class PostPhotos extends HttpServlet {

	DB db = MongoPool.getMainDB();
	String session9;
	String email;
	String command;
	public static final Logger LOG=Logger.getLogger(PostPhotos.class);
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, java.io.IOException {

		command = request.getParameter("command");

		email = request.getParameter("email");
		session9 = request.getParameter("session9");


		if (command.equals("newalbum")) {
			doNewAlbum(request,response);
		}
		if (command.equals("posturl")) {
			doPostUrl(request,response);
		}

		if (command.equals("getalbums")) {
			doGetAlbums(request,response);
		}


	}


	private void doGetAlbums(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {

		PrintWriter out = response.getWriter();  
		DBCollection albums = db.getCollection("albums");

		//BasicDBObject query = new BasicDBObject();
		//query.append("cid", 245);
		//queryy.append("comments.cid", 232);


		DBCursor cur = albums.find().sort(new BasicDBObject("timestamp",1)).sort(new BasicDBObject("timestamp",-1)).limit(20);

		if (!cur.hasNext()) {
			cur.close();
			LOG.info("doGetAlbums no albums ");
			throw new RPCServiceExeption("Error: no albums");
		}

		else {
			int i=0;
			while(cur.hasNext()) {
				////System.out.println(cursor.next());
				DBObject album = cur.next();
				LOG.info("doGetAlbums album " + album);
				String albname = (String) album.get("albname");
				String alb_id = (String) album.get("_id").toString();
				LOG.info("doGetAlbums albname " + albname);

				/*
				out.println(
						"<input  type=\"hidden\" data-albid=\"" + 999 + "\" id=\"albid" + i + "\" value=\"" + alb_id + "\"/>"						
				);*/
				out.println(
						"<div class=\"albbutt\" data-albname=\"" + albname + "\" data-albid=\"" + alb_id + "\" id=\"albname" + i + "\">" + albname + "</div>"						
						);
				i++;

			}
			out.close();  
		}


	}

	private void doNewAlbum(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
		PrintWriter out = response.getWriter();  

		String albname = request.getParameter("albname");
		int albage = Integer.parseInt(request.getParameter("albage"));
		String albcity = request.getParameter("albcity");
		String albpage = request.getParameter("albpage");
		int status = 2; //not published


		CookieObj cookieObj = new CookieObj(); 
		cookieObj.setEmail(email);
		cookieObj.setMd5session(session9);

		User user = (new UserCookie()).getUserByCookie(cookieObj);

		LOG.info("doNewAlbum user " + user.toString());





		DBCollection albums = db.getCollection("albums");

		LOG.info("doNewAlbum " + albname + albage + albcity + albpage);

		BasicDBObject album = new BasicDBObject();
		album.put("albname", albname);
		album.put("albage", albage);
		album.put("albcity", albcity);
		album.put("albpage", albpage);
		album.put("timestamp", (long)System.currentTimeMillis()/1000);
		album.put("email", email);
		album.put("status", status);


		albums.insert(album);

		//Albums albumsExec = new Albums();
		//albumsExec.incAlbumsCount();
		//incAlbumsCount

		ObjectId id = (ObjectId)album.get( "_id" );
		LOG.info("ObjectId id  " + id);

		LOG.info("doNewAlbum DONE");

		updateNewAlbumsStat();
		
		out.println( id );  
		out.close();  
		
		
	}

	private void doPostUrl(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
		response.setContentType( "text/html" );  
		PrintWriter out = response.getWriter();  

		String pageurl = request.getParameter("pageurl").trim();
		String photourl = request.getParameter("photourl").trim();
		String album = request.getParameter("album").trim();
		int status = 1; //not published



		String toRet = null;

		if (pageurl==null) {
			toRet = "Error: Parametr toRet is null";
			out.println(toRet);  
			out.close();  
			return;
		}
		if (photourl==null) {
			toRet = "Error: Parametr toRet is null";
			out.println(toRet);  
			out.close();  
			return;
		}
		if (album==null) {
			toRet = "Error: Parametr album is null";
			out.println(toRet);  
			out.close();  
			return;
		}

		FileGrabber fg = new FileGrabber();
		HashMap photo = fg.getFile(photourl);

		if (photo.get("error")==null) {

			CookieObj cookieObj = new CookieObj(); 
			cookieObj.setEmail(email);
			cookieObj.setMd5session(session9);

			User user = (new UserCookie()).getUserByCookie(cookieObj);	


			DBCollection images = db.getCollection("images");

			LOG.info("doPostUrl " + photourl + album);

			BasicDBObject image = new BasicDBObject();
			image.put("photourl", photourl);
			image.put("pageurl", pageurl);
			image.put("gridfs_id_m", photo.get("saved_id_m"));
			image.put("gridfs_id_0", photo.get("saved_id_0"));
			image.put("gridfs_id_1", photo.get("saved_id_1"));
			image.put("gridfs_id_2", photo.get("saved_id_2"));
			image.put("ishasorig", photo.get("ishasorig"));
			image.put("md5", photo.get("md5"));			
			image.put("album", new ObjectId(album));
			LOG.info("images.insert(image); 2");
			image.put("status", status);
			image.put("email", email);
			image.put("timestamp", (long)System.currentTimeMillis()/1000);



			images.insert(image);



			ObjectId coverPicId = (ObjectId)photo.get("saved_id_2");
			ObjectId coverImgObjId = (ObjectId)image.get( "_id" );
			LOG.info("ObjectId id  " + coverImgObjId);

			LOG.info("doPostUrl DONE");
			updateAlbumInfo(album, coverImgObjId, coverPicId);
			toRet = "Success!";
		}
		else {
			toRet = (String) photo.get("error");
		}

		out.println(toRet);  
		out.close();  
		/*
		out.println( "<html><head>" );  
		out.println( "<title>A Sample Servlet!</title>" );  
		out.println( "</head>" );  
		out.println( "<body>" );  
		out.println( "<h1>command" + command + "</h1>" );  
		out.println( "<h1>url" + url + "</h1>" );  
		out.println( "<h1>album" + album + "</h1>" );
		out.println( "<h1>email" + email + "</h1>" );
		out.println( "<h1>session9" + session9 + "</h1>" );
		out.println( "</body></html>" );  
		out.close();  */

	}


	private void updateAlbumInfo(String albumID, ObjectId coverImgObjId, ObjectId coverPicId) {
		DBCollection albums = db.getCollection("albums");
		BasicDBObject queryup = new BasicDBObject();
		queryup.append("_id", new ObjectId(albumID));

		BasicDBObject inc = new BasicDBObject("$inc", new BasicDBObject("photocount", 1));

		albums.update(
				queryup,
				inc,
				true,
				false);


		///Set Cover Object Img
		BasicDBObject queryup2 = new BasicDBObject();

		//queryup2.append("_id", new ObjectId("503a624322d2e3398fb64b0c"));		
		queryup2.append("_id", new ObjectId(albumID));		
		BasicDBObject inc2 = new BasicDBObject("$set",new BasicDBObject("coverimgobjid", coverImgObjId));
		albums.update(
				queryup2,
				inc2,
				true,
				false);


		///Set cover pic
		BasicDBObject queryup3 = new BasicDBObject();

		//queryup2.append("_id", new ObjectId("503a624322d2e3398fb64b0c"));		
		queryup3.append("_id", new ObjectId(albumID));		
		BasicDBObject inc3 = new BasicDBObject("$set",new BasicDBObject("coverpicid", coverPicId));
		albums.update(
				queryup3,
				inc3,
				true,
				false);



		updateNewPicsStat();


	}




	private void updateNewPicsStat() {


		//Some total aggregation pics stat
		DBCollection misc = db.getCollection("misc");
		BasicDBObject queryup4 = new BasicDBObject();
		queryup4.append("stat", new BasicDBObject("$exists", true));
		//queryup.append("_id", new ObjectId(albumID));

		BasicDBObject inc4 = new BasicDBObject("$inc", new BasicDBObject("stat.totalImgs", 1));

		misc.update(
				queryup4,
				inc4,
				true,
				false);


	}




	private void updateNewAlbumsStat() {


		//Some total aggregation pics stat
		DBCollection misc = db.getCollection("misc");
		BasicDBObject queryup4 = new BasicDBObject();
		queryup4.append("stat", new BasicDBObject("$exists", true));
		//queryup.append("_id", new ObjectId(albumID));

		BasicDBObject inc4 = new BasicDBObject("$inc", new BasicDBObject("stat.totalAlbums", 1));

		misc.update(
				queryup4,
				inc4,
				true,
				false);


	}
}
