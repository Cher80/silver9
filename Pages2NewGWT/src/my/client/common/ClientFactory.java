package my.client.common;

import my.shared.CookieObj;
import my.shared.User;

import com.google.gwt.core.client.GWT;
import com.google.gwt.place.shared.PlaceController;
import com.google.gwt.user.client.Window.Navigator;
import com.google.web.bindery.event.shared.SimpleEventBus;


public class ClientFactory {
	
	 //private final SimpleEventBusSingleton eventBus = new SimpleEventBusSingleton();
	 private static SimpleEventBus eventBus = new SimpleEventBus();
	 private static PlaceController placeController = new PlaceController(eventBus);
	 private static HistoryKeeper myHistoryKeeper = new HistoryKeeper();
	 private static AppPlaceHistoryMapper historyMapper= GWT.create(AppPlaceHistoryMapper.class);
	 private static User user = null;
	 private static CookieObj cookieObj;
	 private static boolean ifIOS;
	 //private final GoodbyeView goodbyeView = new GoodbyeViewImpl();
	 static {
		 if (Navigator.getPlatform().toLowerCase().contains("iphone") ||
					Navigator.getPlatform().toLowerCase().contains("ipad") ||
					Navigator.getPlatform().toLowerCase().contains("android") ||
					Navigator.getPlatform().toLowerCase().contains("ios")) {

			 setIfIOS(true);
			} else {
				 setIfIOS(false);
			}
	 }

	public static SimpleEventBus getEventBus() {
		// TODO Auto-generated method stub
		return eventBus;
	}



	
	public static PlaceController getPlaceController() {
		return placeController;
	}

	public static HistoryKeeper getHistoryKeeper() {
		// TODO Auto-generated method stub
		return myHistoryKeeper;
	}

	public static AppPlaceHistoryMapper getHistoryMapper() {
		return historyMapper;
	}




	public static User getUser() {
		return user;
	}




	public static void setUser(User user) {
		ClientFactory.user = user;
	}




	public static CookieObj getCookieObj() {
		return cookieObj;
	}




	public static void setCookieObj(CookieObj cookieObj) {
		ClientFactory.cookieObj = cookieObj;
	}




	public static boolean isIfIOS() {
		return ifIOS;
	}




	public static void setIfIOS(boolean ifIOS) {
		ClientFactory.ifIOS = ifIOS;
	}
}
