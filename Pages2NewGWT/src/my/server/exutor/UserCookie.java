package my.server.exutor;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import my.client.rpcs.RPCServiceExeption;
import my.server.Commons;
import my.server.MongoPool;
import my.shared.User;

public class UserCookie {
	public static final Logger LOG=Logger.getLogger(UserCookie.class);

	public void UserCookie() {

	}

	public User getUserByCookie(String cookie) {

		if (cookie==null) {
			throw new RPCServiceExeption("Error: cookie = null!");
		}
		String[] tokens = cookie.split("###");
		String email = tokens[0];
		String session = tokens[1];
		LOG.info("email " + email);
		LOG.info("session " + session);

		DB db = MongoPool.getMainDB();
		DBCollection users = db.getCollection("users");

		BasicDBObject query = new BasicDBObject();
		query.append("email", email);


		DBCursor cur = users.find(query);


		if (!cur.hasNext()) {
			cur.close();
			throw new RPCServiceExeption("Error: no such registered email");
		}

		else {

			try {

				while(cur.hasNext()) {
					//System.out.println(cursor.next());
					DBObject user = cur.next();
					LOG.info("getUserByCookie user found" + user.get("email"));
					//String pass1db = (String) user.get("pass1");
					String nickdb = (String) user.get("nick");

					//String md5pass = Commons.MD5(MongoPool.getSecretKey() + pass1);

					//LOG.info("md5pass " + md5pass);
					//LOG.info("pass1db " + pass1db);
					String sessionCalculated = Commons.MD5(MongoPool.getSecretKey() + email);
					//String sessionCalculated = Commons.MD5(MongoPool.getSecretKey() + pass1);
					if (sessionCalculated.equals(session)) {
						//String md5session = Commons.MD5(MongoPool.getSecretKey() + email);
						LOG.info("sessionCalculated.equals(session)");
						//Cookie cookie=new Cookie("silver9session", email + "###" + md5session);
						//response.addCookie(cookie);
						cur.close();
						User userObj = new User();
						userObj.setEmail(email);
						userObj.setNick(nickdb);
						return userObj;


					}
					else {
						throw new RPCServiceExeption("Error: wrong session");
					}

				}
			} finally {
				cur.close();
			} 

			//cur.close();
			//throw new RPCServiceExeption("Error: email already exist");
			//			cursor.next();
		}


		return null;
	}
}
