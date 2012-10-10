package my.client.blocks;

import my.shared.TagObj;
import my.shared.TagsObj;

public class TagProviderHelper {

	private TagsObj tagsObj;
	
	public TagProviderHelper (TagsObj _tagsObj) {
		tagsObj = _tagsObj;
	}
	
	private boolean isCreatedGroupInVotedGroup(String group) {
		for(int i=0;i<tagsObj.getTagsObj().size(); i++) {
			TagObj curTagObj = tagsObj.getTagsObj().get(i);
			if (curTagObj.getTagGroup().equals(group) && curTagObj.isAllowVoteToUser() == false) {
				return true; 
			}
		}
		return false;
	}
	
	
	private TagObj createNewTagObj(String type, String readableName, String group) {
		TagObj tagObj = new TagObj();
		tagObj.setTagGroup(group);
		tagObj.setTagType(type);
		//tagObj.setTagReadableName("Namegen" + type);
		tagObj.setTagReadableName(readableName);
		if (isCreatedGroupInVotedGroup(group)) {
			tagObj.setAllowVoteToUser(false);
		}
		return tagObj;
	}
	
	public TagObj getTagObj (String type, String readableName, String group) {
		
		TagObj likeTag = getFromTagFromResponse(type);
		
		if (likeTag == null) {
			likeTag = createNewTagObj(type, readableName, group);
		}
		likeTag.setTagReadableName(readableName);
		return likeTag;
	}
	
	private TagObj getFromTagFromResponse(String type) {
		
		//Log.debug("getFromTagFromResponse type" + type);
		for (int i=0; i<tagsObj.getTagsObj().size(); i++) {
			TagObj curTagObj = tagsObj.getTagsObj().get(i);
			//Log.debug("getFromTagFromResponse curTagObj.getTagType() " +  curTagObj.getTagType());

			if (curTagObj.getTagType().equals(type)) {
				//Log.debug("getFromTagFromResponse name " + curTagObj.getTagReadableName());
			
				return curTagObj;
			}
		} 
		return null;
	}
}
