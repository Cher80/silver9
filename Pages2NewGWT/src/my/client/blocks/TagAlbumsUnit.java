package my.client.blocks;

import java.util.Date;

import my.client.albumspage.AlbumsActivity;
import my.client.common.ClientFactory;
import my.client.common.IconButt;
import my.client.events.NewCommentEvent;
import my.client.events.ReloadAlbumsEvent;
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

public class TagAlbumsUnit extends Composite {

	private FlowPanel panel = new FlowPanel();
	private IconButt doTagButt;
	private String tagType;
	private String tagReadableName;

	private AlbumsActivity albumsActivity;
	//private Button doTagMark;

	//private int 

	public TagAlbumsUnit(String tagTypee, String tagReadableNamee) {

		super();
		
		this.tagType = tagTypee;
		this.tagReadableName = tagReadableNamee;
		panel.addStyleName("TagAlbumsUnit");
		panel.addStyleName(tagType);
		
		//doTagButt = new Button(tagReadableName);
		doTagButt = new IconButt(); 
		doTagButt.addStyleName("ButtTag");
		doTagButt.icon.addStyleName("ButtTagIcon");
		doTagButt.icon.addStyleName("ButtTagIcon_" + tagType);
		doTagButt.content.addStyleName("ButtTagContent");
		doTagButt.content.addStyleName("ButtTagContent_" + tagType);
		doTagButt.text.addStyleName("ButtTagText");
		doTagButt.text.addStyleName("ButtTagText_" + tagType);
		if (this.tagType.equals("LIKE")||this.tagType.equals("DISLIKE")) {
			doTagButt.text.addStyleName("text11_White");
		} else {
			doTagButt.text.addStyleName("text11_White");
		}
		
		if (this.tagType.equals("LIKE")) {
			doTagButt.content.addStyleName("like_Color");
		}
		else if (this.tagType.equals("DISLIKE")) {
			doTagButt.content.addStyleName("dislike_Color");
		}
		else {
			doTagButt.content.addStyleName("grey_tag_Color");
		}
		
	
		/*doTagButt.text.addStyleName("text11_White");*/
		doTagButt.setText(tagReadableName);
		
		
		panel.add(doTagButt);
	
		doTagButt.panel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				Log.debug("doTagButt " + tagType);
				ReloadAlbumsEvent eventReload = new ReloadAlbumsEvent(tagType,1);
				ClientFactory.getEventBus().fireEvent(eventReload);
				
				//doSetTag(tagObj, albumObj, ClientFactory.getUser());
				
			}
		});



		initWidget(panel);
	}





}
