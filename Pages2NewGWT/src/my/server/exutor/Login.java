package my.server.exutor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import my.client.rpcs.RPCServiceExeption;
import my.server.Commons;
import my.server.MongoPool;
import my.shared.CookieObj;
import my.shared.User;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class Login {

	public static final Logger LOG=Logger.getLogger(Login.class);

	String email;
	String pass1;
	HttpServletRequest request;
	

	public void checkIsAlreadyLogin() {
		Cookie[] cookies=request.getCookies();

		if (cookies !=null) {
			for (int i=0; i<cookies.length; i++) {
				LOG.info("cookies[i].getName() " + cookies[i].getName());
				if (cookies[i].getName().equals("silver9session")) {
					throw new RPCServiceExeption("Error: you are already logged");
				}
			}
		}
	}

	public User executeLogin(String email,String pass1,boolean canSkipPassword, HttpServletResponse response, HttpServletRequest request) throws RPCServiceExeption {
		this.request = request;
		this.email = email;
		this.pass1 = pass1;

		LOG.info("executeLogin " + email + pass1);	

		checkIsAlreadyLogin();
		LOG.info("no Cookie, do login");	
		//checkInput();
		//checkIfEmailExist();
		//checkIfNickExist();









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
					LOG.info("user " + user);
					
					
					String pass1db = null;
					
					
					if (user.containsField("pass1")) 
					pass1db = (String) user.get("pass1");
					
					String md5pass = Commons.MD5(MongoPool.getSecretKey() + pass1);

					LOG.info("md5pass " + md5pass);
					LOG.info("pass1db " + pass1db);
					if (pass1db.equals(md5pass) || canSkipPassword) {
						
						CoomonUser coomonUser = new CoomonUser();
						CookieObj cookieObj = coomonUser.getCookieObjFromDBO(user);
						//SilverCookie silverCookie = new SilverCookie(response, email);
						(new SilverCookie(response, request)).setCookie(cookieObj);
						
						cur.close();
						return coomonUser.getUserFromDBO(user);
						//return userObj;
						
						
					}
					else {
						throw new RPCServiceExeption("Error: wrong password");
					}
				
				}
			} finally {
				cur.close();
			} 

			//cur.close();
			//throw new RPCServiceExeption("Error: email already exist");
			//			cursor.next();
		}

		/*
		try {
            while(cur.hasNext()) {
                System.out.println(cursor.next());
            }
        } finally {
            cursor.close();
        }*/




		//ObjectId id = (ObjectId)user.get( "_id" );
		//LOG.info("ObjectId id  " + id);



		//db.getCollection("users");int


		return null;
	}
}
