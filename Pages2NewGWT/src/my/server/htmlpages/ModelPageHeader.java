package my.server.htmlpages;

import my.shared.AlbumObj;

public class ModelPageHeader {

	private AlbumObj albumObj; 
	
	public ModelPageHeader(AlbumObj _albumObj) {
		this.albumObj = _albumObj;	
	}
	
	public String render() {		
		String toRet = "";
		toRet += "<h1>Name: " + albumObj.getAlbname() +   "</h1> ";
		toRet += "<h3>age: " + albumObj.getAlbage() +  " city:"+ albumObj.getAlbcity() + "</h1> ";
		return toRet;
	}
	
	
}
