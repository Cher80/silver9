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
		panel.addStyleName("TagsSetBlock");
		
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
		TagObj likeTag = (new TagProviderHelper(tagsObj)).getTagObj("LIKE", "Like" ,"LIKES");
		TagObj dislikeTag = (new TagProviderHelper(tagsObj)).getTagObj("DISLIKE", "Dislike","LIKES");
		TagsObj likesTagsobj = new TagsObj();
		likesTagsobj.getTagsObj().add(likeTag);
		likesTagsobj.getTagsObj().add(dislikeTag);
		TagsSetGroup likesGroup = new TagsSetGroup("Rate her", likesTagsobj, albumObj);
		panel.add(likesGroup);
		
		/////Hairs 
		TagObj blondTag = (new TagProviderHelper(tagsObj)).getTagObj("BLOND", "Blond","HAIRS");
		TagObj darkTag = (new TagProviderHelper(tagsObj)).getTagObj("DARK", "Dark","HAIRS");
		TagObj redTag = (new TagProviderHelper(tagsObj)).getTagObj("RED", "Red", "HAIRS");
		TagsObj hairTagsobj = new TagsObj();
		hairTagsobj.getTagsObj().add(blondTag);
		hairTagsobj.getTagsObj().add(darkTag);
		hairTagsobj.getTagsObj().add(redTag);
		TagsSetGroup hairsGroup = new TagsSetGroup("Hairs?", hairTagsobj, albumObj);
		panel.add(hairsGroup);
		
		
		///////Height
		TagObj cuteTag = (new TagProviderHelper(tagsObj)).getTagObj("CUTE", "Petit","HEIGHT");
		TagObj normalTag = (new TagProviderHelper(tagsObj)).getTagObj("NORM", "Normal","HEIGHT");
		TagObj tallTag = (new TagProviderHelper(tagsObj)).getTagObj("TALL", "Tall", "HEIGHT");
		TagsObj heightTagsobj = new TagsObj();
		heightTagsobj.getTagsObj().add(cuteTag);
		heightTagsobj.getTagsObj().add(normalTag);
		heightTagsobj.getTagsObj().add(tallTag);
		TagsSetGroup heightGroup = new TagsSetGroup("Height?", heightTagsobj, albumObj);
		panel.add(heightGroup);

		
		///////Bra size
		TagObj braLargeTag = (new TagProviderHelper(tagsObj)).getTagObj("BRA_LARGE", "Large","BRA");
		TagObj braMediumTag = (new TagProviderHelper(tagsObj)).getTagObj("BRA_MEDIUM", "Medium","BRA");
		TagObj braSmallTag = (new TagProviderHelper(tagsObj)).getTagObj("BRA_SMALL", "Small", "BRA");
		TagsObj braTagsobj = new TagsObj();
		braTagsobj.getTagsObj().add(braLargeTag);
		braTagsobj.getTagsObj().add(braMediumTag);
		braTagsobj.getTagsObj().add(braSmallTag);
		TagsSetGroup braGroup = new TagsSetGroup("Bra size?", braTagsobj, albumObj);
		panel.add(braGroup);
		
		
		initWidget(panel);
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
