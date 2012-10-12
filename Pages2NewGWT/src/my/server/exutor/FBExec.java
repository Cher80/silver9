package my.server.exutor;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import my.client.rpcs.RPCServiceExeption;
import my.server.CommonsServer;
import my.server.MongoPool;
import my.shared.AlbumObj;
import my.shared.ImgObj;
import my.shared.User;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

public class FBExec {
	public static final Logger LOG=Logger.getLogger(FBExec.class);
	DB db = MongoPool.getMainDB();
	public User user;
	public AlbumObj albumObj;
	public ImgObj imgObj;
	public String filePath;
	public String pagePath;
	//public String

	public FBExec(AlbumObj _albumObj, User _user) {
		this.user = _user;
		this.albumObj = _albumObj;
	}

/*
	public void fbGetAccesToken() {
		DBCollection users = db.getCollection("users");

		BasicDBObject query = new BasicDBObject();

		query.append("_id",  new ObjectId(user.getUid().trim()));

		BasicDBObject sort = new BasicDBObject();
		sort.append("timestamp", -1);

		DBCursor cur;
		cur = users.find(query).sort(sort);

		
		if (!cur.hasNext()) {
			cur.close();
			throw new RPCServiceExeption("You need to be FB logged");
		}

		//else {


		
		while(cur.hasNext()) {
			BasicDBObject user = (BasicDBObject)cur.next();
			//ImgObj imgObj = converFromDBOtoObj(image);
			
			if (user.containsField("albage")) 
				albumObj.setAlbage((int)album.get("albage"));
			
			LOG.info("image.get(photourl).toString() " + image.get("photourl").toString());
			imgsObj.getImages().add(imgObj);
		}

	}
	*/

	
	public void fbAccessToken() throws RPCServiceExeption {
		LOG.info("fbAccessToken  " + user.getFBaccess_token());
		if (!user.isFBUser()) {
			throw new RPCServiceExeption("You need to be registered as FB user");
		}
		
		if (user.getFBaccess_token() == null || user.getFBaccess_token() =="") {
			throw new RPCServiceExeption("Cant find FB access token. Please relogin");
		}
		
		
		
		
	}
	
	
	public String getPageAccessToken() throws RPCServiceExeption {
		String getModeratedAccsString = "https://graph.facebook.com/me/accounts?access_token=" + user.getFBaccess_token();
		//String tokenResponse = urlReader(getToken);
		URL getModeratedAccsURL = null;
		try {
			getModeratedAccsURL = new URL(getModeratedAccsString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		String getModeratedAccsResponse = null;

		try {
			getModeratedAccsResponse = CommonsServer.readPage(getModeratedAccsURL);
			LOG.info("FBPost OK" + getModeratedAccsResponse);
		} catch (Exception e1) {
			// TODO Auto-generated catch block

			LOG.info("FBPost error" + e1);
		} 
		
		
		
		JSONObject obj=(JSONObject)JSONValue.parse(getModeratedAccsResponse);
		
		//JSONObject dataJson=(JSONObject)JSONValue.parse(getModeratedAccsResponse);
		//JSONObject errorJson=(JSONObject)JSONValue.parse(getModeratedAccsResponse);
		
		//if (obj.get("data")!=null)
		//JSONObject dataJson = (JSONObject)obj.get("data");
		//JSONObject errorJson = (JSONObject)obj.get("error");
		if (obj.get("error")!=null) {
			throw new RPCServiceExeption("ERROR" + obj.get("error"));
		}
		
		String pageAccessToken = null;
		
		if (obj.get("data")!=null) {
			
			JSONArray dataJson = (JSONArray)obj.get("data"); 
			for (int i=0;i<dataJson.size();i++) {
				dataJson.get(i);
				LOG.info("dataJson i " + i + dataJson.get(i));
				LOG.info("dataJson i " + i +  ((JSONObject)dataJson.get(i)).get("category"));
				String category = (String) ((JSONObject)dataJson.get(i)).get("category");
				String name = (String) ((JSONObject)dataJson.get(i)).get("name");
				if ( category.equals("Magazine") && name.equals("PinBelle")  )  {
					pageAccessToken = (String) ((JSONObject)dataJson.get(i)).get("access_token");
					LOG.info("GOT PAGE TOKEN: " + pageAccessToken);
				}
			}
		}
		
		if (pageAccessToken==null) {
			throw new RPCServiceExeption("ERROR Yo are dont administrator of Pinbelle page");
		}
		return pageAccessToken;
		
	}
	

	
	

	public String doPostPage(String pageAccessToken) throws RPCServiceExeption {
		//String graphPostString = "https://graph.facebook.com/PinBelle/feed?access_token=" + user.getFBaccess_token() + "&message=olo";
		//String tokenResponse = urlReader(getToken);
		
		/*
		URL graphPostURL = null;
		try {
			graphPostURL = new URL(graphPostString);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  

		String graphPostResponse = null;

		try {
			graphPostResponse = CommonsServer.readPage(graphPostURL);
			LOG.info("FBPost OK" + graphPostResponse);
		} catch (Exception e1) {
			// TODO Auto-generated catch block

			LOG.info("FBPost error" + e1);
		} */
		
		//String graphPostURL = "https://graph.facebook.com/PinBelle/feed?access_token=" + user.getFBaccess_token();
		//String graphPostParams = "message=http://en.wikipedia.org/wiki/POST_%28HTTP%29" + System.currentTimeMillis();
		
		//List <NameValuePair> nvps = new ArrayList <NameValuePair>();
		//nvps.add(new BasicNameValuePair("message", "http://yssa.ru"));
		//nvps.add(new BasicNameValuePair("picture", "http://www.pinbelle.com/extranewgwt/getphoto?photoid=5075ddade4b02821650bf8be"));
		//String graphPostResponse = CommonsServer.postHTTPJakarta(graphPostURL,nvps);
		//LOG.info("graphPostResponse " + graphPostResponse);
		
		String graphPostURL = "https://graph.facebook.com/PinBelle/photos?access_token=" + pageAccessToken;
		//String fileUrl = "http://www.pinbelle.com/extranewgwt/getphoto?photoid=5075ddade4b02821650bf8be";
		String fileMime = "image/jpeg";
		String fileName = "file";
		
		HashMap<String,String> params = new HashMap<String,String>();
		String textToPost = 
				"Name: " + albumObj.getAlbname() + "\r\n" +
				"Age: " + albumObj.getAlbage() + "\r\n" +
				"City: " + albumObj.getAlbcity() + "\r\n" +
				"More photos at pinbelle.com: " + pagePath;
		params.put("message", textToPost);
		//params.entrySet().add(new Entry<String,String>("message","nah"));
		
		CommonsServer.postMultiPartHTTPJakarta(graphPostURL, filePath, params, fileName, fileMime);
		
	
		/*
		JSONObject obj=(JSONObject)JSONValue.parse(getModeratedAccsResponse);
		
	
		if (obj.get("error")!=null) {
			throw new RPCServiceExeption("ERROR" + obj.get("error"));
		}
		
		String pageAccessToken = null;
		
		if (obj.get("data")!=null) {
			
			JSONArray dataJson = (JSONArray)obj.get("data"); 
			for (int i=0;i<dataJson.size();i++) {
				dataJson.get(i);
				LOG.info("dataJson i " + i + dataJson.get(i));
				LOG.info("dataJson i " + i +  ((JSONObject)dataJson.get(i)).get("category"));
				String category = (String) ((JSONObject)dataJson.get(i)).get("category");
				String name = (String) ((JSONObject)dataJson.get(i)).get("name");
				if ( category.equals("Magazine") && name.equals("PinBelle")  )  {
					pageAccessToken = (String) ((JSONObject)dataJson.get(i)).get("access_token");
					LOG.info("GOT PAGE TOKEN: " + pageAccessToken);
				}
			}
		}
		
		if (pageAccessToken==null) {
			throw new RPCServiceExeption("ERROR Yo are dont administrator of Pinbelle page");
		}
		*/
		return null;
		
	}
	
	
	
	public void getImgPath() {
		String curimgId = albumObj.getCoverImgObjID();
		Images imagesExec = new Images();
		imgObj = imagesExec.getOneImageById(curimgId);
		filePath = "http://www.pinbelle.com/extranewgwt/getphoto?photoid=" + imgObj.getImgGridfs_id_m();
		//pagePath = "albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
		//return filePath;
	}
	
	public void getPagePath() {
		pagePath = "http://www.pinbelle.com/#ModelPref:albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
	}
	
	public void fbPost()  {
		
		fbAccessToken();
		getImgPath();
		getPagePath();
		String pageAccessToken  = getPageAccessToken();
		doPostPage(pageAccessToken);
		
	
		//LOG.info("dataJson " + dataJson);
		//LOG.info("errorJson" + errorJson);
		
		
	}

}
