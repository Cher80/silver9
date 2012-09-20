package my.server.exutor;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import my.server.MongoPool;
import my.shared.ActivitiesObj;
import my.shared.StatObj;

public class StatExec {
	DB db = MongoPool.getMainDB();
	public static final Logger LOG=Logger.getLogger(StatExec.class);

	public StatObj getStat() {
		StatObj statObj = new StatObj();
		DBCollection activities = db.getCollection("misc");

		BasicDBObject query = new BasicDBObject();
		query.append("stat", new BasicDBObject("$exists", true));

		DBCursor cur = activities.find(query);
		
		if (!cur.hasNext()) {
			cur.close();
			//return statObj;
		}

		else {



			while(cur.hasNext()) {
				BasicDBObject statDBO = (BasicDBObject)cur.next();
				statObj = convertStatDBOtoObj(statDBO);
			}
		}
		return statObj;
	}
	
	public StatObj convertStatDBOtoObj(BasicDBObject statContDBO) {
		StatObj statObjCur = new StatObj();
		BasicDBObject statDBO = (BasicDBObject) statContDBO.get("stat");
		
		if (statDBO.containsField("totalAlbums")) 
			statObjCur.setTotalAlbums(statDBO.getInt("totalAlbums"));
		if (statDBO.containsField("totalImgs")) 
			statObjCur.setTotalImgs(statDBO.getInt("totalImgs"));
		if (statDBO.containsField("lastAlbums")) 
			statObjCur.setLastAlbums(statDBO.getInt("lastAlbums"));
		if (statDBO.containsField("lastImgs")) 
			statObjCur.setLastImgs(statDBO.getInt("lastImgs"));
		return statObjCur;
	}
}
