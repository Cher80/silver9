package my.shared;

import java.io.Serializable;

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
		return activityID.trim();
	}
	public void setActivityID(String activityID) {
		this.activityID = activityID.trim();
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
		return uid.trim();
	}
	public void setUid(String uid) {
		this.uid = uid.trim();
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
		return coverImgObjID.trim();
	}
	public void setCoverImgObjID(String coverImgObjID) {
		this.coverImgObjID = coverImgObjID.trim();
	}
	public String getAlbid() {
		return albid.trim();
	}
	public void setAlbid(String albid) {
		this.albid = albid.trim();
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
		return commentID.trim();
	}
	public void setCommentID(String commentID) {
		this.commentID = commentID.trim();
	}
	public String getTagGroup() {
		return tagGroup;
	}
	public void setTagGroup(String tagGroup) {
		this.tagGroup = tagGroup;
	}
	
}
