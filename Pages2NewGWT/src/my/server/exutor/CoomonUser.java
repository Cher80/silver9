package my.server.exutor;

import org.bson.types.ObjectId;

import my.shared.CookieObj;
import my.shared.User;

import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;

public class CoomonUser {

	public void CoomonUser() {
		
	}
	
	public User getUserFromDBO(DBObject user1) {
BasicDBObject user = (BasicDBObject)user1;
		String pass1db = null;
		String nickdb = null;
		String email = null;
		String fbaccess_token = null;
		
		boolean isfbdb = false;
		int urole = 1;
		ObjectId id = null;
		
		if (user.containsField("pass1")) 
		pass1db = (String) user.get("pass1");
		
		if (user.containsField("nick")) 
		nickdb = (String) user.get("nick");
		
		if (user.containsField("isfb")) 
		isfbdb = (boolean) user.get("isfb");
		
		if (user.containsField("urole")) 
		urole = (int) user.getInt("urole");
		
		if (user.containsField("_id")) 
		id = (ObjectId)user.get( "_id" );
		
		if (user.containsField("email")) 
			email = (String)user.get( "email" );
		
		if (user.containsField("fbaccess_token")) 
			fbaccess_token = (String)user.get( "fbaccess_token" );
		
		User userObj = new User();
		userObj.setEmail(email);
		userObj.setNick(nickdb);
		userObj.setFBUser(isfbdb);
		userObj.setFBaccess_token(fbaccess_token);
		userObj.setUserRole(urole);
		userObj.setUid((String)id.toString());
		return userObj;

		
	}
	
	
	public CookieObj getCookieObjFromDBO(DBObject user1) {
		
		BasicDBObject user = (BasicDBObject) user1;
		
		String pass1db = null;
		String nickdb = null;
		String email = null;
		boolean isfbdb = false;
		int urole = 1;
		ObjectId id = null;
		
		if (user.containsField("pass1")) 
		pass1db = (String) user.get("pass1");
		
		if (user.containsField("nick")) 
		nickdb = (String) user.get("nick");
		
		if (user.containsField("isfb")) 
		isfbdb = (boolean) user.get("isfb");
		
		if (user.containsField("urole")) 
		//urole = (int) user.get("urole");
			urole = (int) user.getInt("urole");
		
		if (user.containsField("_id")) 
		id = (ObjectId)user.get( "_id" );
		
		if (user.containsField("email")) 
			email = (String)user.get( "email" );
		
		
		CookieObj cookieObj = new CookieObj();
		cookieObj.setEmail(email);
		cookieObj.setNick(nickdb);
		cookieObj.setFBUser(isfbdb);
		cookieObj.setUserRole(urole);
		//cookieObj.setNick();
		

		
		return cookieObj;
	}
}
