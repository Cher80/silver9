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

public class TagUnit extends Composite {



	private FlowPanel panel = new FlowPanel();
	private TagObj tagObj;
	private AlbumObj albumObj;
	private Button doTagMark;
	private Label marksCountLabel;
	private Label statusLabel = new Label("");
	
	public TagUnit(TagObj tagobjj, AlbumObj albumObjj) {
		super();
		this.tagObj = tagobjj;
		this.albumObj = albumObjj;
		
		doTagMark = new Button(tagObj.getTagReadableName());
		marksCountLabel = new Label("Counts" + tagObj.getTagTotalPluses());
		
		
		panel.add(doTagMark);
		panel.add(marksCountLabel);
		doTagMark.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				doSetTag(tagObj, albumObj, ClientFactory.getUser());
				
			}
		});
		
		//Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);







		initWidget(panel);
	}

	
	
	public void doSetTag(TagObj tagObj, AlbumObj albumObj, User user) {

		//Log.debug("ClientFactory.getUser().getUid())!!" + ClientFactory.getUser().getUid() );

		



		RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

		// Set up the callback object.
		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) {

				if (caught instanceof RPCServiceExeption) {
					Log.debug("exeption!!" + ((RPCServiceExeption)caught).getErrorCode());
				}
			}

			@Override
			public void onSuccess(Object result) {
				Log.debug("doSetTag onSuccess ");

				//NewCommentEvent event = new NewCommentEvent((CommentObj)result);
				//ClientFactory.getEventBus().fireEvent(event);
				//Log.debug("NewCommentEvent fired");
				//Notifications notif = new Notifications("You have posted comment", true, true);
				statusLabel.setText("Posted tag! Thanks!");
				

			}
		};



		communicatorSvc.doSetTag(tagObj, albumObj, user, callback); 
	}



}
