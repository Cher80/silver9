package my.client.blocks;

import java.util.Date;

import my.client.common.ClientFactory;
import my.client.modelpage.ModelActivity;
import my.client.modelpage.ModelPlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
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

public class CommentBlock extends Composite {



	private FlowPanel panel = new FlowPanel();

	//private Button showOrigpxButt = new Button("showOrigpx");
	private CommentObj commentObj;
	//private AlbumObj albumObj;


	public CommentBlock(CommentObj commentObj) {
		super();
		this.commentObj = commentObj;
		
		String commStr = "<b>CommentID</b> = " + commentObj.getCommentID() +
				"; <b>CommentText</b> = " + commentObj.getCommentText() + 
				"; <b>CommentNick</b> = " + commentObj.getCommentAuthorNick() +
				"; <br/><br/>"
				;
		HTML toRender = new HTML(commStr);

		
		//doGetComments(albumObj);
		//Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);




			 panel.add(toRender);
		//	 panel.add(postComment);
		//panel.add(showOrigpxButt);





		initWidget(panel);
	}
	
	
	




}
