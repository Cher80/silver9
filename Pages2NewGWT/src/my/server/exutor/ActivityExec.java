package my.server.exutor;

import my.server.Commons;
import my.server.MongoPool;
import my.shared.ActivityObj;
import my.shared.AlbumObj;
import my.shared.TagObj;
import my.shared.User;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;

public class ActivityExec {

	DB db = MongoPool.getMainDB();
	public static final Logger LOG=Logger.getLogger(TagExec.class);
	private ActivityObj activityObj;
	//private AlbumObj albumObj;
	//private User user;

	
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
		activityDBO.put("uid",  Commons.normalizeID(activityObj.getUid()));
		activityDBO.put("nick", activityObj.getNick());
		activityDBO.put("albname", activityObj.getAlbname());
		activityDBO.put("coverImgObjID",  Commons.normalizeID(activityObj.getCoverImgObjID()));
		activityDBO.put("albid", Commons.normalizeID(activityObj.getAlbid()));
		activityDBO.put("tagType", activityObj.getTagType());
		activityDBO.put("tagGroup", activityObj.getTagGroup());
		activityDBO.put("tagReadableName", activityObj.getTagReadableName());
		activityDBO.put("commentText", activityObj.getCommentText());
		activityDBO.put("commentID", Commons.normalizeID(activityObj.getCommentID()));
		return activityDBO;
		
	}

}
