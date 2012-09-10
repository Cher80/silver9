package my.shared;

import java.io.Serializable;

public class CommentObj implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private String commentText;
	private long commentTimeStamp = 0;
	private String commentAuthorID;
	private String commentAuthorNick;
	private String albumId;
	private String albumModelName;
	private String coverImgObjID = "";
	private String commentID = null;
	
	
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}

	public long getCommentTimeStamp() {
		return commentTimeStamp;
	}
	public void setCommentTimeStamp(long commentTimeStamp) {
		this.commentTimeStamp = commentTimeStamp;
	}


	public String getCommentAuthorID() {
		return commentAuthorID;
	}
	public void setCommentAuthorID(String commentAuthorID) {
		this.commentAuthorID = commentAuthorID;
	}
	public String getCommentAuthorNick() {
		return commentAuthorNick;
	}
	public void setCommentAuthorNick(String commentAuthorNick) {
		this.commentAuthorNick = commentAuthorNick;
	}
	public String getAlbumId() {
		return albumId;
	}
	public void setAlbumId(String albumId) {
		this.albumId = albumId;
	}
	public String getCommentID() {
		return commentID;
	}
	public void setCommentID(String commentID) {
		this.commentID = commentID;
	}
	public String getAlbumModelName() {
		return albumModelName;
	}
	public void setAlbumModelName(String albumModelName) {
		this.albumModelName = albumModelName;
	}
	public String getCoverImgObjID() {
		return coverImgObjID;
	}
	public void setCoverImgObjID(String coverImgObjID) {
		this.coverImgObjID = coverImgObjID;
	}
}
