package my.server.htmlpages;

import java.util.Date;

import my.shared.AlbumObj;
import my.shared.CommentObj;
import my.shared.CommentsObj;
import my.shared.ImgObj;

public class ModelPageComment {

	//private ImgObj imgObj; 
	private AlbumObj albumObj;
	private CommentObj commentObj;
	
	public ModelPageComment(CommentObj _commentObj, AlbumObj _albumObj) {
		this.commentObj = _commentObj;
		this.albumObj = _albumObj;
		
	}
	
	public String render() {
		
		Date commDate = new Date(commentObj.getCommentTimeStamp());
		String toRet = "";
		toRet += "<div style=\"clear:both;\">";
		toRet += "Author: " + commentObj.getCommentAuthorNick() + " date: " + commDate.toString();
		toRet += "<div style=\"clear:both;\">";
		toRet += commentObj.getCommentText();
		toRet +="</div>";
		toRet +="</div>";
		toRet +="<br/>";
		
		return toRet;
	}
	
	
}
