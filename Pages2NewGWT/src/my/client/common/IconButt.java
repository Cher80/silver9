package my.client.common;

import my.client.albumspage.AlbumsPlace;
import my.client.forum.ForumViewInterface.Presenter;
import my.client.helpers.HavePlace;
import my.client.modelpage.ModelPlace;
import my.client.windows.RegisterPopup;
import my.shared.ActivityObj;
import my.shared.AlbumObj;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.ScrollPanel;

public class IconButt extends Composite {

    public FocusPanel panel = new FocusPanel();
    public FlowPanel icon = new FlowPanel();
   // private FlowPanel backGround = new FlowPanel();
    public FlowPanel text = new FlowPanel();
    public FlowPanel content = new FlowPanel();
    public FlowPanel clearfloat = new FlowPanel();
    //private Button gotoAlbum = new Button("gotoAlbum");
    //private AlbumObj albumObj;
    
     //AdminsStuffBlock

    public void addStyles() {
    	panel.addStyleName("ButtonPanel");
    	icon.addStyleName("ButtonIcon");
    	content.addStyleName("ButtonContent");
    	text.addStyleName("ButtonText");
    	clearfloat.addStyleName("ClearFloat");
    }
    
    
    public IconButt() {
    	//this.albumObj = albumObjj;
    	//addStyles();
    	
    	panel.addStyleName("ButtonPanel");
    	icon.addStyleName("ButtonIcon");
    	content.addStyleName("ButtonContent");
    	text.addStyleName("ButtonText");
    	clearfloat.addStyleName("ClearFloat");
    	
    	panel.add(content);
    	//content.add(backGround);
    	content.add(icon);
    	content.add(text);
    	content.add(clearfloat);
    	
    	//text.add(new HTML(""));
    	
    	
    	//backGround.addStyleName("ButtonBackground");
    	//backGround.addStyleName("BackBlock");
    	/*
    	panel.add(backGround);
    	
    	icon.addStyleName("BackBlockIcon");
    	panel.add(icon);
    	
    	panel.addStyleName("BackBlock");*/
    	/*
    	panel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//History.back();
				//String params = "albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
				//ClientFactory.getPlaceController().goTo(new AlbumsPlace(""));

				Activity curActivity = ClientFactory.getHistoryKeeper().getPrevious();
				Place oldPlace = ((HavePlace) curActivity).getPlace();
				ClientFactory.getPlaceController().goTo(oldPlace);
			}
		});
		*/

    	
    			

		
	
		initWidget(panel);
	}
    
    
    
    public void setText(String textToSet) {
    	text.add(new HTML(textToSet));
    	text.getElement().setInnerText(textToSet);
    } 
}
