package my.shared;

import java.io.Serializable;
import java.util.ArrayList;

public class TagsObj implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ArrayList <TagObj> tagsObj = new ArrayList<TagObj>();

	public ArrayList <TagObj> getTagsObj() {
		return tagsObj;
	}

	public void setTagsObj(ArrayList <TagObj> tagsObj) {
		this.tagsObj = tagsObj;
	};
}
