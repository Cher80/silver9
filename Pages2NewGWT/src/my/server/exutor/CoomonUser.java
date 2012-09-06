package my.server.exutor;

import org.bson.types.ObjectId;

import my.shared.CookieObj;
import my.shared.User;

import com.mongodb.DBObject;

public class CoomonUser {

	public void CoomonUser() {
		
	}
	
	public User getUserFromDBO(DBObject user) {
		
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
		urole = (int) user.get("urole");
		
		if (user.containsField("_id")) 
		id = (ObjectId)user.get( "_id" );
		
		if (user.containsField("email")) 
			email = (String)user.get( "email" );
		
		User userObj = new User();
		userObj.setEmail(email);
		userObj.setNick(nickdb);
		userObj.setFBUser(isfbdb);
		userObj.setUserRole(urole);
		userObj.setUid((String)id.toString());
		return userObj;

		
	}
	
	
	public CookieObj getCookieObjFromDBO(DBObject user) {
		
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
		urole = (int) user.get("urole");
		
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
