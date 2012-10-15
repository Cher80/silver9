package my.server.htmlpages;

import my.shared.AlbumObj;
import my.shared.ImgObj;

public class ModelPagePic {

	private ImgObj imgObj; 
	private AlbumObj albumObj;
	
	public ModelPagePic(ImgObj _imgObj, AlbumObj _albumObj) {
		this.imgObj = _imgObj;
		this.albumObj = _albumObj;
		
	}
	
	public String render() {
		
		
		String toRet = "";
		toRet += "<div style=\"float:left;\">";
		toRet += "<a href=\"/extranewgwt/pageshtml?page=img&imgid=" + imgObj.getImgID() + "&albid=" + albumObj.getAlbid() + "\">  <img src=\"/extranewgwt/getphoto?photoid=" + imgObj.getImgGridfs_id_1() + "\"/></a></br>";
		toRet +="<a href=\"/extranewgwt/pageshtml?page=img&imgid=" + imgObj.getImgID() + "&albid=" + albumObj.getAlbid() + "\">Show photo" + albumObj.getAlbname() + "</a></br>";
		toRet +="</div>";
		return toRet;
	}
	
	
}
