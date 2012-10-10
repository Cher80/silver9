package my.client.blocks;

import java.util.Date;

import my.client.common.ClientFactory;
import my.client.common.GoogleAnalytics;
import my.client.common.IconButt;
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
	private FlowPanel tagWinnerMark = new FlowPanel();
	private TagObj tagObj;
	private AlbumObj albumObj;
	//private Button doTagMark;
	private Label marksCountLabel;
	private Label isAlredyVotedLabel;
	private Label statusLabel = new Label("");
	private TagsSetGroup tagsSetGroup;
	
	private IconButt doTagButt; 
	private FlowPanel clearPanel = new FlowPanel(); 
	//private int 
	
	public TagUnit(TagObj tagobjj, AlbumObj albumObjj, TagsSetGroup tagsSetGroupp) {
		super();
		this.tagObj = tagobjj;
		this.albumObj = albumObjj;
		this.tagsSetGroup = tagsSetGroupp;
		panel.addStyleName("TagUnit");
		//doTagMark = new Button(tagObj.getTagReadableName());
		
		
		marksCountLabel = new Label("" + tagObj.getTagTotalPluses());
		marksCountLabel.addStyleName("marksCountLabel");
		marksCountLabel.addStyleName("text_10_grey");
		//isAlredyVotedLabel = new Label("isAllowVoteToUser " + tagObj.isAllowVoteToUser());
		
		
		doTagButt = new IconButt(); 
		doTagButt.addStyleName("ButtTag");
		
		doTagButt.content.addStyleName("ButtTagContent");
		doTagButt.content.addStyleName("ButtTagContent_" + tagObj.getTagType());
		doTagButt.text.addStyleName("ButtTagText");
		doTagButt.text.addStyleName("ButtTagText_" + tagObj.getTagType());
		
		if (tagObj.getTagType().equals("LIKE")||tagObj.getTagType().equals("DISLIKE")) {
			doTagButt.text.addStyleName("text11_White");
			doTagButt.icon.addStyleName("ButtTagIcon");
			doTagButt.icon.addStyleName("ButtTagIcon_" + tagObj.getTagType());
		} else {
			doTagButt.text.addStyleName("text11_White");
			if (tagObj.isTagIsWinner()) {
				tagWinnerMark.addStyleName("ButtSetTagIconWinner");
			}else {
				tagWinnerMark.addStyleName("ButtSetTagIconLooser");
			}
			doTagButt.icon.addStyleName("hideTagIcon");
		}
		
		
		
		if (tagObj.getTagType().equals("LIKE")) {
			doTagButt.content.addStyleName("like_Color");
		}
		else if (tagObj.getTagType().equals("DISLIKE")) {
			doTagButt.content.addStyleName("dislike_Color");
		}
		else {
			doTagButt.content.addStyleName("grey_tag_Color");
		}
		
	
		/*doTagButt.text.addStyleName("text11_White");*/
		doTagButt.setText(tagObj.getTagReadableName());
		
		
		
		
		if (!tagObj.isAllowVoteToUser()) {
			doTagButt.setEnabled(false);
		}
		
		
		panel.add(tagWinnerMark);
		panel.add(doTagButt);
		
		
		panel.add(marksCountLabel);
		//panel.add(isAlredyVotedLabel);
		doTagButt.panel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {

				if (tagObj.isAllowVoteToUser()) {
					doSetTag(tagObj, albumObj, ClientFactory.getUser());
					GoogleAnalytics.trackEvent("Pinbelle", "TagSet_Clicked", TagUnit.this.tagObj.getTagType());
					Notifications notif = new Notifications("Thanks for your vote", true, true);
				}
				else {
					Notifications notif = new Notifications("You have already voted", true, true);
				}
				
			}
		});
		
		//Date date = new java.util.Date((long)(imgObj.getImgTimestamp())*1000);







		initWidget(panel);
	}

	
	public void setVoted() {
		doTagButt.setEnabled(false);
		tagObj.setAllowVoteToUser(false);
	}
	
	public void setPlusVote() {
		//doTagMark.setEnabled(false);
		tagObj.setTagTotalPluses(tagObj.getTagTotalPluses() + 1 );
		int tagTotalPluses = tagObj.getTagTotalPluses();
		marksCountLabel.setText("" + tagTotalPluses);
	}
	
	
	public void doSetTag(TagObj tagObj, AlbumObj albumObj, User user) {

		//Log.debug("ClientFactory.getUser().getUid())!!" + ClientFactory.getUser().getUid() );

		



		RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

		// Set up the callback object.
		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) {

				if (caught instanceof RPCServiceExeption) {
					Log.debug("exeption!!" + ((RPCServiceExeption)caught).getErrorCode());
					Notifications notif = new Notifications(((RPCServiceExeption)caught).getErrorCode(), true, true);
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
				tagsSetGroup.setGroupVoted();
				setPlusVote();
				
				

			}
		};



		communicatorSvc.doSetTag(tagObj, albumObj, user, callback); 
	}



}
