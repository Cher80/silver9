package my.client.blocks;

import java.util.ArrayList;
import java.util.Date;

import my.client.common.ClientFactory;
import my.client.events.NewCommentEvent;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.Notifications;
import my.client.windows.UserHasLoggedEvent;
import my.shared.AlbumObj;
import my.shared.CommentObj;
import my.shared.ImgObj;
import my.shared.ModelPageObj;
import my.shared.TagObj;
import my.shared.TagsObj;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class TagsSetBlock extends Composite {



	private FlowPanel panel = new FlowPanel();
	private TagsObj tagsObj;
	private ArrayList <String> types;
	private AlbumObj albumObj;

	public TagsSetBlock(TagsObj tagsObjj, ArrayList <String> typess, AlbumObj albumObjj) {
		super();
		this.tagsObj = tagsObjj;
		this.types = typess;
		this.albumObj = albumObjj;
		
		
		//createTagGroup("LIKES");
		//Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);
		
		//////LIKES
		
		
		
		/*
		TagsSetGroup likesGroup = new TagsSetGroup("LIKES", getFromTagsObj("LIKES"));
		panel.add(likesGroup);
		
		TagsSetGroup hairsGroup = new TagsSetGroup("HAIRS", getFromTagsObj("HAIRS"));
		panel.add(hairsGroup);
*/

		
		
		//TagsObj likesTagsobj = new TagsObj();
		//TagsSetGroup likesGroup = new TagsSetGroup("LIKES", getFromTagsObj("LIKES"));

		/////Likes 
		TagObj likeTag = getTagObj("LIKE","LIKES");
		TagObj dislikeTag = getTagObj("DISLIKE","LIKES");
		TagsObj likesTagsobj = new TagsObj();
		likesTagsobj.getTagsObj().add(likeTag);
		likesTagsobj.getTagsObj().add(dislikeTag);
		TagsSetGroup likesGroup = new TagsSetGroup("LIKES", likesTagsobj, albumObj);

		
		/////Hairs 
		TagObj blondTag = getTagObj("BLOND","HAIRS");
		TagObj darkTag = getTagObj("DARK","HAIRS");
		TagObj redTag = getTagObj("RED","HAIRS");
		TagsObj hairTagsobj = new TagsObj();
		hairTagsobj.getTagsObj().add(blondTag);
		hairTagsobj.getTagsObj().add(darkTag);
		hairTagsobj.getTagsObj().add(redTag);
		TagsSetGroup hairsGroup = new TagsSetGroup("HAIRS", hairTagsobj, albumObj);

		panel.add(hairsGroup);
		panel.add(likesGroup);
		initWidget(panel);
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
	
	
	private TagObj createNewTagObj(String type,String group) {
		TagObj tagObj = new TagObj();
		tagObj.setTagGroup(group);
		tagObj.setTagType(type);
		tagObj.setTagReadableName("Namegen" + type);
		if (isCreatedGroupInVotedGroup(group)) {
			tagObj.setAllowVoteToUser(false);
		}
		return tagObj;
	}
	
	private TagObj getTagObj (String type,String group) {
		
		TagObj likeTag = getFromTagFromResponse(type);
		
		if (likeTag == null) {
			likeTag = createNewTagObj(type, group);
		}
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
	
/*
	private TagsObj getFromTagsObj(String type) {
		TagsObj tagsGroup = new TagsObj();
		Log.debug("getFromTagsObj type" + type);
		for (int i=0; i<tagsObj.getTagsObj().size(); i++) {
			TagObj curTagObj = tagsObj.getTagsObj().get(i);
			if (curTagObj.getTagGroup().equals(type)) {
				Log.debug("getFromTagsObj getTagGroup" + curTagObj.getTagGroup());
				tagsGroup.getTagsObj().add(curTagObj);
			}
		} 
		return tagsGroup;
	}
	*/



}
