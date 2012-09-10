package my.shared;

import java.io.Serializable;

public class ModelPageObj implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private CommentsObj comments;
	private ImgsObj images;
	private TagsObj tagsObj;
	private String curimgID;
	private AlbumObj albumObj;
	private String curImg;
	
	public CommentsObj getComments() {
		return comments;
	}
	public void setComments(CommentsObj comments) {
		this.comments = comments;
	}
	public ImgsObj getImages() {
		return images;
	}
	public void setImages(ImgsObj images) {
		this.images = images;
	}
	public String getCurimgID() {
		return curimgID;
	}
	public void setCurimgID(String curimgID) {
		this.curimgID = curimgID;
	}
	public AlbumObj getAlbumObj() {
		return albumObj;
	}
	public void setAlbumObj(AlbumObj albumObj) {
		this.albumObj = albumObj;
	}
	public String getCurImg() {
		return curImg;
	}
	public void setCurImg(String curImg) {
		this.curImg = curImg;
	}
	public TagsObj getTagsObj() {
		return tagsObj;
	}
	public void setTagsObj(TagsObj tagsObj) {
		this.tagsObj = tagsObj;
	}
}
