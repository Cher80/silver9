package my.shared;

import java.io.Serializable;

import my.server.CommonsServer;

public class ActivityObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String activityID = "";
	
	private String activityType = "";
	private long timestamp = 0;
	
	private String uid = "";
	private String nick= "Anonymous";
	
	private String albname = "";
	private String coverImgObjID = "";
	private String albid = "";
	
	private String tagType = "";
	private String tagReadableName = "";
	private String tagGroup = "";
	
	private String commentText = "";
	private String commentID = "";
	
	
	
	
	public String getActivityID() {
		return CommonsShared.safeString(activityID);
	}
	public void setActivityID(String activityID) {
		this.activityID = CommonsShared.safeString(activityID);
	}
	public String getActivityType() {
		return activityType;
	}
	public void setActivityType(String activityType) {
		this.activityType = activityType;
	}
	public long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}
	public String getUid() {
		return CommonsShared.safeString(uid);
	}
	public void setUid(String uid) {
			this.uid = CommonsShared.safeString(uid);
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getAlbname() {
		return albname;
	}
	public void setAlbname(String albname) {
		this.albname = albname;
	}
	
	public String getCoverImgObjID() {
		return CommonsShared.safeString(coverImgObjID);		
	}
	
	public void setCoverImgObjID(String coverImgObjID) {
			this.coverImgObjID = CommonsShared.safeString(coverImgObjID);
	}
	
	public String getAlbid() {
		return CommonsShared.safeString(albid);
	}
	public void setAlbid(String albid) {
			this.albid = CommonsShared.safeString(albid);
	}
	
	public String getTagType() {
		return tagType;
	}
	public void setTagType(String tagType) {
		this.tagType = tagType;
	}
	public String getTagReadableName() {
		return tagReadableName;
	}
	public void setTagReadableName(String tagReadableName) {
		this.tagReadableName = tagReadableName;
	}
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public String getCommentID() {
		return CommonsShared.safeString(commentID);
	}
	public void setCommentID(String commentID) {
			this.commentID = CommonsShared.safeString(commentID);
	}
	
	public String getTagGroup() {
		return tagGroup;
	}
	public void setTagGroup(String tagGroup) {
		this.tagGroup = tagGroup;
	}
	
}
