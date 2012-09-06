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

public class CommentPost extends Composite {



	private FlowPanel panel = new FlowPanel();
	private Button postComment = new Button("postComment");
	//private Button showOrigpxButt = new Button("showOrigpx");
	private CommentObj commentObj;
	private AlbumObj albumObj;
	private TextArea textArea = new TextArea();
	private Label statusLabel = new Label("Comment post status");
	private HTML headerHTML = new HTML("<h3>Post comment</h3>");

	public CommentPost(AlbumObj albumObjj) {
		super();
		this.albumObj = albumObjj;
		textArea.setCharacterWidth(80);
		textArea.setVisibleLines(3);


		//Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);




		panel.add(headerHTML);
		panel.add(textArea);
		panel.add(postComment);
		panel.add(statusLabel);
		//panel.add(showOrigpxButt);


		postComment.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {


				doPostComment();
			}
		});



		initWidget(panel);
	}





	public void doPostComment() {

		//Log.debug("ClientFactory.getUser().getUid())!!" + ClientFactory.getUser().getUid() );

		CommentObj commentObj = new CommentObj();

		//if (ClientFactory.getUser()!=null) { 
			commentObj.setCommentAuthorID(ClientFactory.getUser().getUid());
			commentObj.setCommentAuthorNick(ClientFactory.getUser().getNick());
		//}
		//else {
		//	commentObj.setCommentAuthorID("anonim");
		//	commentObj.setCommentAuthorNick("anonim");
		//}
		commentObj.setCommentText(textArea.getText());
		commentObj.setAlbumId(albumObj.getAlbid());
		commentObj.setAlbumModelName(albumObj.getAlbname());
		commentObj.setCommentTimeStamp(1);



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
				Log.debug("doPostComment onSuccess ");

				NewCommentEvent event = new NewCommentEvent((CommentObj)result);
				ClientFactory.getEventBus().fireEvent(event);
				Log.debug("NewCommentEvent fired");
				Notifications notif = new Notifications("You have posted comment", true, true);
				statusLabel.setText("Posted! Thanks!");
				textArea.setText("");

			}
		};



		communicatorSvc.doPostComment(commentObj, callback);
	}
}
