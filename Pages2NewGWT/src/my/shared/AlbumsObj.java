package my.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class AlbumsObj implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private ArrayList <AlbumObj> albums = new ArrayList<AlbumObj>();
	private int totalCount = 0; 
	
	public ArrayList <AlbumObj> getAlbums() {
		return albums;
	}
	public void setAlbums(ArrayList <AlbumObj> albums) {
		this.albums = albums;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	};


}
