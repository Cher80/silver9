package my.client.blocks;

import my.client.common.ClientFactory;
import my.client.forum.ForumViewInterface.Presenter;
import my.client.helpers.HavePlace;
import my.client.windows.RegisterPopup;

import com.google.gwt.activity.shared.Activity;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class TopMenu extends Composite {

    private FlowPanel panel = new FlowPanel();

    private Button backButt = new Button("BackButt");
    
    private UserArea userArea = new UserArea();
    
    public TopMenu() {
    	
    	panel.addStyleName("TopMenu");
    	panel.add(backButt);
    	
    	panel.add(userArea);
    	
        backButt.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//History.back();
				Activity curActivity = ClientFactory.getHistoryKeeper().getPrevious();
				Place oldPlace = ((HavePlace) curActivity).getPlace();
				ClientFactory.getPlaceController().goTo(oldPlace);
			}
		});
    			

		
		//HandlerRegistration addScrollHandler = ScrollPanel.addScrollHandler(null);
		
		initWidget(panel);
	}
}
