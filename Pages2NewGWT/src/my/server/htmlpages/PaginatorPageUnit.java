package my.server.htmlpages;

import my.shared.AlbumObj;

public class PaginatorPageUnit {

	private int page; 
	
	public PaginatorPageUnit(int _page) {
		this.page = _page;	
	}
	
	public String render() {		
		String toRet = "";
		toRet += "<a href=\"/extranewgwt/pageshtml?page=albums&pageno=" + page + "\">" + page + " </a> ";
		return toRet;
	}
	
	
}
