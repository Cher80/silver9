package my.shared;

import java.io.Serializable;

public class AlbumObj implements Serializable {

	private static final long serialVersionUID = 1L;
	private String albname;
	private int albage;
	private String albcity;
	private Long timestamp;
	private int status;
	private String albpage;
	private int photocount;
	private String coverphoto;
	private String albid;
	
	
	public String getAlbname() {
		return albname;
	}
	public void setAlbname(String albname) {
		this.albname = albname;
	}
	public int getAlbage() {
		return albage;
	}
	public void setAlbage(int albage) {
		this.albage = albage;
	}
	public String getAlbcity() {
		return albcity;
	}
	public void setAlbcity(String albcity) {
		this.albcity = albcity;
	}
	public Long getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getAlbpage() {
		return albpage;
	}
	public void setAlbpage(String albpage) {
		this.albpage = albpage;
	}
	public int getPhotocount() {
		return photocount;
	}
	public void setPhotocount(int photocount) {
		this.photocount = photocount;
	}
	public String getCoverphoto() {
		return coverphoto;
	}
	public void setCoverphoto(String coverphoto) {
		this.coverphoto = coverphoto;
	}
	public String getAlbid() {
		return albid;
	}
	public void setAlbid(String albid) {
		this.albid = albid;
	}
	

}
