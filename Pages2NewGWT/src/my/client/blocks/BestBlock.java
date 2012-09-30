package my.client.blocks;

import my.client.common.ClientFactory;
import my.client.forum.ForumViewInterface.Presenter;
import my.client.helpers.HavePlace;
import my.client.windows.RegisterPopup;
import my.shared.ActivitiesObj;
import my.shared.AlbumsObj;

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

public class BestBlock extends Composite {

    private FlowPanel panel = new FlowPanel();
    private Label bestHeader = new Label(); 
    
//    private UserArea userArea = new UserArea();
    private AlbumsObj albumsObj = new AlbumsObj();
    //AdminsStuffBlock
    
    public BestBlock(AlbumsObj albumsObjj) {
    	this.albumsObj = albumsObjj;
    	
    	
    	bestHeader.setText("Latest best");
    	bestHeader.addStyleName("bestHeader");
    	bestHeader.addStyleName("text12_white_bold");
    	// HTML html = new HTML("<b>Bset</b>");
    	 panel.add(bestHeader);

    	for (int i=0;i<albumsObj.getAlbums().size(); i++) {
    		BestUnit bestUnit = new BestUnit(albumsObj.getAlbums().get(i), i);
    		panel.add(bestUnit);
    	}
    	
		initWidget(panel);
	}
}
