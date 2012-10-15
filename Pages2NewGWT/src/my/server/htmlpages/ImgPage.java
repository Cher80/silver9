package my.server.htmlpages;

import my.server.exutor.Albums;
import my.server.exutor.Images;
import my.shared.AlbumObj;
import my.shared.AlbumsObj;
import my.shared.ImgObj;

public class ImgPage {

	private String imgid; 
	private String albid;
	
	public ImgPage(String _imgid, String _albid) {
		this.albid = _albid;
		this.imgid = _imgid;
		
	}
	
	public String render() {
		String toRet = "";
		
		Albums albums =  new Albums();
		AlbumsObj albumsObj = albums.getAlbumsByTime(0, 0, null, 1, albid);	
		
		AlbumObj albumObj = null;
		if (albumsObj.getAlbums().size()>0) {
			albumObj = albumsObj.getAlbums().get(0);
		} else {
			albumObj = new AlbumObj(); 
		}
		
		
		Images images = new Images();
		ImgObj imgObj = images.getOneImageById(imgid);
		
		///////////render////////////
		toRet +="<h3><a href=\"http://pinbelle.com/\">Goto to main page</a></h3>";
		toRet +="<a href=\"/extranewgwt/pageshtml?page=album&albid=" + albumObj.getAlbid() + "\">back to model page</a></br>";
		
		
		ImgPageHeader imgPageHeader = new ImgPageHeader(albumObj);
		toRet += imgPageHeader.render();
		
		ImgPagePic imgPagePic = new ImgPagePic(imgObj,albumObj);
		toRet += imgPagePic.render();
		
		
		
		
		
		return toRet;
	}
	
	
}
