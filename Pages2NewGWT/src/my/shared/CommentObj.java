package my.shared;

import java.io.Serializable;

public class CommentObj implements Serializable {
	

	private static final long serialVersionUID = 1L;
	private String commentText;
	private String commentdAuthorName;
	private long commentTimeStamp;
	
	public String getCommentText() {
		return commentText;
	}
	public void setCommentText(String commentText) {
		this.commentText = commentText;
	}
	public String getCommentdAuthorName() {
		return commentdAuthorName;
	}
	public void setCommentdAuthorName(String commentdAuthorName) {
		this.commentdAuthorName = commentdAuthorName;
	}
	public long getCommentTimeStamp() {
		return commentTimeStamp;
	}
	public void setCommentTimeStamp(long commentTimeStamp) {
		this.commentTimeStamp = commentTimeStamp;
	}
}
