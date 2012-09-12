package my.server.exutor;

import my.client.rpcs.RPCServiceExeption;
import my.server.CommonsServer;
import my.server.MongoPool;
import my.shared.ActivitiesObj;
import my.shared.ActivityObj;
import my.shared.AlbumObj;
import my.shared.CommonsShared;
import my.shared.TagObj;
import my.shared.User;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class ActivityExec {

	DB db = MongoPool.getMainDB();
	public static final Logger LOG=Logger.getLogger(TagExec.class);
	private ActivityObj activityObj;
	//private AlbumObj albumObj;
	//private User user;

	public ActivitiesObj getActivitiesBlock() {

		ActivitiesObj activitiesObj = new ActivitiesObj();
		DBCollection activities = db.getCollection("activity");

		//BasicDBObject query = new BasicDBObject();
		//query.append("tagtype", tagType); 

		BasicDBObject sort = new BasicDBObject();
		sort.append("timestamp", -1);

		DBCursor cur = activities.find().sort(sort).skip(0).limit(10);


		if (!cur.hasNext()) {
			cur.close();
			return activitiesObj;
		}

		else {



			while(cur.hasNext()) {
				DBObject curActivity = cur.next();
				ActivityObj activityObj = convertActivityFromDBOToObj((BasicDBObject) curActivity);
				activitiesObj.getActivities().add(activityObj);
				
			}
		}
		
		return activitiesObj;

	}



	public void setActivity(ActivityObj activityObjj) {
		this.activityObj=activityObjj;


		BasicDBObject activityDBO = converActivityFromObjToDBO(activityObj);


		DBCollection activity = db.getCollection("activity");

		activity.insert(activityDBO);


	}

	public BasicDBObject converActivityFromObjToDBO(ActivityObj activityObj) {
		BasicDBObject activityDBO = new BasicDBObject();
		//activityDBO.put("activityID", activityObj);
		activityDBO.put("activityType", activityObj.getActivityType());
		activityDBO.put("timestamp", (long)System.currentTimeMillis()/1000);
		activityDBO.put("uid",  CommonsServer.normalizeID(activityObj.getUid()));
		activityDBO.put("nick", activityObj.getNick());
		activityDBO.put("albname", activityObj.getAlbname());
		activityDBO.put("coverImgObjID",  CommonsServer.normalizeID(activityObj.getCoverImgObjID()));
		activityDBO.put("albid", CommonsServer.normalizeID(activityObj.getAlbid()));
		activityDBO.put("tagType", activityObj.getTagType());
		activityDBO.put("tagGroup", activityObj.getTagGroup());
		activityDBO.put("tagReadableName", activityObj.getTagReadableName());
		activityDBO.put("commentText", activityObj.getCommentText());
		activityDBO.put("commentID", CommonsServer.normalizeID(activityObj.getCommentID()));
		return activityDBO;

	}
	
	

	public ActivityObj convertActivityFromDBOToObj(BasicDBObject activityDBO) {
		ActivityObj activityObj = new ActivityObj();
		//activityDBO.put("activityID", activityObj);
		
		if (activityDBO.containsField("activityType")) 
			activityObj.setActivityType(activityDBO.getString("activityType"));
		if (activityDBO.containsField("timestamp")) 
			activityObj.setTimestamp(activityDBO.getLong("timestamp")); 
		if (activityDBO.containsField("uid")) 
			activityObj.setUid(CommonsServer.fromIDtoString(activityDBO.getObjectId("uid")));
		if (activityDBO.containsField("nick")) 
			activityObj.setNick(activityDBO.getString("nick"));
		if (activityDBO.containsField("albname")) 
			activityObj.setAlbname(activityDBO.getString("albname"));
		if (activityDBO.containsField("coverImgObjID")) 
			activityObj.setCoverImgObjID(CommonsServer.fromIDtoString(activityDBO.getObjectId("coverImgObjID")));
		if (activityDBO.containsField("albid")) 
			activityObj.setAlbid(CommonsServer.fromIDtoString(activityDBO.getObjectId("albid")));
		
		if (activityDBO.containsField("tagType")) 
			activityObj.setTagType(activityDBO.getString("tagType"));
		
		if (activityDBO.containsField("tagGroup")) 
			activityObj.setTagGroup(activityDBO.getString("tagGroup"));
		
		if (activityDBO.containsField("tagReadableName")) 
			activityObj.setTagReadableName(activityDBO.getString("tagReadableName"));
		
		if (activityDBO.containsField("commentText")) 
			activityObj.setCommentText(activityDBO.getString("commentText"));
		
		if (activityDBO.containsField("commentID")) {
			activityObj.setCommentID(CommonsServer.fromIDtoString(activityDBO.getObjectId("commentID")));
			//LOG.debug("activityDBO.getObjectId(commentID)" + activityDBO.getObjectId("commentID"));
		}
		return activityObj;

	}

}
