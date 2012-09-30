package my.server.exutor;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import my.client.rpcs.RPCServiceExeption;
import my.server.MongoPool;
import my.server.grabber.PostPhotos;
import my.shared.ActivityObj;
import my.shared.AlbumObj;
import my.shared.TagObj;
import my.shared.TagsObj;
import my.shared.User;

public class TagExec {

	DB db = MongoPool.getMainDB();
	public static final Logger LOG=Logger.getLogger(TagExec.class);
	private TagObj tagObj;
	private AlbumObj albumObj;
	private User user;
	private List<String>votedGroups = new ArrayList<String>();



	public TagObj convertTagFromDBOtoObj(BasicDBObject tagDBO) {

		String tagType = "";
		String tagReadableName = "";
		int tagTotalPluses = 0;
		boolean tagIsWinner = false;
		boolean allowVoteToUser = true;
		boolean currentUserVoted = false;
		String tagGroup = "";

		if (tagDBO.containsField("tagtype")) 
			tagType = (String) tagDBO.getString("tagtype");

		if (tagDBO.containsField("tagreadablename")) 
			tagReadableName = (String) tagDBO.getString("tagreadablename");

		if (tagDBO.containsField("tagtotalpluses")) 
			tagTotalPluses = tagDBO.getInt("tagtotalpluses");

		if (tagDBO.containsField("tagiswinner")) 
			//tagDBO.g
			tagIsWinner = (boolean) tagDBO.getBoolean("tagiswinner");

		if (tagDBO.containsField("allowvotetoUser")) 
			allowVoteToUser = (boolean)tagDBO.getBoolean( "allowvotetoUser" );

		if (tagDBO.containsField("currentuservoted")) 
			currentUserVoted = (boolean)tagDBO.getBoolean( "currentuservoted" );

		if (tagDBO.containsField("taggroup")) 
			//tagDBO.getString(key)
			tagGroup = (String) tagDBO.getString("taggroup");

		TagObj tagObj = new TagObj();
		tagObj.setAllowVoteToUser(allowVoteToUser);
		tagObj.setCurrentUserVoted(currentUserVoted);
		tagObj.setTagGroup(tagGroup);
		tagObj.setTagIsWinner(tagIsWinner);
		tagObj.setTagReadableName(tagReadableName);
		tagObj.setTagTotalPluses(tagTotalPluses);
		tagObj.setTagType(tagType);

		return tagObj;

	}


	public void checkIfAlreadyVoted2() {

		TagsObj tagsObj = new TagsObj();

		DB db = MongoPool.getMainDB();
		DBCollection activity = db.getCollection("activity");

		BasicDBObject query = new BasicDBObject();
		query.append("albid", new ObjectId(albumObj.getAlbid().trim()));
		query.append("uid", new ObjectId(user.getUid().trim()));
		query.append("activityType", "TAG");

		LOG.debug("checkIfAlreadyVoted user.getUid() " + user.getUid());
		DBCursor cur = activity.find(query);
		//		LOG.debug("cur.hasNext()" + cur.hasNext());
		try {
			while(cur.hasNext()) {
				BasicDBObject curActivityDBO = (BasicDBObject) cur.next();

				if (curActivityDBO.containsField("tagGroup")) {
					String tagGroup =  curActivityDBO.getString("tagGroup");
					//LOG.debug("curActivityDBO user ");
					LOG.debug("curActivityDBO tagGroup " + tagGroup);
					if (!user.isAnonymous()) {
						votedGroups.add(tagGroup);
						LOG.debug("user.equals Anonymous false");
					}
					else {
						//votedGroups.add(tagGroup);
						LOG.debug("user.equals Anonymous true");
					}
				}
			}
		} finally {
			cur.close();
		}

		//eturn tagsObj;
	}

	public void checkIfAlreadyVoted() {

		TagsObj tagsObj = new TagsObj();

		DB db = MongoPool.getMainDB();
		DBCollection activity = db.getCollection("activity");

		BasicDBObject query = new BasicDBObject();
		query.append("albid", new ObjectId(albumObj.getAlbid().trim()));
		query.append("uid", new ObjectId(user.getUid().trim()));
		query.append("activityType", "TAG");

		LOG.debug("checkIfAlreadyVoted user.getUid() " + user.getUid());
		DBCursor cur = activity.find(query);
		//		LOG.debug("cur.hasNext()" + cur.hasNext());
		try {
			while(cur.hasNext()) {
				BasicDBObject curActivityDBO = (BasicDBObject) cur.next();

				if (curActivityDBO.containsField("tagGroup")) {
					String tagGroup =  curActivityDBO.getString("tagGroup");
					//LOG.debug("curActivityDBO user ");
					LOG.debug("curActivityDBO tagGroup " + tagGroup);
					if (!user.isAnonymous()) {
						votedGroups.add(tagGroup);
						LOG.debug("user.equals Anonymous false");
					}
					else {
						//votedGroups.add(tagGroup);
						LOG.debug("user.equals Anonymous true");
					}
				}
			}
		} finally {
			cur.close();
		}

		//eturn tagsObj;
	}


	public TagObj isInVotedGroup(TagObj tagObj) {
		for (int i=0;i<votedGroups.size();i++) {

			if (votedGroups.get(i).equals(tagObj.getTagGroup())) {
				tagObj.setAllowVoteToUser(false);
				LOG.debug("setAllowVoteToUser false");
				return tagObj;
			}
			else {
				tagObj.setAllowVoteToUser(true);

			}
		}
		//LOG.debug("isInVotedGroup votedGroups " + votedGroups.get(i));
		LOG.debug("isInVotedGroup tagObj" + tagObj.getTagGroup());
		LOG.debug("setAllowVoteToUser true");
		return tagObj;
	}


	public TagsObj getTagObjs2(AlbumObj albumObjj, User userr) {
		this.albumObj =albumObjj;
		this.user = userr;

		checkIfAlreadyVoted2();

		TagsObj tagsObj = new TagsObj();

		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");

		//int bestPlusses = 0;
		//String bestTagType = "";

		BasicDBObject query = new BasicDBObject();
		//query.append("tags.taggroup", tagObj.getTagGroup());
		query.append("_id", new ObjectId(albumObj.getAlbid().trim()));


		DBCursor cur = albums.find(query);
		//		LOG.debug("cur.hasNext()" + cur.hasNext());
		try {
			while(cur.hasNext()) {
				DBObject curAlbum = cur.next();


				if (curAlbum.containsField("tags2")) {
					BasicDBObject curTags = (BasicDBObject) curAlbum.get("tags2");


					Iterator it = curTags.entrySet().iterator();


					while (it.hasNext()) {
						//BasicDBObject curTag =  (BasicDBObject) curTags.entrySet().iterator().next();
						Map.Entry pairs = (Map.Entry)it.next();
						BasicDBObject curTagDBO = (BasicDBObject) pairs.getValue();

						TagObj tagObj = this.convertTagFromDBOtoObj(curTagDBO);
						tagObj = isInVotedGroup(tagObj);
						tagsObj.getTagsObj().add(tagObj);
						//map.put("1", "Department A");
						//map.put("2", "Department B");


						LOG.debug("getTagObjs2 curTag.get " + curTagDBO.get("tagtype"));
					}
				}
				//for (i)
				/*
				BasicDBList tagsDBList =  (BasicDBList) curAlbumDBO.get("tags"); //int tagtotalpluses = (int)curTagDBO.get("tags.tagtotalpluses");
				//
				for (int i=0;i<tagsDBList.size();i++) {
					BasicDBObject curTagDBO = (BasicDBObject)tagsDBList.get(i);
					//LOG.debug("tagObj.getTagReadableName() " + curTagDBO.get("") .getTagReadableName());
					TagObj tagObj = this.convertTagFromDBOtoObj(curTagDBO);

					tagObj = isInVotedGroup(tagObj);
					LOG.debug("tagObj.getTagReadableName() " + tagObj.getTagReadableName());
					tagsObj.getTagsObj().add(tagObj);
				}*/
			}
		} finally {
			cur.close();
		}

		return tagsObj;
		//LOG.debug("bestTagType " + bestTagType);

	}


	public TagsObj getTagObjs(AlbumObj albumObjj, User userr) {
		this.albumObj =albumObjj;
		this.user = userr;

		//checkIfAlreadyVoted();

		TagsObj tagsObj = new TagsObj();

		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");

		//int bestPlusses = 0;
		//String bestTagType = "";

		BasicDBObject query = new BasicDBObject();
		//query.append("tags.taggroup", tagObj.getTagGroup());
		query.append("_id", new ObjectId(albumObj.getAlbid().trim()));


		DBCursor cur = albums.find(query);
		//		LOG.debug("cur.hasNext()" + cur.hasNext());
		try {
			while(cur.hasNext()) {
				DBObject curAlbumDBO = cur.next();
				BasicDBList tagsDBList =  (BasicDBList) curAlbumDBO.get("tags"); //int tagtotalpluses = (int)curTagDBO.get("tags.tagtotalpluses");
				//
				for (int i=0;i<tagsDBList.size();i++) {
					BasicDBObject curTagDBO = (BasicDBObject)tagsDBList.get(i);
					//LOG.debug("tagObj.getTagReadableName() " + curTagDBO.get("") .getTagReadableName());
					TagObj tagObj = this.convertTagFromDBOtoObj(curTagDBO);

					tagObj = isInVotedGroup(tagObj);
					LOG.debug("tagObj.getTagReadableName() " + tagObj.getTagReadableName());
					tagsObj.getTagsObj().add(tagObj);
				}
			}
		} finally {
			cur.close();
		}

		return tagsObj;
		//LOG.debug("bestTagType " + bestTagType);

	}


	public TagObj executeSetTag2(TagObj tagObjj, AlbumObj albumObjj, User userr) {
		this.tagObj = tagObjj;
		this.albumObj =albumObjj;
		this.user = userr;

		if(!checkIfExist2()) {
			createTag2();
		}

		doIncTag2();
		setWinner2();
		setActivity();


		return null;
	}



	public TagObj executeSetTag(TagObj tagObjj, AlbumObj albumObjj, User userr) {
		TagObj tagRetObj = new TagObj();
		//tagRetObj.setTagReadableName(tagObj.getTagReadableName()+"allona");
		this.tagObj = tagObjj;
		this.albumObj =albumObjj;
		this.user = userr;


		if(!checkIfExist()) {
			createTag();
		}

		doIncTag();
		setWinner();

		setActivity();

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


	public void setActivity() {
		ActivityObj activityObj = new ActivityObj();

		//activityObj.setActivityID(activityID);
		activityObj.setActivityType("TAG");
		activityObj.setAlbid(this.albumObj.getAlbid());
		activityObj.setAlbname(this.albumObj.getAlbname());
		activityObj.setCommentID("");
		activityObj.setCommentText("");
		activityObj.setCoverImgObjID(this.albumObj.getCoverImgObjID());
		activityObj.setCoverPicObjID(this.albumObj.getCoverPicID());
		activityObj.setNick(this.user.getNick());

		activityObj.setTagReadableName(this.tagObj.getTagReadableName());
		activityObj.setTagType(this.tagObj.getTagType());
		LOG.debug("setActivity uid " + this.user.getUid());
		activityObj.setUid(this.user.getUid());
		activityObj.setTagGroup(this.tagObj.getTagGroup());

		ActivityExec activityExec = new ActivityExec();
		activityExec.setActivity(activityObj);
	}


	public void setTagIsWinner2(boolean isWinner, String curTagType ) {

		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");

		BasicDBObject query2 = new BasicDBObject();
		//query2.append("tags.tagtype", curTagType);
		query2.append("_id", new ObjectId(albumObj.getAlbid()));

		BasicDBObject inc = new BasicDBObject("$set", new BasicDBObject("tags2." + curTagType + ".tagiswinner", isWinner));

		albums.update(
				query2,
				inc,
				true,
				false);
	}



	public void setTagIsWinner(boolean isWinner, String curTagType ) {

		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");

		BasicDBObject query2 = new BasicDBObject();
		query2.append("tags.tagtype", curTagType);
		query2.append("_id", new ObjectId(albumObj.getAlbid()));

		BasicDBObject inc = new BasicDBObject("$set", new BasicDBObject("tags.$.tagiswinner", isWinner));

		albums.update(
				query2,
				inc,
				true,
				false);
	}



	public void setWinner2() {

		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");

		int bestPlusses = 0;
		String bestTagType = "";

		BasicDBObject query = new BasicDBObject();
		//query.append("tags.taggroup", tagObj.getTagGroup());
		query.append("_id", new ObjectId(albumObj.getAlbid().trim()));


		DBCursor cur = albums.find(query);
		//		LOG.debug("cur.hasNext()" + cur.hasNext());
		try {
			while(cur.hasNext()) {
				DBObject curAlbumDBO = cur.next();

				BasicDBObject curTags = (BasicDBObject) curAlbumDBO.get("tags2");


				Iterator it = curTags.entrySet().iterator();


				while (it.hasNext()) {
					//BasicDBObject curTag =  (BasicDBObject) curTags.entrySet().iterator().next();
					Map.Entry pairs = (Map.Entry)it.next();
					BasicDBObject curTagDBO = (BasicDBObject) pairs.getValue();

					//BasicDBObject curTagDBO = (BasicDBObject)tagsDBList.get(i);
					if (curTagDBO.get("taggroup").equals(tagObj.getTagGroup())) {
						int tagtotalpluses = (int)curTagDBO.get("tagtotalpluses");
						String curTagType = (String)curTagDBO.get("tagtype");
						LOG.debug("tagtotalpluses " + tagtotalpluses + "curTagType " + curTagType);
						//Set all tags to false, the set winner later

						setTagIsWinner2(false,curTagType);

						if (tagtotalpluses>=bestPlusses) {
							bestPlusses = tagtotalpluses;
							bestTagType = (String)curTagDBO.get("tagtype");
						}
					}
				}
			}
		} finally {
			cur.close();
		}

		LOG.debug("bestTagType " + bestTagType);


		//Set winner
		setTagIsWinner2(true,bestTagType);


		//cur.close();

	}



	public void setWinner() {

		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");

		int bestPlusses = 0;
		String bestTagType = "";

		BasicDBObject query = new BasicDBObject();
		//query.append("tags.taggroup", tagObj.getTagGroup());
		query.append("_id", new ObjectId(albumObj.getAlbid().trim()));


		DBCursor cur = albums.find(query);
		//		LOG.debug("cur.hasNext()" + cur.hasNext());
		try {
			while(cur.hasNext()) {
				DBObject curAlbumDBO = cur.next();
				BasicDBList tagsDBList =  (BasicDBList) curAlbumDBO.get("tags"); //int tagtotalpluses = (int)curTagDBO.get("tags.tagtotalpluses");
				//
				for (int i=0;i<tagsDBList.size();i++) {
					BasicDBObject curTagDBO = (BasicDBObject)tagsDBList.get(i);
					if (curTagDBO.get("taggroup").equals(tagObj.getTagGroup())) {
						int tagtotalpluses = (int)curTagDBO.get("tagtotalpluses");
						String curTagType = (String)curTagDBO.get("tagtype");
						LOG.debug("tagtotalpluses " + tagtotalpluses + "curTagType " + curTagType);
						//Set all tags to false, the set winner later

						setTagIsWinner(false,curTagType);

						if (tagtotalpluses>=bestPlusses) {
							bestPlusses = tagtotalpluses;
							bestTagType = (String)curTagDBO.get("tagtype");
						}
					}
				}
			}
		} finally {
			cur.close();
		}

		LOG.debug("bestTagType " + bestTagType);


		//Set winner
		setTagIsWinner(true,bestTagType);


		//cur.close();

	}


	public void doIncTag2() {
		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");

		BasicDBObject query = new BasicDBObject();
		//query.append("tags.tagtype", tagObj.getTagType());
		query.append("_id", new ObjectId(albumObj.getAlbid()));

		BasicDBObject inc = new BasicDBObject("$inc", new BasicDBObject("tags2." + tagObj.getTagType()+ ".tagtotalpluses", 1));

		albums.update(
				query,
				inc,
				true,
				false);

	}


	public void doIncTag() {
		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");

		BasicDBObject query = new BasicDBObject();
		query.append("tags.tagtype", tagObj.getTagType());
		query.append("_id", new ObjectId(albumObj.getAlbid()));

		BasicDBObject inc = new BasicDBObject("$inc", new BasicDBObject("tags.$.tagtotalpluses", 1));

		albums.update(
				query,
				inc,
				true,
				false);

	}





	public void createTag2() {

		LOG.debug("Lets do new tag");
		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");

		//ArrayList<BasicDBObject> tags = new ArrayList<BasicDBObject>();


		BasicDBObject tag = new BasicDBObject();
		tag.append("tagtype", tagObj.getTagType());
		tag.append("tagreadablename", tagObj.getTagReadableName());
		tag.append("tagtotalpluses", 0);
		tag.append("tagiswinner",false);
		tag.append("allowvotetouser", true);
		tag.append("currentuservoted", false);
		tag.append("taggroup", tagObj.getTagGroup());

		//tags.add(tag);

		BasicDBObject query = new BasicDBObject();
		//		query.append("tags.tagtype", tagObj.getTagType());
		query.append("_id", new ObjectId(albumObj.getAlbid()));
		//LOG.debug("albumObj.getAlbid()=" +"xxx" + albumObj.getAlbid().trim());

		//BasicDBObject push = new BasicDBObject();
		//push.append("tags", tag);
		//BasicDBObject set = new BasicDBObject("$set",tags);

		BasicDBObject set = new BasicDBObject("$set",new BasicDBObject("tags2." + tagObj.getTagType(),tag));

		albums.update(
				query,
				set,
				true,
				true);
		//db.albums.update({"_id":ObjectId("503d226d22d205dc9f9afb3b")},{$unset: {tags:1}},false,true)

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










	public boolean checkIfExist2() {

		boolean isExist = false;
		DB db = MongoPool.getMainDB();
		DBCollection albums = db.getCollection("albums");

		BasicDBObject query = new BasicDBObject();
		LOG.debug("tags.tagtype " + tagObj.getTagType());
		query.append("tags2." + tagObj.getTagType() + ".tagtype", tagObj.getTagType());
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
