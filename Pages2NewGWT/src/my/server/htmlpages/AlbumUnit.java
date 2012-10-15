package my.server.htmlpages;

import my.shared.AlbumObj;

public class AlbumUnit {

	private AlbumObj albumObj; 
	
	public AlbumUnit(AlbumObj _albumObj) {
		this.albumObj = _albumObj;
		
	}
	
	public String render() {
		
		
		String toRet = "";
		toRet += "<div style=\"float:left;\">";
		toRet += "<a href=\"/extranewgwt/pageshtml?page=album&albid=" + albumObj.getAlbid() + "\">  <img src=\"/extranewgwt/getphoto?photoid=" + albumObj.getCoverPicID() + "\"/></a></br>";
		toRet +="<a href=\"/extranewgwt/pageshtml?page=album&albid=" + albumObj.getAlbid() + "\"/>" + albumObj.getAlbname() + "</a></br>";
		toRet += " age:" + albumObj.getAlbage() + "</br>";
		toRet += " city:" + albumObj.getAlbcity()  + "</br>";
		toRet +="</div>";
		return toRet;
	}
	
	
}
