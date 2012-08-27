package my.server.exutor;


import my.client.rpcs.RPCServiceExeption;
import my.server.MongoPool;

import my.shared.ImgObj;
import my.shared.ImgsObj;

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
			LOG.info("getImages 1");
			DBObject image = cur.next();
			LOG.info("getImages 2");
			ImgObj imgObj = new ImgObj();
			LOG.info("getImages 3");
			
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

			LOG.info("image.get(photourl).toString() " + image.get("photourl").toString());
			imgsObj.getImages().add(imgObj);
			LOG.info("getImages 33");
		}


		return imgsObj;


	}
}

