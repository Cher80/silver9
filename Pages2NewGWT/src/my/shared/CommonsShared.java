package my.shared;

import org.bson.types.ObjectId;

public class CommonsShared {

	
	
	
	public static String safeString (String stringId) {
		if (stringId==null||stringId.equals("")) { 
			//id = null;
			return null;
		}
		else {
			return stringId.trim();
		}
	
	}
	
	public static String safeIDBack (String stringId) {
		if (stringId==null) { 
			//id = null;
			return stringId = "";
		}
		else {
			return stringId.trim();
		}
	
	}
}
