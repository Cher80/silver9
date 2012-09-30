package my.client.blocks.design;

import my.client.albumspage.AlbumsPlace;
import my.client.common.ClientFactory;
import my.client.events.PageTitleEvent;
import my.client.events.ReloadAlbumsEvent;
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
import com.google.gwt.user.client.ui.ScrollPanel;

public class LogoBlock extends Composite {

    private FocusPanel panel = new FocusPanel();

    //private Button gotoAlbum = new Button("gotoAlbum");
    //private AlbumObj albumObj;
    
     //AdminsStuffBlock
    
    public LogoBlock() {
    	//this.albumObj = albumObjj;
    	
    	
    	panel.addStyleName("logo_panel");
    	panel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				PageTitleEvent pageTitleEvent = new PageTitleEvent("");
				ClientFactory.getEventBus().fireEvent(pageTitleEvent);
				//History.back();
				//String params = "albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
				if (ClientFactory.getPlaceController().getWhere() instanceof AlbumsPlace ) {
					ReloadAlbumsEvent eventReload = new ReloadAlbumsEvent(null,1);
					ClientFactory.getEventBus().fireEvent(eventReload);
				}
				else {
					ClientFactory.getHistoryKeeper().doEmptyStack();
					ClientFactory.getPlaceController().goTo(new AlbumsPlace(""));
					
				}
			
				
			}
		});

    	
    			

		
	
		initWidget(panel);
	}
}
