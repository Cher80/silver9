package my.server.exutor;

import java.util.ArrayList;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.bson.types.ObjectId;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import my.client.rpcs.RPCServiceExeption;
import my.server.CommonsServer;
import my.server.MongoPool;
import my.server.RPCServiceImpl;
import my.server.Verifier;
import my.shared.ActivityObj;
import my.shared.AlbumObj;
import my.shared.CommentObj;
import my.shared.CommentsObj;
import my.shared.CommonsShared;
import my.shared.FieldVerifier;
import my.shared.User;


public class CommentsExec {

	public static final Logger LOG=Logger.getLogger(CommentsExec.class);
	//бейзганс е
	
	private HttpServletRequest request;
	private CommentObj commentObj;
	private DB db = MongoPool.getMainDB();
	private AlbumObj albumObj;

	public CommentsExec() {

	}

	private void checkInput() {
		//Nick
	
		//Pass
		if (Verifier.isContainBadCharacters(commentObj.getCommentText())) {
			throw new RPCServiceExeption("Error: pass1 Please dont XSS me!");
		}
		
		if (Verifier.isContainBadCharacters(commentObj.getAlbumModelName())) {
			throw new RPCServiceExeption("Error: pass1 Please dont XSS me!");
		}
		
		if (Verifier.isContainBadCharacters(commentObj.getCommentAuthorNick())) {
			throw new RPCServiceExeption("Error: pass1 Please dont XSS me!");
		}

		if (!FieldVerifier.isLenghtOK(commentObj.getCommentText(),1,1030)) {
			throw new RPCServiceExeption("Error: too big cooment");
		}
		
		if (!FieldVerifier.isLenghtOK(commentObj.getAlbumModelName(),1,30)) {
			throw new RPCServiceExeption("Error: too big model name");
		}
		
		if (!FieldVerifier.isLenghtOK(commentObj.getCommentAuthorNick(),1,30)) {
			throw new RPCServiceExeption("Error: too big author name");
		}

	}


	

	public CommentObj executeCommentPost(CommentObj commentObj, AlbumObj albumObjj) throws RPCServiceExeption {
		//this.request = request;
		this.commentObj = commentObj;
		this.albumObj = albumObjj;
		
		commentObj.setCoverPicObjID(albumObj.getCoverPicID());
		//if (true) 
		//throw new RPCServiceExeption("Eto strashnaya oshibka");

		LOG.info("executeCommentPost " + commentObj.getCommentText());	

		checkInput();
		
		
		
		if (commentObj.getCommentAuthorNick().equals("Anonymous")) {
			LOG.info("executeCommentPost Anonymous");	
			Anonim anonim = new Anonim();
			String uid = anonim.getAnonimUser().getUid();
			commentObj.setCommentAuthorID(uid);
		}
			//newAnonimUser
		
		commentObj.setCommentTimeStamp((long)System.currentTimeMillis()/1000);
		
		
		BasicDBObject comment = new BasicDBObject();
		comment.put("commenttext", commentObj.getCommentText());
		comment.put("commenttimestamp", commentObj.getCommentTimeStamp());
		comment.put("commentauthorid",  CommonsServer.normalizeID(commentObj.getCommentAuthorID().trim()));
		comment.put("commentauthornick", commentObj.getCommentAuthorNick());
		comment.put("albumid",  CommonsServer.normalizeID(commentObj.getAlbumId().trim()));
		comment.put("albummodelname", commentObj.getAlbumModelName());
		comment.put("albumcoverImgObjID", CommonsServer.normalizeID(albumObj.getCoverImgObjID()));
		comment.put("albumcoverPicObjID", CommonsServer.normalizeID(albumObj.getCoverPicID()));
		//user.put("session", md5session);
		
		//comment.put("text", "Comment text" + randomInt + "_" + s);

		DBCollection comments = db.getCollection("comments");
		
		comments.insert(comment);
		
		
		ObjectId id = (ObjectId)comment.get( "_id" );
		LOG.info("ObjectId id  " + id);

		
		commentObj.setCommentID((String)id.toString());
		
		setActivity();
		
		return commentObj;

		//db.getCollection("users");


		//return 4;
	}

	
	
	///////////////////////////////////////
	
	public void setActivity() {
		ActivityObj activityObj = new ActivityObj();
		
		//activityObj.setActivityID(activityID);
		activityObj.setActivityType("COMMENT");
		activityObj.setAlbid(this.commentObj.getAlbumId());
		activityObj.setAlbname(this.commentObj.getAlbumModelName());
		activityObj.setCommentID(this.commentObj.getCommentID());
		activityObj.setCommentText(this.commentObj.getCommentText());
		activityObj.setCoverImgObjID(this.commentObj.getCoverImgObjID());
		activityObj.setCoverPicObjID(this.commentObj.getCoverPicObjID());
		activityObj.setNick(this.commentObj.getCommentAuthorNick());
		
		activityObj.setTagReadableName(null);
		activityObj.setTagType(null);
		activityObj.setUid(this.commentObj.getCommentAuthorID());
		
		ActivityExec activityExec = new ActivityExec();
		activityExec.setActivity(activityObj);
	}
	
	
	
	
	////////////////////////////////////////////
	
	public CommentsObj doGetComments(String albid) throws RPCServiceExeption {
		
		DBCollection comments = db.getCollection("comments");

		BasicDBObject query = new BasicDBObject();
		query.append("albumid", new ObjectId(albid.trim()));

		BasicDBObject sort = new BasicDBObject();
		sort.append("commenttimestamp", -1);

		DBCursor cur = comments.find(query).sort(sort).skip(0).limit(20);
			
		CommentsObj commentsObj = new CommentsObj();
		
		while(cur.hasNext()) {
			BasicDBObject commentDBO = (BasicDBObject) cur.next();
			
			CommentObj commentObj = new CommentObj();
			
			commentObj = commentDBOtoObj(commentDBO);
			
			commentsObj.getComments().add(commentObj);
		
		}
		
		return commentsObj;
	
		
	}
		
		public CommentObj commentDBOtoObj(BasicDBObject commentDBO) {

			CommentObj commentObj = new CommentObj();
			
			 String commentText = null;
			 long commentTimeStamp = 0;
			 String commentAuthorID = null;
			 String commentAuthorNick = null;
			 String albumId = null;
			 String albumModelName = null;
			 String commentID = null;
			 String albumcoverImgObjID = null;
			 String albumcoverPicObjID = null;
		
				//comment.put("albumcoverImgObjID", CommonsServer.normalizeID(commentObj.getCoverImgObjID()));
				//comment.put("albumcoverPicObjID", CommonsServer.normalizeID(commentObj.getCoverPicObjID()));

				if (commentDBO.containsField("albumcoverImgObjID")) 
					albumcoverImgObjID = CommonsServer.fromIDtoString(commentDBO.getObjectId("albumcoverImgObjID")); // (String) commentDBO.get("albumcoverImgObjID");
				
				if (commentDBO.containsField("albumcoverPicObjID")) 
					albumcoverPicObjID = CommonsServer.fromIDtoString(commentDBO.getObjectId("albumcoverPicObjID")); //(String) commentDBO.get("albumcoverPicObjID");
			 
			if (commentDBO.containsField("commenttext")) 
				commentText = (String) commentDBO.get("commenttext");
			
			if (commentDBO.containsField("commenttimestamp")) 
				commentTimeStamp = (long) commentDBO.get("commenttimestamp");
			
			if (commentDBO.containsField("commentauthorid")) 
				commentAuthorID = CommonsServer.fromIDtoString(commentDBO.getObjectId("commentauthorid")); //(String) commentDBO.get("commentauthorid").toString();
			
			if (commentDBO.containsField("commentauthornick")) 
				commentAuthorNick = (String) commentDBO.get("commentauthornick");
			
			if (commentDBO.containsField("albumid")) 
				albumId = CommonsServer.fromIDtoString(commentDBO.getObjectId("albumid")); // (String) commentDBO.get("albumid").toString();
			
			if (commentDBO.containsField("albummodelname")) 
				albumModelName = (String) commentDBO.get("albummodelname");
			
			if (commentDBO.containsField("_id")) 
				commentID = CommonsServer.fromIDtoString(commentDBO.getObjectId("_id")); //(String) commentDBO.get("_id").toString();
		
			commentObj.setAlbumId(albumId);
			commentObj.setAlbumModelName(albumModelName);
			commentObj.setCommentAuthorID(commentAuthorID);
			commentObj.setCommentAuthorNick(commentAuthorNick);
			commentObj.setCommentID(commentID);
			commentObj.setCommentText(commentText);
			commentObj.setCommentTimeStamp(commentTimeStamp);
			commentObj.setCoverImgObjID(albumcoverImgObjID);
			commentObj.setCoverPicObjID(albumcoverPicObjID);
			return commentObj;
		}
		
		
		
	 
	
	
}
