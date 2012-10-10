package my.server.exutor;

import javax.servlet.http.Cookie;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import my.client.rpcs.RPCServiceExeption;
import my.server.CommonsServer;
import my.server.MongoPool;
import my.shared.CookieObj;
import my.shared.User;

public class UserCookie {
	public static final Logger LOG=Logger.getLogger(UserCookie.class);

	public UserCookie() {

	}

	public User getUserByCookie(CookieObj cookieObj) throws RPCServiceExeption {
		
		if (cookieObj==null) {
			throw new RPCServiceExeption("Error: cookie = null!");
		}
		
		/*
		cookie = cookie.replace("\"", "");

		String[] tokens = cookie.split("###");
		String email = tokens[0];
		String session = tokens[1];
		LOG.info("UserCookie email " + email);
		LOG.info("UserCookie session " + session);
*/
		String email = cookieObj.getEmail();
		String session =  cookieObj.getMd5session();
		
		
		DB db = MongoPool.getMainDB();
		DBCollection users = db.getCollection("users");

		BasicDBObject query = new BasicDBObject();
		query.append("email", email);


		DBCursor cur = users.find(query);


		if (!cur.hasNext()) {
			cur.close();
			throw new RPCServiceExeption("Error: no such registered email. Please clear cookie");
		}

		else {

			try {

				while(cur.hasNext()) {

					DBObject user = cur.next();
					LOG.info("getUserByCookie user found" + user.get("email"));
					

										
					
					String sessionCalculated = CommonsServer.MD5(MongoPool.getSecretKey() + email);

					if (sessionCalculated.equals(session)) {
						LOG.info("sessionCalculated.equals(session)");
						cur.close();
						CoomonUser coomonUser = new CoomonUser();
						return coomonUser.getUserFromDBO(user);

					}
					else {
						throw new RPCServiceExeption("Error: wrong session. Please clear cookie");
					}

				}
			} finally {
				cur.close();
			} 

		}


		return null;
	}
}
