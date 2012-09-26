package my.shared;

import java.io.Serializable;

public class AlbumObj implements Serializable {

	private static final long serialVersionUID = 1L;
	private String albname = "";
	private int albage = 0;
	private String albcity = "";
	private Long timestamp = new Long(0);
	private int status = 2;
	private String albpage = "";
	private int photocount = 0;
	private String coverImgObjID = null;
	private String coverPicID = null;
	private String albid = null;
	private int tagLIKE = 0;
	private int tagDISLIKE = 0;
	private TagsObj tagsObj = new TagsObj();
	
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

	public String getAlbid() {
		return albid;
	}
	public void setAlbid(String albid) {
		this.albid = CommonsShared.safeString(albid);
	}
	public String getCoverImgObjID() {
		return coverImgObjID;
	}
	public void setCoverImgObjID(String coverImgObjID) {
		this.coverImgObjID = CommonsShared.safeString(coverImgObjID);
	}
	public String getCoverPicID() {
		return coverPicID;
	}
	public void setCoverPicID(String coverPicID) {
		this.coverPicID = CommonsShared.safeString(coverPicID);
	}
	public int getTagLIKE() {
		return tagLIKE;
	}
	public void setTagLIKE(int tagLIKE) {
		this.tagLIKE = tagLIKE;
	}
	public int getTagDISLIKE() {
		return tagDISLIKE;
	}
	public void setTagDISLIKE(int tagDISLIKE) {
		this.tagDISLIKE = tagDISLIKE;
	}
	public TagsObj getTagsObj() {
		return tagsObj;
	}
	public void setTagsObj(TagsObj tagsObj) {
		this.tagsObj = tagsObj;
	}
	
	public int getCountForTag(String tag) {
		for (int i=0; i<tagsObj.getTagsObj().size(); i++) {
			if (tagsObj.getTagsObj().get(i).getTagType().equals(tag)) {
				return tagsObj.getTagsObj().get(i).getTagTotalPluses();
			}
		}
		return 0;
	}
	

}
