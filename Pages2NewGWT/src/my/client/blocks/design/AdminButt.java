package my.client.blocks.design;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.HTML;

import my.client.common.ClientFactory;
import my.client.common.IconButt;
import my.client.helpers.HavePlace;
import my.client.windows.AdminsPopup;
import my.client.windows.LoginPopup;

public class AdminButt extends IconButt {
	
	
	public AdminButt() {
		super();
		
		//panel.addStyleName("ButtonPanel");
		//this.addStyleName("BackButtonIcon");
    	icon.addStyleName("AdminButtonIcon");
    	content.addStyleName("AdminButtonContent");
    	text.addStyleName("AdminButtonText");
    	text.addStyleName("text11_White");
    	//content.addStyleName("ButtonContent");
    	//text.addStyleName("BackButtonText");
    	//clearfloat.addStyleName("ClearFloat");
		
		text.add(new HTML("Admin"));
		//super.panel.addClickHandler(handler)
		super.panel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//History.back();
				//String params = "albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
				//ClientFactory.getPlaceController().goTo(new AlbumsPlace(""));
				AdminsPopup adminsPopup = new AdminsPopup();
				adminsPopup.center(); 
				adminsPopup.show();
				//Activity curActivity = ClientFactory.getHistoryKeeper().getPrevious();
				//Place oldPlace = ((HavePlace) curActivity).getPlace();
				//ClientFactory.getPlaceController().goTo(oldPlace);
			}
		});
	}
}
