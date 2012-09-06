package my.client.blocks;

import java.util.Date;

import my.client.common.ClientFactory;
import my.client.events.NewCommentEvent;
import my.client.events.NewCommentEventHandler;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.UserHasLoggedEvent;
import my.shared.AlbumObj;
import my.shared.CommentObj;
import my.shared.CommentsObj;
import my.shared.ImgObj;
import my.shared.ModelPageObj;

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
import com.google.gwt.user.client.ui.TextArea;
import com.google.gwt.user.client.ui.TextBox;

public class CommentsBlock extends Composite implements NewCommentEventHandler {



	private FlowPanel panel = new FlowPanel();

	//private Button showOrigpxButt = new Button("showOrigpx");
	private CommentsObj commentsObj;
	//private AlbumObj albumObj;
	private HTML headerHTML = new HTML("<h3>Current comments</h3>");


	public CommentsBlock(CommentsObj commentsObj) {
		super();
		this.commentsObj = commentsObj;
		ClientFactory.getEventBus().addHandler(NewCommentEvent.TYPE, this);

		panel.add(headerHTML);
		
		for (int i=0; i<commentsObj.getComments().size(); i++) {
			CommentBlock commentBlock = new CommentBlock(commentsObj.getComments().get(i));
			panel.add(commentBlock);
		}
		
		//doGetComments(albumObj);
		//Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);




		//		 panel.add(textArea);
		//	 panel.add(postComment);
		//panel.add(showOrigpxButt);





		initWidget(panel);
	}




	public void addComment(CommentObj commentObj) {
		CommentBlock commentBlock = new CommentBlock(commentObj);
		//panel.add(commentBlock);
		panel.insert(commentBlock, 1);
	}
	
	

	public void doGetComments(AlbumObj albumObj) {

		
		Log.debug("doGetComments");





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
				Log.debug("doGetComments onSuccess ");

			}
		};



		communicatorSvc.doGetComments(albumObj.getAlbid(), callback);
	}




	@Override
	public void onNewComment(NewCommentEvent event) {
		// TODO Auto-generated method stub
		addComment(event.getCommentObj());
	}
}
