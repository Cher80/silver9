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
		
		
		
		panel.addStyleName("TagAlbumsBlock");
		
		
		FlowPanel likesBlock = new FlowPanel();
		likesBlock.addStyleName("likesBlock");
		likesBlock.addStyleName("border_right_dotted");
		panel.add(likesBlock);
		
		Label ratingLabel = new Label("Sort by rating");
		ratingLabel.addStyleName("ratingLabel");
		ratingLabel.addStyleName("text_12_white_bold");
		likesBlock.add(ratingLabel);

		
		
		TagAlbumsGroup likesGroup = new TagAlbumsGroup("LIKES", "Likes");
		TagAlbumsUnit likeUnit = new TagAlbumsUnit("LIKE", "Like");
		TagAlbumsUnit dislikeUnit = new TagAlbumsUnit("DISLIKE", "Dislike");
		likesGroup.addTag(likeUnit);
		likesGroup.addTag(dislikeUnit);
		likesBlock.add(likesGroup);
		
		Label clearAllLabel = new Label("x Clear all");
		clearAllLabel.addStyleName("clearAllLabel");
		clearAllLabel.addStyleName("link_11_grey");
		likesBlock.add(clearAllLabel);
		clearAllLabel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ReloadAlbumsEvent eventReload = new ReloadAlbumsEvent(null,1);
				ClientFactory.getEventBus().fireEvent(eventReload);
			}
		});
		
		
		///////TAGS Icon////////////////////
		
		FlowPanel tagsIconBlock = new FlowPanel();
		tagsIconBlock.addStyleName("tagIconBlock");
		//tagsIconBlock.addStyleName("border_right_dotted");
		panel.add(tagsIconBlock);
		
		Label tagsLabel = new Label("Filter by tags");
		tagsLabel.addStyleName("tagsLabel");
		tagsLabel.addStyleName("text_12_white_bold");
		tagsIconBlock.add(tagsLabel);
		
		
		FlowPanel tagsBlockIcon = new FlowPanel();
		tagsBlockIcon.addStyleName("tagsBlockIcon");
		tagsIconBlock.add(tagsBlockIcon);
		
		Label clearAllLabel2 = new Label("x Clear all");
		clearAllLabel2.addStyleName("clearAllLabel");
		clearAllLabel2.addStyleName("link_11_grey");
		tagsIconBlock.add(clearAllLabel2);
		clearAllLabel2.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				ReloadAlbumsEvent eventReload = new ReloadAlbumsEvent(null,1);
				ClientFactory.getEventBus().fireEvent(eventReload);
			}
		});
		/////////Tags///////////
		FlowPanel tagsBlock = new FlowPanel();
		tagsBlock.addStyleName("tagsBlock");
		panel.add(tagsBlock);
		
		TagAlbumsGroup hairsGroup = new TagAlbumsGroup("HAIRS", "Hairs");
		hairsGroup.addStyleName("border_right_dotted");
		TagAlbumsUnit blondUnit = new TagAlbumsUnit("BLOND","Blond");
		TagAlbumsUnit redUnit = new TagAlbumsUnit("RED", "Red");
		TagAlbumsUnit darkUnit = new TagAlbumsUnit("DARK", "Red");
		hairsGroup.addTag(blondUnit);
		hairsGroup.addTag(redUnit);
		hairsGroup.addTag(darkUnit);
		tagsBlock.add(hairsGroup);
			
		
		TagAlbumsGroup heightGroup = new TagAlbumsGroup("HEIGHT", "Height");
		heightGroup.addStyleName("border_right_dotted");
		TagAlbumsUnit cuteUnit = new TagAlbumsUnit("CUTE","Cute");
		TagAlbumsUnit normUnit = new TagAlbumsUnit("NORM", "Norm");
		TagAlbumsUnit tallUnit = new TagAlbumsUnit("TALL", "Tall");
		heightGroup.addTag(cuteUnit);
		heightGroup.addTag(normUnit);
		heightGroup.addTag(tallUnit);
		tagsBlock.add(heightGroup);


		


		initWidget(panel);
	}

	



}
