package my.server.htmlpages;

import my.server.exutor.Albums;
import my.server.exutor.CommentsExec;
import my.server.exutor.Images;
import my.server.exutor.TagExec;
import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.CommentObj;
import my.shared.CommentsObj;
import my.shared.ImgObj;
import my.shared.ImgsObj;
import my.shared.ModelPageObj;
import my.shared.TagsObj;

public class ModelPage {
	private String albid;

	public ModelPage(String _albid) {
		this.albid = _albid;
	}
	
	public String  getModel() {
		
		String toRet = "";
		
		ModelPageObj modelPageObj = new ModelPageObj();		

		Albums albums =  new Albums();
		AlbumsObj albumsObj = albums.getAlbumsByTime(0, 0, null, 1, albid);	
		
		AlbumObj albumObj = null;
		if (albumsObj.getAlbums().size()>0) {
			albumObj = albumsObj.getAlbums().get(0);
			modelPageObj.setAlbumObj(albumObj); 
		} else {
			modelPageObj.setAlbumObj(new AlbumObj()); 
		}



		Images images =  new Images();
		ImgsObj imgsObj= images.getImages(albid);		
		modelPageObj.setImages(imgsObj); 

		CommentsExec commentsExec = new CommentsExec(); 
		CommentsObj commentsObj = commentsExec.doGetComments(albid);
		modelPageObj.setComments(commentsObj);

		////////render//////
		toRet +="<h3><a href=\"http://pinbelle.com/\">Goto to main page</a></h3>";
		toRet +="<a href=\"/extranewgwt/pageshtml?page=albums&pageno=0\">back to models catalog</a></br>";
		
		ModelPageHeader modelPageHeader =  new ModelPageHeader(modelPageObj.getAlbumObj());
		toRet += modelPageHeader.render();
		
		toRet +="<h4>Photos:</h4>";
		for (int i=0;i<imgsObj.getImages().size();i++) {
			ImgObj imgObj = imgsObj.getImages().get(i);
			ModelPagePic modelPagePic = new ModelPagePic(imgObj,albumObj);
			toRet += modelPagePic.render();
		}
		
		
		toRet +="<div style=\"clear:both\"></div><h4>Comments:</h4>";
		for (int i=0;i<commentsObj.getComments().size();i++) {
			CommentObj commentObj = commentsObj.getComments().get(i);
			ModelPageComment modelPageComment = new ModelPageComment(commentObj,albumObj);
			toRet += modelPageComment.render();
		}
		
		
		return toRet;
	}
	
}


