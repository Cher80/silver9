package my.client.blocks.design;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.HTML;

import my.client.common.ClientFactory;
import my.client.common.IconButt;
import my.client.events.PageChangedEvent;
import my.client.events.PageChangedEventHandler;
import my.client.helpers.HavePlace;

public class BackButt extends IconButt implements PageChangedEventHandler {
	private int stackSize = 1;
	
	public BackButt() {
		super();
		setState(0);
		ClientFactory.getEventBus().addHandler(PageChangedEvent.TYPE, this);
		//panel.addStyleName("ButtonPanel");
		//this.addStyleName("BackButtonIcon");
    	icon.addStyleName("BackButtonIcon");
    	content.addStyleName("BackButtonContent");
    	//content.addStyleName("ButtonContent");
    	//text.addStyleName("BackButtonText");
    	//clearfloat.addStyleName("ClearFloat");
		
		//text.add(new HTML("Allo na"));
		//super.panel.addClickHandler(handler)
		super.panel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//History.back();
				//String params = "albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
				//ClientFactory.getPlaceController().goTo(new AlbumsPlace(""));

				Activity curActivity = ClientFactory.getHistoryKeeper().getPrevious();
				Place oldPlace = ((HavePlace) curActivity).getPlace();
				ClientFactory.getPlaceController().goTo(oldPlace);
			}
		});
	}

	@Override
	public void onPageChanged(PageChangedEvent event) {
		// TODO Auto-generated method stub
		//System.out.println("stack size " + event.getStackSize());
		this.stackSize = event.getStackSize();
		
		
		if (stackSize>1) {
		//	
			setState(1);
		}
		else {
			
			setState(0);
		}
	}
	
	public  void setState(int state) {
		if (state == 0) {
			this.getElement().getStyle().setProperty("opacity", "0.3");
			this.getElement().getStyle().setProperty("cursor", "default");
		}
		else {
			this.getElement().getStyle().setProperty("opacity", "1.0");
			this.getElement().getStyle().setProperty("cursor", "pointer");
		}
	}
}
