package my.server.htmlpages;

import my.shared.AlbumObj;

public class ImgPageHeader {

	private AlbumObj albumObj; 
	
	public ImgPageHeader(AlbumObj _albumObj) {
		this.albumObj = _albumObj;	
	}
	
	public String render() {		
		String toRet = "";
		toRet += "<h1>Photo of: " + albumObj.getAlbname() +   "</h1> ";
		toRet += "<h3>age: " + albumObj.getAlbage() +  " city:"+ albumObj.getAlbcity() + "</h1> ";
		return toRet;
	}
	
	
}
