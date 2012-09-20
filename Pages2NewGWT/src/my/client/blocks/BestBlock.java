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
import com.google.gwt.user.client.ui.ScrollPanel;

public class BestBlock extends Composite {

    private FlowPanel panel = new FlowPanel();
    
    
//    private UserArea userArea = new UserArea();
    private AlbumsObj albumsObj = new AlbumsObj();
    //AdminsStuffBlock
    
    public BestBlock(AlbumsObj albumsObjj) {
    	this.albumsObj = albumsObjj;
    	
    	 HTML html = new HTML("<h1>Bset block</h1>");
    	 panel.add(html);

    	for (int i=0;i<albumsObj.getAlbums().size(); i++) {
    		BestUnit bestUnit = new BestUnit(albumsObj.getAlbums().get(i));
    		panel.add(bestUnit);
    	}
    	
		initWidget(panel);
	}
}
