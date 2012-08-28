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

	public ImgObj getImgByID (String imdID) {
		for(int i=0;i<images.size();i++) {
			ImgObj curImgObj = images.get(i);
			if (curImgObj.getImgID().equals(imdID.trim())) {
				return curImgObj;
			}
		}
		return null;
		
	}
	
	public int getImgPos(String imdID) {
		int curPos = -1;
		for(int i=0;i<images.size();i++) {
			ImgObj curImgObj = images.get(i);
			if (curImgObj.getImgID().equals(imdID.trim())) {
				//return curImgObj;
				curPos = i;
				return curPos;
			}
		}
		return curPos;
	}
	
	
	public ImgObj getNextImg (String imdID) {
		int nextPos = -1;
		int curPos = getImgPos(imdID);
		if (curPos>=0) {
			if (curPos==(images.size()-1)) {
				nextPos = 0;
				//return nextPos;
			}
			if (curPos<(images.size()-1)) {
				nextPos = curPos +1;
			}
		}
		
		if (nextPos>=0) {
			return images.get(nextPos);
		}
		else {
			return null;
		}
		//return null;
	}
	
	public ImgObj getPrevImg (String imdID) {
		int nextPos = -1;
		int curPos = getImgPos(imdID);
		if (curPos>=0) {
			if (curPos==0) {
				nextPos = images.size()-1;
				//return nextPos;
			}
			if (curPos>0 && curPos<=(images.size()-1)) {
				nextPos = curPos  - 1;
			}
		}
		
		if (nextPos>=0) {
			return images.get(nextPos);
		}
		else {
			return null;
		}
		//return null;
	}
		
		
		
		
		
	
		
	

}
