package my.client.blocks;

import java.util.Date;

import my.client.albumspage.AlbumsActivity;
import my.client.common.ClientFactory;
import my.client.events.NewCommentEvent;
import my.client.events.ReloadAlbumsEvent;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.AdminsPopup;
import my.client.windows.LoginPopup;
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

public class AdminsStuffBlock extends Composite {

	private FlowPanel panel = new FlowPanel();
	private Button showAdminsWindowButt = new Button("showAdminsWindowButt");
//	private String tag;

	private AlbumsActivity albumsActivity;
	//private Button doTagMark;

	//private int 

	public AdminsStuffBlock() {

		super();
		
		if (ClientFactory.getCookieObj().getUserRole()==2) {
			panel.add(showAdminsWindowButt);
		}
	
		showAdminsWindowButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				AdminsPopup adminsPopup = new AdminsPopup();
				adminsPopup.center(); 
				adminsPopup.show();
				
				//Log.debug("doTagButt " + tag);
				//ReloadAlbumsEvent eventReload = new ReloadAlbumsEvent(tag,1);
				//ClientFactory.getEventBus().fireEvent(eventReload);
				
				//doSetTag(tagObj, albumObj, ClientFactory.getUser());
				
			}
		});



		initWidget(panel);
	}





}
