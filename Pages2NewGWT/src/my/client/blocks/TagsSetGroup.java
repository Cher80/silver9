package my.client.blocks;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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


	private Label tagGroupName = new Label();
	private FlowPanel panel = new FlowPanel();
	private TagsObj tagsObjGroup;
	private String groupReadableName;
	private AlbumObj albumObj;
	private List<TagUnit>tagsInGroup = new ArrayList<TagUnit>();

	public TagsSetGroup(String _groupReadableName, TagsObj tagsObjj, AlbumObj albumObjj) {
		super();
		this.tagsObjGroup = tagsObjj;
		this.groupReadableName = _groupReadableName;
		this.albumObj = albumObjj;
		
		
		if (tagsObjGroup.getTagsObj().get(0).getTagGroup().equals("LIKES")) {
			tagGroupName.addStyleName("tagGroupName_LIKES");
		}
		else {
			tagGroupName.addStyleName("tagGroupNameSimple");
		}
		tagGroupName.addStyleName("text12_white_bold");
		
		tagGroupName.setText(groupReadableName);
		panel.add(tagGroupName);
		panel.addStyleName("TagsSetGroup");
		panel.addStyleName("border_right_dotted");
		
		//Log.debug("TagsSetGroup this.type " + this.type );

		for (int i=0; i<tagsObjGroup.getTagsObj().size(); i++) {
			TagObj curTagObj = tagsObjGroup.getTagsObj().get(i);
			Log.debug("TagsSetGroup " + curTagObj.getTagGroup() + curTagObj.getTagReadableName() );
			TagUnit tagUnit = new TagUnit(curTagObj,albumObj, this);
			tagsInGroup.add(tagUnit);
			panel.add(tagUnit);
		} 
		
		//Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);







		initWidget(panel);
	}
	
	
	public void setGroupVoted() {
		for (int i=0;i<tagsInGroup.size();i++) {
			tagsInGroup.get(i).setVoted();
		}
	}




}
