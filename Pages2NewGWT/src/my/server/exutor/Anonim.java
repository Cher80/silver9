package my.server.exutor;

import javax.servlet.http.HttpServletRequest;

import my.server.CommonsServer;
import my.server.MongoPool;
import my.shared.CommentObj;
import my.shared.CommentsObj;
import my.shared.User;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Anonim {

	public static final Logger LOG=Logger.getLogger(Anonim.class);
	private DB db = MongoPool.getMainDB();

	public Anonim() {

	}

	public User getAnonimUser() {

		DBCollection comments = db.getCollection("users");

		BasicDBObject query = new BasicDBObject();
		query.append("email", "anonymous@anonymous.com");

		DBCursor cur = comments.find(query);

		CommentsObj commentsObj = new CommentsObj();
		if (!cur.hasNext()) {
			return newAnonimUser();
		}
		else {
			while(cur.hasNext()) {
				DBObject userDBO = cur.next();
				CoomonUser commonUser = new CoomonUser();
				User user = commonUser.getUserFromDBO(userDBO);
				return user;
			}
		}
		return null; 

	}

	public User newAnonimUser() {

		String md5pass = CommonsServer.MD5(MongoPool.getSecretKey() + "37ch83er");

		BasicDBObject userDBO = new BasicDBObject();
		userDBO.put("nick", "Anonymous");
		userDBO.put("email", "anonymous@anonymous.com");
		userDBO.put("pass1", md5pass);
		userDBO.put("isfb", false);
		userDBO.put("urole", 0);
		DBCollection users = db.getCollection("users");

		users.insert(userDBO);
		CoomonUser commonUser = new CoomonUser();
		User user = commonUser.getUserFromDBO(userDBO);
		return user ;
	}


}
