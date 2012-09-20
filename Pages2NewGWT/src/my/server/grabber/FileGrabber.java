package my.server.grabber;

import java.awt.image.BufferedImage;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.ImageOutputStreamImpl;
import javax.imageio.stream.MemoryCacheImageOutputStream;

import my.client.rpcs.RPCServiceExeption;
import my.server.MongoPool;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.bson.types.ObjectId;
import org.imgscalr.Scalr;

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

		//System.out.println("Connecting to Mura site...\n");

		URL url = new URL(urlstr);
		url.openConnection();
		InputStream reader = url.openStream();

		/*
		File yourFile = new File("/imgs/1.jpg");
		LOG.info("getAbsolutePath " + yourFile.getAbsolutePath());
		LOG.info("getCanonicalPath " + yourFile.getCanonicalPath());
		LOG.info("canWrite " + yourFile.canWrite());

		if(!yourFile.exists()) {
			LOG.info("!yourFile.exists()");
			yourFile.createNewFile();
		} 
		FileOutputStream writer = new FileOutputStream(yourFile, false); 
		*/

		//FileOutputStream writer = new FileOutputStream("/data/1.jpg");

		byte[] buffer = new byte[153600];
		int totalBytesRead = 0;
		int bytesRead = 0;


		//System.out.println("Reading ZIP file 150KB blocks at a time.\n");

		ByteArrayOutputStream buffer2 = new ByteArrayOutputStream();

		while ((bytesRead = reader.read(buffer)) > 0)
		{ 
			//writer.write(buffer, 0, bytesRead);
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


		InputStream in = new ByteArrayInputStream( buffer2.toByteArray() );
		BufferedImage bufferedImage = ImageIO.read( in );
		
		//Is has original
		boolean isHasOrig = isHasOriginal(bufferedImage);
		
		
		ObjectId saved_id_m;
		ObjectId saved_id_0;
		
		if (isHasOrig) {
			//Save original
			saved_id_0 = saveToGridFS(buffer2, md5String2+ "_orig");
			
			//Save main img resized
			BufferedImage thumbnail_m = this.makeResize(bufferedImage, 1500);
			ByteArrayOutputStream thumbnail_baos_m = SetJpegQuality(thumbnail_m);
			saved_id_m = saveToGridFS(thumbnail_baos_m, md5String2+ "_1500");
		} else {
			//Save original
			saved_id_0 = saveToGridFS(buffer2, md5String2+ "_orig");
			
			//Save main img (same as original to avoid errors)
			saved_id_m = saveToGridFS(buffer2, md5String2+ "_1500");
		}
		
		

		//Prepare square image
		BufferedImage thumbnail_squared = makeSquareImage(bufferedImage);
		
		//Save thumb 1
		BufferedImage thumbnail_1 = this.makeResize(thumbnail_squared, 120);
		ByteArrayOutputStream thumbnail_baos_1 = SetJpegQuality(thumbnail_1);
		ObjectId saved_id_1 = saveToGridFS(thumbnail_baos_1, md5String2+ "_120");
		
		//Save thumb 2
		BufferedImage thumbnail_2 = this.makeResize(thumbnail_squared, 245);
		ByteArrayOutputStream thumbnail_baos_2 = SetJpegQuality(thumbnail_2);
		ObjectId saved_id_2 = saveToGridFS(thumbnail_baos_2, md5String2+ "_245");


		long endTime = System.currentTimeMillis();

		System.out.println("Done. " + (new Integer(totalBytesRead).toString()) + " bytes read (" + (new Long(endTime - startTime).toString()) + " millseconds).\n");
		//writer.close();
		reader.close();
		
		HashMap photo = new HashMap();
		photo.put("saved_id_m", saved_id_m);
		photo.put("saved_id_0", saved_id_0);
		photo.put("saved_id_1", saved_id_1);
		photo.put("saved_id_2", saved_id_2);
		photo.put("ishasorig", isHasOrig);
		photo.put("md5", md5String2);
		photo.put("error", error);

		return photo;

	}

public boolean isHasOriginal(BufferedImage bufferedImage) {
	boolean isHas = false;
	int height=bufferedImage.getHeight();
	int width=bufferedImage.getWidth();
	LOG.info("isHasOriginal height " + height);
	LOG.info("isHasOriginal width " + width);
	if (height>1500||width>1500) {
		isHas=true;
	}
	
	return isHas;
}
	
	public ByteArrayOutputStream SetJpegQuality (BufferedImage bufferedImage) throws IOException {
		ByteArrayOutputStream thumbnail_baos = new ByteArrayOutputStream();

		Iterator iter = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writerr = (ImageWriter)iter.next();
		
		ImageWriteParam iwp = writerr.getDefaultWriteParam();
		iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		//iwp.setCompressionQuality(1);   // an integer between 0 and 1
		iwp.setCompressionQuality((float) 0.92);
		
		//ImageWriteParam iwp = new MyImageWriteParam();
		//iwp.setCompressionMode(ImageWriteParam.MODE_EXPLICIT) ;
		//iwp.setCompressionQuality(1);
		
		
		// 1 specifies minimum compression and maximum quality

		//File file = new File(OUTPUTFILE);
		//FileImageOutputStream output = new FileImageOutputStream(file);

		MemoryCacheImageOutputStream  memoryCacheImageOutputStream = new MemoryCacheImageOutputStream(thumbnail_baos);
		writerr.setOutput(memoryCacheImageOutputStream);
		//IIOImage image = new IIOImage(BUFFEREDIMAGE, null, null);
		//writerr.write(bufferedImage);
	//	writerr.writeToSequence(image, param)
		//writerr.write(null, bufferedImage, iwp);
		//IIOImage iIOImage = new IIOImage();
		
		writerr.write(null, new IIOImage(bufferedImage, null, null), iwp);
		//IIOImageToBufferedImage
		//writerr.write(streamMetadata, image, param)
		writerr.dispose();

		//ImageIO.write( thumbnail, "jpg", thumbnail_baos );
		thumbnail_baos.flush();
		LOG.info("thumbnail_baos.size() " + thumbnail_baos.size());
		return thumbnail_baos;
	}

	public BufferedImage makeResize(BufferedImage bufferedImage, int resize) {
		BufferedImage thumbnail = Scalr.resize(bufferedImage, Scalr.Method.QUALITY , resize);
		return thumbnail;
	}


	public BufferedImage makeSquareImage(BufferedImage bufferedImage) {
		int img_height = bufferedImage.getHeight(); 
		int img_width = bufferedImage.getWidth(); 
		int y_offset = 0;
		int x_offset = 0;
		boolean isPortret = false;
		int cropSize;
		if (img_height >= img_width) {
			isPortret = true;
			cropSize = img_width;
			y_offset = (img_height-img_width)/2;
		} else {
			isPortret = false;
			cropSize = img_height;
			x_offset = (img_width - img_height)/2;
		}		

		BufferedImage thumbnail = Scalr.crop(bufferedImage, x_offset, y_offset, cropSize, cropSize);
		return thumbnail;
	}


	public ObjectId saveToGridFS(ByteArrayOutputStream buffer2, String fname) {
		GridFS fs = new GridFS(dbImgs, "images");
		GridFSInputFile gfsFile = fs.createFile(buffer2.toByteArray());
		gfsFile.setFilename(fname +".jpg");
		gfsFile.save();
		ObjectId saved_id = (ObjectId)gfsFile.get( "_id" );
		String md5 = (String)gfsFile.get( "md5" );
		LOG.info("Gridfs ObjectId id  " + saved_id + " MD5 " + md5);
		return saved_id;
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
	
	
	
	protected BufferedImage IIOImageToBufferedImage(IIOImage iioImage)
	{
	RenderedImage renderedImage = iioImage.getRenderedImage();
	BufferedImage bufferedImage = (BufferedImage)renderedImage;

	return bufferedImage;
	}
}
