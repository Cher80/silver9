package my.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class ImgsObj implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList <ImgObj> images = new ArrayList<ImgObj>();
	
	public ArrayList <ImgObj> getImages() {
		return images;
	}
	public void setImages(ArrayList <ImgObj> images) {
		this.images = images;
	}


}
