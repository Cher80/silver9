package my.shared;

import java.io.Serializable;

public class StatObj implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int totalAlbums = 0;
	private int totalImgs = 0;
	private int lastAlbums = 0;
	private int lastImgs = 0;
	public int getTotalAlbums() {
		return totalAlbums;
	}
	public void setTotalAlbums(int totalAlbums) {
		this.totalAlbums = totalAlbums;
	}
	public int getTotalImgs() {
		return totalImgs;
	}
	public void setTotalImgs(int totalImgs) {
		this.totalImgs = totalImgs;
	}
	public int getLastAlbums() {
		return lastAlbums;
	}
	public void setLastAlbums(int lastAlbums) {
		this.lastAlbums = lastAlbums;
	}
	public int getLastImgs() {
		return lastImgs;
	}
	public void setLastImgs(int lastImgs) {
		this.lastImgs = lastImgs;
	}
	
}
