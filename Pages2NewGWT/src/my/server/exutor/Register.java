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
import my.server.Commons;
import my.server.MongoPool;
import my.server.RPCServiceImpl;
import my.server.Verifier;
import my.shared.FieldVerifier;
import my.shared.User;


public class Register {

	public static final Logger LOG=Logger.getLogger(Register.class);
	//бейзганс е
	String nick;
	String email;
	String pass1;
	String pass2;
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
                System.out.println(cursor.next());
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

	public User executeRegister(String nick,String email,String pass1,String pass2, HttpServletResponse response, HttpServletRequest request) throws RPCServiceExeption {
		this.request = request;
		this.nick = nick;
		this.email = email;
		this.pass1 = pass1;
		this.pass2 = pass2;
		//if (true) 
		//throw new RPCServiceExeption("Eto strashnaya oshibka");

		LOG.info("executeRegister " + nick + email + pass1 + pass2);	

		checkIsAlreadyRegistered();
		checkInput();
		checkIfEmailExist();
		checkIfNickExist();






		DB db = MongoPool.getMainDB();

		String md5pass = Commons.MD5(MongoPool.getSecretKey() + pass1);
		//String md5session = Commons.MD5(MongoPool.getSecretKey() + email);
		
		LOG.info("getSecretKey " + MongoPool.getSecretKey());
		LOG.info("MD5 + salt pass " + md5pass);
		

		BasicDBObject user = new BasicDBObject();
		user.put("nick", nick);
		user.put("email", email);
		user.put("pass1", md5pass);
		//user.put("session", md5session);
		
		//comment.put("text", "Comment text" + randomInt + "_" + s);

		DBCollection users = db.getCollection("users");
		
		users.insert(user);
		
		(new SilverCookie(response, email)).setCookie();
		//Cookie cookie=new Cookie("silver9session", email + "###" + md5session);
		//cookie.setPath("/");
		//response.addCookie(cookie);
		
		ObjectId id = (ObjectId)user.get( "_id" );
		LOG.info("ObjectId id  " + id);

		
		User userObj = new User();
		userObj.setEmail(email);
		userObj.setNick(nick);
		return userObj;

		//db.getCollection("users");


		//return 4;
	}

}
