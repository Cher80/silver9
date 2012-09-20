package my.client.blocks;

import my.client.blocks.design.AdminButt;
import my.client.blocks.design.BackButt;
import my.client.blocks.design.LogoBlock;
import my.client.common.IconButt;
import my.client.common.ClientFactory;
import my.client.forum.ForumViewInterface.Presenter;
import my.client.helpers.HavePlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.RegisterPopup;
import my.shared.AlbumsObj;
import my.shared.CookieObj;
import my.shared.Settings;
import my.shared.StatObj;
import my.shared.TopBlockObj;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.ScrollPanel;

public class TopMenu extends Composite {

    private FlowPanel panel = new FlowPanel();

    //private Button backButt = new Button("BackButt");
    
    private UserArea userArea;
    private StatBlock statBlock;
  
    private LogoBlock logoBlock= new LogoBlock();
    private BackButt backBlock;
    private FlowPanel clearFloat = new FlowPanel();
    //AdminsStuffBlock
    
    public TopMenu() {
    	 
    	getTopBlockObj();
    	
    	panel.addStyleName("TopMenu");
    	clearFloat.addStyleName("clearBothPanel");
    	backBlock = new BackButt();
    	//panel.add(backButt);
    	panel.add(backBlock);
		panel.add(logoBlock);
    	
    	
        
    			

		
		//HandlerRegistration addScrollHandler = ScrollPanel.addScrollHandler(null);
		
		initWidget(panel);
	}
    
    
    
    public void getTopBlockObj() {
    	
    	CookieObj cookieObj = ClientFactory.getCookieObj();
    	
		RPCServiceAsync communicatorSvc = GWT.create(RPCService.class);

		// Set up the callback object.
		AsyncCallback callback = new AsyncCallback() {

			public void onFailure(Throwable caught) { 

				if (caught instanceof RPCServiceExeption) {
					Log.debug("exeption!!" + ((RPCServiceExeption)caught).getErrorCode());
				}
			}

			@Override
			public void onSuccess(Object result) {
				Log.debug("TopBlock onSuccess ");
				TopBlockObj topBlockObj = (TopBlockObj)result;
				User user = ((TopBlockObj)result).getUser();
				StatObj statObj = ((TopBlockObj)result).getStatObj();
				userArea = new UserArea(user);
				statBlock = new StatBlock(statObj);
				
				
				
				panel.add(statBlock);
				//panel.add(backButt);
				
				panel.add(userArea);
				
				panel.add(clearFloat);
				//populateAlbumsView(albumsObj,forceClearOnFinish);
			}
		};

		communicatorSvc.getTopBlock(cookieObj, callback);
	}
}
