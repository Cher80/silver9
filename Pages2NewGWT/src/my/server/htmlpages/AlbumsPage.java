package my.server.htmlpages;

import my.server.exutor.Albums;
import my.shared.AlbumObj;
import my.shared.AlbumsObj;

public class AlbumsPage {

	public String  getAlbums(int pageNo, int limit) {
		
		String toRet = "";
		Albums albums = new Albums();
		AlbumsObj albumsObj = albums.getAlbumsByTime(pageNo* PagesHTML.ALBUMS_PER_PAGE, PagesHTML.ALBUMS_PER_PAGE, null, 1, null);
		
		
		toRet +="<h3><a href=\"http://pinbelle.com/\">Goto to main page</a></h3>";
		
		for (int i=0; i<albumsObj.getAlbums().size(); i++) {
			AlbumObj albumObj = albumsObj.getAlbums().get(i);
			AlbumUnit albumUnit = new AlbumUnit(albumObj);
			toRet = toRet + albumUnit.render();
			
		}
		
		
		
		PaginatorUnit albumsPaginator = new PaginatorUnit(albumsObj.getTotalCount());
		toRet = toRet + albumsPaginator.render();
		
		return toRet;
	}
	
}


