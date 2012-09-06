package my.client.blocks;

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

public class TagsSetGroup extends Composite {



	private FlowPanel panel = new FlowPanel();
	private TagsObj tagsObjGroup;
	private String type;
	private AlbumObj albumObj;

	public TagsSetGroup(String typee, TagsObj tagsObjj, AlbumObj albumObjj) {
		super();
		this.tagsObjGroup = tagsObjj;
		this.type = typee;
		this.albumObj = albumObjj;
		Log.debug("TagsSetGroup this.type " + this.type );

		for (int i=0; i<tagsObjGroup.getTagsObj().size(); i++) {
			TagObj curTagObj = tagsObjGroup.getTagsObj().get(i);
			Log.debug("TagsSetGroup " + curTagObj.getTagGroup() + curTagObj.getTagReadableName() );
			TagUnit tagUnit = new TagUnit(curTagObj,albumObj);
			panel.add(tagUnit);
		} 
		
		//Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);







		initWidget(panel);
	}




}
