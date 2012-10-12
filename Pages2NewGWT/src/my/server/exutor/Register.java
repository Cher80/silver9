package my.server.exutor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;

import my.client.rpcs.RPCServiceExeption;
import my.server.CommonsServer;
import my.server.MongoPool;
import my.server.RPCServiceImpl;
import my.server.Verifier;
import my.shared.CookieObj;
import my.shared.FieldVerifier;
import my.shared.User;


public class Register {

	public static final Logger LOG=Logger.getLogger(Register.class);
	//бейзганс е
	String nick;
	String email;
	String pass1;
	String pass2;
	boolean isFb;
	int urole;
	HttpServletRequest request;

	public Register() {

	}

	private void checkInput() {
		//Nick
		if (Verifier.isContainBadCharacters(nick)) {
			throw new RPCServiceExeption("Error: nick Please dont XSS me!");
		}

		if (!FieldVerifier.isLenghtOK(nick,1,20)) {
			throw new RPCServiceExeption("Error: nick too small (min 1 max 20)");
		}

		//Email
		if (Verifier.isContainBadCharacters(email)) {
			throw new RPCServiceExeption("Error: email Please dont XSS me!");
		}

		if (!FieldVerifier.isValidEmailAddress(email)) {
			throw new RPCServiceExeption("Error: bad email");
		}

		//Pass
		if (Verifier.isContainBadCharacters(pass1)) {
			throw new RPCServiceExeption("Error: pass1 Please dont XSS me!");
		}

		if (!FieldVerifier.isLenghtOK(pass1,1,30)) {
			throw new RPCServiceExeption("Error: password too small");
		}

		if (!pass1.equals(pass2)) {
			throw new RPCServiceExeption("Error: passwords don't match");
		}
	}


	private void checkIfNickExist() {
		DB db = MongoPool.getMainDB();
		DBCollection users = db.getCollection("users");
		
		BasicDBObject query = new BasicDBObject();
		query.append("nick", nick);


		DBCursor cur = users.find(query);
		if (!cur.hasNext()) {
			cur.close();
		}
		else {
			cur.close();
			throw new RPCServiceExeption("Error: nick already exist");
		}
	}
	
	
	
	public boolean checkIfEmailExist(String emailp) {
		DB db = MongoPool.getMainDB();
		DBCollection users = db.getCollection("users");
		
		BasicDBObject query = new BasicDBObject();
		query.append("email", emailp.trim());


		DBCursor cur = users.find(query);
		if (cur.size()==0) {
			cur.close();
			LOG.info("Email not found ");
			return false;
		}
		else {
			cur.close();
			LOG.info("Email found ");
			return true;
		}
		

	}
	
	private void checkIfEmailExist() {
		DB db = MongoPool.getMainDB();
		DBCollection users = db.getCollection("users");
		
		BasicDBObject query = new BasicDBObject();
		query.append("email", email);


		DBCursor cur = users.find(query);
		if (cur.size()==0) {
			cur.close();
		}
		else {
			cur.close();
			throw new RPCServiceExeption("Error: email already exist");
		}
		
		/*
		try {
            while(cur.hasNext()) {
                //System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }*/
	}

	public void checkIsAlreadyRegistered() {
		Cookie[] cookies=request.getCookies();

		if (cookies !=null) {
			for (int i=0; i<cookies.length; i++) {
				LOG.info("cookies[i].getName() " + cookies[i].getName());
				if (cookies[i].getName().equals("silver9session")) {
					throw new RPCServiceExeption("Error: you are already logged in");
				}
			}
		}
		else {
			//Cookie cookie=new Cookie("silver9session","1234olo");
			//getThreadLocalResponse().addCookie(cookie);
			//getThreadLocalResponse()cookies
			//this.getThreadLocalResponse()
			//LOG.info("Error: Please login!");
			//throw new RPCServiceExeption("Error: Please login!");
			
		}
	}

	public User executeRegister(String nick,String email,String pass1,String pass2, boolean isFb, int urole, HttpServletResponse response, HttpServletRequest request) throws RPCServiceExeption {
		this.request = request;
		this.nick = nick;
		this.email = email;
		this.pass1 = pass1;
		this.pass2 = pass2;
		this.isFb = isFb;
		this.urole = urole;
		
		if (nick.equals("Anonymous")) {
			this.urole = 0;
			urole = 0;
		}
		//if (true) 
		//throw new RPCServiceExeption("Eto strashnaya oshibka");

		LOG.info("executeRegister " + nick + email + pass1 + pass2 + isFb + urole);	

		checkIsAlreadyRegistered();
		checkInput();
		checkIfEmailExist();
		checkIfNickExist();






		DB db = MongoPool.getMainDB();

		String md5pass = CommonsServer.MD5(MongoPool.getSecretKey() + pass1);
		//String md5session = Commons.MD5(MongoPool.getSecretKey() + email);
		
		LOG.info("getSecretKey " + MongoPool.getSecretKey());
		LOG.info("MD5 + salt pass " + md5pass);
		

		BasicDBObject user = new BasicDBObject();
		user.put("nick", nick);
		user.put("email", email);
		user.put("pass1", md5pass);
		user.put("isfb", isFb);
		user.put("urole", urole);
		//this.isFb = isFb;
		//this.urole = urole;
		//user.put("session", md5session);
		
		//comment.put("text", "Comment text" + randomInt + "_" + s);

		DBCollection users = db.getCollection("users");
		
		users.insert(user);
		
		
		CoomonUser coomonUser = new CoomonUser();
		CookieObj cookieObj = coomonUser.getCookieObjFromDBO(user);
		
		(new SilverCookie(response, request)).setCookie(cookieObj);
		//Cookie cookie=new Cookie("silver9session", email + "###" + md5session);
		//cookie.setPath("/");
		//response.addCookie(cookie);
		
		ObjectId id = (ObjectId)user.get( "_id" );
		LOG.info("ObjectId id  " + id);

		
		User userObj = new User();
		userObj.setEmail(email);
		userObj.setNick(nick);
		userObj.setFBUser(isFb);
		userObj.setUserRole(urole);
		userObj.setUid((String)id.toString());
		return userObj;

		//db.getCollection("users");


		//return 4;
	}

	
	public void setFBAccesToken(User user, String token,int token_expires) {
		
		DB db = MongoPool.getMainDB();
		DBCollection users = db.getCollection("users");
		
		BasicDBObject query = new BasicDBObject();
		
		query.append("_id", new ObjectId(user.getUid()));		
		BasicDBObject updt = new BasicDBObject("$set",new BasicDBObject("fbaccess_token", token));
		
		users.update(
				query,
				updt,
		true,
		false);
		
		
		BasicDBObject query2 = new BasicDBObject();
		
		query2.append("_id", new ObjectId(user.getUid()));		
		BasicDBObject updt2 = new BasicDBObject("$set",new BasicDBObject("fbexpires", token_expires));
		
		users.update(
				query2,
				updt2,
		true,
		false);
		
	}
	
	
	
	
	
}
