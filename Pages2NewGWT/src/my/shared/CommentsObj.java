package my.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class CommentsObj implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList <CommentObj> comments = new ArrayList<CommentObj>();;

	public ArrayList <CommentObj> getComments() {
		return comments;
	}

	public void setComments(ArrayList <CommentObj> comments) {
		this.comments = comments;
	}
}
