package my.server.exutor;

import java.util.ArrayList;

import my.client.rpcs.RPCServiceExeption;
import my.server.Commons;
import my.server.MongoPool;
import my.shared.AlbumObj;
import my.shared.AlbumsObj;
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
	
	
	public void incAlbumsCount() {
		DBCollection albums = db.getCollection("albums");
		
		/*
		BasicDBObject  query1 = new BasicDBObject();
		query1.append("countalbumsfield", 1);
		DBCursor cur;
		if (albidtoshow!=null) {
			cur = albums.find(query)
		*/
		BasicDBObject queryup = new BasicDBObject();
		queryup.append("albumscountobject", true);
		
		BasicDBObject inc = new BasicDBObject("$inc", new BasicDBObject("albumscount", 1));

		albums.update(
		queryup,
		inc,
		true,
		true);
	}
	
	public int getAlbumsCount() {
		DBCollection albums = db.getCollection("albums");
		
		BasicDBObject query = new BasicDBObject();
		query.append("albumscountobject", true);
		DBCursor cur = albums.find(query);
		
		int albumscount = 0;
		try {
            while(cur.hasNext()) {
            	DBObject counters = cur.next();
            	albumscount = (int)counters.get("albumscount");
            }
        } finally {
        	cur.close();
        }
		return albumscount;
	}
	
	
	
	public int getSimpleAlbumsCount(DBCursor cur) {
		int count = cur.count();
		return count;
	}

	public AlbumsObj getAlbumsByTime(int offset, int limit, String tagType, int statusPublished, String albidtoshow) throws RPCServiceExeption {


		DBCollection albums = db.getCollection("albums");
		//BasicDBObject query = null;
		BasicDBObject query = new BasicDBObject();
		if (albidtoshow!=null) {
			query.append("_id", new ObjectId(albidtoshow.trim()));
		}
		LOG.debug("getAlbumsByTime " + tagType);
		if (tagType!=null) {
			BasicDBObject inTagElements = new BasicDBObject();
			inTagElements.append("tagtype", tagType); 
			inTagElements.append("tagiswinner", true);
			BasicDBObject tagElemMatch = new BasicDBObject("$elemMatch",inTagElements);
			query.append("tags", tagElemMatch);
			LOG.debug("tagType!=null ");
		}
		if (statusPublished>=0) {
			query.append("status", statusPublished);
		}

		BasicDBObject sort = new BasicDBObject();
		sort.append("timestamp", -1);

		//int limit = new BasicDBObject();
		//query.append("timestamp", 1);

		DBCursor cur;
		if (albidtoshow!=null) {
			cur = albums.find(query).limit(1);
		} else {
			cur = albums.find(query).sort(sort).skip(offset).limit(limit);
		}
			
		AlbumsObj albumsObj = new AlbumsObj();

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
					//albumObj.setTimestamp((long)album.get("timestamp"));
					albumObj.setTimestamp(new Long(23));
				if (album.containsField("photocount")) 
					albumObj.setPhotocount((int)album.get("photocount"));
				if (album.containsField("coverimgobjid")) 
					albumObj.setCoverImgObjID((String)album.get("coverimgobjid").toString());
				if (album.containsField("coverpicid")) 
					albumObj.setCoverPicID((String)album.get("coverpicid").toString());
				if (album.containsField("_id")) 
					albumObj.setAlbid((String)album.get("_id").toString());

				albumsObj.getAlbums().add(albumObj);

			}
			
			
			int albumsCount = getSimpleAlbumsCount(cur);
			albumsObj.setTotalCount(albumsCount);

			return albumsObj;
		}
	}
}
