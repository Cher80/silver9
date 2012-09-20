package my.client.blocks;

import my.client.common.ClientFactory;
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
import com.google.gwt.user.client.ui.ScrollPanel;

public class BestUnit extends Composite {

    private FlowPanel panel = new FlowPanel();

    private Button gotoAlbum = new Button("gotoAlbum");
    private AlbumObj albumObj;
    
     //AdminsStuffBlock
    
    public BestUnit(AlbumObj albumObjj) {
    	this.albumObj = albumObjj;
    	
    	

    	panel.add(gotoAlbum);

    	gotoAlbum.setText("Best Album " + albumObj.getAlbname());
    	
    	gotoAlbum.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//History.back();
				String params = "albid=" + albumObj.getAlbid() + "&coverid=" + albumObj.getCoverImgObjID();
				ClientFactory.getPlaceController().goTo(new ModelPlace(params,false, null));

			}
		});
    			

		
	
		initWidget(panel);
	}
}
