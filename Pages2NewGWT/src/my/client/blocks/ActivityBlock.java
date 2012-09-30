package my.client.blocks;

import my.client.common.ClientFactory;
import my.client.forum.ForumViewInterface.Presenter;
import my.client.helpers.HavePlace;
import my.client.windows.RegisterPopup;
import my.shared.ActivitiesObj;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.ScrollPanel;

public class ActivityBlock extends Composite {

    private FlowPanel panel = new FlowPanel();
    private Label activHeader = new Label(); 
    
    //private UserArea userArea = new UserArea();
    private ActivitiesObj activitiesObj = new ActivitiesObj();
    //AdminsStuffBlock
    
    public ActivityBlock(ActivitiesObj activitiesObjj) {
    	this.activitiesObj = activitiesObjj;
    	
    	activHeader.setText("Latest activity");
    	activHeader.addStyleName("activHeader");
    	activHeader.addStyleName("text12_white_bold");
    	// HTML html = new HTML("<b>Bset</b>");
    	 panel.add(activHeader);
    	
    	// HTML html = new HTML("<b>Activ</b>");
    	// panel.add(html);

    	for (int i=0;i<activitiesObj.getActivities().size(); i++) {
    		ActivityUnit activityUnit = new ActivityUnit(activitiesObj.getActivities().get(i));
    		panel.add(activityUnit);
    	}
    	
		initWidget(panel);
	}
}
