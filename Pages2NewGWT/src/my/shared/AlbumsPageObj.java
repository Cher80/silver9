package my.shared;

import java.io.Serializable;

public class AlbumsPageObj implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 
	 */

	private AlbumsObj bestAlbumsObj;
	private ActivitiesObj activitiesObj;
	private AlbumsObj albumsObj;
	
	public AlbumsObj getAlbumsObj() {
		return albumsObj;
	}
	public void setAlbumsObj(AlbumsObj albumsObj) {
		this.albumsObj = albumsObj;
	}
	public ActivitiesObj getActivitiesObj() {
		return activitiesObj;
	}
	public void setActivitiesObj(ActivitiesObj activitiesObj) {
		this.activitiesObj = activitiesObj;
	}
	public AlbumsObj getBestAlbumsObj() {
		return bestAlbumsObj;
	}
	public void setBestAlbumsObj(AlbumsObj bestAlbumsObj) {
		this.bestAlbumsObj = bestAlbumsObj;
	}
	
	
}
