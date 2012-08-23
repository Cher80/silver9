package my.server.grabber;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

import my.client.rpcs.RPCServiceExeption;
import my.server.MongoPool;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.gridfs.GridFS;
import com.mongodb.gridfs.GridFSInputFile;

public class FileGrabber {
	public static final Logger LOG=Logger.getLogger(FileGrabber.class);
	DB dbImgs = MongoPool.getImgsDB();
	DB dbMain = MongoPool.getMainDB();
	
	public FileGrabber() {

	}

	public HashMap getFile(String urlstr) throws IOException {

		
		long startTime = System.currentTimeMillis();

		System.out.println("Connecting to Mura site...\n");

		URL url = new URL(urlstr);
		url.openConnection();
		InputStream reader = url.openStream();

		File yourFile = new File("/imgs/1.jpg");
		LOG.info("getAbsolutePath " + yourFile.getAbsolutePath());
		LOG.info("getCanonicalPath " + yourFile.getCanonicalPath());
		LOG.info("canWrite " + yourFile.canWrite());

		if(!yourFile.exists()) {
			LOG.info("!yourFile.exists()");
			yourFile.createNewFile();
		} 
		FileOutputStream writer = new FileOutputStream(yourFile, false); 

		//FileOutputStream writer = new FileOutputStream("/data/1.jpg");

		byte[] buffer = new byte[153600];
		int totalBytesRead = 0;
		int bytesRead = 0;


		System.out.println("Reading ZIP file 150KB blocks at a time.\n");

		ByteArrayOutputStream buffer2 = new ByteArrayOutputStream();
		
		while ((bytesRead = reader.read(buffer)) > 0)
		{ 
			writer.write(buffer, 0, bytesRead);
			buffer2.write(buffer, 0, bytesRead);
			buffer = new byte[153600];
			//bufferAll
			
			totalBytesRead += bytesRead;
		}

		String md5String2  = DigestUtils.md5Hex(buffer2.toByteArray());
		LOG.info("md5String2 buffer2 " + md5String2);
		
		
		String error = null;
		if (checkIfExistMD5(md5String2)) {
			error = "MD5 checker: photo exist!";
		}
		
		
		GridFS fs = new GridFS(dbImgs, "images");
		GridFSInputFile gfsFile = fs.createFile(buffer2.toByteArray());
		gfsFile.setFilename("999.jpg");
		gfsFile.save();
		ObjectId saved_id = (ObjectId)gfsFile.get( "_id" );
		String md5 = (String)gfsFile.get( "md5" );
		LOG.info("Gridfs ObjectId id  " + saved_id + " MD5 " + md5);
		
		long endTime = System.currentTimeMillis();

		System.out.println("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).\n");
		writer.close();
		reader.close();
		HashMap photo = new HashMap();
		photo.put("saved_id", saved_id);
		photo.put("md5", md5String2);
		photo.put("error", error);
		
		return photo;

	}
	
	
	private boolean checkIfExistMD5(String md5String) {
		
		boolean isMD5exist = false;
		LOG.info("checkIfExist start");
		//DB db = MongoPool.getMainDB();
		//DB db = MongoPool.getImgsDB();
		
		DBCollection images = dbMain.getCollection("images");
		
		BasicDBObject query = new BasicDBObject();
		query.append("md5", md5String);


		DBCursor cur = images.find(query);
		
		if (cur.hasNext()) {
			isMD5exist = true;
		}
		/*
		if (cur.size()==0) {
			cur.close();
		}
		else {
			cur.close();
			throw new RPCServiceExeption("Error: email already exist");
		}*/
		
		/*
		try {
            while(cur.hasNext()) {
                //System.out.println(cur.next());
                LOG.info("checkIfExist cur.next()  " + cur.next());
            }
        } finally {
        	cur.close();
        }
		*/
		LOG.info("checkIfExistMD5 isMD5exist " + isMD5exist);
		return isMD5exist;
		
	}
}
