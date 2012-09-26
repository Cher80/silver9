package my.server.exutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

import my.client.rpcs.RPCServiceExeption;
import my.server.CommonsServer;
import my.server.MongoPool;
import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.CommonsShared;
import my.shared.ResponseStatus;
import my.shared.TagObj;
import my.shared.TagsObj;
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

	
	
	
	
	
	
	public AlbumsObj getAlbumsBestBloc() throws RPCServiceExeption {

		AlbumsObj albumsObj = new AlbumsObj();
		DBCollection albums = db.getCollection("albums");

		BasicDBObject query = new BasicDBObject();
		query.append("tags2.LIKE.tagiswinner", true);
		
		long treeDaysMinus = System.currentTimeMillis()/1000 - 86400*33;
		//BasicDBObject query2 = new BasicDBObject();
		query.append("timestamp", new BasicDBObject("$gt", treeDaysMinus));
		
		BasicDBObject sort = new BasicDBObject();
		sort.append("tags2.LIKE.tagtotalpluses", -1);

		//query.append("tags.tagtype", "LIKE");

	

		//int limit = new BasicDBObject();
		//query.append("timestamp", 1);


		DBCursor cur = albums.find(query).sort(sort).limit(10);



		if (!cur.hasNext()) {
			cur.close();
			//throw new RPCServiceExeption("No albms");
			return albumsObj;
		}

		else {



			while(cur.hasNext()) {
				BasicDBObject album = (BasicDBObject)cur.next();
				AlbumObj curAlbumObj = this.convertFromDBOtoOBJ(album);
				albumsObj.getAlbums().add(curAlbumObj);
				LOG.debug("getAlbumsBestBloc " + album.getObjectId("_id"));

			}


			return albumsObj;
		}
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
			//BasicDBObject inTagElements = new BasicDBObject();
			//inTagElements.append("tagtype", tagType); 
			//inTagElements.append("tagiswinner", true);
			//BasicDBObject tagElemMatch = new BasicDBObject("$elemMatch",inTagElements);

			//BasicDBObject queryTag = new BasicDBObject();
			query.append("tags2." + tagType + ".tagiswinner", true);
			//query.append("tags2." + tagType + ".tagtype", tagType);
			
			//query.append("tags", tagElemMatch);
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
			return albumsObj; 
			//throw new RPCServiceExeption("No albms");
		}

		else {



			while(cur.hasNext()) {
				BasicDBObject album = (BasicDBObject)cur.next();

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

LOG.debug("album.get(timestamp)" + album.get("timestamp"));
				AlbumObj albumObj = convertFromDBOtoOBJ(album);
				albumsObj.getAlbums().add(albumObj);
				LOG.debug(" albumObj.getTimestamp()" + albumObj.getTimestamp());
			}


			int albumsCount = getSimpleAlbumsCount(cur);
			albumsObj.setTotalCount(albumsCount);

			return albumsObj;
		}
	}

	
  public AlbumObj convertFromDBOtoOBJ (BasicDBObject album) {
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
			albumObj.setTimestamp(album.getLong("timestamp")); 
		if (album.containsField("photocount")) 
			albumObj.setPhotocount((int)album.get("photocount"));
		if (album.containsField("coverimgobjid")) 
			albumObj.setCoverImgObjID(CommonsServer.fromIDtoString(album.getObjectId("coverimgobjid")));
		if (album.containsField("coverpicid")) 
			albumObj.setCoverPicID(CommonsServer.fromIDtoString(album.getObjectId("coverpicid")));
		if (album.containsField("_id")) 
			albumObj.setAlbid(CommonsServer.fromIDtoString(album.getObjectId("_id")));
		//if (album.containsField("tags2.LIKE"))
		
		if (album.containsField("tags2")) {
			BasicDBObject curTags = (BasicDBObject) album.get("tags2");

			TagsObj tagsObj = new TagsObj();
			Iterator it = curTags.entrySet().iterator();


			while (it.hasNext()) {
				//BasicDBObject curTag =  (BasicDBObject) curTags.entrySet().iterator().next();
				Map.Entry pairs = (Map.Entry)it.next();
				BasicDBObject curTagDBO = (BasicDBObject) pairs.getValue();

				TagExec tagExec = new TagExec();
				TagObj tagObj = tagExec.convertTagFromDBOtoObj(curTagDBO);
				//tagObj = isInVotedGroup(tagObj);
				tagsObj.getTagsObj().add(tagObj);
				//map.put("1", "Department A");
				//map.put("2", "Department B");


				LOG.debug("getTagObjs2 curTag.get " + curTagDBO.get("tagtype"));
			}
			albumObj.setTagsObj(tagsObj);
		}
		
return albumObj;
  }
	
	
	public ResponseStatus doAlbumStatus(AlbumObj albumObj, int statusPublished) {


		BasicDBObject queryup2 = new BasicDBObject();

		DBCollection albums = db.getCollection("albums");
		//BasicDBObject queryup = new BasicDBObject();
		//queryup2.append("_id", new ObjectId("503a624322d2e3398fb64b0c"));		
		queryup2.append("_id", new ObjectId(albumObj.getAlbid().trim()));		
		BasicDBObject inc2 = new BasicDBObject("$set",new BasicDBObject("status", statusPublished));
		albums.update(
				queryup2,
				inc2,
				true,
				false);

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setOK(true);
		responseStatus.setTextStatus("Status Changed"); 
		return responseStatus; 
	}
	
	
	

	public ResponseStatus doDeAlbum(AlbumObj albumObj) {


		BasicDBObject query = new BasicDBObject();

		DBCollection albums = db.getCollection("albums");
		//BasicDBObject queryup = new BasicDBObject();
		//queryup2.append("_id", new ObjectId("503a624322d2e3398fb64b0c"));		
		query.append("_id", new ObjectId(albumObj.getAlbid().trim()));		
		albums.remove(query);

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setOK(true);
		responseStatus.setTextStatus("deleted"); 
		return responseStatus; 
	}
	
	
}
