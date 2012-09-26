package my.client.blocks;

import my.client.common.ClientFactory;
import my.client.forum.ForumViewInterface.Presenter;
import my.client.helpers.HavePlace;
import my.client.modelpage.ModelPlace;
import my.client.windows.RegisterPopup;
import my.shared.ActivityObj;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class ActivityUnit extends Composite {

    private FlowPanel panel = new FlowPanel();

    private Button gotoAlbum = new Button("gotoAlbum");
    private ActivityObj activityObj;
    
     //AdminsStuffBlock
    
    public ActivityUnit(ActivityObj activityObjj) {
    	this.activityObj = activityObjj;
    	
    	

    	panel.add(gotoAlbum);

    	//gotoAlbum.setText("Activ " + activityObj.getActivityType() + " Tag " + activityObj.getTagType() + " Comment " + activityObj.getCommentText() + " album " + activityObj.getAlbname());
    	gotoAlbum.setText("Activ " + activityObj.getActivityType());
    	
    	gotoAlbum.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//History.back();
				String params = "albid=" + activityObj.getAlbid() + "&coverid=" + activityObj.getCoverImgObjID();
				ClientFactory.getPlaceController().goTo(new ModelPlace(params,false, null));

			}
		});
    			

		
		//HandlerRegistration addScrollHandler = ScrollPanel.addScrollHandler(null);
		
		initWidget(panel);
	}
}
