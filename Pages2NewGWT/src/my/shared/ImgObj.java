package my.shared;

import java.io.Serializable;

public class ImgObj implements Serializable {
	
	/**
	 * 
	 */
	
	private String imgPhotourl;
	private String imgPageurl;
	private String imgGridfs_id_m;
	private String imgGridfs_id_0;
	private String imgGridfs_id_1;
	private String imgGridfs_id_2;
	private boolean imgIshasorig;
	private String imgMd5;		
	private String imgAlbum;
	private int imgStatus;
	private String imgEmail;
	
	private static final long serialVersionUID = 1L;
	private String imgID;
	private long imgTimestamp;
	
	public String getImgID() {
		return imgID;
	}
	
	public void setImgID(String imgID) {
		this.imgID = imgID;
	}
	
	public long getImgTimestamp() {
		return imgTimestamp;
	}
	
	public void setImgTimestamp(long imgTimestamp) {
		this.imgTimestamp = imgTimestamp;
	}

	public String getImgPhotourl() {
		return imgPhotourl;
	}

	public void setImgPhotourl(String imgPhotourl) {
		this.imgPhotourl = imgPhotourl;
	}

	public String getImgPageurl() {
		return imgPageurl;
	}

	public void setImgPageurl(String imgPageurl) {
		this.imgPageurl = imgPageurl;
	}

	public String getImgGridfs_id_m() {
		return imgGridfs_id_m;
	}

	public void setImgGridfs_id_m(String imgGridfs_id_m) {
		this.imgGridfs_id_m = imgGridfs_id_m;
	}

	public String getImgGridfs_id_0() {
		return imgGridfs_id_0;
	}

	public void setImgGridfs_id_0(String imgGridfs_id_0) {
		this.imgGridfs_id_0 = imgGridfs_id_0;
	}

	public String getImgGridfs_id_1() {
		return imgGridfs_id_1;
	}

	public void setImgGridfs_id_1(String imgGridfs_id_1) {
		this.imgGridfs_id_1 = imgGridfs_id_1;
	}

	public String getImgGridfs_id_2() {
		return imgGridfs_id_2;
	}

	public void setImgGridfs_id_2(String imgGridfs_id_2) {
		this.imgGridfs_id_2 = imgGridfs_id_2;
	}

	public Boolean getImgIshasorig() {
		return imgIshasorig;
	}

	public void setImgIshasorig(Boolean imgIshasorig) {
		this.imgIshasorig = imgIshasorig;
	}

	public String getImgMd5() {
		return imgMd5;
	}

	public void setImgMd5(String imgMd5) {
		this.imgMd5 = imgMd5;
	}

	public String getImgAlbum() {
		return imgAlbum;
	}

	public void setImgAlbum(String imgAlbum) {
		this.imgAlbum = imgAlbum;
	}

	public int getImgStatus() {
		return imgStatus;
	}

	public void setImgStatus(int imgStatus) {
		this.imgStatus = imgStatus;
	}

	public String getImgEmail() {
		return imgEmail;
	}

	public void setImgEmail(String imgEmail) {
		this.imgEmail = imgEmail;
	}
}
