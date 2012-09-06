package my.server.exutor;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import my.client.rpcs.RPCServiceExeption;
import my.server.MongoPool;
import my.server.grabber.PostPhotos;
import my.shared.AlbumObj;
import my.shared.TagObj;
import my.shared.User;

public class TagExec {

	DB db = MongoPool.getMainDB();
	public static final Logger LOG=Logger.getLogger(TagExec.class);
	private TagObj tagObj;
	private AlbumObj albumObj;
	private User user;


	


	public TagObj executeSetTag(TagObj tagObjj, AlbumObj albumObjj, User userr) {
		TagObj tagRetObj = new TagObj();
		//tagRetObj.setTagReadableName(tagObj.getTagReadableName()+"allona");
		this.tagObj = tagObjj;
		this.albumObj =albumObjj;
		this.user = userr;
		
		
		if(!checkIfExist()) {
			createTag();
		}

		/*

		DBCollection albums = db.getCollection("albums");
		BasicDBObject queryup = new BasicDBObject();
		queryup.append("_id", new ObjectId(albumObj.getAlbid()));

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

		 */






		return tagRetObj;


	}
	
	
	public void createTag() {
		
		LOG.debug("Lets do new tag");
		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");
		
		ArrayList<BasicDBObject> tags = new ArrayList<BasicDBObject>();


		BasicDBObject tag = new BasicDBObject();
		tag.append("tagtype", tagObj.getTagType());
		tag.append("tagreadablename", tagObj.getTagReadableName());
		tag.append("tagtotalpluses", tagObj.getTagTotalPluses());
		tag.append("tagiswinner",tagObj.isTagIsWinner());
		tag.append("allowvotetouser", tagObj.isAllowVoteToUser());
		tag.append("currentuservoted", tagObj.isCurrentUserVoted());
		tag.append("taggroup", tagObj.getTagGroup());

		tags.add(tag);

		BasicDBObject query = new BasicDBObject();
//		query.append("tags.tagtype", tagObj.getTagType());
		query.append("_id", new ObjectId(albumObj.getAlbid()));
		//LOG.debug("albumObj.getAlbid()=" +"xxx" + albumObj.getAlbid().trim());
		
		//BasicDBObject push = new BasicDBObject();
		//push.append("tags", tag);
		//BasicDBObject set = new BasicDBObject("$set",tags);
		
		BasicDBObject set = new BasicDBObject("$push",new BasicDBObject("tags",tag));
		
		albums.update(
				query,
				set,
				true,
				true);
		//db.albums.update({"_id":ObjectId("503d226d22d205dc9f9afb3b")},{$unset: {tags:1}},false,true)
		
	}
	
	
	public boolean checkIfExist() {

		boolean isExist = false;
		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");

		BasicDBObject query = new BasicDBObject();
		LOG.debug("tags.tagtype " + tagObj.getTagType());
		query.append("tags.tagtype", tagObj.getTagType());
		LOG.debug("albumObj.getAlbid() " + albumObj.getAlbid());
		query.append("_id", new ObjectId(albumObj.getAlbid()));
		
		
		DBCursor cur = albums.find(query);
		LOG.debug("cur.hasNext()" + cur.hasNext());
		if (!cur.hasNext()) {
			LOG.debug("No tag");
			isExist = false;
		}
		else {
			LOG.debug("Yes tag");
			isExist = true;
		}

		cur.close();
		return isExist;
	}
}
