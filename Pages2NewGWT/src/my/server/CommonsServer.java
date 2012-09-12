package my.server;

import org.bson.types.ObjectId;

public class CommonsServer {

	public static String MD5(String md5) {
		   try {
		        java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		        byte[] array = md.digest(md5.getBytes());
		        StringBuffer sb = new StringBuffer();
		        for (int i = 0; i < array.length; ++i) {
		          sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
		       }
		        return sb.toString();
		    } catch (java.security.NoSuchAlgorithmException e) {
		    }
		    return null;
		}
	
	public static ObjectId normalizeID (String id) {
		if (id==null||id.equals("")) {
			//id = null;
			return null;
		}
		else {
			return new ObjectId(id.trim());
		}
	
	}
	
	
	public static String fromIDtoString (ObjectId objId) {
		if (objId==null) {
			//id = null;
			return "";
		}
		else {
			return objId.toString();
		}
	
	}
	
	

}
