package my.server.exutor;


import my.client.rpcs.RPCServiceExeption;
import my.server.CommonsServer;
import my.server.MongoPool;

import my.shared.AlbumObj;
import my.shared.CommonsShared;
import my.shared.ImgObj;
import my.shared.ImgsObj;
import my.shared.ResponseStatus;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Images {


	public static final Logger LOG=Logger.getLogger(Images.class);
	DB db = MongoPool.getMainDB();

	private String albid;

	public ImgsObj getImages(String albidparam) throws RPCServiceExeption {
		this.albid = albidparam.trim();
		ImgsObj imgsObj = new ImgsObj();

		DBCollection images = db.getCollection("images");

		BasicDBObject query = new BasicDBObject();
		query.append("album", new ObjectId(albid));
		
		//BasicDBObject query = new BasicDBObject();
		query.append("status", 1);

		BasicDBObject sort = new BasicDBObject();
		sort.append("timestamp", -1);

		DBCursor cur;
		cur = images.find(query).sort(sort);

		/*
		if (!cur.hasNext()) {
			cur.close();
			throw new RPCServiceExeption("No images for this album");
		}*/

		//else {

		

		while(cur.hasNext()) {
			BasicDBObject image = (BasicDBObject)cur.next();
			ImgObj imgObj = converFromDBOtoObj(image);
			LOG.info("image.get(photourl).toString() " + image.get("photourl").toString());
			imgsObj.getImages().add(imgObj);
		}


		return imgsObj;


	}
	
	
	public ImgObj converFromDBOtoObj(BasicDBObject image) {
		
		ImgObj imgObj = new ImgObj();
		
		if (image.containsField("_id")) 
			imgObj.setImgID((String)image.get("_id").toString());
		if (image.containsField("photourl")) 
			imgObj.setImgPhotourl((String)image.get("photourl").toString());
		if (image.containsField("pageurl")) 
			imgObj.setImgPageurl((String)image.get("pageurl").toString());
		if (image.containsField("gridfs_id_m")) 
			imgObj.setImgGridfs_id_m((String)image.get("gridfs_id_m").toString());				
		if (image.containsField("gridfs_id_0")) 
			imgObj.setImgGridfs_id_0((String)image.get("gridfs_id_0").toString());				
		if (image.containsField("gridfs_id_1")) 
			imgObj.setImgGridfs_id_1((String)image.get("gridfs_id_1").toString());				
		if (image.containsField("gridfs_id_2")) 
			imgObj.setImgGridfs_id_2((String)image.get("gridfs_id_2").toString());	
		if (image.containsField("ishasorig")) 
			imgObj.setImgIshasorig((boolean)image.get("ishasorig"));
		if (image.containsField("md5")) 
			imgObj.setImgMd5((String)image.get("md5").toString());
		if (image.containsField("album")) 
			imgObj.setImgAlbum((String)image.get("album").toString());
		if (image.containsField("status")) 
			imgObj.setImgStatus((int)image.get("status"));
		if (image.containsField("timestamp")) 
			imgObj.setImgTimestamp((long)image.get("timestamp"));

		return imgObj;
	}
	
	

	public ResponseStatus doImgStatus(ImgObj imgObj, int statusPublished) throws RPCServiceExeption {
		// TODO Auto-generated method stub
		DBCollection images = db.getCollection("images");

		BasicDBObject query = new BasicDBObject();
		query.append("_id", CommonsServer.normalizeID(imgObj.getImgID()));
		
		BasicDBObject set = new BasicDBObject("$set",new BasicDBObject("status", statusPublished));
		images.update(
				query,
				set,
				true,
				false);

		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setOK(true);
		responseStatus.setTextStatus("Img Status Changed"); 
		return responseStatus; 

	}




	public ResponseStatus doImgCover(AlbumObj albumObj, ImgObj imgObj) throws RPCServiceExeption {
		
		DBCollection images = db.getCollection("albums");

		BasicDBObject query = new BasicDBObject();
		query.append("_id", CommonsServer.normalizeID(albumObj.getAlbid()));
		
		BasicDBObject set = new BasicDBObject("$set",new BasicDBObject("coverimgobjid", CommonsServer.normalizeID(imgObj.getImgID())));
		images.update(
				query,
				set,
				true,
				false);

		BasicDBObject set2 = new BasicDBObject("$set",new BasicDBObject("coverpicid", CommonsServer.normalizeID(imgObj.getImgGridfs_id_2())));
		images.update(
				query,
				set2,
				true,
				false);

		
		
		ResponseStatus responseStatus = new ResponseStatus();
		responseStatus.setOK(true);
		responseStatus.setTextStatus("Cover setted"); 
		return responseStatus; 
	}
	
	
	
}

