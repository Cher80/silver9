package my.client.blocks;

import java.util.Date;

import my.client.albumspage.AlbumsActivity;
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

public class TagAlbumsBlock extends Composite {

	private FlowPanel panel = new FlowPanel();

private AlbumsActivity albumsActivity;
	//private Button doTagMark;

	//private int 
	
	public TagAlbumsBlock() {
		
		super();
		
		TagAlbumsGroup likesGroup = new TagAlbumsGroup("LIKES");
		TagAlbumsUnit likeUnit = new TagAlbumsUnit("LIKE");
		TagAlbumsUnit dislikeUnit = new TagAlbumsUnit("DISLIKE");
		likesGroup.addTag(likeUnit);
		likesGroup.addTag(dislikeUnit);
		panel.add(likesGroup);
		
		
		TagAlbumsGroup hairsGroup = new TagAlbumsGroup("HAIRS");
		TagAlbumsUnit blondUnit = new TagAlbumsUnit("BLOND");
		TagAlbumsUnit redUnit = new TagAlbumsUnit("RED");
		TagAlbumsUnit darkUnit = new TagAlbumsUnit("DARK");
		hairsGroup.addTag(blondUnit);
		hairsGroup.addTag(redUnit);
		hairsGroup.addTag(darkUnit);
		panel.add(hairsGroup);
			





		initWidget(panel);
	}

	



}
