package my.server.htmlpages;

import my.shared.AlbumObj;

public class PaginatorUnit {

	private int totalCount; 
	
	public PaginatorUnit(int _totalCount) {
		this.totalCount = _totalCount;	
	}
	
	public String render() {
		
		
		String toRet = "";
		toRet += "<div style=\"clear:both;\">";
		toRet += "pages: ";
		
		int pagesCount  = totalCount/PagesHTML.ALBUMS_PER_PAGE + 1;
		for (int i=0; i<pagesCount; i++) {
			PaginatorPageUnit paginatorPageUnit = new PaginatorPageUnit(i);
			toRet += paginatorPageUnit.render();
		}
		//toRet += "paginator" + totalCount;
		/*
		toRet += "<a href=\"/extranewgwt/pageshtml?page=album&id=" + albumObj.getAlbid() + "\">  <img src=\"/extranewgwt/getphoto?photoid=" + albumObj.getCoverPicID() + "\"/></a></br>";
		toRet +="<a href=\"/extranewgwt/pageshtml?page=album&id=" + albumObj.getAlbid() + "\"/>" + albumObj.getAlbname() + "</a></br>";
		toRet += " age:" + albumObj.getAlbage() + "</br>";
		toRet += " city:" + albumObj.getAlbcity()  + "</br>";
		*/
		toRet +="</div>";
		return toRet;
	}
	
	
}
