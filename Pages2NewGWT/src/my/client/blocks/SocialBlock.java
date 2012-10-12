package my.client.blocks;

import javax.servlet.http.Cookie;

import my.client.blocks.design.AdminButt;
import my.client.common.ClientFactory;
import my.client.common.GoogleAnalytics;
import my.client.common.IconButt;
import my.client.helpers.HavePlace;
import my.client.rpcs.RPCService;
import my.client.rpcs.RPCServiceAsync;
import my.client.rpcs.RPCServiceExeption;
import my.client.windows.LoginPopup;
import my.client.windows.Notifications;
import my.client.windows.RegisterPopup;
import my.client.windows.UserHasLoggedEvent;
import my.client.windows.UserHasLoggedEventHandler;
import my.shared.CookieObj;
import my.shared.User;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.activity.shared.Activity;
import com.google.gwt.core.shared.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.place.shared.Place;
import com.google.gwt.user.client.Cookies;
import com.google.gwt.user.client.Timer;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Label;

public class SocialBlock extends Composite  {


	private FlowPanel panel = new FlowPanel();
	private IconButt feedBackButt;
	private IconButt fbGroupButt;


	//private CookieObj cookieObj;

	public SocialBlock() {
		super();
		
		panel.addStyleName("SocialBlock");
		//nick = new Label("Anonymous");

		feedBackButt = new IconButt(); 
		feedBackButt.icon.addStyleName("feedBackIcon");
		feedBackButt.content.addStyleName("feedBackContent");
		feedBackButt.text.addStyleName("feedBackText");
		feedBackButt.text.addStyleName("text11_White");
		feedBackButt.setText("Feedback");
		
		
		fbGroupButt = new IconButt(); 
		fbGroupButt.icon.addStyleName("fbGroupIcon");
		fbGroupButt.content.addStyleName("fbGroupContent");
		fbGroupButt.text.addStyleName("fbGroupButtonText");
		fbGroupButt.text.addStyleName("text11_White");
		fbGroupButt.setText("Join us");
		
		
		fbGroupButt.panel.addClickHandler(new ClickHandler() {
			public void onClick(ClickEvent event) {
				//Window.open("http://stackoverflow.com", "_blank", null);
				//if (curImgObj.getImgIshasorig()) {		
				GoogleAnalytics.trackEvent("Pinbelle", "PhotoLayer_ShowOriginal_Clicked", "default");
				Window.open("http://www.facebook.com/PinBelle", "_blank", "");
//				Log.debug("showOrigpxButt.addClickHandler = " + Window.Location.getHost());
			}
		});
		
		
		feedBackButt.panel.getElement().setAttribute("onmouseover", "UE.Popin.preload();");
		feedBackButt.panel.getElement().setAttribute("onclick", "UE.Popin.show(); return false;");
		//http://www.facebook.com/PinBelle
		
		
		panel.add(fbGroupButt);
		panel.add(feedBackButt);

		//HandlerRegistration addScrollHandler = ScrollPanel.addScrollHandler(null);

		initWidget(panel);
	}


	


	

}
