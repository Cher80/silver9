package my.client;
 
//import my.client.MyComposite;
import my.client.albumspage.AlbumsPlace;
import my.client.blocks.TopMenu;
import my.client.common.AppActivityMapper;
import my.client.common.AppPlaceHistoryMapper;
import my.client.common.ClientFactory;
import my.client.common.MyActivityManager;
import my.client.common.MyFlowPanel;
import my.client.forum.ForumPlace;
import my.client.helpers.HavePlace;
import my.shared.CookieObj;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.activity.shared.ActivityMapper;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.place.shared.PlaceHistoryHandler;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.SimplePanel;
import com.google.web.bindery.event.shared.EventBus;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class ExtraNewGWT implements EntryPoint {
	
	//private CookieObj cookieObj = (new CookieObj()).generateFromString(cookie);
	private Place defaultPlace = new AlbumsPlace(Integer.toString(0));

    private SimplePanel appWidget = new SimplePanel();
    private SimplePanel appWidgetOld = new SimplePanel();
    private FlowPanel menuWidgetPanel = new FlowPanel();
    private ClientFactory clientFactory;
    //private MySimpleLayoutPanel appWidNewNew = new MySimpleLayoutPanel();
    private MyFlowPanel appWidNewNew = new MyFlowPanel();
    private FlowPanel container = new FlowPanel();
    private FlowPanel clearFlow = new FlowPanel();
    
    private TopMenu topMenu;

	/**
	 * This is the entry point method.
	 */
	public void onModuleLoad() {
		String cookie = Cookies.getCookie("silver9session");
		CookieObj cookieObj = new CookieObj();
		User user = new User();
		if (cookie!=null) {
			
			Log.debug("ExtraNewGWT onModuleLoad cookie = " + cookie);
			cookieObj.generateFromString(cookie);
			//Temp user populate, set role
			user.setUserRole(cookieObj.getUserRole());
		}
		else {
			cookieObj = null;
			//Temp user populate, set anonim
			user.setUserRole(0);
		}
		ClientFactory.setCookieObj(cookieObj);
		ClientFactory.setUser(user);
		
		
		topMenu = new TopMenu();
		menuWidgetPanel.addStyleName("topMenuWrap");
		menuWidgetPanel.addStyleName("back_pattern");
		RootPanel.get().add(menuWidgetPanel);
	
		
		menuWidgetPanel.add(topMenu);
		
		//myEventBus.addHandler(ComposedEvent.TYPE, new MyCompositeEventHandler2());
		//SimpleEventBusSingleton.getInstance().addHandler(ComposedEvent.TYPE, this);
		
		
		
		
		container.setStyleName("mainContainer");
		//Window.getClientWidth();
		//container.getElement().getStyle().setProperty("width", "left");
		RootPanel.get().add(container);
		//layoutContainer.add(container);
		
		container.add(appWidNewNew);
		
		FlowPanel clearBothPanel = new FlowPanel();
		clearBothPanel.setStyleName("clearBothPanel");
		container.add(clearBothPanel);
		
		//RootPanel.get().add(appWidget);
		//RootPanel.get().add(menuWidget);
		//RootPanel.get().add(appWidNew);
		
		
		//Button myButt1 = new Button("Goback!");
		//container.add(myButt1);
		//RootPanel.get().add(myButt1);

		//menuWidget.add(myTopmenuView);
		
		this.clientFactory = GWT.create(my.client.common.ClientFactory.class);
        EventBus eventBus = clientFactory.getEventBus();
        PlaceController placeController = clientFactory.getPlaceController();

        // Start ActivityManager for the main widget with our ActivityMapper
        ActivityMapper activityMapper = new AppActivityMapper(clientFactory);
        MyActivityManager activityManager = new MyActivityManager(activityMapper);
        activityManager.setDisplay(appWidNewNew);

        // Start PlaceHistoryHandler with our PlaceHistoryMapper
        AppPlaceHistoryMapper historyMapper = clientFactory.getHistoryMapper();
        PlaceHistoryHandler historyHandler = new PlaceHistoryHandler(historyMapper);
        //System.out.println("goto defaultPlace");
        historyHandler.register(placeController, eventBus, defaultPlace);

        
        // Goes to the place represented on URL else default place
        historyHandler.handleCurrentHistory();


		
		
	}
}
