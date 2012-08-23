package my.server.exutor;

import my.server.MongoPool;

import com.mongodb.DB;

public class CommonDB {
	
	private DB db = MongoPool.getMainDB();
	
	public void CommonDB() {
		
	}
	
	//public
}
