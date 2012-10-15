package my.server.htmlpages;

import my.shared.AlbumObj;
import my.shared.ImgObj;

public class ImgPagePic {

	private ImgObj imgObj; 
	private AlbumObj albumObj;
	
	public ImgPagePic(ImgObj _imgObj, AlbumObj _albumObj) {
		this.imgObj = _imgObj;
		this.albumObj = _albumObj;
		
	}
	
	public String render() {
		
		
		String toRet = "";
		toRet += "<div>";
		toRet += "<img src=\"/extranewgwt/getphoto?photoid=" + imgObj.getImgGridfs_id_m() + "\"/></br>";
		toRet +="</div>";
		return toRet;
	}
	
	
}
