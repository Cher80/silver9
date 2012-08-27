package my.server.exutor;

import java.util.ArrayList;

import my.client.rpcs.RPCServiceExeption;
import my.server.Commons;
import my.server.MongoPool;
import my.shared.AlbumObj;
import my.shared.User;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Albums {

	public static final Logger LOG=Logger.getLogger(Albums.class);
	DB db = MongoPool.getMainDB();

	public ArrayList<AlbumObj> getAlbumsByTime(int offset, int limit, String albidtoshow) throws RPCServiceExeption {


		DBCollection albums = db.getCollection("albums");
		BasicDBObject query = null;
		if (albidtoshow!=null) {
		query = new BasicDBObject();
		query.append("_id", new ObjectId(albidtoshow.trim()));
		}

		BasicDBObject sort = new BasicDBObject();
		sort.append("timestamp", -1);

		//int limit = new BasicDBObject();
		//query.append("timestamp", 1);

		DBCursor cur;
		if (albidtoshow!=null) {
			cur = albums.find(query).limit(1);
		} else {
			cur = albums.find().sort(sort).skip(offset).limit(limit);
		}
			
		ArrayList<AlbumObj> albumObjs = new ArrayList<AlbumObj>();

		//DBCursor cur = users.find(query);


		if (!cur.hasNext()) {
			cur.close();
			throw new RPCServiceExeption("No albms");
		}

		else {



			while(cur.hasNext()) {
				DBObject album = cur.next();

				/*
				if (!album.containsField("albage")) {

					LOG.info("Netu albage");						
				}
				if (!album.containsField("status")) {
					LOG.info("Netu status");						
				}


				//System.out.println(cursor.next());
				LOG.info("Albums albname: " + (String) album.get("albname"));	
				//user.get("pass1");
				//String nickdb = (String) user.get("nick");

				if (album.get("albage")==null) {
					throw new RPCServiceExeption("albage null");
				}
				if (album.get("albcity")==null) {
					throw new RPCServiceExeption("albcity null");
				}

				if (album.get("albname")==null) {
					throw new RPCServiceExeption("albname null");
				}
				if (album.get("albpage")==null) {
					throw new RPCServiceExeption("albpage null");
				}

				if (album.get("status")==null) {
					throw new RPCServiceExeption("status null");
				}
				if (album.get("timestamp")==null) {
					throw new RPCServiceExeption("timestamp null");
				}
				 */


				AlbumObj albumObj = new AlbumObj();

				if (album.containsField("albage")) 
					albumObj.setAlbage((int)album.get("albage"));
				if (album.containsField("albcity")) 
					albumObj.setAlbcity((String)album.get("albcity").toString());
				if (album.containsField("albname")) 
					albumObj.setAlbname((String)album.get("albname").toString());
				if (album.containsField("albpage")) 
					albumObj.setAlbpage((String)album.get("albpage").toString());
				if (album.containsField("status")) 
					albumObj.setStatus((int)album.get("status"));
				if (album.containsField("timestamp")) 
					albumObj.setTimestamp((long)album.get("timestamp"));
				if (album.containsField("photocount")) 
					albumObj.setPhotocount((int)album.get("photocount"));
				if (album.containsField("coverphoto")) 
					albumObj.setCoverphoto((String)album.get("coverphoto").toString());
				if (album.containsField("_id")) 
					albumObj.setAlbid((String)album.get("_id").toString());

				albumObjs.add(albumObj);

			}

			return albumObjs;
		}
	}
}
