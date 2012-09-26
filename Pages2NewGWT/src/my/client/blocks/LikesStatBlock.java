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
import my.shared.StatObj;
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

public class LikesStatBlock extends Composite {

	
	private FlowPanel panel = new FlowPanel();


	private AlbumObj albumObj;
	//private Button doTagMark;

	//private int 

	private Label belleRatingLabel = new Label();
	private Label belleLikesDescLabel = new Label();
	private Label belleDislikesDescLabel = new Label();
	private FlowPanel belleLikesIcon = new FlowPanel();
	private FlowPanel belleDislikesIcon = new FlowPanel();
	private FlowPanel belleLikesUnder = new FlowPanel();
	private FlowPanel belleDislikesUnder = new FlowPanel();
	private Label belleLikesLabel = new Label();
	private Label belleDislikesLabel = new Label();
	private boolean isSimple;

	public LikesStatBlock(AlbumObj albumObjj, boolean isSimplee) {

		super();
		this.isSimple = isSimplee;
		this.albumObj = albumObjj;
		panel.addStyleName("LikesStatBlock");
		

		///////Model likes and dislikes
		belleRatingLabel.addStyleName("belleRatingLabel");
		belleRatingLabel.addStyleName("text_11_grey_bold");
		
		belleLikesDescLabel.addStyleName("belleLikesDescLabel");
		belleLikesDescLabel.addStyleName("text_11_grey");
		belleDislikesDescLabel.addStyleName("belleDislikesDescLabel");
		belleDislikesDescLabel.addStyleName("text_11_grey");
		
		belleRatingLabel.setText("Beauty rating");
		belleLikesDescLabel.setText("likes");
		belleDislikesDescLabel.setText("dislikes");
		
		belleLikesUnder.addStyleName("belleLikesUnder");
		belleLikesUnder.addStyleName("like_Color");
		belleDislikesUnder.addStyleName("belleDislikesUnder");
		belleDislikesUnder.addStyleName("dislike_Color");
		
		belleLikesIcon.addStyleName("belleLikesIcon");
		belleDislikesIcon.addStyleName("belleDislikesIcon");
		
		belleLikesLabel.addStyleName("belleLikesLabel");
		belleLikesLabel.addStyleName("text_11_white_bold");
		belleLikesLabel.setText(albumObj.getCountForTag("LIKE") + "");
		
		
		belleDislikesLabel.addStyleName("belleDislikesLabel");
		belleDislikesLabel.addStyleName("text_11_white_bold");
		belleDislikesLabel.setText(albumObj.getCountForTag("DISLIKE") + "");
		
		if (!isSimple) {
		panel.add(belleRatingLabel);
		panel.add(belleLikesDescLabel);
		panel.add(belleDislikesDescLabel);
		}
		panel.add(belleLikesUnder);
		panel.add(belleDislikesUnder);
		
		panel.add(belleLikesIcon);
		panel.add(belleDislikesIcon);
		
		panel.add(belleLikesLabel);
		panel.add(belleDislikesLabel);
		
		initWidget(panel);
	}





}
